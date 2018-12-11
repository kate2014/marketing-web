package com.zhongmei.yunfu.service

import com.zhongmei.yunfu.controller.model.base.IShopIdenty
import com.zhongmei.yunfu.controller.model.base.WebBaseModel
import com.zhongmei.yunfu.core.security.Token
import com.zhongmei.yunfu.domain.entity.AuthUserEntity
import com.zhongmei.yunfu.service.AuthUserService
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * Created by Administrator on 2017/7/13.
 */
class LoginManager private constructor() {

    private val log = LoggerFactory.getLogger(LoginManager::class.java)
    var user: AuthUserEntity? = null
        private set
    //get() = field ?: AuthUserEntity()

    fun login(loginService: AuthUserService, account: String, password: String, shopId: Long): Boolean {
        return login(loginService, account, password, shopId, "[null]", false)
    }

    fun login(loginService: AuthUserService, account: String, password: String, shopId: Long, authCode: String, authCoeVerify: Boolean = true): Boolean {
        log.info("login: account=%s, password=%s, authCode=%s", account, password, authCode)
        if (!authCoeVerify || authCode == getAuthCode()) {
            this.user = loginService.login(account, password, shopId)
            if (user != null) {
                log.info("login success")
                threadSession.get()?.setAttribute("loginManager", this)
                return true
            }
        }

        log.error("login error")
        return false
    }

    fun login(token: String): Boolean {
        if (StringUtils.isNotBlank(token)) {
            val decode = Token.decode(token)
            if (decode != null) {
                val userEntity = AuthUserEntity()
                userEntity.account = decode.account
                userEntity.password = decode.password
                userEntity.id = decode.userId
                userEntity.name = decode.userName
                userEntity.shopIdenty = decode.shopId
                userEntity.brandIdenty = decode.brandId

                this.user = userEntity
                log.info("login success")
                threadSession.get()?.setAttribute("loginManager", this)
                return true
            }
        }

        log.error("login error")
        throw IllegalStateException("Token未认证")
        //return false
    }

    fun setLoginUser(user: AuthUserEntity?) {
        this.user = user
        if (user != null) {
            log.info("login success")
            threadSession.get()?.setAttribute("loginManager", this)
        }
    }

    fun logoff() {
        user = null
        threadSession.get()?.setAttribute("loginManager", null)
        threadSession.get()?.invalidate()
    }

    fun isLogin(): Boolean = user != null

    fun authUri(requestURI: String): Boolean {
        /*var roles = user?.roles
        if (roles != null) {
            for (r in roles) {
                return authUri(r.functionMeuns, requestURI)
            }
        }

        return authUri(user?.functionMenus, requestURI)*/
        return true
    }

    /*private fun authUri(menus: List<AuthPermission>?, requestURI: String): Boolean {
        if (menus != null) {
            for (menu in menus) {
                if (requestURI == menu.url) {
                    return true
                }
            }
        }
        return true
    }*/

    companion object {

        private val threadSession = ThreadLocal<HttpSession>()

        @JvmStatic
        fun beginRequest(request: HttpServletRequest) {
            threadSession.set(request.session)
            var manager = threadSession.get()?.getAttribute("loginManager") as LoginManager?
            if (manager == null) {
                threadSession.get()?.setAttribute("loginManager", LoginManager())
            }
        }

        @JvmStatic
        fun endRequest() {
            threadSession.set(null)
        }

        @JvmStatic
        fun get(): LoginManager {
            val session = threadSession.get()
            var manager = session?.getAttribute("loginManager") as LoginManager?
            if (manager != null) {
                return manager
            }
            /*val person = manager.getPerson()
            if (!(person == null || "sysadmin" == person!!.getName() || "peradmin" == person!!.getName()))
            manager.person = HibernateUtil.getSession().get(
            Person::class.java, person!!.getId()) as Person*/
            return LoginManager()
        }

        fun setAuthCode(authCode: String) {
            threadSession.get()?.setAttribute("authCode", authCode)
        }

        fun getAuthCode(): String? {
            return threadSession.get()?.getAttribute("authCode") as String?
        }

        @JvmStatic
        fun setUser(webBaseModel: WebBaseModel?) {
            webBaseModel?.user = get().user
        }

        @JvmStatic
        fun setUserShopIdenty(webBaseModel: IShopIdenty?) {
            webBaseModel?.setBrandIdenty(get().user?.brandIdenty)
            webBaseModel?.setShopIdenty(get().user?.shopIdenty)
        }
    }
}
