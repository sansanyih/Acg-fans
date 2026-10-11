package com.example.acgdata.controller;

import com.example.acgdata.common.Result;
import com.example.acgdata.dto.CharacterSaveDTO;
import com.example.acgdata.service.CharacterService;
import com.example.acgdata.vo.CharacterAnimeItemVO;
import com.example.acgdata.vo.CharacterDeleteVO;
import com.example.acgdata.vo.CharacterItemVO;
import com.example.acgdata.vo.CharacterLikeVO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色接口。
 *
 * <p>路径与返回结构与旧接口保持一致，只在最外层加了一层 {@link Result}。
 * 旧实现是内存 {@code List<Map<String,Object>>} 假数据，现在走 Controller → Service → Mapper → MySQL。
 */
@RestController
@RequestMapping("/api/character")
@RequiredArgsConstructor
@Validated
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/get")
    public Result<List<CharacterItemVO>> getCharacterList() {
        return Result.ok(characterService.listAll());
    }

    @GetMapping("/get/{id}")
    public Result<CharacterItemVO> getCharacterById(@PathVariable Long id) {
        return Result.ok(characterService.getVO(id));
    }

    @GetMapping("/getByAnime/{animeId}")
    public Result<List<CharacterAnimeItemVO>> getCharactersByAnime(@PathVariable Long animeId) {
        return Result.ok(characterService.listByAnimeId(animeId));
    }

    /**
     * 搜索角色。
     * 方法名 {@code searchCharacter} 是对外契约：SearchController 的聚合搜索会调用它。
     */
    @GetMapping("/search")
    public Result<List<CharacterItemVO>> searchCharacter(@RequestParam String keyword) {
        return Result.ok(characterService.search(keyword));
    }

    @PostMapping("/insert")
    public Result<CharacterItemVO> insert(@Valid @RequestBody CharacterSaveDTO dto) {
        return Result.ok("新增成功", characterService.saveCharacter(dto));
    }

    @PutMapping("/update/{id}")
    public Result<CharacterItemVO> update(@PathVariable Long id, @Valid @RequestBody CharacterSaveDTO dto) {
        return Result.ok("修改成功", characterService.updateCharacter(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public Result<CharacterDeleteVO> delete(@PathVariable Long id) {
        characterService.removeCharacter(id);
        // 旧接口：{ "success": "true", "message": "角色删除成功" }
        return Result.ok(CharacterDeleteVO.removed("角色删除成功"));
    }

    @PostMapping("/like/{id}")
    public Result<CharacterLikeVO> like(@PathVariable Long id) {
        CharacterItemVO character = characterService.like(id);
        return Result.ok(new CharacterLikeVO(Boolean.TRUE, character.getLikes(), null, null));
    }

    @PostMapping("/dislike/{id}")
    public Result<CharacterLikeVO> dislike(@PathVariable Long id) {
        CharacterItemVO character = characterService.dislike(id);
        return Result.ok(new CharacterLikeVO(Boolean.TRUE, null, character.getDislikes(), null));
    }
}
