package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import com.example.acgdata.service.EditStatisticsService;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    private List<Map<String, Object>> videoList = new ArrayList<>();

    public VideoController() {
        initVideoData();
    }

    private void initVideoData() {
        Map<String, Object> video1 = new LinkedHashMap<>();
        video1.put("id", 1);
        video1.put("thumbnail","https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_1.png");
        video1.put("title", "『꯭M꯭O꯭M꯭』꯭天꯭空꯭是꯭蔚꯭蓝꯭色꯭，꯭窗꯭外꯭有꯭千꯭纸꯭鹤꯭！");
        video1.put("author", "三十六贱笑");
        video1.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar.jpg");
        video1.put("authorRole", "创作者");
        video1.put("authorLevel", 6);
        video1.put("authorBio", "热爱AMV制作的UP主，擅长情感向混剪，希望用视频传递温暖与感动");
        video1.put("publishDate", "2021-02-08 10:08:21");
        video1.put("description", "这次也是很用心的作品，三十六贱笑处刑曲！！！！希望多来个三连！！！带给妈妈的一首歌！BGM：MOM 辛苦审核大大了，如果有任何问题，请务必打回，我会认真修改的！");
        video1.put("externalLink", "https://www.bilibili.com/video/BV1qv411e77m");
        video1.put("relatedTags", Arrays.asList("MOM", "AMV", "治愈", "亲情"));
        video1.put("type", "video");
        
        List<Map<String, Object>> relatedAnime1 = new ArrayList<>();
        Map<String, Object> anime1 = new LinkedHashMap<>();
        anime1.put("id", 1);
        anime1.put("title", "ジョジョの奇妙な冒険");
        anime1.put("chineseTitle", "JOJO的奇妙冒险");
        anime1.put("image", "https://example.com/jojo.jpg");
        anime1.put("description", "讲述乔斯达家族与迪奥·布兰度之间跨越 generations 的宿命对决");
        relatedAnime1.add(anime1);
        video1.put("relatedAnime", relatedAnime1);
        
        List<Map<String, Object>> comments1 = new ArrayList<>();
        Map<String, Object> comment1 = new LinkedHashMap<>();
        comment1.put("id", 1);
        comment1.put("user", "二次元爱好者");
        comment1.put("userAvatar", "https://i0.hdslb.com/bfs/face/user1.jpg");
        comment1.put("content", "看哭了，太感人了！");
        comment1.put("date", "2021-02-08 12:30:00");
        comment1.put("likeCount", 523);
        comments1.add(comment1);
        Map<String, Object> comment12 = new LinkedHashMap<>();
        comment12.put("id", 2);
        comment12.put("user", "音乐爱好者");
        comment12.put("userAvatar", "https://i0.hdslb.com/bfs/face/user2.jpg");
        comment12.put("content", "BGM选得太好了，配合画面完美！");
        comment12.put("date", "2021-02-08 14:20:00");
        comment12.put("likeCount", 345);
        comments1.add(comment12);
        video1.put("comments", comments1);
        videoList.add(video1);

        Map<String, Object> video2 = new LinkedHashMap<>();
        video2.put("id", 2);
        video2.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_2.png");
        video2.put("title", "【你的名字/蓝光画质1080P/前前前世】AE渲染超爆炸燃向MAD·AMV");
        video2.put("author", "一只小小姚");
        video2.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar2.jpg");
        video2.put("authorRole", "创作者");
        video2.put("authorLevel", 5);
        video2.put("authorBio", "专注于新海诚作品的AMV创作，追求极致的画面表现和情感表达");
        video2.put("publishDate", "2017-09-19 14:22:33");
        video2.put("description", "耗时三个月制作，使用AE进行后期渲染，1080P蓝光画质呈现《你的名字》经典场景，搭配RADWIMPS的前前前世，带你重温那份感动。");
        video2.put("externalLink", "https://www.bilibili.com/video/BV1Nx411x73X");
        video2.put("relatedTags", Arrays.asList("你的名字", "新海诚", "RADWIMPS", "前前前世"));
        video2.put("type", "video");
        
        List<Map<String, Object>> relatedAnime2 = new ArrayList<>();
        Map<String, Object> anime2 = new LinkedHashMap<>();
        anime2.put("id", 4);
        anime2.put("title", "君の名は。");
        anime2.put("chineseTitle", "你的名字");
        anime2.put("image", "https://example.com/yourname.jpg");
        anime2.put("description", "关于时间穿越和交换身体的奇幻爱情故事");
        relatedAnime2.add(anime2);
        video2.put("relatedAnime", relatedAnime2);
        
        List<Map<String, Object>> comments2 = new ArrayList<>();
        Map<String, Object> comment2 = new LinkedHashMap<>();
        comment2.put("id", 1);
        comment2.put("user", "宫水三叶");
        comment2.put("userAvatar", "https://i0.hdslb.com/bfs/face/user2.jpg");
        comment2.put("content", "画质炸裂！剪辑节奏太棒了！");
        comment2.put("date", "2017-09-20 09:15:00");
        comment2.put("likeCount", 892);
        comments2.add(comment2);
        Map<String, Object> comment22 = new LinkedHashMap<>();
        comment22.put("id", 2);
        comment22.put("user", "新海诚粉丝");
        comment22.put("userAvatar", "https://i0.hdslb.com/bfs/face/user3.jpg");
        comment22.put("content", "新海诚的作品配上RADWIMPS的音乐，简直绝配！");
        comment22.put("date", "2017-09-20 11:30:00");
        comment22.put("likeCount", 678);
        comments2.add(comment22);
        video2.put("comments", comments2);
        videoList.add(video2);

        Map<String, Object> video3 = new LinkedHashMap<>();
        video3.put("id", 3);
        video3.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_3.png");
        video3.put("title", "【4K】收藏级 刀剑神域 Alicization NCOP1 「ADAMAS」完整版");
        video3.put("author", "Sallos");
        video3.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar3.jpg");
        video3.put("authorRole", "创作者");
        video3.put("authorLevel", 6);
        video3.put("authorBio", "专注于动漫音乐和高画质内容分享，致力于为大家带来最好的视听体验");
        video3.put("publishDate", "2022-07-26 18:30:00");
        video3.put("description", "4K 60帧收藏级画质，刀剑神域Alicization篇NCOP1「ADAMAS」完整版，LiSA演唱，极致视听体验。");
        video3.put("externalLink", "https://www.bilibili.com/video/BV1pS4y1t7Lz");
        video3.put("relatedTags", Arrays.asList("刀剑神域", "SAO", "LiSA", "4K"));
        video3.put("type", "video");
        
        List<Map<String, Object>> relatedAnime3 = new ArrayList<>();
        Map<String, Object> anime3 = new LinkedHashMap<>();
        anime3.put("id", 3);
        anime3.put("title", "ソードアート・オンライン");
        anime3.put("chineseTitle", "刀剑神域");
        anime3.put("image", "https://example.com/sao.jpg");
        anime3.put("description", "关于VRMMORPG中被困玩家的生存冒险故事");
        relatedAnime3.add(anime3);
        video3.put("relatedAnime", relatedAnime3);
        
        List<Map<String, Object>> comments3 = new ArrayList<>();
        Map<String, Object> comment3 = new LinkedHashMap<>();
        comment3.put("id", 1);
        comment3.put("user", "桐人君");
        comment3.put("userAvatar", "https://i0.hdslb.com/bfs/face/user3.jpg");
        comment3.put("content", "4K画质太清晰了，收藏了！");
        comment3.put("date", "2022-07-26 20:00:00");
        comment3.put("likeCount", 1205);
        comments3.add(comment3);
        video3.put("comments", comments3);
        videoList.add(video3);

        Map<String, Object> video4 = new LinkedHashMap<>();
        video4.put("id", 4);
        video4.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_4.png");
        video4.put("title", "曾经火遍全网的《Barricades》带你燃爆全场");
        video4.put("author", "爱喝阿宅的可乐");
        video4.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar4.jpg");
        video4.put("authorRole", "创作者");
        video4.put("authorLevel", 5);
        video4.put("authorBio", "进击的巨人忠实粉丝，热爱泽野弘之的音乐，专注于高燃混剪创作");
        video4.put("publishDate", "2020-05-10 12:00:00");
        video4.put("description", "进击的巨人经典插曲《Barricades》高燃混剪，泽野弘之作曲，带你重温调查兵团的英勇战斗。");
        video4.put("externalLink", "https://www.bilibili.com/video/BV1az4y1R7DA");
        video4.put("relatedTags", Arrays.asList("进击的巨人", "泽野弘之", "Barricades", "高燃"));
        video4.put("type", "video");
        
        List<Map<String, Object>> comments4 = new ArrayList<>();
        Map<String, Object> comment4 = new LinkedHashMap<>();
        comment4.put("id", 1);
        comment4.put("user", "自由之翼");
        comment4.put("userAvatar", "https://i0.hdslb.com/bfs/face/user4.jpg");
        comment4.put("content", "心脏撒撒给哟！");
        comment4.put("date", "2020-05-10 15:30:00");
        comment4.put("likeCount", 3421);
        comments4.add(comment4);
        video4.put("comments", comments4);
        videoList.add(video4);

        Map<String, Object> video5 = new LinkedHashMap<>();
        video5.put("id", 5);
        video5.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_5.png");
        video5.put("title", "周杰伦新歌《圣诞星》改编翻唱 改成这样实属胆大妄为");
        video5.put("author", "大力【翻唱】");
        video5.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar5.jpg");
        video5.put("authorRole", "创作者");
        video5.put("authorLevel", 6);
        video5.put("authorBio", "专业翻唱歌手，擅长改编流行歌曲，赋予经典作品新的生命力");
        video5.put("publishDate", "2023-12-25 18:00:00");
        video5.put("description", "大胆改编周杰伦最新单曲《圣诞星》，加入Rap元素和和声编排，给经典带来全新演绎。");
        video5.put("externalLink", "https://www.bilibili.com/video/BV1bN4y1s7uZ");
        video5.put("relatedTags", Arrays.asList("周杰伦", "圣诞星", "翻唱", "改编"));
        video5.put("type", "video");
        
        List<Map<String, Object>> comments5 = new ArrayList<>();
        Map<String, Object> comment5 = new LinkedHashMap<>();
        comment5.put("id", 1);
        comment5.put("user", "杰迷小王");
        comment5.put("userAvatar", "https://i0.hdslb.com/bfs/face/user5.jpg");
        comment5.put("content", "改编得很有创意，Rap部分很惊艳！");
        comment5.put("date", "2023-12-25 20:00:00");
        comment5.put("likeCount", 1567);
        comments5.add(comment5);
        video5.put("comments", comments5);
        videoList.add(video5);

        Map<String, Object> video6 = new LinkedHashMap<>();
        video6.put("id", 6);
        video6.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_6.png");
        video6.put("title", "【DARLING in the FRANXX/AMV/高甜】你就是我的darling吗？");
        video6.put("author", "seiya呐");
        video6.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar6.jpg");
        video6.put("authorRole", "创作者");
        video6.put("authorLevel", 4);
        video6.put("authorBio", "国家队忠实粉丝，专注于DARLING in the FRANXX相关内容创作");
        video6.put("publishDate", "2019-07-03 16:45:00");
        video6.put("description", "DARLING in the FRANXX高甜混剪，02与广的爱情故事，比翼之鸟，不飞则已，一飞冲天。");
        video6.put("externalLink", "https://www.bilibili.com/video/BV1Lx411973m");
        video6.put("relatedTags", Arrays.asList("DARLING in the FRANXX", "02", "国家队", "恋爱"));
        video6.put("type", "video");
        
        List<Map<String, Object>> comments6 = new ArrayList<>();
        Map<String, Object> comment6 = new LinkedHashMap<>();
        comment6.put("id", 1);
        comment6.put("user", "比翼之鸟");
        comment6.put("userAvatar", "https://i0.hdslb.com/bfs/face/user6.jpg");
        comment6.put("content", "02天下第一！");
        comment6.put("date", "2019-07-03 18:00:00");
        comment6.put("likeCount", 2103);
        comments6.add(comment6);
        video6.put("comments", comments6);
        videoList.add(video6);

        Map<String, Object> video7 = new LinkedHashMap<>();
        video7.put("id", 7);
        video7.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_7.png");
        video7.put("title", "当白金之星愣住的那一刻，我就知道他已经回不来了");
        video7.put("author", "JO厨阿强");
        video7.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar7.jpg");
        video7.put("authorRole", "创作者");
        video7.put("authorLevel", 5);
        video7.put("authorBio", "JOJO忠实粉丝，专注于JOJO系列的混剪创作，尤其是星尘远征军相关内容");
        video7.put("publishDate", "2022-08-15 14:20:00");
        video7.put("description", "JOJO的奇妙冒险星尘远征军催泪混剪，承太郎与白金之星的最终之战，致敬永远的星尘十字军。");
        video7.put("externalLink", "https://www.bilibili.com/video/BV15H4y1m7t2/");
        video7.put("relatedTags", Arrays.asList("JOJO的奇妙冒险", "空条承太郎", "白金之星", "催泪"));
        video7.put("type", "video");
        
        List<Map<String, Object>> comments7 = new ArrayList<>();
        Map<String, Object> comment7 = new LinkedHashMap<>();
        comment7.put("id", 1);
        comment7.put("user", "替身使者");
        comment7.put("userAvatar", "https://i0.hdslb.com/bfs/face/user7.jpg");
        comment7.put("content", "欧拉欧拉欧拉！看哭了...");
        comment7.put("date", "2022-08-15 16:00:00");
        comment7.put("likeCount", 1876);
        comments7.add(comment7);
        video7.put("comments", comments7);
        videoList.add(video7);

        Map<String, Object> video8 = new LinkedHashMap<>();
        video8.put("id", 8);
        video8.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_8.png");
        video8.put("title", "这可能是你听过最稳的one last kiss高中生翻唱现场！");
        video8.put("author", "肆壬_Miznoe");
        video8.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar8.jpg");
        video8.put("authorRole", "创作者");
        video8.put("authorLevel", 4);
        video8.put("authorBio", "高中生翻唱歌手，热爱EVA和宇多田光的音乐，希望通过歌声传递情感");
        video8.put("publishDate", "2023-12-31 20:00:00");
        video8.put("description", "在学校元旦晚会上演唱《新世纪福音战士新剧场版：终》主题曲One Last Kiss，宇多田光经典之作。");
        video8.put("externalLink", "https://www.bilibili.com/video/BV1zw411g7vw");
        video8.put("relatedTags", Arrays.asList("EVA", "One Last Kiss", "宇多田光", "翻唱"));
        video8.put("type", "video");
        
        List<Map<String, Object>> comments8 = new ArrayList<>();
        Map<String, Object> comment8 = new LinkedHashMap<>();
        comment8.put("id", 1);
        comment8.put("user", "绫波丽");
        comment8.put("userAvatar", "https://i0.hdslb.com/bfs/face/user8.jpg");
        comment8.put("content", "声音好稳！卢浮宫走起！");
        comment8.put("date", "2023-12-31 22:00:00");
        comment8.put("likeCount", 2341);
        comments8.add(comment8);
        video8.put("comments", comments8);
        videoList.add(video8);

        Map<String, Object> video9 = new LinkedHashMap<>();
        video9.put("id", 9);
        video9.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_9.png");
        video9.put("title", "用二十部动漫唱一首《有点甜》，牛顿发现了万有引力，而我发现了你！");
        video9.put("author", "动漫唯美风");
        video9.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar9.jpg");
        video9.put("authorRole", "创作者");
        video9.put("authorLevel", 6);
        video9.put("authorBio", "热爱恋爱向动漫，专注于制作甜蜜向AMV，希望通过视频传递青春与爱情的美好");
        video9.put("publishDate", "2021-03-27 12:00:00");
        video9.put("description", "精选二十部恋爱向动漫，用《有点甜》串联起那些甜蜜的瞬间，每一帧都是青春的味道。");
        video9.put("externalLink", "https://www.bilibili.com/video/BV13v41187QM");
        video9.put("relatedTags", Arrays.asList("综漫", "恋爱", "有点甜", "MAD"));
        video9.put("type", "video");
        
        List<Map<String, Object>> comments9 = new ArrayList<>();
        Map<String, Object> comment9 = new LinkedHashMap<>();
        comment9.put("id", 1);
        comment9.put("user", "甜党首领");
        comment9.put("userAvatar", "https://i0.hdslb.com/bfs/face/user9.jpg");
        comment9.put("content", "太甜了！全程姨母笑！");
        comment9.put("date", "2021-03-27 14:30:00");
        comment9.put("likeCount", 1567);
        comments9.add(comment9);
        video9.put("comments", comments9);
        videoList.add(video9);

        Map<String, Object> video10 = new LinkedHashMap<>();
        video10.put("id", 10);
        video10.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_10.png");
        video10.put("title", "【铃芽之旅/Towards the Light】我害怕不再有草太的世界");
        video10.put("author", "怡宸");
        video10.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar10.jpg");
        video10.put("authorRole", "创作者");
        video10.put("authorLevel", 5);
        video10.put("authorBio", "新海诚作品爱好者，专注于情感向混剪创作，希望通过视频传递温暖与希望");
        video10.put("publishDate", "2023-03-26 10:00:00");
        video10.put("description", "新海诚最新力作《铃芽之旅》情感向混剪，铃芽与草太的关门之旅，献给所有经历过灾难与重建的人们。");
        video10.put("externalLink", "https://www.bilibili.com/video/BV1JX4y1R7qV");
        video10.put("relatedTags", Arrays.asList("铃芽之旅", "新海诚", "灾难", "治愈"));
        video10.put("type", "video");
        
        List<Map<String, Object>> comments10 = new ArrayList<>();
        Map<String, Object> comment10 = new LinkedHashMap<>();
        comment10.put("id", 1);
        comment10.put("user", "闭门师");
        comment10.put("userAvatar", "https://i0.hdslb.com/bfs/face/user10.jpg");
        comment10.put("content", "大臣我呀，没法去铃芽家一起生活了...");
        comment10.put("date", "2023-03-26 12:00:00");
        comment10.put("likeCount", 2890);
        comments10.add(comment10);
        video10.put("comments", comments10);
        videoList.add(video10);

        Map<String, Object> video11 = new LinkedHashMap<>();
        video11.put("id", 11);
        video11.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_11.png");
        video11.put("title", "挑战B站最强踩点！108部动画超燃混剪！！！");
        video11.put("author", "星铁代肝原神代肝糖糖");
        video11.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar11.jpg");
        video11.put("authorRole", "创作者");
        video11.put("authorLevel", 6);
        video11.put("authorBio", "二次元爱好者，专注于高燃综漫混剪，追求极致的踩点和视觉效果");
        video11.put("publishDate", "2020-11-13 18:00:00");
        video11.put("description", "耗时半年收集素材，精选108部动画进行超燃踩点混剪，每一秒都是经费燃烧，带你感受二次元的魅力。");
        video11.put("externalLink", "https://www.bilibili.com/video/BV1nV411a7jr");
        video11.put("relatedTags", Arrays.asList("综漫", "踩点", "高燃", "108部动画"));
        video11.put("type", "video");
        
        List<Map<String, Object>> comments11 = new ArrayList<>();
        Map<String, Object> comment11 = new LinkedHashMap<>();
        comment11.put("id", 1);
        comment11.put("user", "踩点狂魔");
        comment11.put("userAvatar", "https://i0.hdslb.com/bfs/face/user11.jpg");
        comment11.put("content", "这踩点太舒服了，全程鸡皮疙瘩！");
        comment11.put("date", "2020-11-13 20:00:00");
        comment11.put("likeCount", 4567);
        comments11.add(comment11);
        video11.put("comments", comments11);
        videoList.add(video11);

        Map<String, Object> video12 = new LinkedHashMap<>();
        video12.put("id", 12);
        video12.put("thumbnail", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/video_12.png");
        video12.put("title", "火爆全网的《就算你活在二次元》，鬼知道我看了几遍！！");
        video12.put("author", "果冻lui");
        video12.put("authorAvatar", "https://i0.hdslb.com/bfs/face/avatar12.jpg");
        video12.put("authorRole", "创作者");
        video12.put("authorLevel", 5);
        video12.put("authorBio", "二次元文化爱好者，专注于制作情感向综漫视频，希望通过视频连接更多同好");
        video12.put("publishDate", "2020-08-30 16:00:00");
        video12.put("description", "《就算你活在二次元》深情演绎，献给所有热爱二次元的朋友们，虽然我们相隔一个次元，但爱能跨越一切。");
        video12.put("externalLink", "https://www.bilibili.com/video/BV1Qv411y7jk");
        video12.put("relatedTags", Arrays.asList("二次元", "催泪", "综漫", "恋爱"));
        video12.put("type", "video");
        
        List<Map<String, Object>> comments12 = new ArrayList<>();
        Map<String, Object> comment12_2 = new LinkedHashMap<>();
        comment12_2.put("id", 1);
        comment12_2.put("user", "次元突破者");
        comment12_2.put("userAvatar", "https://i0.hdslb.com/bfs/face/user12.jpg");
        comment12_2.put("content", "泪目了，这就是我热爱二次元的原因！");
        comment12_2.put("date", "2020-08-30 18:00:00");
        comment12_2.put("likeCount", 3124);
        comments12.add(comment12_2);
        video12.put("comments", comments12);
        videoList.add(video12);
    }

    @GetMapping("/get")
    public List<Map<String, Object>> getLatestVideos() {
        return videoList.stream()
                .sorted((a, b) -> {
                    String dateA = (String) a.get("publishDate");
                    String dateB = (String) b.get("publishDate");
                    return dateB.compareTo(dateA);
                })
                .map(video -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", video.get("id"));
                    listItem.put("thumbnail", video.get("thumbnail"));
                    listItem.put("title", video.get("title"));
                    listItem.put("author", video.get("author"));
                    listItem.put("authorAvatar", video.get("authorAvatar"));
                    listItem.put("publishDate", video.get("publishDate"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getVideoById(@PathVariable int id) {
        return videoList.stream()
                .filter(video -> (int) video.get("id") == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/addComment/{id}")
    public Map<String, Object> addComment(@PathVariable int id, @RequestBody Map<String, Object> commentData) {
        for (Map<String, Object> video : videoList) {
            if ((int) video.get("id") == id) {
                List<Map<String, Object>> comments = (List<Map<String, Object>>) video.get("comments");
                Map<String, Object> newComment = new LinkedHashMap<>();
                newComment.put("id", comments.size() + 1);
                newComment.put("user", commentData.get("user"));
                newComment.put("userAvatar", commentData.getOrDefault("userAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/default-avatar.jpg"));
                newComment.put("content", commentData.get("content"));
                newComment.put("date", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                newComment.put("likeCount", 0);
                comments.add(newComment);
                return newComment;
            }
        }
        return null;
    }

    @PostMapping("/insert")
    public Map<String, Object> addVideo(@RequestBody Map<String, Object> videoData) {
        Map<String, Object> newVideo = new LinkedHashMap<>(videoData);
        newVideo.put("id", videoList.size() + 1);
        newVideo.put("publishDate", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        newVideo.put("authorAvatar", videoData.getOrDefault("authorAvatar", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/default-avatar.jpg"));
        newVideo.put("authorBio", videoData.getOrDefault("authorBio", "这个作者很懒，还没有填写自我介绍"));
        newVideo.put("type", "video");
        newVideo.put("comments", new ArrayList<Map<String, Object>>());
        videoList.add(newVideo);
        return newVideo;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> updateVideo(@PathVariable int id, @RequestBody Map<String, Object> videoData) {
        for (Map<String, Object> video : videoList) {
            if ((int) video.get("id") == id) {
                videoData.forEach((key, value) -> {
                    if (!"id".equals(key) && !"publishDate".equals(key)) {
                        video.put(key, value);
                    }
                });
                // 增加编辑计数
                EditStatisticsService.incrementEditCount("video");
                return video;
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteVideo(@PathVariable int id) {
        Map<String, Object> result = new LinkedHashMap<>();
        boolean removed = videoList.removeIf(video -> (int) video.get("id") == id);
        result.put("success", removed);
        result.put("message", removed ? "删除成功" : "视频不存在");
        return result;
    }

    @GetMapping("/search")
    public List<Map<String, Object>> searchVideo(@RequestParam String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return videoList.stream()
                .filter(video -> {
                    // 搜索标题
                    String title = (String) video.get("title");
                    if (title != null && title.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索描述
                    String description = (String) video.get("description");
                    if (description != null && description.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索作者
                    String author = (String) video.get("author");
                    if (author != null && author.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索标签
                    List<String> tags = (List<String>) video.get("relatedTags");
                    if (tags != null) {
                        for (String tag : tags) {
                            if (tag.toLowerCase().contains(lowerKeyword)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .map(video -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", video.get("id"));
                    listItem.put("title", video.get("title"));
                    listItem.put("thumbnail", video.get("thumbnail"));
                    listItem.put("description", video.get("description"));
                    listItem.put("duration", video.get("duration"));
                    listItem.put("views", video.get("views"));
                    listItem.put("likes", video.get("likes"));
                    listItem.put("publishDate", video.get("publishDate"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }
}
