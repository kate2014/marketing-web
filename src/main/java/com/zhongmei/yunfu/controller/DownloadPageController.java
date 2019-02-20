package com.zhongmei.yunfu.controller;

import com.zhongmei.yunfu.controller.model.CouponSearchModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 下载界面
 */

@Controller
@RequestMapping("/internal/download")
public class DownloadPageController extends BaseController {

    @RequestMapping({"/page"})
    public String list(Model model, CouponSearchModel searchModel) {

        return "download_page";
    }

}

