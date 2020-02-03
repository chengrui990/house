package com.crui.house.common.page;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * VM Args:
 *
 * @author crui
 */
@Data
public class Pagination {
    private int pageNum;
    private int pageSize;
    private long totalCount;
    private List<Integer> pages = Lists.newArrayList();


    public Pagination(int pageNum, int pageSize, long totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        for (int i=1; i<=pageNum; i++){
            pages.add(i);
        }
        Long pageCount = totalCount / pageSize + ((totalCount % pageSize == 0)? 0 : 1);
        if (pageCount > pageNum){
            for (int i = pageNum+1; i<=pageCount;i++){
                pages.add(i);
            }
        }
        System.out.println("Pagination.pages:" + pages.toString());
    }
}
