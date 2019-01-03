package com.zhongmei.yunfu.web;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.service.AuthUserService;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController extends BaseController {

    @Autowired
    AuthUserService authUserService;

    @RequestMapping({"/", "/home"})
    public String home() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login/test")
    public String login(Model model, Long shopId, String account, String password) {
        if (shopId != null && !StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)) {
            boolean login = LoginManager.get().login(authUserService, account, password, shopId);
            if (login) {
                return redirect("/");
            }
        }

        model.addAttribute("account", account);
        model.addAttribute("password", password);
        return "login_test";
    }

    @RequestMapping("/**/token/{token}")
    public String token(HttpServletRequest request, @PathVariable String token) {
        if (LoginManager.get().login(authUserService, token)) {
            String href = request.getRequestURI().replaceFirst(request.getContextPath(), "").replace("/token/" + token, "");
            if (request.getQueryString() != null) {
                href = href + "?" + request.getQueryString();
            }
            return redirect(href);
        }

        throw new IllegalStateException("Token未认证");
    }
}
