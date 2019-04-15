package com.zhongmei.yunfu.controller

import com.baomidou.mybatisplus.plugins.Page
import com.zhongmei.yunfu.WebPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Modifier
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

abstract class BaseController {

    @Autowired
    lateinit var request: HttpServletRequest

    fun getBasePath(request: HttpServletRequest): String {
        val path = request.contextPath
        var basePath = request.scheme + "://" + request.serverName
        if (request.serverPort != 80) {
            basePath += ":" + request.serverPort
        }
        basePath += path /*+ "/"*/
        return basePath
    }

    fun redirect(url: String): String {
        return String.format("redirect:%s", url)
    }

    protected fun setWebPage(model: Model, page: Page<*>, searchModel: Any?, url: String? = null) {
        var requestUrl = getRequestUrl(url)
        setWebPage(model, requestUrl, page, searchModel)
    }

    private fun getRequestUrl(url: String?): String {
        if (url == null) {
            var newUrl = StringBuilder()
            var classUrl = getMappingUrl(javaClass)
            if (classUrl != null) {
                newUrl.append(classUrl)
            }

            var methodName = Throwable().stackTrace.first {
                it.className == javaClass.name
            }.methodName

            var method = javaClass.declaredMethods.first {
                it.name == methodName
            }

            var methodUrl = getMappingUrl(method)
            if (methodUrl != null) {
                if (!methodUrl.startsWith("/")) {
                    newUrl.append("/")
                }
                newUrl.append(methodUrl)
            }
            return newUrl.toString()
        }
        return url
    }

    private fun getMappingUrl(annotatedElement: AnnotatedElement): String? {
        //return when (annotatedElement) {
        //annotatedElement.isAnnotationPresent(GetMapping::class.java) -> annotatedElement.getAnnotation(GetMapping::class.java).value[0]
        //annotatedElement.isAnnotationPresent(PostMapping::class.java) -> annotatedElement.getAnnotation(PostMapping::class.java).value[0]
        //annotatedElement.isAnnotationPresent(RequestMapping::class.java) -> annotatedElement.getAnnotation(RequestMapping::class.java).value[0]
        //else -> null
        //}

        var result: String? = null
        if (annotatedElement.isAnnotationPresent(GetMapping::class.java)) {
            result = annotatedElement.getAnnotation(GetMapping::class.java).value[0]
        }

        if (result == null) {
            if (annotatedElement.isAnnotationPresent(PostMapping::class.java)) {
                result = annotatedElement.getAnnotation(PostMapping::class.java).value[0]
            }
        }

        if (result == null) {
            if (annotatedElement.isAnnotationPresent(RequestMapping::class.java)) {
                result = annotatedElement.getAnnotation(RequestMapping::class.java).value[0]
            }
        }

        return result
    }


    protected fun setWebPage(model: Model, url: String, page: Page<*>, searchModel: Any?) {
        val pageInfo = createPageInfo(page, url, searchModel)
        model.addAttribute("pageInfo", pageInfo)
    }

    private fun createPageInfo(page: Page<*>, url: String, searchModel: Any?): WebPage {
        var url = request.requestURI.replaceFirst(request.contextPath, "")
        val urlParams = StringBuilder()
        if (searchModel != null) {
            val declaredFields = searchModel.javaClass.declaredFields
            for (i in declaredFields.indices) {
                val declaredField = declaredFields[i]
                if (!Modifier.isStatic(declaredField.modifiers)) {
                    try {
                        declaredField.isAccessible = true
                        val value = declaredField.get(searchModel)
                        if (value != null) {
                            if (i > 0) {
                                urlParams.append("&")
                            }
                            urlParams.append(declaredField.name + "=" + value)
                        }
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        if (!urlParams.toString().isEmpty()) {
            url += "?$urlParams"
        }

        //url = getBasePath(request) + url
        return WebPage(url, page.total, page.size.toLong(), page.current.toLong(), 5)
    }

    fun formatDateString(date: Date?): String {
        return if (date == null) "" else SimpleDateFormat("yyyy-MM-dd").format(date)
    }

    fun stringToDate(birthday: String?): Date? {
        return try {
            if (birthday == null) null else SimpleDateFormat("yyyy-MM-dd").parse(birthday)
        } catch (e: ParseException) {
            null
        }
    }

    /*companion object {
        @JvmStatic
        fun redirect(url: String): String {
            return String.format("redirect:%s", url)
        }
    }*/
}
