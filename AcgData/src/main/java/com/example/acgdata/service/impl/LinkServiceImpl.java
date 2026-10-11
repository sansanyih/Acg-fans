package com.example.acgdata.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.acgdata.common.ResultCode;
import com.example.acgdata.common.exception.BusinessException;
import com.example.acgdata.dto.LinkSaveDTO;
import com.example.acgdata.entity.Link;
import com.example.acgdata.mapper.LinkMapper;
import com.example.acgdata.service.LinkService;
import com.example.acgdata.vo.LinkVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友情链接服务实现（分层模板：其它模块照这个写）。
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public List<LinkVO> listAll() {
        return list(Wrappers.<Link>lambdaQuery()
                .orderByAsc(Link::getSort)
                .orderByAsc(Link::getId))
                .stream()
                .map(LinkVO::from)
                .toList();
    }

    @Override
    public LinkVO getVO(Long id) {
        return LinkVO.from(getExisting(id));
    }

    @Override
    public LinkVO saveLink(LinkSaveDTO dto) {
        Link link = new Link();
        apply(link, dto);
        save(link);
        return LinkVO.from(link);
    }

    @Override
    public LinkVO updateLink(Long id, LinkSaveDTO dto) {
        Link link = getExisting(id);
        apply(link, dto);
        updateById(link);
        return LinkVO.from(link);
    }

    @Override
    public void removeLink(Long id) {
        getExisting(id);
        // 逻辑删除（@TableLogic）
        removeById(id);
    }

    private Link getExisting(Long id) {
        Link link = getById(id);
        if (link == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        return link;
    }

    private void apply(Link link, LinkSaveDTO dto) {
        link.setName(dto.getName());
        link.setIconUrl(dto.getIconUrl());
        link.setTargetUrl(dto.getTargetUrl());
        link.setSort(dto.getSort() == null ? 0 : dto.getSort());
    }
}
