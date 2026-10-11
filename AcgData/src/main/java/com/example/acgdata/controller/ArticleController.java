package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import com.example.acgdata.service.EditStatisticsService;

@RestController
@RequestMapping("/api/article")
@CrossOrigin(origins = "*")
public class ArticleController {

    private List<Map<String, Object>> articleList = new ArrayList<>();

    public ArticleController() {
        initArticleData();
    }

    private void initArticleData() {
        Map<String, Object> article1 = new LinkedHashMap<>();
        article1.put("id", 1);
        article1.put("cover", "https://example.com/article1.jpg");
        article1.put("title", "国gal的\"武汉帮\"是否真实存在?");
        article1.put("content", "当这个问题被提出时，我们需要的不是去证明或证伪它。因为这个问题根本就没办法证明或证伪，除非你能在使用了企查查之外的工具后，偷偷摸进lsp的公司办公室然后偷走他们的财务报表，于是你想着终于我终于能昭告天下lsp到底资助了哪些人，武汉帮到底有哪些成员，然后号召本就分裂的玩家/消费者群体抵制他们，然而这并不可能，你无法在一个令人不安的政治隐喻里找到这个不存在的问题答案。\n\n\"帮\"一词本身就具有非正式的政治团体的意思，举个例子来说就是……我擦我不好说，换句话说，把因可能拥有共同的经济利益的数家公司称作\"帮派\"是完全错误的，这是在把问题复杂化和神秘化以及政治化，除传播错误信息和不必要的恐慌外，还会导致政治运动中普遍发生的扩大化，实际也就是把一切可能与lsp有关系的制作组通通丢进\"武汉帮\"这个垃圾桶，然后你指着这个垃圾桶说：'瞅天呐是他们在破坏国gal的环境！大伙快快抵制他们！'这并不能对改善国gal，或者往大了说，改善整个gal圈的环境有一丝一毫的作用。这难道不是正中了他们黑红炒作的下怀吗？至于正确的做法，我也不知道，就像某人说过的，当下不存在整体的国gal，只有一个个制作组和他们本就少的可怜的粉丝群体。");
        article1.put("author", "咕噜咕噜");
        article1.put("authorAvatar", "https://example.com/avatar1.jpg");
        article1.put("publishDate", "2026-02-13");
        article1.put("readCount", 1106);
        article1.put("likeCount", 3);
        article1.put("summary", "一些思考，当作发牢骚吧");
        article1.put("lastEdit", "2026年2月13日 18:26:13");
        article1.put("type", "article");
        
        List<Map<String, Object>> comments1 = new ArrayList<>();
        Map<String, Object> comment1 = new LinkedHashMap<>();
        comment1.put("id", 1);
        comment1.put("user", "读者1");
        comment1.put("content", "说得很有道理！");
        comment1.put("date", "2026-02-14");
        comments1.add(comment1);
        Map<String, Object> comment2 = new LinkedHashMap<>();
        comment2.put("id", 2);
        comment2.put("user", "读者2");
        comment2.put("content", "分析得很透彻，支持作者！");
        comment2.put("date", "2026-02-15");
        comments1.add(comment2);
        article1.put("comments", comments1);

        Map<String, Object> article2 = new LinkedHashMap<>();
        article2.put("id", 2);
        article2.put("cover", "https://example.com/article2.jpg");
        article2.put("title", "2026年春季新番前瞻");
        article2.put("content", "2026年春季档有很多值得期待的新番，包括《鬼灭之刃 柱训练篇》、《辉夜大小姐想让我告白-超级浪漫-》等。本文将为大家详细介绍这些新番的看点和播出时间。\n\n首先是《鬼灭之刃 柱训练篇》，作为鬼灭之刃系列的续作，这一部将聚焦于鬼杀队柱们的特训，为最终的无限城决战做准备。ufotable的制作质量一直为人称道，相信这一部也不会让观众失望。\n\n然后是《辉夜大小姐想让我告白-超级浪漫-》，这是该系列的最终季，白银御行与四宫辉夜终于确认了彼此的心意，故事将迎来圆满的结局。");
        article2.put("author", "动漫评论家");
        article2.put("authorAvatar", "https://example.com/avatar2.jpg");
        article2.put("publishDate", "2026-03-01");
        article2.put("readCount", 856);
        article2.put("likeCount", 12);
        article2.put("summary", "2026年春季新番推荐");
        article2.put("lastEdit", "2026年3月1日 10:00:00");
        article2.put("type", "article");
        
        List<Map<String, Object>> comments2 = new ArrayList<>();
        Map<String, Object> comment21 = new LinkedHashMap<>();
        comment21.put("id", 1);
        comment21.put("user", "动漫爱好者");
        comment21.put("content", "期待春季新番！");
        comment21.put("date", "2026-03-02");
        comments2.add(comment21);
        Map<String, Object> comment22 = new LinkedHashMap<>();
        comment22.put("id", 2);
        comment22.put("user", "新番追踪者");
        comment22.put("content", "分析得很详细，感谢分享！");
        comment22.put("date", "2026-03-03");
        comments2.add(comment22);
        article2.put("comments", comments2);

        Map<String, Object> article3 = new LinkedHashMap<>();
        article3.put("id", 3);
        article3.put("cover", "https://example.com/article3.jpg");
        article3.put("title", "国漫崛起：从《一人之下》到《罗小黑战记》");
        article3.put("content", "近年来，国漫行业发展迅速，涌现出了许多优秀的作品。本文将探讨国漫的发展历程和未来趋势。\n\n《一人之下》作为国漫的代表作之一，以其独特的世界观和精彩的剧情吸引了大量粉丝。而《罗小黑战记》则以其可爱的画风和温馨的故事赢得了观众的喜爱。\n\n随着技术的不断进步和制作水平的提高，国漫正在逐渐走向世界舞台。相信在不久的将来，我们会看到更多优秀的国漫作品。");
        article3.put("author", "国漫爱好者");
        article3.put("authorAvatar", "https://example.com/avatar3.jpg");
        article3.put("publishDate", "2026-03-15");
        article3.put("readCount", 623);
        article3.put("likeCount", 8);
        article3.put("summary", "国漫发展现状分析");
        article3.put("lastEdit", "2026年3月15日 15:30:00");
        article3.put("type", "article");
        
        List<Map<String, Object>> comments3 = new ArrayList<>();
        Map<String, Object> comment31 = new LinkedHashMap<>();
        comment31.put("id", 1);
        comment31.put("user", "国漫支持者");
        comment31.put("content", "国漫确实在崛起！");
        comment31.put("date", "2026-03-16");
        comments3.add(comment31);
        Map<String, Object> comment32 = new LinkedHashMap<>();
        comment32.put("id", 2);
        comment32.put("user", "动漫评论家");
        comment32.put("content", "分析得很到位，期待更多优秀国漫！");
        comment32.put("date", "2026-03-17");
        comments3.add(comment32);
        article3.put("comments", comments3);

        articleList.add(article1);
        articleList.add(article2);
        articleList.add(article3);

        Map<String, Object> article4 = new LinkedHashMap<>();
        article4.put("id", 4);
        article4.put("cover", "https://example.com/article4.jpg");
        article4.put("title", "独立制作人所遭遇的编剧困境");
        article4.put("content", "这是我第一次发帖……算得上是求助帖，可能有些言语混乱的地方，希望大家多多包涵\n\n某种意义上来说，我算是个失败的制作人。虽然是独自创作，但我耗费了极多的隐形成本——时间\n\n从22年的5月份左右，我便开始构思一部名为《迷城·失落之城》的galgame计划，那时的我在上高一；在后来的日子里，我接连学习了编剧，板绘，编曲等等内容，勉强称得上及格，但在剧本方面我却始终不满意\n\n到了现在，过去了将近四年，我做出了整整7个版本的共通线……没错，整整7个版本，这些将被抛弃的脚本，加起来足足有40万字，如果算上提纲，角色设定，背景故事等等，恐怕要奔着50万字去了——这可是40万字的废稿啊！但不知为什么，我似乎卡在了某个瓶颈，无论如何都是在原地踏步，因此决定向社区求助\n\n当然，实现说明，我一定会坚持下去的，目前时间站在我这边……我已经决定休学了，距离我的预计发布还有接近两年的时间，还有机会\n\n这里只讨论编剧上的问题，如果你要好的见解，请尽情的批判我\n\n\n问题的起源要更早\n\n人在初出茅庐的阶段总是雄心壮志的；在还没有准备做gal时（也就是刚上高中那会），我曾在脑海里设计过一个庞大的世界观。它的设定严谨，世界观庞大，但它并不适用于galgame。不幸的是，下定决心做galgame的我使用了这个巨大的架空世界观\n\n这就是我遇到的首个问题——\"设定\"对于剧本篇幅的占据过于巨大\n\n举个相对简单的例子（包含部分剧透），男主拥有制造幻觉的超能力，这很好理解。但他的超能力来源于\"暗粒子感染\"，而但是感染这一条，就能牵扯出包括\"黑暗生命\"（怪物），屏障，夜晚致死，永暗，疫情等等内容，而它们都来自于这个世界观的底层逻辑——光暗粒子。但是我请问了，这俩玩意跟男主谈恋爱有联系吗……o_o");
        article4.put("author", "四大鬼王之首");
        article4.put("authorAvatar", "https://example.com/avatar4.jpg");
        article4.put("publishDate", "2026-01-28");
        article4.put("readCount", 1086);
        article4.put("likeCount", 4);
        article4.put("summary", "独立制作人的编剧困境与求助");
        article4.put("lastEdit", "2026年1月28日 00:00:00");
        article4.put("type", "article");
        
        List<Map<String, Object>> comments4 = new ArrayList<>();
        Map<String, Object> comment4 = new LinkedHashMap<>();
        comment4.put("id", 1);
        comment4.put("user", "(^_^)**");
        comment4.put("userAvatar", "https://example.com/comment-avatar1.jpg");
        comment4.put("content", "不是老资历（给老资历跪了），这里来聊聊。\n\n首先其一：关于设定的问题，在我看来设定详细且环环相扣其实是好事（对于创作IP而非故事而言），对于世界观的构建其实是相当有帮助的。\n\n其实这里可以考虑一下‘古早’的做法？\n\n出一个配套的设定集，但问题就是太‘古早’了。\n\n或者干脆不解释只暗示，只有非常重要的内容才‘借他人之口/世界观细节’来解释。\n\n其二：男主戏份太多……女主是做什么的？起什么作用？\n\n这样想想看能不能把女主‘揉’进去？要是无论如何都和女主关系不大，那就‘女路人’了（笑）。\n\n既然提到和‘记忆’有关，那女主在这里面起到什么作用呢？\n\n既然对逻辑合理性有要求，这么想想或许有帮助？\n\n当然实在不行就只能把女主‘砍’掉了（笑死）。");
        comment4.put("date", "2026-01-31 01:53");
        comment4.put("replyCount", 0);
        comments4.add(comment4);
        Map<String, Object> comment42 = new LinkedHashMap<>();
        comment42.put("id", 2);
        comment42.put("user", "独立游戏开发者");
        comment42.put("userAvatar", "https://example.com/comment-avatar2.jpg");
        comment42.put("content", "作为同样的独立开发者，我非常理解你的困境。设定过于庞大确实会影响剧情发展，建议先专注于核心故事，设定可以在后续慢慢补充。");
        comment42.put("date", "2026-02-01 10:20");
        comment42.put("replyCount", 0);
        comments4.add(comment42);
        article4.put("comments", comments4);

        articleList.add(article4);
    }

