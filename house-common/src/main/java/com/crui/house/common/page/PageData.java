package com.crui.house.common.page;

import lombok.Data;

import java.util.List;

/**
 * VM Args:
 *
 * @author crui
 */
@Data
public class PageData<T> {
    private List<T> list;
    private Pagination pagination;
    public PageData(Pagination pagination, List<T> list){
        this.pagination = pagination;
        this.list = list;
    }

    public static <T> PageData<T> buildPage(List<T> list, long count, Integer pageSize, Integer pageNum){
        Pagination pagination = new Pagination(pageNum, pageSize, count);
        return new PageData<>(pagination,list);
    }
}
