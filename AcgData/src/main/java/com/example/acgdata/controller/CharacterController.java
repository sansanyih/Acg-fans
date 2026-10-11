package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import com.example.acgdata.service.EditStatisticsService;

@RestController
@RequestMapping("/api/character")
@CrossOrigin(origins = "*")
public class CharacterController {

    private List<Map<String, Object>> characterList = new ArrayList<>();

    public CharacterController() {
        initCharacterData();
    }

    private void initCharacterData() {
        // JOJO的奇妙冒险角色
        Map<String, Object> jotaro = new LinkedHashMap<>();
        jotaro.put("id", 1);
        jotaro.put("name", "空条承太郎");
        jotaro.put("japaneseName", "空条承太郎");
        jotaro.put("image", "https://example.com/jotaro.jpg");
        jotaro.put("animeId", 1);
        jotaro.put("animeTitle", "ジョジョの奇妙な冒険");
        jotaro.put("animeChineseTitle", "JOJO的奇妙冒险");
        jotaro.put("voiceActor", "小野大辅");
        jotaro.put("gender", "男");
        jotaro.put("age", 17);
        jotaro.put("height", "195cm");
        jotaro.put("weight", "82kg");
        jotaro.put("birthday", "02-28");
        jotaro.put("description", "空条承太郎是《JOJO的奇妙冒险》第三部星尘十字军的主角，以其标志性的帽子、校服和冷静的性格而闻名。他拥有强大的替身能力白金之星，能够以超高速进行攻击，后期更是觉醒了时间停止的能力。承太郎虽然外表冷漠，但内心充满正义感，总是在关键时刻保护同伴，是JOJO系列中最受欢迎的角色之一。他的口头禅\"欧拉欧拉\"和独特的战斗风格成为了动漫史上的经典。");
        jotaro.put("cv", "小野大辅");
        jotaro.put("likes", 12500);
        jotaro.put("dislikes", 120);
        jotaro.put("type", "character");
        characterList.add(jotaro);

        Map<String, Object> joseph = new LinkedHashMap<>();
        joseph.put("id", 2);
        joseph.put("name", "乔瑟夫·乔斯达");
        joseph.put("japaneseName", "ジョセフ·ジョースター");
        joseph.put("image", "https://example.com/joseph.jpg");
        joseph.put("animeId", 1);
        joseph.put("animeTitle", "ジョジョの奇妙な冒険");
        joseph.put("animeChineseTitle", "JOJO的奇妙冒险");
        joseph.put("voiceActor", "杉田智和");
        joseph.put("gender", "男");
        joseph.put("age", 68);
        joseph.put("height", "195cm");
        joseph.put("weight", "88kg");
        joseph.put("birthday", "09-27");
        joseph.put("description", "乔瑟夫·乔斯达是《JOJO的奇妙冒险》第二部战斗潮流的主角，也是第三部星尘十字军的重要成员。他性格幽默开朗，喜欢开玩笑，经常使用机智的战术和波纹气功战胜敌人。尽管年事已高，但在第三部中依然展现出过人的智慧和勇气，是团队中的智囊。乔瑟夫的口头禅\"你的下一句话是...\"成为了他的标志性特征，他的存在为整个系列增添了许多欢乐元素。");
        joseph.put("cv", "杉田智和");
        joseph.put("likes", 9800);
        joseph.put("dislikes", 85);
        joseph.put("type", "character");
        characterList.add(joseph);

        // 进击的巨人角色
        Map<String, Object> eren = new LinkedHashMap<>();
        eren.put("id", 3);
        eren.put("name", "艾伦·耶格尔");
        eren.put("japaneseName", "エレン·イェーガー");
        eren.put("image", "https://example.com/eren.jpg");
        eren.put("animeId", 2);
        eren.put("animeTitle", "進撃の巨人");
        eren.put("animeChineseTitle", "进击的巨人");
        eren.put("voiceActor", "梶裕贵");
        eren.put("gender", "男");
        eren.put("age", 19);
        eren.put("height", "183cm");
        eren.put("weight", "70kg");
        eren.put("birthday", "03-30");
        eren.put("description", "艾伦·耶格尔是《进击的巨人》的主角，从小生活在墙内的希干希纳区。母亲被巨人吃掉后，他立下誓言要消灭所有巨人。艾伦拥有进击的巨人、始祖巨人和战锤巨人的力量，是整个故事的核心人物。他经历了从热血少年到成熟领导者的转变，最终为了实现自己的理想而做出了重大牺牲。艾伦的成长历程充满了痛苦和挣扎，但他始终坚持自己的信念，成为了推动故事发展的关键力量。");
        eren.put("cv", "梶裕贵");
        eren.put("likes", 15600);
        eren.put("dislikes", 230);
        eren.put("type", "character");
        characterList.add(eren);

        Map<String, Object> mikasa = new LinkedHashMap<>();
        mikasa.put("id", 4);
        mikasa.put("name", "三笠·阿克曼");
        mikasa.put("japaneseName", "ミカサ·アッカーマン");
        mikasa.put("image", "https://example.com/mikasa.jpg");
        mikasa.put("animeId", 2);
        mikasa.put("animeTitle", "進撃の巨人");
        mikasa.put("animeChineseTitle", "进击的巨人");
        mikasa.put("voiceActor", "石川由依");
        mikasa.put("gender", "女");
        mikasa.put("age", 19);
        mikasa.put("height", "170cm");
        mikasa.put("weight", "68kg");
        mikasa.put("birthday", "02-10");
        mikasa.put("description", "三笠·阿克曼是艾伦的养妹，阿克曼一族的后裔，拥有超强的战斗能力。她从小失去父母，被艾伦的家人收养，因此对艾伦有着强烈的保护欲。三笠是调查兵团的精英战士，以其冷静的判断力和卓越的战斗技巧而闻名。她总是在关键时刻保护艾伦，是他最可靠的伙伴。三笠的围巾是艾伦送给她的礼物，成为了她的标志性物品，象征着她对艾伦的深厚感情。");
        mikasa.put("cv", "石川由依");
        mikasa.put("likes", 18900);
        mikasa.put("dislikes", 95);
        mikasa.put("type", "character");
        characterList.add(mikasa);

        // 鬼灭之刃角色
        Map<String, Object> tanjiro = new LinkedHashMap<>();
        tanjiro.put("id", 5);
        tanjiro.put("name", "灶门炭治郎");
        tanjiro.put("japaneseName", "竈門炭治郎");
        tanjiro.put("image", "https://example.com/tanjiro.jpg");
        tanjiro.put("animeId", 3);
        tanjiro.put("animeTitle", "鬼滅の刃");
        tanjiro.put("animeChineseTitle", "鬼灭之刃");
        tanjiro.put("voiceActor", "花江夏树");
        tanjiro.put("gender", "男");
        tanjiro.put("age", 15);
        tanjiro.put("height", "165cm");
        tanjiro.put("weight", "61kg");
        tanjiro.put("birthday", "12-27");
        tanjiro.put("description", "灶门炭治郎是《鬼灭之刃》的主角，一个心地善良的少年。全家被鬼杀害，唯一幸存的妹妹祢豆子也变成了鬼。为了让妹妹变回人类，炭治郎踏上了斩鬼之旅，加入了鬼杀队。他拥有敏锐的嗅觉和温柔的性格，即使面对鬼也能保持同情。炭治郎掌握了水之呼吸，并逐渐觉醒了日之呼吸的力量，成为了鬼杀队的希望。他的坚持和善良感染了许多人，是一个真正的英雄。");
        tanjiro.put("cv", "花江夏树");
        tanjiro.put("likes", 21000);
        tanjiro.put("dislikes", 75);
        tanjiro.put("type", "character");
        characterList.add(tanjiro);

        Map<String, Object> nezuko = new LinkedHashMap<>();
        nezuko.put("id", 6);
        nezuko.put("name", "灶门祢豆子");
        nezuko.put("japaneseName", "竈門禰豆子");
        nezuko.put("image", "https://example.com/nezuko.jpg");
        nezuko.put("animeId", 3);
        nezuko.put("animeTitle", "鬼滅の刃");
        nezuko.put("animeChineseTitle", "鬼灭之刃");
        nezuko.put("voiceActor", "鬼头明里");
        nezuko.put("gender", "女");
        nezuko.put("age", 14);
        nezuko.put("height", "153cm");
        nezuko.put("weight", "45kg");
        nezuko.put("birthday", "12-28");
        nezuko.put("description", "灶门祢豆子是炭治郎的妹妹，被鬼舞辻无惨变成了鬼，但在哥哥的保护下保持了人类的意识。她能够在阳光下活动，并且可以随意改变身体大小。祢豆子性格纯真善良，总是在关键时刻保护哥哥和同伴。她的存在是炭治郎前进的动力，也是鬼杀队与鬼之间桥梁的象征。祢豆子的可爱形象和强大实力深受观众喜爱，成为了《鬼灭之刃》中最受欢迎的角色之一。");
        nezuko.put("cv", "鬼头明里");
        nezuko.put("likes", 25000);
        nezuko.put("dislikes", 35);
        nezuko.put("type", "character");
        characterList.add(nezuko);
    }

