package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import com.example.acgdata.service.EditStatisticsService;

@RestController
@RequestMapping("/api/anime")
@CrossOrigin(origins = "*")
public class AnimeController {

    private List<Map<String, Object>> animeList = new ArrayList<>();

    public AnimeController() {
        initAnimeData();
    }

    private void initAnimeData() {
        Map<String, Object> jojo = new LinkedHashMap<>();
        jojo.put("id", 1);
        jojo.put("title", "ジョジョの奇妙な冒険");
        jojo.put("chineseTitle", "JOJO的奇妙冒险");
        jojo.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_2.png");
        jojo.put("description", "讲述乔斯达家族与迪奥·布兰度之间跨越 generations 的宿命对决");
        jojo.put("introduction", "《JOJO的奇妙冒险》是日本漫画家荒木飞吕彦创作的漫画作品，讲述了乔斯达家族与迪奥·布兰度之间跨越数代的宿命对决。作品以其独特的画风、精彩的战斗场景和深刻的人物刻画而闻名。");
        jojo.put("studio", "David Production");
        jojo.put("tags", Arrays.asList("战斗", "热血", "超能力", "冒险"));
        jojo.put("episodes", 152);
        jojo.put("startDate", "2012-10-05");
        jojo.put("broadcastDay", "星期四");
        jojo.put("originalWork", "荒木飞吕彦");
        jojo.put("director", "津田尚克");
        jojo.put("script", "小林靖子");
        jojo.put("storyboard", "津田尚克, 铃木健一");
        jojo.put("performance", "津田尚克, 铃木健一");
        jojo.put("music", "菅野祐悟");
        jojo.put("characterDesign", "岸田隆宏");
        jojo.put("seriesComposition", "小林靖子");
        jojo.put("artDirector", "一色美绪");
        jojo.put("colorDesign", "佐藤美由纪");
        jojo.put("chiefAnimationDirector", "岸田隆宏");
        jojo.put("type", "anime");
        
        List<Map<String, Object>> jojoViewingRecords = new ArrayList<>();
        Map<String, Object> jojoRecord1 = new LinkedHashMap<>();
        jojoRecord1.put("id", 1);
        jojoRecord1.put("user", "JOJO粉");
        jojoRecord1.put("userAvatar", "https://example.com/avatar1.jpg");
        jojoRecord1.put("rating", 9.5);
        jojoRecord1.put("totalRating", 7);
        jojoRecord1.put("musicRating", 9);
        jojoRecord1.put("artRating", 8);
        jojoRecord1.put("scriptRating", 9);
        jojoRecord1.put("performanceRating", 9);
        jojoRecord1.put("programRating", 8);
        jojoRecord1.put("voiceRating", 10);
        jojoRecord1.put("comment", "JOJO系列的巅峰之作，战斗场景精彩，人物刻画深刻，替身能力设定新颖！");
        jojoRecord1.put("date", "2026-01-15 14:30:00");
        jojoViewingRecords.add(jojoRecord1);
        
        Map<String, Object> jojoRecord2 = new LinkedHashMap<>();
        jojoRecord2.put("id", 2);
        jojoRecord2.put("user", "动漫迷");
        jojoRecord2.put("userAvatar", "https://example.com/avatar2.jpg");
        jojoRecord2.put("rating", 9.2);
        jojoRecord2.put("totalRating", 7);
        jojoRecord2.put("musicRating", 8);
        jojoRecord2.put("artRating", 9);
        jojoRecord2.put("scriptRating", 8);
        jojoRecord2.put("performanceRating", 9);
        jojoRecord2.put("programRating", 8);
        jojoRecord2.put("voiceRating", 9);
        jojoRecord2.put("comment", "荒木飞吕彦的画风独特，剧情紧凑，每个角色都有鲜明的个性，值得一看！");
        jojoRecord2.put("date", "2026-01-10 19:20:00");
        jojoViewingRecords.add(jojoRecord2);
        jojo.put("viewingRecords", jojoViewingRecords);
        
        List<Map<String, Object>> jojoChars = new ArrayList<>();
        Map<String, Object> jojoChar1 = new LinkedHashMap<>();
        jojoChar1.put("id", 1);
        jojoChar1.put("name", "空条承太郎");
        jojoChar1.put("voiceActor", "小野大辅");
        jojoChar1.put("image", "https://example.com/jotaro.jpg");
        jojoChar1.put("likes", 12500);
        jojoChars.add(jojoChar1);
        Map<String, Object> jojoChar2 = new LinkedHashMap<>();
        jojoChar2.put("id", 2);
        jojoChar2.put("name", "乔瑟夫·乔斯达");
        jojoChar2.put("voiceActor", "杉田智和");
        jojoChar2.put("image", "https://example.com/joseph.jpg");
        jojoChar2.put("likes", 9800);
        jojoChars.add(jojoChar2);
        Map<String, Object> jojoChar3 = new LinkedHashMap<>();
        jojoChar3.put("id", 7);
        jojoChar3.put("name", "迪奥·布兰度");
        jojoChar3.put("voiceActor", "子安武人");
        jojoChar3.put("image", "https://example.com/dio.jpg");
        jojoChar3.put("likes", 8500);
        jojoChars.add(jojoChar3);
        jojo.put("characters", jojoChars);
        
        jojo.put("officialWebsite", "https://jojo-animation.com/");
        
        List<Map<String, Object>> jojoComments = new ArrayList<>();
        Map<String, Object> jojoComment1 = new LinkedHashMap<>();
        jojoComment1.put("id", 1);
        jojoComment1.put("user", "JOJO粉");
        jojoComment1.put("content", "最喜欢承太郎的欧拉欧拉！");
        jojoComment1.put("date", "2026-01-01");
        jojoComments.add(jojoComment1);
        Map<String, Object> jojoComment2 = new LinkedHashMap<>();
        jojoComment2.put("id", 2);
        jojoComment2.put("user", "动漫迷");
        jojoComment2.put("content", "剧情紧凑，打斗场面精彩！");
        jojoComment2.put("date", "2026-01-02");
        jojoComments.add(jojoComment2);
        jojo.put("comments", jojoComments);

        Map<String, Object> aot = new LinkedHashMap<>();
        aot.put("id", 2);
        aot.put("title", "進撃の巨人");
        aot.put("chineseTitle", "进击的巨人");
        aot.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_3.png");
        aot.put("description", "人类与巨人之间的生存之战");
        aot.put("introduction", "《进击的巨人》是谏山创创作的漫画作品，讲述了人类为了生存与巨人之间的战斗。作品以其紧凑的剧情、深刻的世界观和复杂的人物关系而受到广泛关注。");
        aot.put("studio", "WIT Studio, MAPPA");
        aot.put("tags", Arrays.asList("战斗", "热血", "奇幻", "黑暗"));
        aot.put("episodes", 75);
        aot.put("startDate", "2013-04-07");
        aot.put("broadcastDay", "星期日");
        aot.put("originalWork", "谏山创");
        aot.put("director", "荒木哲郎, 林祐一郎");
        aot.put("script", "小林靖子");
        aot.put("storyboard", "荒木哲郎, 肥冢正史");
        aot.put("performance", "荒木哲郎, 肥冢正史");
        aot.put("music", "泽野弘之");
        aot.put("characterDesign", "浅野恭司");
        aot.put("seriesComposition", "小林靖子");
        aot.put("artDirector", "小仓宏昌");
        aot.put("colorDesign", "桥本贤");
        aot.put("chiefAnimationDirector", "浅野恭司");
        aot.put("type", "anime");
        
        List<Map<String, Object>> aotViewingRecords = new ArrayList<>();
        Map<String, Object> aotRecord1 = new LinkedHashMap<>();
        aotRecord1.put("id", 1);
        aotRecord1.put("user", "自由之翼");
        aotRecord1.put("userAvatar", "https://example.com/avatar3.jpg");
        aotRecord1.put("rating", 9.8);
        aotRecord1.put("totalRating", 7);
        aotRecord1.put("musicRating", 10);
        aotRecord1.put("artRating", 9);
        aotRecord1.put("scriptRating", 9);
        aotRecord1.put("performanceRating", 9);
        aotRecord1.put("programRating", 9);
        aotRecord1.put("voiceRating", 9);
        aotRecord1.put("comment", "神作！剧情反转层出不穷，世界观宏大，人物塑造立体，泽野弘之的音乐更是点睛之笔！");
        aotRecord1.put("date", "2026-01-20 20:15:00");
        aotViewingRecords.add(aotRecord1);
        
        Map<String, Object> aotRecord2 = new LinkedHashMap<>();
        aotRecord2.put("id", 2);
        aotRecord2.put("user", "巨人粉");
        aotRecord2.put("userAvatar", "https://example.com/avatar4.jpg");
        aotRecord2.put("rating", 9.5);
        aotRecord2.put("totalRating", 7);
        aotRecord2.put("musicRating", 10);
        aotRecord2.put("artRating", 8);
        aotRecord2.put("scriptRating", 9);
        aotRecord2.put("performanceRating", 9);
        aotRecord2.put("programRating", 8);
        aotRecord2.put("voiceRating", 9);
        aotRecord2.put("comment", "从第一季到最终季，每一季都带给我惊喜，谏山创的构思太巧妙了！");
        aotRecord2.put("date", "2026-01-18 16:40:00");
        aotViewingRecords.add(aotRecord2);
        aot.put("viewingRecords", aotViewingRecords);
        
        List<Map<String, Object>> aotChars = new ArrayList<>();
        Map<String, Object> aotChar1 = new LinkedHashMap<>();
        aotChar1.put("id", 3);
        aotChar1.put("name", "艾伦·耶格尔");
        aotChar1.put("voiceActor", "梶裕贵");
        aotChar1.put("image", "https://example.com/eren.jpg");
        aotChar1.put("likes", 15600);
        aotChars.add(aotChar1);
        Map<String, Object> aotChar2 = new LinkedHashMap<>();
        aotChar2.put("id", 4);
        aotChar2.put("name", "三笠·阿克曼");
        aotChar2.put("voiceActor", "石川由依");
        aotChar2.put("image", "https://example.com/mikasa.jpg");
        aotChar2.put("likes", 18900);
        aotChars.add(aotChar2);
        Map<String, Object> aotChar3 = new LinkedHashMap<>();
        aotChar3.put("id", 8);
        aotChar3.put("name", "阿明·阿诺德");
        aotChar3.put("voiceActor", "井上麻里奈");
        aotChar3.put("image", "https://example.com/armin.jpg");
        aotChar3.put("likes", 7800);
        aotChars.add(aotChar3);
        aot.put("characters", aotChars);
        
        aot.put("officialWebsite", "https://shingeki.tv/");
        
        List<Map<String, Object>> aotComments = new ArrayList<>();
        Map<String, Object> aotComment1 = new LinkedHashMap<>();
        aotComment1.put("id", 1);
        aotComment1.put("user", "巨人粉");
        aotComment1.put("content", "剧情反转太多了！");
        aotComment1.put("date", "2026-01-03");
        aotComments.add(aotComment1);
        Map<String, Object> aotComment2 = new LinkedHashMap<>();
        aotComment2.put("id", 2);
        aotComment2.put("user", "动漫迷");
        aotComment2.put("content", "泽野弘之的音乐太燃了！");
        aotComment2.put("date", "2026-01-04");
        aotComments.add(aotComment2);
        aot.put("comments", aotComments);

        Map<String, Object> kny = new LinkedHashMap<>();
        kny.put("id", 3);
        kny.put("title", "鬼滅の刃");
        kny.put("chineseTitle", "鬼灭之刃");
        kny.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_4.png");
        kny.put("description", "灶门炭治郎为了拯救变成鬼的妹妹而踏上斩鬼之旅");
        kny.put("introduction", "《鬼灭之刃》是吾峠呼世晴创作的漫画作品，讲述了灶门炭治郎为了拯救变成鬼的妹妹祢豆子而踏上斩鬼之旅的故事。作品以其精美的画面、感人的剧情和鲜明的人物形象而大受欢迎。");
        kny.put("studio", "ufotable");
        kny.put("tags", Arrays.asList("战斗", "热血", "奇幻", "亲情"));
        kny.put("episodes", 44);
        kny.put("startDate", "2019-04-06");
        kny.put("broadcastDay", "星期六");
        kny.put("originalWork", "吾峠呼世晴");
        kny.put("director", "外崎春雄");
        kny.put("script", "ufotable");
        kny.put("storyboard", "外崎春雄, 细川秀树");
        kny.put("performance", "外崎春雄, 细川秀树");
        kny.put("music", "梶浦由记, 椎名豪");
        kny.put("characterDesign", "松岛晃");
        kny.put("seriesComposition", "ufotable");
        kny.put("artDirector", "佐藤胜");
        kny.put("colorDesign", "大前祐子");
        kny.put("chiefAnimationDirector", "松岛晃");
        kny.put("type", "anime");
        
        List<Map<String, Object>> knyViewingRecords = new ArrayList<>();
        Map<String, Object> knyRecord1 = new LinkedHashMap<>();
        knyRecord1.put("id", 1);
        knyRecord1.put("user", "鬼灭粉");
        knyRecord1.put("userAvatar", "https://example.com/avatar5.jpg");
        knyRecord1.put("rating", 9.6);
        knyRecord1.put("totalRating", 7);
        knyRecord1.put("musicRating", 9);
        knyRecord1.put("artRating", 10);
        knyRecord1.put("scriptRating", 8);
        knyRecord1.put("performanceRating", 9);
        knyRecord1.put("programRating", 9);
        knyRecord1.put("voiceRating", 9);
        knyRecord1.put("comment", "ufotable的制作太精良了！打斗场面华丽，剧情感人，祢豆子真的很可爱！");
        knyRecord1.put("date", "2026-01-12 18:30:00");
        knyViewingRecords.add(knyRecord1);
        
        Map<String, Object> knyRecord2 = new LinkedHashMap<>();
        knyRecord2.put("id", 2);
        knyRecord2.put("user", "动漫爱好者");
        knyRecord2.put("userAvatar", "https://example.com/avatar6.jpg");
        knyRecord2.put("rating", 9.4);
        knyRecord2.put("totalRating", 7);
        knyRecord2.put("musicRating", 9);
        knyRecord2.put("artRating", 10);
        knyRecord2.put("scriptRating", 8);
        knyRecord2.put("performanceRating", 9);
        knyRecord2.put("programRating", 9);
        knyRecord2.put("voiceRating", 9);
        knyRecord2.put("comment", "炭治郎的温柔和坚持让人感动，每个柱的设定都很有特色，值得一看！");
        knyRecord2.put("date", "2026-01-10 14:20:00");
        knyViewingRecords.add(knyRecord2);
        kny.put("viewingRecords", knyViewingRecords);
        
        List<Map<String, Object>> knyChars = new ArrayList<>();
        Map<String, Object> knyChar1 = new LinkedHashMap<>();
        knyChar1.put("id", 5);
        knyChar1.put("name", "灶门炭治郎");
        knyChar1.put("voiceActor", "花江夏树");
        knyChar1.put("image", "https://example.com/tanjiro.jpg");
        knyChar1.put("likes", 21000);
        knyChars.add(knyChar1);
        Map<String, Object> knyChar2 = new LinkedHashMap<>();
        knyChar2.put("id", 6);
        knyChar2.put("name", "灶门祢豆子");
        knyChar2.put("voiceActor", "鬼头明里");
        knyChar2.put("image", "https://example.com/nezuko.jpg");
        knyChar2.put("likes", 25000);
        knyChars.add(knyChar2);
        Map<String, Object> knyChar3 = new LinkedHashMap<>();
        knyChar3.put("id", 9);
        knyChar3.put("name", "我妻善逸");
        knyChar3.put("voiceActor", "下野纮");
        knyChar3.put("image", "https://example.com/zenitsu.jpg");
        knyChar3.put("likes", 14500);
        knyChars.add(knyChar3);
        kny.put("characters", knyChars);
        
        kny.put("officialWebsite", "https://kimetsu.com/");
        
        List<Map<String, Object>> knyComments = new ArrayList<>();
        Map<String, Object> knyComment1 = new LinkedHashMap<>();
        knyComment1.put("id", 1);
        knyComment1.put("user", "鬼灭粉");
        knyComment1.put("content", "祢豆子太可爱了！");
        knyComment1.put("date", "2026-01-05");
        knyComments.add(knyComment1);
        Map<String, Object> knyComment2 = new LinkedHashMap<>();
        knyComment2.put("id", 2);
        knyComment2.put("user", "动漫迷");
        knyComment2.put("content", "ufotable的画面太精美了！");
        knyComment2.put("date", "2026-01-06");
        knyComments.add(knyComment2);
        kny.put("comments", knyComments);

        Map<String, Object> onePiece = new LinkedHashMap<>();
        onePiece.put("id", 4);
        onePiece.put("title", "ワンピース");
        onePiece.put("chineseTitle", "海贼王");
        onePiece.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_5.png");
        onePiece.put("description", "蒙奇·D·路飞为了成为海贼王而踏上伟大航线的冒险故事");
        onePiece.put("introduction", "《海贼王》是尾田荣一郎创作的漫画作品，讲述了蒙奇·D·路飞为了成为海贼王而踏上伟大航线的冒险故事。作品以其宏大的世界观、丰富的角色和精彩的冒险故事而成为日本漫画的经典之作。");
        onePiece.put("studio", "东映动画");
        onePiece.put("tags", Arrays.asList("冒险", "热血", "友情", "战斗"));
        onePiece.put("episodes", 1100);
        onePiece.put("startDate", "1999-10-20");
        onePiece.put("broadcastDay", "星期日");
        onePiece.put("originalWork", "尾田荣一郎");
        onePiece.put("director", "宇田钢之助, 境宗久, 长峰达也");
        onePiece.put("script", "尾田荣一郎, 武上纯希");
        onePiece.put("storyboard", "宇田钢之助, 境宗久");
        onePiece.put("performance", "宇田钢之助, 境宗久");
        onePiece.put("music", "田中公平, 滨口史郎");
        onePiece.put("characterDesign", "小泉升, 久田和也");
        onePiece.put("seriesComposition", "武上纯希");
        onePiece.put("artDirector", "吉池隆司");
        onePiece.put("colorDesign", "中尾总子");
        onePiece.put("chiefAnimationDirector", "小泉升");
        onePiece.put("type", "anime");
        
        List<Map<String, Object>> onePieceViewingRecords = new ArrayList<>();
        Map<String, Object> opRecord1 = new LinkedHashMap<>();
        opRecord1.put("id", 1);
        opRecord1.put("user", "海贼迷");
        opRecord1.put("userAvatar", "https://example.com/avatar7.jpg");
        opRecord1.put("rating", 9.7);
        opRecord1.put("totalRating", 7);
        opRecord1.put("musicRating", 9);
        opRecord1.put("artRating", 8);
        opRecord1.put("scriptRating", 9);
        opRecord1.put("performanceRating", 9);
        opRecord1.put("programRating", 8);
        opRecord1.put("voiceRating", 9);
        opRecord1.put("comment", "海贼王真是太精彩了！路飞的冒险故事让人热血沸腾，每个伙伴的故事都很感人！");
        opRecord1.put("date", "2026-01-16 12:30:00");
        onePieceViewingRecords.add(opRecord1);
        
        Map<String, Object> opRecord2 = new LinkedHashMap<>();
        opRecord2.put("id", 2);
        opRecord2.put("user", "动漫爱好者");
        opRecord2.put("userAvatar", "https://example.com/avatar8.jpg");
        opRecord2.put("rating", 9.5);
        opRecord2.put("totalRating", 7);
        opRecord2.put("musicRating", 8);
        opRecord2.put("artRating", 8);
        opRecord2.put("scriptRating", 9);
        opRecord2.put("performanceRating", 9);
        opRecord2.put("programRating", 8);
        opRecord2.put("voiceRating", 9);
        opRecord2.put("comment", "尾田荣一郎的剧情设计太巧妙了，每次都有惊喜，草帽一伙的冒险还在继续！");
        opRecord2.put("date", "2026-01-14 15:20:00");
        onePieceViewingRecords.add(opRecord2);
        onePiece.put("viewingRecords", onePieceViewingRecords);
        
        List<Map<String, Object>> opChars = new ArrayList<>();
        Map<String, Object> opChar1 = new LinkedHashMap<>();
        opChar1.put("name", "蒙奇·D·路飞");
        opChar1.put("voiceActor", "田中真弓");
        opChars.add(opChar1);
        Map<String, Object> opChar2 = new LinkedHashMap<>();
        opChar2.put("name", "罗罗诺亚·索隆");
        opChar2.put("voiceActor", "中井和哉");
        opChars.add(opChar2);
        Map<String, Object> opChar3 = new LinkedHashMap<>();
        opChar3.put("name", "娜美");
        opChar3.put("voiceActor", "冈村明美");
        opChars.add(opChar3);
        onePiece.put("characters", opChars);
        
        onePiece.put("officialWebsite", "https://www.toei-anim.co.jp/tv/onepiece/");
        
        List<Map<String, Object>> opComments = new ArrayList<>();
        Map<String, Object> opComment1 = new LinkedHashMap<>();
        opComment1.put("id", 1);
        opComment1.put("user", "海贼迷");
        opComment1.put("content", "路飞的橡胶果实太厉害了！");
        opComment1.put("date", "2026-01-07");
        opComments.add(opComment1);
        Map<String, Object> opComment2 = new LinkedHashMap<>();
        opComment2.put("id", 2);
        opComment2.put("user", "动漫迷");
        opComment2.put("content", "剧情越来越精彩了！");
        opComment2.put("date", "2026-01-08");
        opComments.add(opComment2);
        onePiece.put("comments", opComments);

        Map<String, Object> naruto = new LinkedHashMap<>();
        naruto.put("id", 5);
        naruto.put("title", "NARUTO -ナルト-");
        naruto.put("chineseTitle", "火影忍者");
        naruto.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_6.png");
        naruto.put("description", "漩涡鸣人为了成为火影而努力奋斗的故事");
        naruto.put("introduction", "《火影忍者》是岸本齐史创作的漫画作品，讲述了漩涡鸣人为了成为火影而努力奋斗的故事。作品以其精彩的忍术战斗、深刻的友情描写和成长主题而受到广泛喜爱。");
        naruto.put("studio", "Studio Pierrot");
        naruto.put("tags", Arrays.asList("忍者", "热血", "友情", "成长"));
        naruto.put("episodes", 720);
        naruto.put("startDate", "2002-10-03");
        naruto.put("broadcastDay", "星期三");
        naruto.put("originalWork", "岸本齐史");
        naruto.put("director", "伊达勇登, 小林治, 今千秋");
        naruto.put("script", "隅泽克之, 武上纯希");
        naruto.put("storyboard", "伊达勇登, 小林治");
        naruto.put("performance", "伊达勇登, 小林治");
        naruto.put("music", "增田俊郎, 高梨康治");
        naruto.put("characterDesign", "西尾铁也, 铃木博文");
        naruto.put("seriesComposition", "隅泽克之");
        naruto.put("artDirector", "池田繁美");
        naruto.put("colorDesign", "川见拓也");
        naruto.put("chiefAnimationDirector", "西尾铁也");
        naruto.put("type", "anime");
        
        List<Map<String, Object>> narutoViewingRecords = new ArrayList<>();
        Map<String, Object> narutoRecord1 = new LinkedHashMap<>();
        narutoRecord1.put("id", 1);
        narutoRecord1.put("user", "火影迷");
        narutoRecord1.put("userAvatar", "https://example.com/avatar9.jpg");
        narutoRecord1.put("rating", 9.6);
        narutoRecord1.put("totalRating", 7);
        narutoRecord1.put("musicRating", 9);
        narutoRecord1.put("artRating", 8);
        narutoRecord1.put("scriptRating", 9);
        narutoRecord1.put("performanceRating", 9);
        narutoRecord1.put("programRating", 8);
        narutoRecord1.put("voiceRating", 9);
        narutoRecord1.put("comment", "火影忍者真是经典中的经典！鸣人的成长故事让人感动，忍术战斗场面精彩绝伦！");
        narutoRecord1.put("date", "2026-01-17 13:45:00");
        narutoViewingRecords.add(narutoRecord1);
        
        Map<String, Object> narutoRecord2 = new LinkedHashMap<>();
        narutoRecord2.put("id", 2);
        narutoRecord2.put("user", "动漫爱好者");
        narutoRecord2.put("userAvatar", "https://example.com/avatar10.jpg");
        narutoRecord2.put("rating", 9.4);
        narutoRecord2.put("totalRating", 7);
        narutoRecord2.put("musicRating", 8);
        narutoRecord2.put("artRating", 8);
        narutoRecord2.put("scriptRating", 9);
        narutoRecord2.put("performanceRating", 9);
        narutoRecord2.put("programRating", 8);
        narutoRecord2.put("voiceRating", 9);
        narutoRecord2.put("comment", "岸本齐史的剧情设计太出色了，鸣人和佐助的羁绊让人难忘，这是一部影响了一代人的作品！");
        narutoRecord2.put("date", "2026-01-15 16:30:00");
        narutoViewingRecords.add(narutoRecord2);
        naruto.put("viewingRecords", narutoViewingRecords);
        
        List<Map<String, Object>> narutoChars = new ArrayList<>();
        Map<String, Object> narutoChar1 = new LinkedHashMap<>();
        narutoChar1.put("name", "漩涡鸣人");
        narutoChar1.put("voiceActor", "竹内顺子");
        narutoChars.add(narutoChar1);
        Map<String, Object> narutoChar2 = new LinkedHashMap<>();
        narutoChar2.put("name", "宇智波佐助");
        narutoChar2.put("voiceActor", "杉山纪彰");
        narutoChars.add(narutoChar2);
        Map<String, Object> narutoChar3 = new LinkedHashMap<>();
        narutoChar3.put("name", "春野樱");
        narutoChar3.put("voiceActor", "中村千绘");
        narutoChars.add(narutoChar3);
        naruto.put("characters", narutoChars);
        
        naruto.put("officialWebsite", "https://www.naruto.com/");
        
        List<Map<String, Object>> narutoComments = new ArrayList<>();
        Map<String, Object> narutoComment1 = new LinkedHashMap<>();
        narutoComment1.put("id", 1);
        narutoComment1.put("user", "火影迷");
        narutoComment1.put("content", "鸣人的螺旋丸太帅了！");
        narutoComment1.put("date", "2026-01-09");
        narutoComments.add(narutoComment1);
        Map<String, Object> narutoComment2 = new LinkedHashMap<>();
        narutoComment2.put("id", 2);
        narutoComment2.put("user", "动漫迷");
        narutoComment2.put("content", "佐助和鸣人的对决太精彩了！");
        narutoComment2.put("date", "2026-01-10");
        narutoComments.add(narutoComment2);
        naruto.put("comments", narutoComments);

        Map<String, Object> tokyoGhoul = new LinkedHashMap<>();
        tokyoGhoul.put("id", 6);
        tokyoGhoul.put("title", "東京喰種");
        tokyoGhoul.put("chineseTitle", "东京食尸鬼");
        tokyoGhoul.put("image", "https://example.com/tokyoghoul.jpg");
        tokyoGhoul.put("description", "金木研被改造为半喰种后在人类与喰种之间挣扎的故事");
        tokyoGhoul.put("introduction", "《东京食尸鬼》是石田翠创作的漫画作品，讲述了金木研被改造为半喰种后在人类与喰种之间挣扎的故事。作品以其黑暗的世界观、深刻的人性探讨和精彩的战斗场景而受到关注。");
        tokyoGhoul.put("studio", "Studio Pierrot");
        tokyoGhoul.put("tags", Arrays.asList("黑暗", "战斗", "奇幻", "人性"));
        tokyoGhoul.put("episodes", 48);
        tokyoGhoul.put("startDate", "2014-07-03");
        tokyoGhoul.put("broadcastDay", "星期四");
        tokyoGhoul.put("originalWork", "石田翠");
        tokyoGhoul.put("director", "森田修平, 渡部稳宽");
        tokyoGhoul.put("script", "御笠之忠次");
        tokyoGhoul.put("storyboard", "森田修平, 渡部稳宽");
        tokyoGhoul.put("performance", "森田修平, 渡部稳宽");
        tokyoGhoul.put("music", "山田丰");
        tokyoGhoul.put("characterDesign", "三轮和宏");
        tokyoGhoul.put("seriesComposition", "御笠之忠次");
        tokyoGhoul.put("artDirector", "清水友幸");
        tokyoGhoul.put("colorDesign", "中岛和子");
        tokyoGhoul.put("chiefAnimationDirector", "三轮和宏");
        tokyoGhoul.put("type", "anime");
        
        List<Map<String, Object>> tokyoGhoulViewingRecords = new ArrayList<>();
        Map<String, Object> tgRecord1 = new LinkedHashMap<>();
        tgRecord1.put("id", 1);
        tgRecord1.put("user", "喰种迷");
        tgRecord1.put("userAvatar", "https://example.com/avatar11.jpg");
        tgRecord1.put("rating", 9.2);
        tgRecord1.put("totalRating", 7);
        tgRecord1.put("musicRating", 8);
        tgRecord1.put("artRating", 9);
        tgRecord1.put("scriptRating", 8);
        tgRecord1.put("performanceRating", 9);
        tgRecord1.put("programRating", 8);
        tgRecord1.put("voiceRating", 9);
        tgRecord1.put("comment", "东京食尸鬼的世界观很独特，金木研的挣扎和成长让人印象深刻，剧情充满深度！");
        tgRecord1.put("date", "2026-01-18 14:20:00");
        tokyoGhoulViewingRecords.add(tgRecord1);
        
        Map<String, Object> tgRecord2 = new LinkedHashMap<>();
        tgRecord2.put("id", 2);
        tgRecord2.put("user", "动漫爱好者");
        tgRecord2.put("userAvatar", "https://example.com/avatar12.jpg");
        tgRecord2.put("rating", 9.0);
        tgRecord2.put("totalRating", 7);
        tgRecord2.put("musicRating", 8);
        tgRecord2.put("artRating", 8);
        tgRecord2.put("scriptRating", 8);
        tgRecord2.put("performanceRating", 9);
        tgRecord2.put("programRating", 8);
        tgRecord2.put("voiceRating", 9);
        tgRecord2.put("comment", "石田翠的作品充满了对人性的探讨，金木研的白发造型太酷了，战斗场面也很精彩！");
        tgRecord2.put("date", "2026-01-16 17:45:00");
        tokyoGhoulViewingRecords.add(tgRecord2);
        tokyoGhoul.put("viewingRecords", tokyoGhoulViewingRecords);
        
        List<Map<String, Object>> tgChars = new ArrayList<>();
        Map<String, Object> tgChar1 = new LinkedHashMap<>();
        tgChar1.put("name", "金木研");
        tgChar1.put("voiceActor", "花江夏树");
        tgChars.add(tgChar1);
        Map<String, Object> tgChar2 = new LinkedHashMap<>();
        tgChar2.put("name", "雾岛董香");
        tgChar2.put("voiceActor", "雨宫天");
        tgChars.add(tgChar2);
        Map<String, Object> tgChar3 = new LinkedHashMap<>();
        tgChar3.put("name", "月山习");
        tgChar3.put("voiceActor", "宫野真守");
        tgChars.add(tgChar3);
        tokyoGhoul.put("characters", tgChars);
        
        tokyoGhoul.put("officialWebsite", "https://tokyoghoul-anime.com/");
        
        List<Map<String, Object>> tgComments = new ArrayList<>();
        Map<String, Object> tgComment1 = new LinkedHashMap<>();
        tgComment1.put("id", 1);
        tgComment1.put("user", "喰种迷");
        tgComment1.put("content", "金木研的白发造型太酷了！");
        tgComment1.put("date", "2026-01-11");
        tgComments.add(tgComment1);
        Map<String, Object> tgComment2 = new LinkedHashMap<>();
        tgComment2.put("id", 2);
        tgComment2.put("user", "动漫迷");
        tgComment2.put("content", "剧情很有深度，值得思考！");
        tgComment2.put("date", "2026-01-12");
        tgComments.add(tgComment2);
        tokyoGhoul.put("comments", tgComments);

        Map<String, Object> aotFinal = new LinkedHashMap<>();
        aotFinal.put("id", 7);
        aotFinal.put("title", "進撃の巨人 The Final Season");
        aotFinal.put("chineseTitle", "进击的巨人 最终季");
        aotFinal.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_8.png");
        aotFinal.put("description", "进击的巨人系列的最终篇章，揭示世界的真相");
        aotFinal.put("introduction", "《进击的巨人 最终季》是进击的巨人系列的最终篇章，讲述了艾伦·耶格尔等人揭示世界真相的故事。本季由MAPPA制作，剧情更加紧凑，世界观更加宏大。");
        aotFinal.put("studio", "MAPPA");
        aotFinal.put("tags", Arrays.asList("战斗", "热血", "奇幻", "黑暗"));
        aotFinal.put("episodes", 28);
        aotFinal.put("startDate", "2020-12-07");
        aotFinal.put("broadcastDay", "星期一");
        aotFinal.put("originalWork", "谏山创");
        aotFinal.put("director", "林祐一郎, 若林信");
        aotFinal.put("script", "濑古浩司");
        aotFinal.put("storyboard", "林祐一郎, 若林信");
        aotFinal.put("performance", "林祐一郎, 若林信");
        aotFinal.put("music", "泽野弘之");
        aotFinal.put("characterDesign", "岸友洋");
        aotFinal.put("seriesComposition", "濑古浩司");
        aotFinal.put("artDirector", "吉原俊一郎");
        aotFinal.put("colorDesign", "桥本贤");
        aotFinal.put("chiefAnimationDirector", "岸友洋");
        aotFinal.put("type", "anime");
        
        List<Map<String, Object>> aotFinalViewingRecords = new ArrayList<>();
        Map<String, Object> aotfRecord1 = new LinkedHashMap<>();
        aotfRecord1.put("id", 1);
        aotfRecord1.put("user", "自由之翼");
        aotfRecord1.put("userAvatar", "https://example.com/avatar13.jpg");
        aotfRecord1.put("rating", 9.9);
        aotfRecord1.put("totalRating", 7);
        aotfRecord1.put("musicRating", 10);
        aotfRecord1.put("artRating", 9);
        aotfRecord1.put("scriptRating", 10);
        aotfRecord1.put("performanceRating", 9);
        aotfRecord1.put("programRating", 9);
        aotfRecord1.put("voiceRating", 9);
        aotfRecord1.put("comment", "进击的巨人最终季太震撼了！艾伦的选择让人深思，结局虽然争议，但确实是神作！");
        aotfRecord1.put("date", "2026-01-19 19:30:00");
        aotFinalViewingRecords.add(aotfRecord1);
        
        Map<String, Object> aotfRecord2 = new LinkedHashMap<>();
        aotfRecord2.put("id", 2);
        aotfRecord2.put("user", "巨人粉");
        aotfRecord2.put("userAvatar", "https://example.com/avatar14.jpg");
        aotfRecord2.put("rating", 9.7);
        aotfRecord2.put("totalRating", 7);
        aotfRecord2.put("musicRating", 10);
        aotfRecord2.put("artRating", 8);
        aotfRecord2.put("scriptRating", 9);
        aotfRecord2.put("performanceRating", 9);
        aotfRecord2.put("programRating", 8);
        aotfRecord2.put("voiceRating", 9);
        aotfRecord2.put("comment", "MAPPA的制作虽然和WIT Studio风格不同，但依然很精良，谏山创的结局设计太出人意料了！");
        aotfRecord2.put("date", "2026-01-17 21:15:00");
        aotFinalViewingRecords.add(aotfRecord2);
        aotFinal.put("viewingRecords", aotFinalViewingRecords);
        
        List<Map<String, Object>> aotfChars = new ArrayList<>();
        Map<String, Object> aotfChar1 = new LinkedHashMap<>();
        aotfChar1.put("name", "艾伦·耶格尔");
        aotfChar1.put("voiceActor", "梶裕贵");
        aotfChars.add(aotfChar1);
        Map<String, Object> aotfChar2 = new LinkedHashMap<>();
        aotfChar2.put("name", "三笠·阿克曼");
        aotfChar2.put("voiceActor", "石川由依");
        aotfChars.add(aotfChar2);
        Map<String, Object> aotfChar3 = new LinkedHashMap<>();
        aotfChar3.put("name", "阿明·阿诺德");
        aotfChar3.put("voiceActor", "井上麻里奈");
        aotfChars.add(aotfChar3);
        aotFinal.put("characters", aotfChars);
        
        aotFinal.put("officialWebsite", "https://shingeki.tv/final/");
        
        List<Map<String, Object>> aotfComments = new ArrayList<>();
        Map<String, Object> aotfComment1 = new LinkedHashMap<>();
        aotfComment1.put("id", 1);
        aotfComment1.put("user", "巨人粉");
        aotfComment1.put("content", "最终季的剧情太震撼了！");
        aotfComment1.put("date", "2026-01-13");
        aotfComments.add(aotfComment1);
        Map<String, Object> aotfComment2 = new LinkedHashMap<>();
        aotfComment2.put("id", 2);
        aotfComment2.put("user", "动漫迷");
        aotfComment2.put("content", "MAPPA的制作也很精良！");
        aotfComment2.put("date", "2026-01-14");
        aotfComments.add(aotfComment2);
        aotFinal.put("comments", aotfComments);

        Map<String, Object> sao = new LinkedHashMap<>();
        sao.put("id", 8);
        sao.put("title", "ソードアート・オンライン");
        sao.put("chineseTitle", "刀剑神域");
        sao.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_9.png");
        sao.put("description", "桐人被困在虚拟现实游戏中，为了生存而战斗的故事");
        sao.put("introduction", "《刀剑神域》是川原砾创作的轻小说作品，讲述了桐人被困在虚拟现实游戏中，为了生存而战斗的故事。作品以其独特的游戏世界观、精彩的战斗场景和深刻的情感描写而受到广泛喜爱。");
        sao.put("studio", "A-1 Pictures");
        sao.put("tags", Arrays.asList("游戏", "热血", "冒险", "恋爱"));
        sao.put("episodes", 96);
        sao.put("startDate", "2012-07-08");
        sao.put("broadcastDay", "星期日");
        sao.put("originalWork", "川原砾");
        sao.put("director", "伊藤智彦, 小野学");
        sao.put("script", "木泽行人, 中本宗应");
        sao.put("storyboard", "伊藤智彦, 小野学");
        sao.put("performance", "伊藤智彦, 小野学");
        sao.put("music", "梶浦由记");
        sao.put("characterDesign", "足立慎吾");
        sao.put("seriesComposition", "木泽行人, 中本宗应");
        sao.put("artDirector", "竹内志保");
        sao.put("colorDesign", "中岛和子");
        sao.put("chiefAnimationDirector", "足立慎吾");
        sao.put("type", "anime");
        
        List<Map<String, Object>> saoViewingRecords = new ArrayList<>();
        Map<String, Object> saoRecord1 = new LinkedHashMap<>();
        saoRecord1.put("id", 1);
        saoRecord1.put("user", "SAO粉");
        saoRecord1.put("userAvatar", "https://example.com/avatar15.jpg");
        saoRecord1.put("rating", 9.3);
        saoRecord1.put("totalRating", 7);
        saoRecord1.put("musicRating", 9);
        saoRecord1.put("artRating", 8);
        saoRecord1.put("scriptRating", 8);
        saoRecord1.put("performanceRating", 9);
        saoRecord1.put("programRating", 8);
        saoRecord1.put("voiceRating", 9);
        saoRecord1.put("comment", "刀剑神域开创了VR游戏题材的先河，桐人和亚丝娜的故事让人感动，战斗场面精彩！");
        saoRecord1.put("date", "2026-01-20 15:45:00");
        saoViewingRecords.add(saoRecord1);
        
        Map<String, Object> saoRecord2 = new LinkedHashMap<>();
        saoRecord2.put("id", 2);
        saoRecord2.put("user", "动漫爱好者");
        saoRecord2.put("userAvatar", "https://example.com/avatar16.jpg");
        saoRecord2.put("rating", 9.1);
        saoRecord2.put("totalRating", 7);
        saoRecord2.put("musicRating", 9);
        saoRecord2.put("artRating", 8);
        saoRecord2.put("scriptRating", 8);
        saoRecord2.put("performanceRating", 9);
        saoRecord2.put("programRating", 8);
        saoRecord2.put("voiceRating", 9);
        saoRecord2.put("comment", "川原砾的世界观设定很独特，梶浦由记的音乐太好听了，桐人的声优松冈祯丞表现出色！");
        saoRecord2.put("date", "2026-01-18 18:30:00");
        saoViewingRecords.add(saoRecord2);
        sao.put("viewingRecords", saoViewingRecords);
        
        List<Map<String, Object>> saoChars = new ArrayList<>();
        Map<String, Object> saoChar1 = new LinkedHashMap<>();
        saoChar1.put("name", "桐人");
        saoChar1.put("voiceActor", "松冈祯丞");
        saoChars.add(saoChar1);
        Map<String, Object> saoChar2 = new LinkedHashMap<>();
        saoChar2.put("name", "亚丝娜");
        saoChar2.put("voiceActor", "户松遥");
        saoChars.add(saoChar2);
        Map<String, Object> saoChar3 = new LinkedHashMap<>();
        saoChar3.put("name", "诗乃");
        saoChar3.put("voiceActor", "泽城美雪");
        saoChars.add(saoChar3);
        sao.put("characters", saoChars);
        
        sao.put("officialWebsite", "https://www.swordart-online.net/");
        
        List<Map<String, Object>> saoComments = new ArrayList<>();
        Map<String, Object> saoComment1 = new LinkedHashMap<>();
        saoComment1.put("id", 1);
        saoComment1.put("user", "SAO迷");
        saoComment1.put("content", "桐人和亚丝娜的感情太甜了！");
        saoComment1.put("date", "2026-01-15");
        saoComments.add(saoComment1);
        Map<String, Object> saoComment2 = new LinkedHashMap<>();
        saoComment2.put("id", 2);
        saoComment2.put("user", "动漫迷");
        saoComment2.put("content", "游戏设定很新颖，剧情很吸引人！");
        saoComment2.put("date", "2026-01-16");
        saoComments.add(saoComment2);
        sao.put("comments", saoComments);

        Map<String, Object> jujutsu = new LinkedHashMap<>();
        jujutsu.put("id", 9);
        jujutsu.put("title", "呪術廻戦");
        jujutsu.put("chineseTitle", "咒术回战");
        jujutsu.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_12.png");
        jujutsu.put("description", "虎杖悠仁与咒术师们一起对抗诅咒的故事");
        jujutsu.put("introduction", "《咒术回战》是芥见下下创作的漫画作品，讲述了虎杖悠仁与咒术师们一起对抗诅咒的故事。作品以其精彩的战斗场景、独特的世界观和鲜明的角色形象而大受欢迎。");
        jujutsu.put("studio", "MAPPA");
        jujutsu.put("tags", Arrays.asList("战斗", "奇幻", "热血", "校园"));
        jujutsu.put("episodes", 24);
        jujutsu.put("startDate", "2020-10-03");
        jujutsu.put("broadcastDay", "星期六");
        jujutsu.put("originalWork", "芥见下下");
        jujutsu.put("director", "朴性厚");
        jujutsu.put("script", "濑古浩司");
        jujutsu.put("storyboard", "朴性厚, 竹下良平");
        jujutsu.put("performance", "朴性厚, 竹下良平");
        jujutsu.put("music", "堤博明, 桶狭间有沙, 照井顺政");
        jujutsu.put("characterDesign", "平松祯史");
        jujutsu.put("seriesComposition", "濑古浩司");
        jujutsu.put("artDirector", "竹田悠介");
        jujutsu.put("colorDesign", "镰田千贺子");
        jujutsu.put("chiefAnimationDirector", "平松祯史");
        jujutsu.put("type", "anime");
        
        List<Map<String, Object>> jujutsuViewingRecords = new ArrayList<>();
        Map<String, Object> jjkRecord1 = new LinkedHashMap<>();
        jjkRecord1.put("id", 1);
        jjkRecord1.put("user", "咒术迷");
        jjkRecord1.put("userAvatar", "https://example.com/avatar17.jpg");
        jjkRecord1.put("rating", 9.5);
        jjkRecord1.put("totalRating", 7);
        jjkRecord1.put("musicRating", 9);
        jjkRecord1.put("artRating", 9);
        jjkRecord1.put("scriptRating", 9);
        jjkRecord1.put("performanceRating", 9);
        jjkRecord1.put("programRating", 9);
        jjkRecord1.put("voiceRating", 9);
        jjkRecord1.put("comment", "咒术回战太精彩了！五条悟的领域展开太帅了，虎杖的性格很讨喜，战斗场面超燃！");
        jjkRecord1.put("date", "2026-01-21 14:30:00");
        jujutsuViewingRecords.add(jjkRecord1);
        
        Map<String, Object> jjkRecord2 = new LinkedHashMap<>();
        jjkRecord2.put("id", 2);
        jjkRecord2.put("user", "动漫爱好者");
        jjkRecord2.put("userAvatar", "https://example.com/avatar18.jpg");
        jjkRecord2.put("rating", 9.3);
        jjkRecord2.put("totalRating", 7);
        jjkRecord2.put("musicRating", 9);
        jjkRecord2.put("artRating", 8);
        jjkRecord2.put("scriptRating", 9);
        jjkRecord2.put("performanceRating", 9);
        jjkRecord2.put("programRating", 8);
        jjkRecord2.put("voiceRating", 9);
        jjkRecord2.put("comment", "MAPPA的制作太精良了，芥见下下的剧情设计很巧妙，每个角色都有鲜明的个性，值得一看！");
        jjkRecord2.put("date", "2026-01-19 17:15:00");
        jujutsuViewingRecords.add(jjkRecord2);
        jujutsu.put("viewingRecords", jujutsuViewingRecords);
        
        List<Map<String, Object>> jjkChars = new ArrayList<>();
        Map<String, Object> jjkChar1 = new LinkedHashMap<>();
        jjkChar1.put("name", "虎杖悠仁");
        jjkChar1.put("voiceActor", "榎木淳弥");
        jjkChars.add(jjkChar1);
        Map<String, Object> jjkChar2 = new LinkedHashMap<>();
        jjkChar2.put("name", "伏黑惠");
        jjkChar2.put("voiceActor", "内田雄马");
        jjkChars.add(jjkChar2);
        Map<String, Object> jjkChar3 = new LinkedHashMap<>();
        jjkChar3.put("name", "钉崎野蔷薇");
        jjkChar3.put("voiceActor", "濑户麻沙美");
        jjkChars.add(jjkChar3);
        jujutsu.put("characters", jjkChars);
        
        jujutsu.put("officialWebsite", "https://jujutsukaisen.jp/");
        
        List<Map<String, Object>> jjkComments = new ArrayList<>();
        Map<String, Object> jjkComment1 = new LinkedHashMap<>();
        jjkComment1.put("id", 1);
        jjkComment1.put("user", "咒术迷");
        jjkComment1.put("content", "五条悟太帅了！");
        jjkComment1.put("date", "2026-01-17");
        jjkComments.add(jjkComment1);
        Map<String, Object> jjkComment2 = new LinkedHashMap<>();
        jjkComment2.put("id", 2);
        jjkComment2.put("user", "动漫迷");
        jjkComment2.put("content", "战斗场面太精彩了！");
        jjkComment2.put("date", "2026-01-18");
        jjkComments.add(jjkComment2);
        jujutsu.put("comments", jjkComments);

        Map<String, Object> violet = new LinkedHashMap<>();
        violet.put("id", 10);
        violet.put("title", "ヴァイオレット・エヴァーガーデン");
        violet.put("chineseTitle", "紫罗兰永恒花园");
        violet.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_13.png");
        violet.put("description", "薇尔莉特·伊芙加登寻找爱的意义的故事");
        violet.put("introduction", "《紫罗兰永恒花园》是晓佳奈创作的轻小说作品，讲述了薇尔莉特·伊芙加登寻找爱的意义的故事。作品以其精美的画面、感人的剧情和深刻的主题而受到广泛好评。");
        violet.put("studio", "京都动画");
        violet.put("tags", Arrays.asList("治愈", "情感", "成长", "战争"));
        violet.put("episodes", 13);
        violet.put("startDate", "2018-01-10");
        violet.put("broadcastDay", "星期三");
        violet.put("originalWork", "晓佳奈");
        violet.put("director", "石立太一");
        violet.put("script", "吉田玲子");
        violet.put("storyboard", "石立太一, 藤田春香");
        violet.put("performance", "石立太一, 藤田春香");
        violet.put("music", "Evan Call");
        violet.put("characterDesign", "高濑亚贵子");
        violet.put("seriesComposition", "吉田玲子");
        violet.put("artDirector", "渡边美希子");
        violet.put("colorDesign", "米田侑加");
        violet.put("chiefAnimationDirector", "高濑亚贵子");
        violet.put("type", "anime");
        
        List<Map<String, Object>> violetViewingRecords = new ArrayList<>();
        Map<String, Object> violetRecord1 = new LinkedHashMap<>();
        violetRecord1.put("id", 1);
        violetRecord1.put("user", "紫罗兰迷");
        violetRecord1.put("userAvatar", "https://example.com/avatar19.jpg");
        violetRecord1.put("rating", 9.8);
        violetRecord1.put("totalRating", 7);
        violetRecord1.put("musicRating", 10);
        violetRecord1.put("artRating", 10);
        violetRecord1.put("scriptRating", 9);
        violetRecord1.put("performanceRating", 9);
        violetRecord1.put("programRating", 9);
        violetRecord1.put("voiceRating", 9);
        violetRecord1.put("comment", "紫罗兰永恒花园太美了！京都动画的制作简直是艺术品，薇尔莉特的成长故事让人感动得流泪！");
        violetRecord1.put("date", "2026-01-22 16:30:00");
        violetViewingRecords.add(violetRecord1);
        
        Map<String, Object> violetRecord2 = new LinkedHashMap<>();
        violetRecord2.put("id", 2);
        violetRecord2.put("user", "动漫爱好者");
        violetRecord2.put("userAvatar", "https://example.com/avatar20.jpg");
        violetRecord2.put("rating", 9.6);
        violetRecord2.put("totalRating", 7);
        violetRecord2.put("musicRating", 10);
        violetRecord2.put("artRating", 10);
        violetRecord2.put("scriptRating", 9);
        violetRecord2.put("performanceRating", 9);
        violetRecord2.put("programRating", 9);
        violetRecord2.put("voiceRating", 9);
        violetRecord2.put("comment", "Evan Call的音乐太好听了，石川由依的配音表现出色，这是一部让人心灵净化的作品！");
        violetRecord2.put("date", "2026-01-20 19:45:00");
        violetViewingRecords.add(violetRecord2);
        violet.put("viewingRecords", violetViewingRecords);
        
        List<Map<String, Object>> violetChars = new ArrayList<>();
        Map<String, Object> violetChar1 = new LinkedHashMap<>();
        violetChar1.put("name", "薇尔莉特·伊芙加登");
        violetChar1.put("voiceActor", "石川由依");
        violetChars.add(violetChar1);
        Map<String, Object> violetChar2 = new LinkedHashMap<>();
        violetChar2.put("name", "基尔伯特·布甘比利亚");
        violetChar2.put("voiceActor", "浪川大辅");
        violetChars.add(violetChar2);
        Map<String, Object> violetChar3 = new LinkedHashMap<>();
        violetChar3.put("name", "克劳迪亚·霍金斯");
        violetChar3.put("voiceActor", "子安武人");
        violetChars.add(violetChar3);
        violet.put("characters", violetChars);
        
        violet.put("officialWebsite", "https://violet-evergarden.jp/");
        
        List<Map<String, Object>> violetComments = new ArrayList<>();
        Map<String, Object> violetComment1 = new LinkedHashMap<>();
        violetComment1.put("id", 1);
        violetComment1.put("user", "紫罗兰迷");
        violetComment1.put("content", "薇尔莉特太可爱了！");
        violetComment1.put("date", "2026-01-19");
        violetComments.add(violetComment1);
        Map<String, Object> violetComment2 = new LinkedHashMap<>();
        violetComment2.put("id", 2);
        violetComment2.put("user", "动漫迷");
        violetComment2.put("content", "画面太美了，剧情很感人！");
        violetComment2.put("date", "2026-01-20");
        violetComments.add(violetComment2);
        violet.put("comments", violetComments);

        Map<String, Object> linglong = new LinkedHashMap<>();
        linglong.put("id", 11);
        linglong.put("title", "灵笼");
        linglong.put("chineseTitle", "灵笼");
        linglong.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_11.png");
        linglong.put("description", "末世中人类在灯塔上的生存故事");
        linglong.put("introduction", "《灵笼》是艺画开天制作的原创动画作品，讲述了末世中人类在灯塔上的生存故事。作品以其精美的画面、宏大的世界观和深刻的主题而受到关注。");
        linglong.put("studio", "艺画开天");
        linglong.put("tags", Arrays.asList("科幻", "末世", "战斗", "冒险"));
        linglong.put("episodes", 16);
        linglong.put("startDate", "2019-07-13");
        linglong.put("broadcastDay", "星期六");
        linglong.put("originalWork", "艺画开天");
        linglong.put("director", "董相博");
        linglong.put("script", "董相博, 饶雪");
        linglong.put("storyboard", "董相博");
        linglong.put("performance", "董相博");
        linglong.put("music", "杨秉音");
        linglong.put("characterDesign", "赵禹晴");
        linglong.put("seriesComposition", "董相博");
        linglong.put("artDirector", "王昕");
        linglong.put("colorDesign", "张帆");
        linglong.put("chiefAnimationDirector", "赵禹晴");
        linglong.put("type", "anime");
        
        List<Map<String, Object>> linglongViewingRecords = new ArrayList<>();
        Map<String, Object> llRecord1 = new LinkedHashMap<>();
        llRecord1.put("id", 1);
        llRecord1.put("user", "灵笼迷");
        llRecord1.put("userAvatar", "https://example.com/avatar21.jpg");
        llRecord1.put("rating", 9.4);
        llRecord1.put("totalRating", 7);
        llRecord1.put("musicRating", 9);
        llRecord1.put("artRating", 10);
        llRecord1.put("scriptRating", 8);
        llRecord1.put("performanceRating", 9);
        llRecord1.put("programRating", 9);
        llRecord1.put("voiceRating", 9);
        llRecord1.put("comment", "灵笼的世界观太宏大了！艺画开天的制作水平很高，画面精美，剧情紧凑，马克和冉冰的故事很感人！");
        llRecord1.put("date", "2026-01-23 15:30:00");
        linglongViewingRecords.add(llRecord1);
        
        Map<String, Object> llRecord2 = new LinkedHashMap<>();
        llRecord2.put("id", 2);
        llRecord2.put("user", "国漫支持者");
        llRecord2.put("userAvatar", "https://example.com/avatar22.jpg");
        llRecord2.put("rating", 9.2);
        llRecord2.put("totalRating", 7);
        llRecord2.put("musicRating", 9);
        llRecord2.put("artRating", 10);
        llRecord2.put("scriptRating", 8);
        llRecord2.put("performanceRating", 9);
        llRecord2.put("programRating", 9);
        llRecord2.put("voiceRating", 9);
        llRecord2.put("comment", "国漫的崛起！灵笼的制作质量可以和日漫媲美，世界观设定新颖，战斗场面精彩，值得支持！");
        llRecord2.put("date", "2026-01-21 18:45:00");
        linglongViewingRecords.add(llRecord2);
        linglong.put("viewingRecords", linglongViewingRecords);
        
        List<Map<String, Object>> llChars = new ArrayList<>();
        Map<String, Object> llChar1 = new LinkedHashMap<>();
        llChar1.put("name", "马克");
        llChar1.put("voiceActor", "李元韬");
        llChars.add(llChar1);
        Map<String, Object> llChar2 = new LinkedHashMap<>();
        llChar2.put("name", "冉冰");
        llChar2.put("voiceActor", "陶典");
        llChars.add(llChar2);
        Map<String, Object> llChar3 = new LinkedHashMap<>();
        llChar3.put("name", "查尔斯");
        llChar3.put("voiceActor", "吴磊");
        llChars.add(llChar3);
        linglong.put("characters", llChars);
        
        linglong.put("officialWebsite", "https://linglong.art/");
        
        List<Map<String, Object>> llComments = new ArrayList<>();
        Map<String, Object> llComment1 = new LinkedHashMap<>();
        llComment1.put("id", 1);
        llComment1.put("user", "灵笼迷");
        llComment1.put("content", "画面太精美了！");
        llComment1.put("date", "2026-01-21");
        llComments.add(llComment1);
        Map<String, Object> llComment2 = new LinkedHashMap<>();
        llComment2.put("id", 2);
        llComment2.put("user", "动漫迷");
        llComment2.put("content", "世界观很宏大，剧情很吸引人！");
        llComment2.put("date", "2026-01-22");
        llComments.add(llComment2);
        linglong.put("comments", llComments);

        Map<String, Object> tianGuan = new LinkedHashMap<>();
        tianGuan.put("id", 12);
        tianGuan.put("title", "天官赐福");
        tianGuan.put("chineseTitle", "天官赐福");
        tianGuan.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_14.png");
        tianGuan.put("description", "谢怜与花城之间的故事");
        tianGuan.put("introduction", "《天官赐福》是墨香铜臭创作的小说作品，讲述了谢怜与花城之间的故事。作品以其精美的画面、感人的剧情和深刻的情感描写而受到广泛喜爱。");
        tianGuan.put("studio", "绘梦动画");
        tianGuan.put("tags", Arrays.asList("古风", "玄幻", "情感", "冒险"));
        tianGuan.put("episodes", 11);
        tianGuan.put("startDate", "2020-10-31");
        tianGuan.put("broadcastDay", "星期六");
        tianGuan.put("originalWork", "墨香铜臭");
        tianGuan.put("director", "李豪凌");
        tianGuan.put("script", "春日幽铃");
        tianGuan.put("type", "anime");
        tianGuan.put("storyboard", "李豪凌");
        tianGuan.put("performance", "李豪凌");
        tianGuan.put("music", "阿鲲");
        tianGuan.put("characterDesign", "申琳");
        tianGuan.put("seriesComposition", "春日幽铃");
        tianGuan.put("artDirector", "杨雪");
        tianGuan.put("colorDesign", "张帆");
        tianGuan.put("chiefAnimationDirector", "申琳");
        
        List<Map<String, Object>> tg2Chars = new ArrayList<>();
        Map<String, Object> tg2Char1 = new LinkedHashMap<>();
        tg2Char1.put("name", "谢怜");
        tg2Char1.put("voiceActor", "姜广涛");
        tg2Chars.add(tg2Char1);
        Map<String, Object> tg2Char2 = new LinkedHashMap<>();
        tg2Char2.put("name", "花城");
        tg2Char2.put("voiceActor", "马正阳");
        tg2Chars.add(tg2Char2);
        Map<String, Object> tg2Char3 = new LinkedHashMap<>();
        tg2Char3.put("name", "灵文");
        tg2Char3.put("voiceActor", "黄莺");
        tg2Chars.add(tg2Char3);
        tianGuan.put("characters", tg2Chars);
        
        tianGuan.put("officialWebsite", "https://tianguan.qq.com/");
        
        List<Map<String, Object>> tg2Comments = new ArrayList<>();
        Map<String, Object> tg2Comment1 = new LinkedHashMap<>();
        tg2Comment1.put("id", 1);
        tg2Comment1.put("user", "天官迷");
        tg2Comment1.put("content", "花城太帅了！");
        tg2Comment1.put("date", "2026-01-23");
        tg2Comments.add(tg2Comment1);
        Map<String, Object> tg2Comment2 = new LinkedHashMap<>();
        tg2Comment2.put("id", 2);
        tg2Comment2.put("user", "动漫迷");
        tg2Comment2.put("content", "画面很美，剧情很感人！");
        tg2Comment2.put("date", "2026-01-24");
        tg2Comments.add(tg2Comment2);
        tianGuan.put("comments", tg2Comments);

        Map<String, Object> shiguang = new LinkedHashMap<>();
        shiguang.put("id", 13);
        shiguang.put("title", "时光代理人");
        shiguang.put("chineseTitle", "时光代理人");
        shiguang.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_15.png");
        shiguang.put("description", "程小时和陆光通过照片穿越时空解决问题的故事");
        shiguang.put("introduction", "《时光代理人》是澜映画制作的原创动画作品，讲述了程小时和陆光通过照片穿越时空解决问题的故事。作品以其紧凑的剧情、精彩的推理和深刻的情感描写而受到关注。");
        shiguang.put("studio", "澜映画");
        shiguang.put("tags", Arrays.asList("科幻", "推理", "情感", "都市"));
        shiguang.put("episodes", 12);
        shiguang.put("startDate", "2021-04-30");
        shiguang.put("broadcastDay", "星期五");
        shiguang.put("originalWork", "澜映画");
        shiguang.put("director", "李豪凌");
        shiguang.put("script", "李豪凌, 刘兴");
        shiguang.put("storyboard", "李豪凌");
        shiguang.put("performance", "李豪凌");
        shiguang.put("music", "罗锟");
        shiguang.put("characterDesign", "申琳");
        shiguang.put("seriesComposition", "李豪凌");
        shiguang.put("artDirector", "杨雪");
        shiguang.put("colorDesign", "张帆");
        shiguang.put("chiefAnimationDirector", "申琳");
        
        List<Map<String, Object>> sgChars = new ArrayList<>();
        Map<String, Object> sgChar1 = new LinkedHashMap<>();
        sgChar1.put("name", "程小时");
        sgChar1.put("voiceActor", "杨天翔");
        sgChars.add(sgChar1);
        Map<String, Object> sgChar2 = new LinkedHashMap<>();
        sgChar2.put("name", "陆光");
        sgChar2.put("voiceActor", "苏尚卿");
        sgChars.add(sgChar2);
        Map<String, Object> sgChar3 = new LinkedHashMap<>();
        sgChar3.put("name", "乔苓");
        sgChar3.put("voiceActor", "李诗萌");
        sgChars.add(sgChar3);
        shiguang.put("characters", sgChars);
        
        shiguang.put("officialWebsite", "https://shiguang.qq.com/");
        
        List<Map<String, Object>> sgComments = new ArrayList<>();
        Map<String, Object> sgComment1 = new LinkedHashMap<>();
        sgComment1.put("id", 1);
        sgComment1.put("user", "时光迷");
        sgComment1.put("content", "剧情太紧凑了！");
        sgComment1.put("date", "2026-01-25");
        sgComments.add(sgComment1);
        Map<String, Object> sgComment2 = new LinkedHashMap<>();
        sgComment2.put("id", 2);
        sgComment2.put("user", "动漫迷");
        sgComment2.put("content", "设定很新颖，推理很精彩！");
        sgComment2.put("date", "2026-01-26");
        sgComments.add(sgComment2);
        shiguang.put("comments", sgComments);

        Map<String, Object> luoXiaoHei = new LinkedHashMap<>();
        luoXiaoHei.put("id", 14);
        luoXiaoHei.put("title", "罗小黑战记");
        luoXiaoHei.put("chineseTitle", "罗小黑战记");
        luoXiaoHei.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_16.png");
        luoXiaoHei.put("description", "罗小黑与人类之间的故事");
        luoXiaoHei.put("introduction", "《罗小黑战记》是MTJJ创作的动画作品，讲述了罗小黑与人类之间的故事。作品以其可爱的画风、幽默的剧情和深刻的主题而受到广泛喜爱。");
        luoXiaoHei.put("studio", "寒木春华");
        luoXiaoHei.put("tags", Arrays.asList("奇幻", "治愈", "幽默", "冒险"));
        luoXiaoHei.put("episodes", 40);
        luoXiaoHei.put("startDate", "2011-03-17");
        luoXiaoHei.put("broadcastDay", "不定期");
        luoXiaoHei.put("originalWork", "MTJJ");
        luoXiaoHei.put("director", "MTJJ");
        luoXiaoHei.put("script", "MTJJ");
        luoXiaoHei.put("storyboard", "MTJJ");
        luoXiaoHei.put("performance", "MTJJ");
        luoXiaoHei.put("music", "幻想动漫音乐");
        luoXiaoHei.put("characterDesign", "MTJJ");
        luoXiaoHei.put("seriesComposition", "MTJJ");
        luoXiaoHei.put("artDirector", "MTJJ");
        luoXiaoHei.put("colorDesign", "MTJJ");
        luoXiaoHei.put("chiefAnimationDirector", "MTJJ");
        
        List<Map<String, Object>> lxhChars = new ArrayList<>();
        Map<String, Object> lxhChar1 = new LinkedHashMap<>();
        lxhChar1.put("name", "罗小黑");
        lxhChar1.put("voiceActor", "山新");
        lxhChars.add(lxhChar1);
        Map<String, Object> lxhChar2 = new LinkedHashMap<>();
        lxhChar2.put("name", "无限");
        lxhChar2.put("voiceActor", "刘明月");
        lxhChars.add(lxhChar2);
        Map<String, Object> lxhChar3 = new LinkedHashMap<>();
        lxhChar3.put("name", "风息");
        lxhChar3.put("voiceActor", "郝祥海");
        lxhChars.add(lxhChar3);
        luoXiaoHei.put("characters", lxhChars);
        
        luoXiaoHei.put("officialWebsite", "https://www.luoxiaohei.com/");
        
        List<Map<String, Object>> lxhComments = new ArrayList<>();
        Map<String, Object> lxhComment1 = new LinkedHashMap<>();
        lxhComment1.put("id", 1);
        lxhComment1.put("user", "小黑迷");
        lxhComment1.put("content", "罗小黑太可爱了！");
        lxhComment1.put("date", "2026-01-27");
        lxhComments.add(lxhComment1);
        Map<String, Object> lxhComment2 = new LinkedHashMap<>();
        lxhComment2.put("id", 2);
        lxhComment2.put("user", "动漫迷");
        lxhComment2.put("content", "剧情很有趣，画风很可爱！");
        lxhComment2.put("date", "2026-01-28");
        lxhComments.add(lxhComment2);
        luoXiaoHei.put("comments", lxhComments);

        Map<String, Object> yiRen = new LinkedHashMap<>();
        yiRen.put("id", 15);
        yiRen.put("title", "一人之下");
        yiRen.put("chineseTitle", "一人之下");
        yiRen.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_20.png");
        yiRen.put("description", "张楚岚与冯宝宝之间的故事");
        yiRen.put("introduction", "《一人之下》是米二创作的漫画作品，讲述了张楚岚与冯宝宝之间的故事。作品以其独特的世界观、精彩的战斗场景和鲜明的角色形象而受到广泛喜爱。");
        yiRen.put("studio", "绘梦动画, 大火鸟文化");
        yiRen.put("tags", Arrays.asList("玄幻", "战斗", "冒险", "搞笑"));
        yiRen.put("episodes", 52);
        yiRen.put("startDate", "2016-07-08");
        yiRen.put("broadcastDay", "星期五");
        yiRen.put("originalWork", "米二");
        yiRen.put("director", "李豪凌, 王昕");
        yiRen.put("script", "米二, 刘兴");
        yiRen.put("storyboard", "李豪凌, 王昕");
        yiRen.put("performance", "李豪凌, 王昕");
        yiRen.put("music", "高梨康治");
        yiRen.put("characterDesign", "申琳");
        yiRen.put("seriesComposition", "米二");
        yiRen.put("artDirector", "杨雪");
        yiRen.put("colorDesign", "张帆");
        yiRen.put("chiefAnimationDirector", "申琳");
        
        List<Map<String, Object>> yrChars = new ArrayList<>();
        Map<String, Object> yrChar1 = new LinkedHashMap<>();
        yrChar1.put("name", "张楚岚");
        yrChar1.put("voiceActor", "夏侯落枫");
        yrChars.add(yrChar1);
        Map<String, Object> yrChar2 = new LinkedHashMap<>();
        yrChar2.put("name", "冯宝宝");
        yrChar2.put("voiceActor", "小连杀");
        yrChars.add(yrChar2);
        Map<String, Object> yrChar3 = new LinkedHashMap<>();
        yrChar3.put("name", "王也");
        yrChar3.put("voiceActor", "宝木中阳");
        yrChars.add(yrChar3);
        yiRen.put("characters", yrChars);
        
        yiRen.put("officialWebsite", "https://yiren.qq.com/");
        
        List<Map<String, Object>> yrComments = new ArrayList<>();
        Map<String, Object> yrComment1 = new LinkedHashMap<>();
        yrComment1.put("id", 1);
        yrComment1.put("user", "一人迷");
        yrComment1.put("content", "冯宝宝太有意思了！");
        yrComment1.put("date", "2026-01-29");
        yrComments.add(yrComment1);
        Map<String, Object> yrComment2 = new LinkedHashMap<>();
        yrComment2.put("id", 2);
        yrComment2.put("user", "动漫迷");
        yrComment2.put("content", "世界观很独特，战斗场面很精彩！");
        yrComment2.put("date", "2026-01-30");
        yrComments.add(yrComment2);
        yiRen.put("comments", yrComments);

        Map<String, Object> upcoming1 = new LinkedHashMap<>();
        upcoming1.put("id", 16);
        upcoming1.put("title", "鬼滅の刃 柱稽古編");
        upcoming1.put("chineseTitle", "鬼灭之刃 柱训练篇");
        upcoming1.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_17.png");
        upcoming1.put("description", "鬼杀队柱们的特训篇章，为最终决战做准备");
        upcoming1.put("introduction", "《鬼灭之刃 柱训练篇》是吾峠呼世晴创作的漫画《鬼灭之刃》的续作动画，讲述了炭治郎在无限城决战前，与鬼杀队的柱们一起进行特训的故事。");
        upcoming1.put("studio", "ufotable");
        upcoming1.put("tags", Arrays.asList("战斗", "热血", "奇幻", "冒险"));
        upcoming1.put("episodes", 8);
        upcoming1.put("startDate", "2026-07-01");
        upcoming1.put("broadcastDay", "星期日");
        upcoming1.put("originalWork", "吾峠呼世晴");
        upcoming1.put("director", "外崎春雄");
        upcoming1.put("script", "ufotable");
        upcoming1.put("storyboard", "外崎春雄");
        upcoming1.put("performance", "外崎春雄");
        upcoming1.put("music", "椎名豪");
        upcoming1.put("characterDesign", "松岛晃");
        upcoming1.put("seriesComposition", "ufotable");
        upcoming1.put("artDirector", "卫藤功二");
        upcoming1.put("colorDesign", "大前祐子");
        upcoming1.put("chiefAnimationDirector", "松岛晃");
        
        List<Map<String, Object>> upcoming1Chars = new ArrayList<>();
        Map<String, Object> upcoming1Char1 = new LinkedHashMap<>();
        upcoming1Char1.put("name", "灶门炭治郎");
        upcoming1Char1.put("voiceActor", "花江夏树");
        upcoming1Chars.add(upcoming1Char1);
        Map<String, Object> upcoming1Char2 = new LinkedHashMap<>();
        upcoming1Char2.put("name", "富冈义勇");
        upcoming1Char2.put("voiceActor", "樱井孝宏");
        upcoming1Chars.add(upcoming1Char2);
        Map<String, Object> upcoming1Char3 = new LinkedHashMap<>();
        upcoming1Char3.put("name", "炼狱杏寿郎");
        upcoming1Char3.put("voiceActor", "日野聪");
        upcoming1Chars.add(upcoming1Char3);
        upcoming1.put("characters", upcoming1Chars);
        
        upcoming1.put("officialWebsite", "https://kimetsu.com/anime/");
        
        List<Map<String, Object>> upcoming1Comments = new ArrayList<>();
        Map<String, Object> upcoming1Comment1 = new LinkedHashMap<>();
        upcoming1Comment1.put("id", 1);
        upcoming1Comment1.put("user", "鬼灭粉");
        upcoming1Comment1.put("content", "终于等到柱训练篇了！");
        upcoming1Comment1.put("date", "2026-04-01");
        upcoming1Comments.add(upcoming1Comment1);
        Map<String, Object> upcoming1Comment2 = new LinkedHashMap<>();
        upcoming1Comment2.put("id", 2);
        upcoming1Comment2.put("user", "动漫迷");
        upcoming1Comment2.put("content", "无限城决战前的铺垫，期待！");
        upcoming1Comment2.put("date", "2026-04-02");
        upcoming1Comments.add(upcoming1Comment2);
        upcoming1.put("comments", upcoming1Comments);

        Map<String, Object> upcoming2 = new LinkedHashMap<>();
        upcoming2.put("id", 17);
        upcoming2.put("title", "輝夜姬想讓人告白-超級浪漫-");
        upcoming2.put("chineseTitle", "辉夜大小姐想让我告白-超级浪漫-");
        upcoming2.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_18.png");
        upcoming2.put("description", "白银御行与四宫辉夜的爱情故事最终章");
        upcoming2.put("introduction", "《辉夜大小姐想让我告白-超级浪漫-》是赤坂明创作的漫画《辉夜大小姐想让我告白～天才们的恋爱头脑战～》的最终季动画，讲述了白银御行与四宫辉夜终于确认彼此心意后的故事。");
        upcoming2.put("studio", "A-1 Pictures");
        upcoming2.put("tags", Arrays.asList("恋爱", "校园", "喜剧", "浪漫"));
        upcoming2.put("episodes", 12);
        upcoming2.put("startDate", "2026-08-15");
        upcoming2.put("broadcastDay", "星期五");
        upcoming2.put("originalWork", "赤坂明");
        upcoming2.put("director", "畠山守");
        upcoming2.put("script", "中西泰博");
        upcoming2.put("storyboard", "畠山守");
        upcoming2.put("performance", "畠山守");
        upcoming2.put("music", "羽冈佳");
        upcoming2.put("characterDesign", "八寻裕子");
        upcoming2.put("seriesComposition", "中西泰博");
        upcoming2.put("artDirector", "新井洋行");
        upcoming2.put("colorDesign", "ホカリカナコ");
        upcoming2.put("chiefAnimationDirector", "八寻裕子");
        
        List<Map<String, Object>> upcoming2Chars = new ArrayList<>();
        Map<String, Object> upcoming2Char1 = new LinkedHashMap<>();
        upcoming2Char1.put("name", "四宫辉夜");
        upcoming2Char1.put("voiceActor", "古贺葵");
        upcoming2Chars.add(upcoming2Char1);
        Map<String, Object> upcoming2Char2 = new LinkedHashMap<>();
        upcoming2Char2.put("name", "白银御行");
        upcoming2Char2.put("voiceActor", "古川慎");
        upcoming2Chars.add(upcoming2Char2);
        Map<String, Object> upcoming2Char3 = new LinkedHashMap<>();
        upcoming2Char3.put("name", "藤原千花");
        upcoming2Char3.put("voiceActor", "小原好美");
        upcoming2Chars.add(upcoming2Char3);
        upcoming2.put("characters", upcoming2Chars);
        
        upcoming2.put("officialWebsite", "https://kaguya-sama.jp/");
        
        List<Map<String, Object>> upcoming2Comments = new ArrayList<>();
        Map<String, Object> upcoming2Comment1 = new LinkedHashMap<>();
        upcoming2Comment1.put("id", 1);
        upcoming2Comment1.put("user", "辉夜粉");
        upcoming2Comment1.put("content", "终于在一起了！");
        upcoming2Comment1.put("date", "2026-04-02");
        upcoming2Comments.add(upcoming2Comment1);
        upcoming2.put("comments", upcoming2Comments);

        Map<String, Object> upcoming3 = new LinkedHashMap<>();
        upcoming3.put("id", 18);
        upcoming3.put("title", "呪術廻戦 第3期");
        upcoming3.put("chineseTitle", "咒术回战 第3期");
        upcoming3.put("image", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/amime_19.png");
        upcoming3.put("description", "涩谷事变后的新篇章，死灭回游篇");
        upcoming3.put("introduction", "《咒术回战 第3期》是芥见下下创作的漫画《咒术回战》的第三季动画，讲述了涩谷事变后，虎杖悠仁等人进入死灭回游游戏的故事。");
        upcoming3.put("studio", "MAPPA");
        upcoming3.put("tags", Arrays.asList("战斗", "热血", "超能力", "黑暗"));
        upcoming3.put("episodes", 24);
        upcoming3.put("startDate", "2026-10-01");
        upcoming3.put("broadcastDay", "星期四");
        upcoming3.put("originalWork", "芥见下下");
        upcoming3.put("director", "朴性厚");
        upcoming3.put("script", "濑古浩司");
        upcoming3.put("storyboard", "朴性厚");
        upcoming3.put("performance", "朴性厚");
        upcoming3.put("music", "堤博明、桶狭间ありさ");
        upcoming3.put("characterDesign", "平松祯史");
        upcoming3.put("seriesComposition", "濑古浩司");
        upcoming3.put("artDirector", "东润一");
        upcoming3.put("colorDesign", "镰田千贺子");
        upcoming3.put("chiefAnimationDirector", "平松祯史");
        
        List<Map<String, Object>> upcoming3Chars = new ArrayList<>();
        Map<String, Object> upcoming3Char1 = new LinkedHashMap<>();
        upcoming3Char1.put("name", "虎杖悠仁");
        upcoming3Char1.put("voiceActor", "榎木淳弥");
        upcoming3Chars.add(upcoming3Char1);
        Map<String, Object> upcoming3Char2 = new LinkedHashMap<>();
        upcoming3Char2.put("name", "伏黑惠");
        upcoming3Char2.put("voiceActor", "内田雄马");
        upcoming3Chars.add(upcoming3Char2);
        Map<String, Object> upcoming3Char3 = new LinkedHashMap<>();
        upcoming3Char3.put("name", "钉崎野蔷薇");
        upcoming3Char3.put("voiceActor", "濑户麻沙美");
        upcoming3Chars.add(upcoming3Char3);
        upcoming3.put("characters", upcoming3Chars);
        
        upcoming3.put("officialWebsite", "https://jujutsu-kaisen.jp/");
        
        List<Map<String, Object>> upcoming3Comments = new ArrayList<>();
        Map<String, Object> upcoming3Comment1 = new LinkedHashMap<>();
        upcoming3Comment1.put("id", 1);
        upcoming3Comment1.put("user", "咒术粉");
        upcoming3Comment1.put("content", "死灭回游篇终于来了！");
        upcoming3Comment1.put("date", "2026-04-03");
        upcoming3Comments.add(upcoming3Comment1);
        upcoming3.put("comments", upcoming3Comments);

        // Add lastEdit and type fields to all anime
        String currentTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        jojo.put("lastEdit", currentTime);
        jojo.put("type", "anime");
        aot.put("lastEdit", currentTime);
        aot.put("type", "anime");
        kny.put("lastEdit", currentTime);
        kny.put("type", "anime");
        onePiece.put("lastEdit", currentTime);
        onePiece.put("type", "anime");
        naruto.put("lastEdit", currentTime);
        naruto.put("type", "anime");
        tokyoGhoul.put("lastEdit", currentTime);
        tokyoGhoul.put("type", "anime");
        aotFinal.put("lastEdit", currentTime);
        aotFinal.put("type", "anime");
        sao.put("lastEdit", currentTime);
        sao.put("type", "anime");
        jujutsu.put("lastEdit", currentTime);
        jujutsu.put("type", "anime");
        violet.put("lastEdit", currentTime);
        violet.put("type", "anime");
        linglong.put("lastEdit", currentTime);
        linglong.put("type", "anime");
        tianGuan.put("lastEdit", currentTime);
        tianGuan.put("type", "anime");
        shiguang.put("lastEdit", currentTime);
        shiguang.put("type", "anime");
        luoXiaoHei.put("lastEdit", currentTime);
        luoXiaoHei.put("type", "anime");
        yiRen.put("lastEdit", currentTime);
        yiRen.put("type", "anime");
        upcoming1.put("lastEdit", currentTime);
        upcoming1.put("type", "anime");
        upcoming2.put("lastEdit", currentTime);
        upcoming2.put("type", "anime");
        upcoming3.put("lastEdit", currentTime);
        upcoming3.put("type", "anime");
        
        animeList.add(jojo);
        animeList.add(aot);
        animeList.add(kny);
        animeList.add(onePiece);
        animeList.add(naruto);
        animeList.add(tokyoGhoul);
        animeList.add(aotFinal);
        animeList.add(sao);
        animeList.add(jujutsu);
        animeList.add(violet);
        animeList.add(linglong);
        animeList.add(tianGuan);
        animeList.add(shiguang);
        animeList.add(luoXiaoHei);
        animeList.add(yiRen);
        animeList.add(upcoming1);
        animeList.add(upcoming2);
        animeList.add(upcoming3);
    }

    @GetMapping("/get")
    public List<Map<String, Object>> getAnimeList() {
        return animeList.stream()
                .map(anime -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", anime.get("id"));
                    listItem.put("title", anime.get("title"));
                    listItem.put("chineseTitle", anime.get("chineseTitle"));
                    listItem.put("image", anime.get("image"));
                    listItem.put("description", anime.get("description"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getAnimeById(@PathVariable int id) {
        return animeList.stream()
                .filter(anime -> (int) anime.get("id") == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/insert")
    public Map<String, Object> addAnime(@RequestBody Map<String, Object> animeData) {
        Map<String, Object> newAnime = new LinkedHashMap<>(animeData);
        newAnime.put("id", animeList.size() + 1);
        animeList.add(newAnime);
        return newAnime;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> updateAnime(@PathVariable int id, @RequestBody Map<String, Object> animeData) {
        for (Map<String, Object> anime : animeList) {
            if ((int) anime.get("id") == id) {
                anime.putAll(animeData);
                anime.put("lastEdit", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                // 增加编辑计数
                EditStatisticsService.incrementEditCount("anime");
                return anime;
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteAnime(@PathVariable int id) {
        Map<String, String> response = new LinkedHashMap<>();
        boolean removed = animeList.removeIf(anime -> (int) anime.get("id") == id);
        response.put("status", removed ? "success" : "error");
        return response;
    }

    @PostMapping("/addComment/{id}")
    public Map<String, Object> addComment(@PathVariable int id, @RequestBody Map<String, Object> commentData) {
        for (Map<String, Object> anime : animeList) {
            if ((int) anime.get("id") == id) {
                List<Map<String, Object>> comments = (List<Map<String, Object>>) anime.get("comments");
                if (comments == null) {
                    comments = new ArrayList<>();
                    anime.put("comments", comments);
                }
                commentData.put("id", comments.size() + 1);
                commentData.put("date", new Date().toString());
                comments.add(commentData);
                return commentData;
            }
        }
        return null;
    }

    @GetMapping("/get/random")
    public List<Map<String, Object>> getRandomAnime() {
        List<Map<String, Object>> shuffledList = new ArrayList<>(animeList);
        Collections.shuffle(shuffledList);
        return shuffledList.stream()
                .limit(10)
                .map(anime -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", anime.get("id"));
                    listItem.put("title", anime.get("title"));
                    listItem.put("chineseTitle", anime.get("chineseTitle"));
                    listItem.put("image", anime.get("image"));
                    listItem.put("description", anime.get("description"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/recent-updated")
    public List<Map<String, Object>> getRecentUpdatedAnime() {
        return animeList.stream()
                .sorted((a, b) -> {
                    String dateA = (String) a.get("lastEdit");
                    String dateB = (String) b.get("lastEdit");
                    if (dateA == null && dateB == null) return 0;
                    if (dateA == null) return 1;
                    if (dateB == null) return -1;
                    return dateB.compareTo(dateA);
                })
                .limit(5)
                .map(anime -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", anime.get("id"));
                    listItem.put("title", anime.get("title"));
                    listItem.put("chineseTitle", anime.get("chineseTitle"));
                    listItem.put("image", anime.get("image"));
                    listItem.put("description", anime.get("description"));
                    listItem.put("lastEdit", anime.get("lastEdit"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/search")
    public List<Map<String, Object>> searchAnime(@RequestParam String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return animeList.stream()
                .filter(anime -> {
                    // 搜索标题
                    String title = (String) anime.get("title");
                    if (title != null && title.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索中文标题
                    String chineseTitle = (String) anime.get("chineseTitle");
                    if (chineseTitle != null && chineseTitle.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索描述
                    String description = (String) anime.get("description");
                    if (description != null && description.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索简介
                    String introduction = (String) anime.get("introduction");
                    if (introduction != null && introduction.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索工作室
                    String studio = (String) anime.get("studio");
                    if (studio != null && studio.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索标签
                    List<String> tags = (List<String>) anime.get("tags");
                    if (tags != null) {
                        for (String tag : tags) {
                            if (tag.toLowerCase().contains(lowerKeyword)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .map(anime -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", anime.get("id"));
                    listItem.put("title", anime.get("title"));
                    listItem.put("chineseTitle", anime.get("chineseTitle"));
                    listItem.put("image", anime.get("image"));
                    listItem.put("description", anime.get("description"));
                    listItem.put("type", anime.get("type"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/latest")
    public List<Map<String, Object>> getLatestAnime() {
        return animeList.stream()
                .sorted((a, b) -> {
                    String dateA = (String) a.get("startDate");
                    String dateB = (String) b.get("startDate");
                    if (dateA == null && dateB == null) return 0;
                    if (dateA == null) return 1;
                    if (dateB == null) return -1;
                    return dateB.compareTo(dateA);
                })
                .limit(10)
                .map(anime -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", anime.get("id"));
                    listItem.put("title", anime.get("title"));
                    listItem.put("chineseTitle", anime.get("chineseTitle"));
                    listItem.put("image", anime.get("image"));
                    listItem.put("description", anime.get("description"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/random-tag")
    public Map<String, Object> getRandomTagAndAnime() {
        // 收集所有唯一的tag
        Set<String> allTags = new HashSet<>();
        for (Map<String, Object> anime : animeList) {
            List<String> tags = (List<String>) anime.get("tags");
            if (tags != null) {
                allTags.addAll(tags);
            }
        }
        
        // 随机选择一个tag
        List<String> tagList = new ArrayList<>(allTags);
        if (tagList.isEmpty()) {
            return null;
        }
        String randomTag = tagList.get(new Random().nextInt(tagList.size()));
        
        // 找出包含该tag的anime
        List<Map<String, Object>> animeWithTag = animeList.stream()
                .filter(anime -> {
                    List<String> tags = (List<String>) anime.get("tags");
                    return tags != null && tags.contains(randomTag);
                })
                .map(anime -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", anime.get("id"));
                    listItem.put("title", anime.get("title"));
                    listItem.put("chineseTitle", anime.get("chineseTitle"));
                    listItem.put("image", anime.get("image"));
                    listItem.put("description", anime.get("description"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
        
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("tag", randomTag);
        result.put("anime", animeWithTag);
        return result;
    }

    @GetMapping("/get/random-ratings")
    public List<Map<String, Object>> getRandomRatings() {
        // 收集所有的viewingRecords
        List<Map<String, Object>> allRatings = new ArrayList<>();
        for (Map<String, Object> anime : animeList) {
            List<Map<String, Object>> ratings = (List<Map<String, Object>>) anime.get("viewingRecords");
            if (ratings != null) {
                for (Map<String, Object> rating : ratings) {
                    // 添加anime信息到评价中，方便前端显示
                    rating.put("animeId", anime.get("id"));
                    rating.put("animeTitle", anime.get("title"));
                    rating.put("animeChineseTitle", anime.get("chineseTitle"));
                    allRatings.add(rating);
                }
            }
        }
        
        // 随机打乱并取前10个
        Collections.shuffle(allRatings);
        return allRatings.stream()
                .limit(10)
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/upcoming")
    public List<Map<String, Object>> getUpcomingAnime() {
        String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return animeList.stream()
                .filter(anime -> {
                    String startDate = (String) anime.get("startDate");
                    return startDate != null && startDate.compareTo(today) > 0;
                })
                .sorted((a, b) -> {
                    String dateA = (String) a.get("startDate");
                    String dateB = (String) b.get("startDate");
                    if (dateA == null && dateB == null) return 0;
                    if (dateA == null) return 1;
                    if (dateB == null) return -1;
                    return dateA.compareTo(dateB);
                })
                .map(anime -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", anime.get("id"));
                    listItem.put("title", anime.get("title"));
                    listItem.put("chineseTitle", anime.get("chineseTitle"));
                    listItem.put("image", anime.get("image"));
                    listItem.put("description", anime.get("description"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }
}
