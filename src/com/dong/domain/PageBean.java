package com.dong.domain;

import java.util.List;

/**
 * 分页对象
 */
public class PageBean<T> {  //加上自定义泛型T，PageBean初始化时确定类型，方便PageBean通用
    private int totalCount; // 总记录数
    private int totalPage;  // 总页码
    private List<T> list;   //每页的数据
    private int CurrentPage;//当前页码
    private int rows;       //每页显示的记录数

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        CurrentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "pageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", CurrentPage=" + CurrentPage +
                ", rows=" + rows +
                '}';
    }
}