    @GetMapping("/get")
    public List<Map<String, Object>> getArticleList() {
        return articleList.stream()
                .map(article -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", article.get("id"));
                    listItem.put("cover", article.get("cover"));
                    listItem.put("title", article.get("title"));
                    listItem.put("summary", article.get("summary"));
                    listItem.put("author", article.get("author"));
                    listItem.put("authorAvatar", article.get("authorAvatar"));
                    listItem.put("publishDate", article.get("publishDate"));
                    listItem.put("readCount", article.get("readCount"));
                    listItem.put("likeCount", article.get("likeCount"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getArticleById(@PathVariable int id) {
        return articleList.stream()
                .filter(article -> (int) article.get("id") == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/insert")
    public Map<String, Object> addArticle(@RequestBody Map<String, Object> articleData) {
        Map<String, Object> newArticle = new LinkedHashMap<>(articleData);
        newArticle.put("id", articleList.size() + 1);
        newArticle.put("readCount", 0);
        newArticle.put("likeCount", 0);
        newArticle.put("publishDate", new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        newArticle.put("lastEdit", new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        newArticle.put("cover", articleData.getOrDefault("cover", "https://example.com/default-cover.jpg"));
        newArticle.put("authorAvatar", articleData.getOrDefault("authorAvatar", "https://example.com/default-avatar.jpg"));
        newArticle.put("type", "article");
        newArticle.put("comments", new ArrayList<Map<String, Object>>());
        articleList.add(newArticle);
        return newArticle;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> updateArticle(@PathVariable int id, @RequestBody Map<String, Object> articleData) {
        for (Map<String, Object> article : articleList) {
            if ((int) article.get("id") == id) {
                article.putAll(articleData);
                article.put("lastEdit", new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                // 增加编辑计数
                EditStatisticsService.incrementEditCount("article");
                return article;
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteArticle(@PathVariable int id) {
        Map<String, String> response = new LinkedHashMap<>();
        boolean removed = articleList.removeIf(article -> (int) article.get("id") == id);
        response.put("status", removed ? "success" : "error");
        return response;
    }

    @PostMapping("/addComment/{id}")
    public Map<String, Object> addComment(@PathVariable int id, @RequestBody Map<String, Object> commentData) {
        for (Map<String, Object> article : articleList) {
            if ((int) article.get("id") == id) {
                List<Map<String, Object>> comments = (List<Map<String, Object>>) article.get("comments");
                if (comments == null) {
                    comments = new ArrayList<>();
                    article.put("comments", comments);
                }
                commentData.put("id", comments.size() + 1);
                commentData.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                comments.add(commentData);
                return commentData;
            }
        }
        return null;
    }

    @PostMapping("/like/{id}")
    public Map<String, Object> likeArticle(@PathVariable int id) {
        for (Map<String, Object> article : articleList) {
            if ((int) article.get("id") == id) {
                int likeCount = (int) article.get("likeCount");
                article.put("likeCount", likeCount + 1);
                return article;
            }
        }
        return null;
    }

    @PostMapping("/view/{id}")
    public Map<String, Object> viewArticle(@PathVariable int id) {
        for (Map<String, Object> article : articleList) {
            if ((int) article.get("id") == id) {
                int readCount = (int) article.get("readCount");
                article.put("readCount", readCount + 1);
                return article;
            }
        }
        return null;
    }

    @GetMapping("/search")
    public List<Map<String, Object>> searchArticle(@RequestParam String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return articleList.stream()
                .filter(article -> {
                    // 搜索标题
                    String title = (String) article.get("title");
                    if (title != null && title.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索内容
                    String content = (String) article.get("content");
                    if (content != null && content.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索作者
                    String author = (String) article.get("author");
                    if (author != null && author.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索摘要
                    String summary = (String) article.get("summary");
                    if (summary != null && summary.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    return false;
                })
                .map(article -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", article.get("id"));
                    listItem.put("cover", article.get("cover"));
                    listItem.put("title", article.get("title"));
                    listItem.put("author", article.get("author"));
                    listItem.put("authorAvatar", article.get("authorAvatar"));
                    listItem.put("publishDate", article.get("publishDate"));
                    listItem.put("readCount", article.get("readCount"));
                    listItem.put("likeCount", article.get("likeCount"));
                    listItem.put("summary", article.get("summary"));
                    listItem.put("type", article.get("type"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }
}