    @GetMapping("/get")
    public List<Map<String, Object>> getCharacterList() {
        return characterList.stream()
                .map(character -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", character.get("id"));
                    listItem.put("name", character.get("name"));
                    listItem.put("image", character.get("image"));
                    listItem.put("animeChineseTitle", character.get("animeChineseTitle"));
                    listItem.put("voiceActor", character.get("voiceActor"));
                    listItem.put("likes", character.get("likes"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getCharacterById(@PathVariable int id) {
        return characterList.stream()
                .filter(character -> (int) character.get("id") == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/getByAnime/{animeId}")
    public List<Map<String, Object>> getCharactersByAnime(@PathVariable int animeId) {
        return characterList.stream()
                .filter(character -> (int) character.get("animeId") == animeId)
                .map(character -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", character.get("id"));
                    listItem.put("name", character.get("name"));
                    listItem.put("image", character.get("image"));
                    listItem.put("voiceActor", character.get("voiceActor"));
                    listItem.put("likes", character.get("likes"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @PostMapping("/insert")
    public Map<String, Object> addCharacter(@RequestBody Map<String, Object> characterData) {
        Map<String, Object> newCharacter = new LinkedHashMap<>(characterData);
        newCharacter.put("id", characterList.size() + 1);
        newCharacter.put("likes", 0);
        newCharacter.put("dislikes", 0);
        newCharacter.put("type", "character");
        characterList.add(newCharacter);
        return newCharacter;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> updateCharacter(@PathVariable int id, @RequestBody Map<String, Object> characterData) {
        for (Map<String, Object> character : characterList) {
            if ((int) character.get("id") == id) {
                character.putAll(characterData);
                // 增加编辑计数
                EditStatisticsService.incrementEditCount("character");
                return character;
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteCharacter(@PathVariable int id) {
        Map<String, String> response = new LinkedHashMap<>();
        boolean removed = characterList.removeIf(character -> (int) character.get("id") == id);
        response.put("success", String.valueOf(removed));
        response.put("message", removed ? "角色删除成功" : "角色不存在");
        return response;
    }

    @GetMapping("/search")
    public List<Map<String, Object>> searchCharacter(@RequestParam String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return characterList.stream()
                .filter(character -> {
                    // 搜索名称
                    String name = (String) character.get("name");
                    if (name != null && name.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索中文名称
                    String chineseName = (String) character.get("chineseName");
                    if (chineseName != null && chineseName.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索描述
                    String description = (String) character.get("description");
                    if (description != null && description.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索出处
                    String source = (String) character.get("source");
                    if (source != null && source.toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    // 搜索标签
                    List<String> tags = (List<String>) character.get("tags");
                    if (tags != null) {
                        for (String tag : tags) {
                            if (tag.toLowerCase().contains(lowerKeyword)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .map(character -> {
                    Map<String, Object> listItem = new LinkedHashMap<>();
                    listItem.put("id", character.get("id"));
                    listItem.put("name", character.get("name"));
                    listItem.put("chineseName", character.get("chineseName"));
                    listItem.put("image", character.get("image"));
                    listItem.put("description", character.get("description"));
                    listItem.put("source", character.get("source"));
                    return listItem;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @PostMapping("/like/{id}")
    public Map<String, Object> likeCharacter(@PathVariable int id) {
        for (Map<String, Object> character : characterList) {
            if ((int) character.get("id") == id) {
                int likes = (int) character.get("likes");
                character.put("likes", likes + 1);
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("success", true);
                result.put("likes", character.get("likes"));
                return result;
            }
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", false);
        result.put("message", "角色不存在");
        return result;
    }

    @PostMapping("/dislike/{id}")
    public Map<String, Object> dislikeCharacter(@PathVariable int id) {
        for (Map<String, Object> character : characterList) {
            if ((int) character.get("id") == id) {
                int dislikes = (int) character.get("dislikes");
                character.put("dislikes", dislikes + 1);
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("success", true);
                result.put("dislikes", character.get("dislikes"));
                return result;
            }
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", false);
        result.put("message", "角色不存在");
        return result;
    }
}