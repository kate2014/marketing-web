package com.zhongmei.yunfu.controller;

import com.zhongmei.yunfu.controller.model.CouponSearchModel;
import com.zhongmei.yunfu.domain.entity.SystemVersionEntity;
import com.zhongmei.yunfu.service.SystemVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 下载界面
 */

@Controller
@RequestMapping("/internal/download")
public class DownloadPageController extends BaseController {

    @Autowired
    SystemVersionService mSystemVersionService;

    @RequestMapping({"/page"})
    public String list(Model model) {

        try {
//            List<SystemVersionEntity> listVersionData =  mSystemVersionService.queryList();
//
//            model.addAttribute("listVersionData", listVersionData);
        }catch (Exception e){
            e.printStackTrace();

            return "fail";
        }

        return "download_page";
    }

}

