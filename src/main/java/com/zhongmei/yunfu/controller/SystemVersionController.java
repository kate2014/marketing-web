package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.controller.model.TradeModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pigeon88
 * @since 2019-02-26
 */
@Controller
@RequestMapping("/internal/systemVersion")
public class SystemVersionController {

    @RequestMapping("/versionList")
    public String systemViersion(Model model, TradeModel mTradeModel) {

        return "";
    }

}

