package com.example.acgdata.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.acgdata.dto.LinkSaveDTO;
import com.example.acgdata.entity.Link;
import com.example.acgdata.vo.LinkVO;

import java.util.List;

public interface LinkService extends IService<Link> {

    List<LinkVO> listAll();

    LinkVO getVO(Long id);

    LinkVO saveLink(LinkSaveDTO dto);

    LinkVO updateLink(Long id, LinkSaveDTO dto);

    void removeLink(Long id);
}
