package com.zhongmei.yunfu

class WebPage {

    val url: String //分页跳转Url
    val rowCount: Long //总记录数
    val pageSize: Long //每页显示记录数
    val currentPage: Long //当前页数
    val pageRangeNum: Long //当前显示的页码个数

    var lowNum: Long //低位数字
    var higNum: Long //高位数字
    val hasPrePage: Boolean //前一页标志
    val hasNextPage: Boolean //后一页标志

    /**
     * 总页数
     */
    var totalPage: Long

    @JvmOverloads constructor(url: String, rowCount: Long, pageSize: Long, pageNo: Long, showPageNum: Long = 5) {
        this.url = url
        this.rowCount = rowCount
        this.pageSize = pageSize
        this.totalPage = if (rowCount == 0L || pageSize == 0L) 0 else rowCount / pageSize + Math.min(rowCount % pageSize, 1)
        this.currentPage = Math.min(Math.max(pageNo, 1), totalPage)
        this.pageRangeNum = showPageNum

        //计算高位与低位
        /*var mid = pageRangeNum / 2
        lowNum = Math.max(currentPage - mid, 1)
        higNum = Math.min(lowNum + pageRangeNum - 1, totalPage)
        if (higNum - lowNum < pageRangeNum - 1) {
            lowNum = Math.max(higNum - (pageRangeNum - 1), 1)
        }*/

        lowNum = (currentPage - 1) / pageRangeNum * pageRangeNum + 1
        higNum = Math.min(lowNum + pageRangeNum - 1, totalPage)
        if (higNum - lowNum < pageRangeNum - 1) {
            lowNum = Math.max(higNum - (pageRangeNum - 1), 1)
        }

        this.hasPrePage = lowNum > 1
        this.hasNextPage = higNum < totalPage
    }
}
