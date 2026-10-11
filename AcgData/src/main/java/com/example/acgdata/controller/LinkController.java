package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.dto.LinkSaveDTO;
import com.example.acgdata.service.LinkService;
import com.example.acgdata.vo.LinkVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 友情链接接口。
 * 路径与返回结构与旧接口保持一致，只在最外层加了一层 Result。
 */
@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
@Validated
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/get")
    public Result<List<LinkVO>> getLinks() {
        return Result.ok(linkService.listAll());
    }

    @GetMapping("/get/{id}")
    public Result<LinkVO> getLinkById(@PathVariable Long id) {
        return Result.ok(linkService.getVO(id));
    }

    @PostMapping("/insert")
    public Result<LinkVO> insert(@Valid @RequestBody LinkSaveDTO dto) {
        return Result.ok("新增成功", linkService.saveLink(dto));
    }

    @PutMapping("/update/{id}")
    public Result<LinkVO> update(@PathVariable Long id, @Valid @RequestBody LinkSaveDTO dto) {
        return Result.ok("修改成功", linkService.updateLink(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Map<String, String>> delete(@PathVariable Long id) {
        linkService.removeLink(id);
        return Result.ok(Map.of("status", "success"));
    }
}
