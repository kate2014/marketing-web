package com.zhongmei.yunfu.controller.brand;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/internal/brand/report")
public class BrandReportController extends BaseController {

    @RequestMapping({"/main"})
    public String shopList(Model model, TradeModel mTradeModel) {

        model.addAttribute("mTradeModel", mTradeModel);
        return "brand_main_report";
    }
}
