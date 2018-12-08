package com.zhongmei.yunfu

class WebPage {

    val url: String //分页跳转Url
    val rowCount: Long //总记录数
    val pageSize: Long //每页显示记录数
    val currentPage: Long //当前页数
    val pageRangeNum: Long //当前显示的页码个数

    val prePageNum: Long //前一页
    val nextPageNum: Long //后一页
    val hasPrePage: Boolean //前一页标志
    val hasNextPage: Boolean //后一页标志
    /**
     * 总页数
     */
    var totalPage: Long = 0
        get() {
            if (rowCount == 0L || pageSize == 0L)
                return 0
            /*if (rowCount % pageSize == 0L)
                totalPage = rowCount / pageSize
            else
                totalPage = rowCount / pageSize + 1
            return totalPage*/
            val _totalPage = rowCount / pageSize
            return if (rowCount % pageSize == 0L) _totalPage else _totalPage + 1
        }

    @JvmOverloads constructor(url: String, rowCount: Long, pageSize: Long, pageNo: Long, showPageNum: Long = 1) {
        this.url = url
        this.rowCount = rowCount
        this.pageSize = pageSize
        this.currentPage = Math.min(Math.max(pageNo, 1), totalPage)
        this.pageRangeNum = Math.min(showPageNum, totalPage)
        when (currentPage) {
            1L -> {
                this.prePageNum = 0
                this.nextPageNum = pageRangeNum + 1
            }
            totalPage -> {
                this.prePageNum = totalPage - pageRangeNum
                this.nextPageNum = totalPage + 1
            }
            else -> {
                var count = pageRangeNum / 2 + pageRangeNum % 2
                this.prePageNum = Math.max(currentPage - count, 0)
                this.nextPageNum = Math.min(currentPage + count, totalPage)
            }
        }

        this.hasPrePage = prePageNum > 0
        this.hasNextPage = nextPageNum < totalPage
    }
}
