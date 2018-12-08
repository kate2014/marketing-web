package com.zhongmei.yunfu;

public final class PageInfo {
    /**
     * 总记录数
     */
    private long rowCount;

    /**
     * 每页显示记录数
     */
    private long pageSize;

    /**
     * 当前页数
     */
    private long currentPage;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 前一页标志
     */
    //private boolean prePage;

    /**
     * 后一页标志
     */
    //private boolean nextPage;

    /**
     * 当前显示的页码个数
     */
    private int pageRangNum;

    /**
     * 前一页
     */
    private long prePageNum;
    /**
     * 后一页
     */
    private long nextPageNum;

    /**
     * 分页跳转Url
     */
    private String url;

    public PageInfo(String url, long rowCount, long pageSize, long currentPage) {
        this(url, rowCount, pageSize, currentPage, 1);
    }

    public PageInfo(String url, long rowCount, long pageSize, long currentPage, int pageRangNum) {
        this.rowCount = rowCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.pageRangNum = pageRangNum;
        this.prePageNum = currentPage - pageRangNum;
        this.nextPageNum = currentPage + pageRangNum;
        //this.prePage = isPrePage();
        //this.nextPage = isNextPage();
        this.totalPage = getTotalPage();
        this.url = url;
    }

    /**
     * 获取总页数
     *
     * @@return
     */
    public long getTotalPage() {
        if (rowCount == 0 || pageSize == 0)
            return 0;
        if (rowCount % pageSize == 0)
            totalPage = rowCount / pageSize;
        else
            totalPage = rowCount / pageSize + 1;
        return totalPage;
    }

    public boolean isPrePage() {
        return currentPage > pageRangNum;
    }

    public boolean isNextPage() {
        return (currentPage - 1) / pageRangNum + pageRangNum < totalPage;
    }

}
