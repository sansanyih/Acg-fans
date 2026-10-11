package com.example.acgdata.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/links")
@CrossOrigin(origins = "*")
public class LinkController {

    private List<Map<String, Object>> linkList = new ArrayList<>();

    public LinkController() {
        // 初始化示例数据
        List<Map<String, Object>> initLinks = Arrays.asList(
                Map.of("id", 1, "name", "Bangumi番组计划", "url", "https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/bangumi.png", "link", "https://chii.in"),
                Map.of("id", 2, "name", "WebGAL", "url", "https://webgal.ink", "link", "https://webgal.ink"),
                Map.of("id", 3, "name", "HumiHumi", "url", "https://humihumi.org", "link", "https://humihumi.org"),
                Map.of("id", 4, "name", "末语引擎", "url", "https://moyu.moe", "link", "https://moyu.moe"),
                Map.of("id", 5, "name", "Nanovel(中文站)", "url", "https://nanovel.net", "link", "https://nanovel.net"),
                Map.of("id", 6, "name", "NGAVG", "url", "https://ngavg.org", "link", "https://ngavg.org"),
                Map.of("id", 7, "name", "Lovestory Project", "url", "https://lsp.moe", "link", "https://lsp.moe"),
                Map.of("id", 8, "name", "月幕GalGame", "url", "https://tsukimi.moe", "link", "https://tsukimi.moe"),
                Map.of("id", 9, "name", "Steam上的中文Galgame", "url", "https://store.steampowered.com", "link", "https://store.steampowered.com"),
                Map.of("id", 10, "name", "豆津账本", "url", "https://doujin.moe", "link", "https://doujin.moe"),
                Map.of("id", 11, "name", "123制作组", "url", "#", "link", "#"),
                Map.of("id", 12, "name", "花舞导航", "url", "#", "link", "#")
        );
        linkList.addAll(initLinks);
    }

    @GetMapping("/get")
    public List<Map<String, Object>> getLinks() {
        return linkList;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getLinkById(@PathVariable int id) {
        return linkList.stream()
                .filter(link -> (int) link.get("id") == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/insert")
    public Map<String, Object> addLink(@RequestBody Map<String, Object> linkData) {
        Map<String, Object> newLink = new HashMap<>(linkData);
        newLink.put("id", linkList.size() + 1);
        linkList.add(newLink);
        return newLink;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> updateLink(@PathVariable int id, @RequestBody Map<String, Object> linkData) {
        for (Map<String, Object> link : linkList) {
            if ((int) link.get("id") == id) {
                link.putAll(linkData);
                return link;
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteLink(@PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        boolean removed = linkList.removeIf(link -> (int) link.get("id") == id);
        response.put("status", removed ? "success" : "error");
        return response;
    }
}
