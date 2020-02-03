package com.crui.house.common.page;

import lombok.Getter;
import lombok.Setter;

/**
 * VM Args:
 *
 * @author crui
 */
@Getter
@Setter
public class PageParams {
    private static final Integer PAGE_SIZE = 2;
    private Integer pageSize;
    private Integer pageNum;
    private Integer offset;
    private Integer limit;

    public static PageParams build(Integer pageSize, Integer pageNum){
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }
        if (pageNum == null){
            pageNum = 1;
        }
        return new PageParams(pageSize,pageNum);
    }

    public PageParams() {
        this(PAGE_SIZE, 1);
    }

    public PageParams(Integer pageSize, Integer pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.offset = pageSize * (pageNum-1);
        this.limit = pageSize;
    }

}
