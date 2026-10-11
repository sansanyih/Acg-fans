package com.example.acgdata.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

/**
 * 分页统一返回结构，避免把 MyBatis-Plus 的 Page 对象直接暴露给前端。
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 总页数 */
    private long pages;

    /** 当前页，从 1 开始 */
    private long current;

    /** 每页条数 */
    private long size;

    /** 当前页数据 */
    private List<T> records;

    public static <T> PageResult<T> of(IPage<T> page) {
        return of(page, Function.identity());
    }

    /** 实体分页结果 -> VO 分页结果 */
    public static <E, V> PageResult<V> of(IPage<E> page, Function<E, V> converter) {
        PageResult<V> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setPages(page.getPages());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setRecords(page.getRecords().stream().map(converter).toList());
        return result;
    }
}
