package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.AnimeSaveDTO;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.entity.Anime;
import com.example.acgdata.mapper.AnimeMapper;
import com.example.acgdata.mapper.row.AnimeListRow;
import com.example.acgdata.mapper.row.RatingListRow;
import com.example.acgdata.service.AnimeService;
import com.example.acgdata.service.CommentService;
import com.example.acgdata.service.EditStatisticService;
import com.example.acgdata.service.TagService;
import com.example.acgdata.vo.AnimeDetailVO;
import com.example.acgdata.vo.AnimeItemVO;
import com.example.acgdata.vo.CharacterVO;
import com.example.acgdata.vo.CommentVO;
import com.example.acgdata.vo.RatingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * 番剧服务实现。
 *
 * <p>分层要点：
 * <ol>
 *     <li>Controller 只做转发，所有拼装逻辑都在这里；</li>
 *     <li>列表接口用「一次 JOIN 查询取回番剧 + 标签」，在内存里按 anime_id 分组拼装，不存在 N+1；</li>
 *     <li>标签读写一律走共享的 {@link TagService}，评论走 {@link CommentService}
 *         （点赞由 LikeService 维护 anime.like_count，本模块只读不写），
 *         本模块不直接操作 tag / anime_tag / comment / like_record 表；</li>
 *     <li>新增 / 修改成功后调用 {@link EditStatisticService#increment(String)} 记一次编辑。</li>
 * </ol>
 */
@Service
@RequiredArgsConstructor
public class AnimeServiceImpl extends ServiceImpl<AnimeMapper, Anime> implements AnimeService {

    /** 评论 / 点赞的目标类型（对应 comment.target_type 与 like_record.target_type） */
    private static final String TARGET_TYPE = "anime";

    /** 编辑统计的类型 */
    private static final String EDIT_TYPE = "anime";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 番剧详情里的声优 / 角色快照。
     * 旧接口把这批数据硬编码在 Controller 的番剧对象里，本次原样迁移到这里；
     * 等 character 模块建表后，这里改成按 anime_id 关联查询即可（方法签名不变）。
     */
    private static final Map<Long, List<CharacterEntry>> CHARACTER_CATALOG = buildCharacterCatalog();

    private final TagService tagService;
    private final CommentService commentService;
    private final EditStatisticService editStatisticService;

    // ---------------------------------------------------------------- 查询

    @Override
    public List<AnimeItemVO> listAll() {
        // 先取 id 列表（保证排序稳定），再一次性联表取回番剧 + 标签
        List<Long> ids = list(Wrappers.<Anime>lambdaQuery()
                .select(Anime::getId)
                .orderByAsc(Anime::getSort)
                .orderByAsc(Anime::getId))
                .stream()
                .map(Anime::getId)
                .toList();
        return toItems(ids);
    }

    @Override
    public AnimeDetailVO getVO(Long id) {
        AnimeDetailVO vo = baseMapper.selectDetailById(id);
        if (vo == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        // 固定标记位（旧接口每个对象都带 type: "anime"）
        vo.setType(AnimeItemVO.TYPE);
        vo.setTags(tagService.listNamesByAnimeId(id));
        vo.setViewingRecords(baseMapper.selectRatingsByAnimeId(id));
        vo.setCharacters(charactersOf(id));
        vo.setComments(commentService.listByTarget(TARGET_TYPE, id));
        return vo;
    }

    @Override
    public List<AnimeItemVO> listRandom(int limit) {
        return toItems(baseMapper.selectRandomIds(safeLimit(limit)));
    }

    @Override
    public List<AnimeItemVO> listLatest(int limit) {
        return toItems(baseMapper.selectLatestIds(safeLimit(limit)));
    }

    @Override
    public List<AnimeItemVO> listUpcoming() {
        return toItems(baseMapper.selectUpcomingIds());
    }

    @Override
    public List<AnimeItemVO> listRecentUpdated(int limit) {
        return toItems(baseMapper.selectRecentUpdatedIds(safeLimit(limit)));
    }

    @Override
    public Map<String, Object> randomTag() {
        List<String> tagNames = baseMapper.selectAllTagNames();
        if (tagNames.isEmpty()) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND, "还没有任何番剧标签");
        }
        String tag = tagNames.get(ThreadLocalRandom.current().nextInt(tagNames.size()));
        List<AnimeItemVO> anime = toItems(baseMapper.selectIdsByTagName(tag));
        // 用 LinkedHashMap 保证 JSON 里 key 的顺序是 tag -> anime（与旧接口一致）
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("tag", tag);
        result.put("anime", anime);
        return result;
    }

    @Override
    public List<RatingVO> randomRatings(int limit) {
        List<Long> ids = new ArrayList<>(baseMapper.selectAllRatingIds());
        if (ids.isEmpty()) {
            return List.of();
        }
        Collections.shuffle(ids);
        List<Long> picked = ids.size() > limit ? ids.subList(0, limit) : ids;
        // selectRatingRowsByIds 返回的是 RatingVO 的子类，泛型上可以直接当 List<RatingVO> 用
        List<RatingVO> ratings = new ArrayList<>(baseMapper.selectRatingRowsByIds(picked));
        Collections.shuffle(ratings);
        return ratings;
    }

    @Override
    public List<AnimeItemVO> search(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return List.of();
        }
        List<Long> ids = baseMapper.selectIdsByKeyword(keyword.trim());
        if (ids.isEmpty()) {
            return List.of();
        }
        // selectIdsByKeyword 里带着标签的 JOIN，可能返回重复 id，这里去重后保持 id 升序
        List<Long> distinctIds = new ArrayList<>(new LinkedHashSet<>(ids));
        List<Anime> animeList = listByIds(distinctIds);
        Map<Long, Anime> byId = new HashMap<>(animeList.size());
        for (Anime anime : animeList) {
            byId.put(anime.getId(), anime);
        }
        List<Anime> ordered = new ArrayList<>(distinctIds.size());
        for (Long id : distinctIds) {
            Anime anime = byId.get(id);
            if (anime != null) {
                ordered.add(anime);
            }
        }
        // 搜索接口按旧契约**不返回标签**（避免多一次联表查询）
        return ordered.stream().map(AnimeItemVO::of).toList();
    }

    // ---------------------------------------------------------------- 写入

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AnimeDetailVO saveAnime(AnimeSaveDTO dto) {
        Anime anime = new Anime();
        anime.setTitle(dto.getTitle().trim());
        apply(anime, dto);
        anime.setLikeCount(0);
        save(anime);

        tagService.bindAnimeTags(anime.getId(), dto.getTags());
        editStatisticService.increment(EDIT_TYPE);
        return getVO(anime.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AnimeDetailVO updateAnime(Long id, AnimeSaveDTO dto) {
        Anime existing = getExisting(id);
        // 在副本上套用请求字段：只有真正有字段变化才算一次编辑（旧实现无条件 +1）
        Anime merged = copyOf(existing);
        boolean changed = apply(merged, dto);

        if (dto.getTags() != null) {
            List<String> current = tagService.listNamesByAnimeId(id);
            if (!sameTags(current, dto.getTags())) {
                tagService.bindAnimeTags(id, dto.getTags());
                changed = true;
            }
        }
        if (!changed) {
            return getVO(id);
        }

        // updateById 会带上 update_time（自动填充），因此 lastEdit 一定刷新
        updateById(merged);
        editStatisticService.increment(EDIT_TYPE);
        return getVO(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAnime(Long id) {
        getExisting(id);
        // 逻辑删除（@TableLogic）
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommentVO addComment(Long id, CommentSaveDTO dto) {
        getExisting(id);
        // 评论人从 JWT 中取，具体落库与用户评论计数由共享 CommentService 负责
        return commentService.add(TARGET_TYPE, id, dto);
    }

    // ---------------------------------------------------------------- 内部方法

    private Anime getExisting(Long id) {
        Anime anime = getById(id);
        if (anime == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        return anime;
    }

    /**
     * 一次查询取回「番剧 + 标签」并按 anime_id 分组拼装列表项，避免 N+1。
     */
    private List<AnimeItemVO> toItems(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        List<AnimeListRow> rows = baseMapper.selectRowsWithTags(ids);
        Map<Long, AnimeItemVO> grouped = new LinkedHashMap<>();
        for (AnimeListRow row : rows) {
            AnimeItemVO item = grouped.get(row.getId());
            if (item == null) {
                // row 继承了 AnimeItemVO，这里造一个干净的对象，避免把 tagName 也序列化出去
                item = toItem(row);
                grouped.put(item.getId(), item);
            }
            String tagName = row.getTagName();
            if (StringUtils.hasText(tagName) && !item.getTags().contains(tagName)) {
                item.getTags().add(tagName);
            }
        }
        // 保持传入 id 的顺序（SQL 里已按 id 升序，这里再兜底一次）
        List<AnimeItemVO> result = new ArrayList<>(ids.size());
        for (Long id : ids) {
            AnimeItemVO item = grouped.get(id);
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * 联表投影行 -> 干净的列表项 VO（丢掉 tagName，补上 type 标记与空标签列表）。
     * 泛型让 selectDetailById / selectRowsWithTags 两种投影都能复用。
     */
    private <T extends AnimeItemVO> AnimeItemVO toItem(T source) {
        AnimeItemVO item = new AnimeItemVO();
        item.setId(source.getId());
        item.setTitle(source.getTitle());
        item.setChineseTitle(source.getChineseTitle());
        item.setImage(source.getImage());
        item.setDescription(source.getDescription());
        item.setEpisodes(source.getEpisodes());
        item.setStartDate(source.getStartDate());
        item.setLastEdit(source.getLastEdit());
        item.setLikeCount(source.getLikeCount());
        item.setType(AnimeItemVO.TYPE);
        item.setTags(new ArrayList<>());
        return item;
    }

    /**
     * 把 DTO 里**非 null** 的字段写到实体上（与旧接口的 putAll 语义一致），
     * 返回是否发生了实际变化。
     */
    private boolean apply(Anime anime, AnimeSaveDTO dto) {
        boolean changed = false;

        if (dto.getTitle() != null) {
            changed |= set(anime.getTitle(), dto.getTitle().trim(), anime::setTitle);
        }
        if (dto.getChineseTitle() != null) {
            changed |= set(anime.getChineseTitle(), dto.getChineseTitle(), anime::setChineseTitle);
        }
        if (dto.getImage() != null) {
            changed |= set(anime.getImage(), dto.getImage(), anime::setImage);
        }
        if (dto.getDescription() != null) {
            changed |= set(anime.getDescription(), dto.getDescription(), anime::setDescription);
        }
        if (dto.getIntroduction() != null) {
            changed |= set(anime.getIntroduction(), dto.getIntroduction(), anime::setIntroduction);
        }
        if (dto.getStudio() != null) {
            changed |= set(anime.getStudio(), dto.getStudio(), anime::setStudio);
        }
        if (dto.getEpisodes() != null) {
            changed |= set(anime.getEpisodes(), dto.getEpisodes(), anime::setEpisodes);
        }
        if (dto.getStartDate() != null) {
            changed |= set(anime.getStartDate(), parseDate(dto.getStartDate()), anime::setStartDate);
        }
        if (dto.getBroadcastDay() != null) {
            changed |= set(anime.getBroadcastDay(), dto.getBroadcastDay(), anime::setBroadcastDay);
        }
        if (dto.getOriginalWork() != null) {
            changed |= set(anime.getOriginalWork(), dto.getOriginalWork(), anime::setOriginalWork);
        }
        if (dto.getDirector() != null) {
            changed |= set(anime.getDirector(), dto.getDirector(), anime::setDirector);
        }
        if (dto.getScript() != null) {
            changed |= set(anime.getScript(), dto.getScript(), anime::setScript);
        }
        if (dto.getStoryboard() != null) {
            changed |= set(anime.getStoryboard(), dto.getStoryboard(), anime::setStoryboard);
        }
        if (dto.getPerformance() != null) {
            changed |= set(anime.getPerformance(), dto.getPerformance(), anime::setPerformance);
        }
        if (dto.getMusic() != null) {
            changed |= set(anime.getMusic(), dto.getMusic(), anime::setMusic);
        }
        if (dto.getCharacterDesign() != null) {
            changed |= set(anime.getCharacterDesign(), dto.getCharacterDesign(), anime::setCharacterDesign);
        }
        if (dto.getSeriesComposition() != null) {
            changed |= set(anime.getSeriesComposition(), dto.getSeriesComposition(), anime::setSeriesComposition);
        }
        if (dto.getArtDirector() != null) {
            changed |= set(anime.getArtDirector(), dto.getArtDirector(), anime::setArtDirector);
        }
        if (dto.getColorDesign() != null) {
            changed |= set(anime.getColorDesign(), dto.getColorDesign(), anime::setColorDesign);
        }
        if (dto.getChiefAnimationDirector() != null) {
            changed |= set(anime.getChiefAnimationDirector(), dto.getChiefAnimationDirector(),
                    anime::setChiefAnimationDirector);
        }
        if (dto.getOfficialWebsite() != null) {
            changed |= set(anime.getOfficialWebsite(), dto.getOfficialWebsite(), anime::setOfficialWebsite);
        }
        return changed;
    }

    /** 单个字段赋值：值不同才写回，并返回是否变化 */
    private <T> boolean set(T current, T incoming, Consumer<T> setter) {
        if (Objects.equals(current, incoming)) {
            return false;
        }
        setter.accept(incoming);
        return true;
    }

    /** 实体浅拷贝（用于「先合并再判断有没有变化」） */
    private Anime copyOf(Anime source) {
        Anime copy = new Anime();
        copy.setId(source.getId());
        copy.setTitle(source.getTitle());
        copy.setChineseTitle(source.getChineseTitle());
        copy.setImage(source.getImage());
        copy.setDescription(source.getDescription());
        copy.setIntroduction(source.getIntroduction());
        copy.setStudio(source.getStudio());
        copy.setEpisodes(source.getEpisodes());
        copy.setStartDate(source.getStartDate());
        copy.setBroadcastDay(source.getBroadcastDay());
        copy.setOriginalWork(source.getOriginalWork());
        copy.setDirector(source.getDirector());
        copy.setScript(source.getScript());
        copy.setStoryboard(source.getStoryboard());
        copy.setPerformance(source.getPerformance());
        copy.setMusic(source.getMusic());
        copy.setCharacterDesign(source.getCharacterDesign());
        copy.setSeriesComposition(source.getSeriesComposition());
        copy.setArtDirector(source.getArtDirector());
        copy.setColorDesign(source.getColorDesign());
        copy.setChiefAnimationDirector(source.getChiefAnimationDirector());
        copy.setOfficialWebsite(source.getOfficialWebsite());
        copy.setLikeCount(source.getLikeCount());
        copy.setSort(source.getSort());
        copy.setCreateTime(source.getCreateTime());
        copy.setUpdateTime(source.getUpdateTime());
        return copy;
    }

    /**
     * 解析 `yyyy-MM-dd`；空串表示「清空日期」，非法格式抛参数异常。
     */
    private LocalDate parseDate(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        try {
            return LocalDate.parse(text.trim(), DATE_FORMATTER);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "开播日期格式应为 yyyy-MM-dd：" + text);
        }
    }

    /** 标签列表比较（忽略顺序与空白项） */
    private boolean sameTags(List<String> current, List<String> incoming) {
        List<String> left = new ArrayList<>();
        for (String name : current) {
            if (StringUtils.hasText(name)) {
                left.add(name.trim());
            }
        }
        List<String> right = new ArrayList<>();
        for (String name : incoming) {
            if (StringUtils.hasText(name)) {
                String trimmed = name.trim();
                if (!right.contains(trimmed)) {
                    right.add(trimmed);
                }
            }
        }
        return left.size() == right.size() && left.containsAll(right);
    }

    private int safeLimit(int limit) {
        if (limit <= 0) {
            return 10;
        }
        // 单页最大 100（与分页插件的上限保持一致）
        return Math.min(limit, 100);
    }

    private List<CharacterVO> charactersOf(Long animeId) {
        List<CharacterEntry> entries = CHARACTER_CATALOG.get(animeId);
        if (entries == null || entries.isEmpty()) {
            return List.of();
        }
        return entries.stream().map(CharacterEntry::toVO).toList();
    }

    /** 角色目录里的一个条目 */
    private record CharacterEntry(long id, String name, String voiceActor, String image, int likes) {

        CharacterVO toVO() {
            CharacterVO vo = new CharacterVO();
            vo.setId(id);
            vo.setName(name);
            vo.setVoiceActor(voiceActor);
            vo.setImage(image);
            vo.setLikes(likes);
            return vo;
        }
    }

    /**
     * 构建角色目录（数据来自旧 Controller 的硬编码角色，原样保留）。
     * id 沿用旧数据；旧数据缺 id 的用 100+ 段占位，likes 缺失的记 0。
     */
    private static Map<Long, List<CharacterEntry>> buildCharacterCatalog() {
        Map<Long, List<CharacterEntry>> catalog = new HashMap<>();
        catalog.put(1L, List.of(
                new CharacterEntry(1L, "空条承太郎", "小野大辅", "https://example.com/jotaro.jpg", 12500),
                new CharacterEntry(2L, "乔瑟夫·乔斯达", "杉田智和", "https://example.com/joseph.jpg", 9800),
                new CharacterEntry(7L, "迪奥·布兰度", "子安武人", "https://example.com/dio.jpg", 8500)));
        catalog.put(2L, List.of(
                new CharacterEntry(3L, "艾伦·耶格尔", "梶裕贵", "https://example.com/eren.jpg", 15600),
                new CharacterEntry(4L, "三笠·阿克曼", "石川由依", "https://example.com/mikasa.jpg", 18900),
                new CharacterEntry(8L, "阿明·阿诺德", "井上麻里奈", "https://example.com/armin.jpg", 7800)));
        catalog.put(3L, List.of(
                new CharacterEntry(5L, "灶门炭治郎", "花江夏树", "https://example.com/tanjiro.jpg", 21000),
                new CharacterEntry(6L, "灶门祢豆子", "鬼头明里", "https://example.com/nezuko.jpg", 25000),
                new CharacterEntry(9L, "我妻善逸", "下野纮", "https://example.com/zenitsu.jpg", 14500)));
        catalog.put(4L, List.of(
                new CharacterEntry(100L, "蒙奇·D·路飞", "田中真弓", "https://example.com/luffy.jpg", 0),
                new CharacterEntry(101L, "罗罗诺亚·索隆", "中井和哉", "https://example.com/zoro.jpg", 0),
                new CharacterEntry(102L, "娜美", "冈村明美", "https://example.com/nami.jpg", 0)));
        catalog.put(5L, List.of(
                new CharacterEntry(103L, "漩涡鸣人", "竹内顺子", "https://example.com/naruto.jpg", 0),
                new CharacterEntry(104L, "宇智波佐助", "杉山纪彰", "https://example.com/sasuke.jpg", 0),
                new CharacterEntry(105L, "春野樱", "中村千绘", "https://example.com/sakura.jpg", 0)));
        catalog.put(6L, List.of(
                new CharacterEntry(106L, "金木研", "花江夏树", "https://example.com/kaneki.jpg", 0),
                new CharacterEntry(107L, "雾岛董香", "雨宫天", "https://example.com/touka.jpg", 0),
                new CharacterEntry(108L, "月山习", "宫野真守", "https://example.com/tsukiyama.jpg", 0)));
        catalog.put(7L, List.of(
                new CharacterEntry(3L, "艾伦·耶格尔", "梶裕贵", "https://example.com/eren.jpg", 15600),
                new CharacterEntry(4L, "三笠·阿克曼", "石川由依", "https://example.com/mikasa.jpg", 18900),
                new CharacterEntry(8L, "阿明·阿诺德", "井上麻里奈", "https://example.com/armin.jpg", 7800)));
        catalog.put(8L, List.of(
                new CharacterEntry(109L, "桐人", "松冈祯丞", "https://example.com/kirito.jpg", 0),
                new CharacterEntry(110L, "亚丝娜", "户松遥", "https://example.com/asuna.jpg", 0),
                new CharacterEntry(111L, "诗乃", "泽城美雪", "https://example.com/sinon.jpg", 0)));
        catalog.put(9L, List.of(
                new CharacterEntry(112L, "虎杖悠仁", "榎木淳弥", "https://example.com/yuji.jpg", 0),
                new CharacterEntry(113L, "伏黑惠", "内田雄马", "https://example.com/megumi.jpg", 0),
                new CharacterEntry(114L, "钉崎野蔷薇", "濑户麻沙美", "https://example.com/nobara.jpg", 0)));
        catalog.put(10L, List.of(
                new CharacterEntry(115L, "薇尔莉特·伊芙加登", "石川由依", "https://example.com/violet.jpg", 0),
                new CharacterEntry(116L, "基尔伯特·布甘比利亚", "浪川大辅", "https://example.com/gilbert.jpg", 0),
                new CharacterEntry(117L, "克劳迪亚·霍金斯", "子安武人", "https://example.com/hodgins.jpg", 0)));
        catalog.put(11L, List.of(
                new CharacterEntry(118L, "马克", "李元韬", "https://example.com/mark.jpg", 0),
                new CharacterEntry(119L, "冉冰", "陶典", "https://example.com/ranbing.jpg", 0),
                new CharacterEntry(120L, "查尔斯", "吴磊", "https://example.com/charles.jpg", 0)));
        catalog.put(12L, List.of(
                new CharacterEntry(121L, "谢怜", "姜广涛", "https://example.com/xielian.jpg", 0),
                new CharacterEntry(122L, "花城", "马正阳", "https://example.com/huacheng.jpg", 0),
                new CharacterEntry(123L, "灵文", "黄莺", "https://example.com/lingwen.jpg", 0)));
        catalog.put(13L, List.of(
                new CharacterEntry(124L, "程小时", "杨天翔", "https://example.com/chengxiaoshi.jpg", 0),
                new CharacterEntry(125L, "陆光", "苏尚卿", "https://example.com/luguang.jpg", 0),
                new CharacterEntry(126L, "乔苓", "李诗萌", "https://example.com/qiaoling.jpg", 0)));
        catalog.put(14L, List.of(
                new CharacterEntry(127L, "罗小黑", "山新", "https://example.com/luoheixiao.jpg", 0),
                new CharacterEntry(128L, "无限", "刘明月", "https://example.com/wuxian.jpg", 0),
                new CharacterEntry(129L, "风息", "郝祥海", "https://example.com/fengxi.jpg", 0)));
        catalog.put(15L, List.of(
                new CharacterEntry(130L, "张楚岚", "夏侯落枫", "https://example.com/zhangchulan.jpg", 0),
                new CharacterEntry(131L, "冯宝宝", "小连杀", "https://example.com/fengbaobao.jpg", 0),
                new CharacterEntry(132L, "王也", "宝木中阳", "https://example.com/wangye.jpg", 0)));
        catalog.put(16L, List.of(
                new CharacterEntry(5L, "灶门炭治郎", "花江夏树", "https://example.com/tanjiro.jpg", 21000),
                new CharacterEntry(133L, "富冈义勇", "樱井孝宏", "https://example.com/giyu.jpg", 0),
                new CharacterEntry(134L, "炼狱杏寿郎", "日野聪", "https://example.com/rengoku.jpg", 0)));
        catalog.put(17L, List.of(
                new CharacterEntry(135L, "四宫辉夜", "古贺葵", "https://example.com/kaguya.jpg", 0),
                new CharacterEntry(136L, "白银御行", "古川慎", "https://example.com/miyuki.jpg", 0),
                new CharacterEntry(137L, "藤原千花", "小原好美", "https://example.com/chika.jpg", 0)));
        catalog.put(18L, List.of(
                new CharacterEntry(112L, "虎杖悠仁", "榎木淳弥", "https://example.com/yuji.jpg", 0),
                new CharacterEntry(113L, "伏黑惠", "内田雄马", "https://example.com/megumi.jpg", 0),
                new CharacterEntry(114L, "钉崎野蔷薇", "濑户麻沙美", "https://example.com/nobara.jpg", 0)));
        return catalog;
    }
}
