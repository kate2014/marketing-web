package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.controller.model.CouponSearchModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 展示FDF
 */
@Controller
@RequestMapping("/internal/showPDF")
public class ShowPDFController {

    @RequestMapping({"/zjsc"})
    public String list(Model model, CouponSearchModel searchModel) {

        return "show_zjsc_pdf";
    }
}
