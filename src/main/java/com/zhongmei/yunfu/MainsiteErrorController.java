package com.zhongmei.yunfu;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/7/12.
 */
//@Controller
public class MainsiteErrorController implements ErrorController {

    public static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public String handleError() {
        return "error404";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
