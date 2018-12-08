package com.zhongmei.yunfu.api

import com.baomidou.mybatisplus.plugins.Page

class ApiPage(page: Page<*>) {

    /**
     * 总数
     */
    var rowCount: Long = page.total

    /**
     * 每页显示条数，默认 10
     */
    var pageSize: Int = page.size

    /**
     * 总页数
     */
    var pageCount: Long = page.pages

    /**
     * 当前页
     */
    var currentPage: Int = page.current
}
