package com.example.acgdata.controller;

import com.example.acgdata.common.PageResult;
import com.example.acgdata.common.Result;
import com.example.acgdata.dto.ArticleSaveDTO;
import com.example.acgdata.dto.CommentSaveDTO;
import com.example.acgdata.service.ArticleService;
import com.example.acgdata.vo.ArticleDetailVO;
import com.example.acgdata.vo.ArticleItemVO;
import com.example.acgdata.vo.CommentVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 文章接口。
 * 路径与 data 结构与旧接口保持一致，只在最外层加了一层 Result。
 * （旧实现里的内存假数据已迁移到 MySQL，见 db/schema/20_article.sql 与 db/data/20_article.sql）
 */
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
@Validated
public class ArticleController {

    private final ArticleService articleService;

    /** 列表：数组，字段同前端 ArticleItem */
    @GetMapping("/get")
    public Result<List<ArticleItemVO>> getArticleList() {
        return Result.ok(articleService.listAll());
    }

    /**
     * 分页 + 关键字条件查询。
     * 示例：GET /api/article/page?current=1&size=5&keyword=新番
     */
    @GetMapping("/page")
    public Result<PageResult<ArticleItemVO>> page(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页码从 1 开始") long current,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "每页至少 1 条")
            @Max(value = 100, message = "每页最多 100 条") long size,
            @RequestParam(required = false) String keyword) {
        return Result.ok(articleService.pageArticles(current, size, keyword));
    }

    /** 详情：单个对象，含 content / lastEdit / comments */
    @GetMapping("/get/{id}")
    public Result<ArticleDetailVO> getArticleById(@PathVariable Long id) {
        return Result.ok(articleService.getDetail(id));
    }

    /**
     * 搜索：数组。
     * 具体实现在 ArticleService#search，SearchController 也复用该方法。
     */
    @GetMapping("/search")
    public Result<List<ArticleItemVO>> searchArticle(@RequestParam String keyword) {
        return Result.ok(articleService.search(keyword));
    }

    /** 新增：返回保存后的完整对象 */
    @PostMapping("/insert")
    public Result<ArticleDetailVO> insert(@Valid @RequestBody ArticleSaveDTO dto) {
        return Result.ok("新增成功", articleService.saveArticle(dto));
    }

    /** 修改：返回修改后的完整对象 */
    @PutMapping("/update/{id}")
    public Result<ArticleDetailVO> update(@PathVariable Long id, @Valid @RequestBody ArticleSaveDTO dto) {
        return Result.ok("修改成功", articleService.updateArticle(id, dto));
    }

    /** 删除：{ "status": "success" } */
    @DeleteMapping("/delete/{id}")
    public Result<Map<String, String>> delete(@PathVariable Long id) {
        articleService.removeArticle(id);
        return Result.ok(Map.of("status", "success"));
    }

    /** 发表评论：返回新增的评论对象 */
    @PostMapping("/addComment/{id}")
    public Result<CommentVO> addComment(@PathVariable Long id, @Valid @RequestBody CommentSaveDTO dto) {
        return Result.ok("评论成功", articleService.addComment(id, dto));
    }

    /** 点赞：返回更新后的详情（likeCount 已 +1） */
    @PostMapping("/like/{id}")
    public Result<ArticleDetailVO> like(@PathVariable Long id) {
        return Result.ok("点赞成功", articleService.like(id));
    }

    /** 浏览：返回更新后的详情（readCount 已 +1） */
    @PostMapping("/view/{id}")
    public Result<ArticleDetailVO> view(@PathVariable Long id) {
        return Result.ok(articleService.view(id));
    }

}
