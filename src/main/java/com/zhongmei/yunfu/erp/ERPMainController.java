package com.zhongmei.yunfu.erp;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.erp.model.ERPBrandModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/internal/erp")
public class ERPMainController extends BaseController {


    @RequestMapping({"/main"})
    public String mianPage(Model model, ERPBrandModel mERPBrandModel) {

        model.addAttribute("mERPBrandModel", mERPBrandModel);

        return "erp_main";
    }

}
