package com.zhongmei.yunfu.controller.model;

import java.util.List;

public class PageResonseModel<T> {
    private int pageNo;//当前页
    private long pages;//总页数
    private int pageSize;//每页显示的数据长度
    private List<T> data;//列表数据


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
