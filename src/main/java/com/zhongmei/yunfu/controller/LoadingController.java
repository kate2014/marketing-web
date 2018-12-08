package com.zhongmei.yunfu.controller;

import com.zhongmei.yunfu.controller.model.TradeModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loading")
public class LoadingController {

    @RequestMapping("/loaingView")
    public String loadSalesReport(Model model, TradeModel mTradeModel){
        return "loading";
    }
}
