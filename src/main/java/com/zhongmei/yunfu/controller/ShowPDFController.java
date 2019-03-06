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

    @RequestMapping({"/installationManual"})
    public String showInstallationM(Model model) {

        return "show_zjsc_pdf";
    }

    @RequestMapping({"/systemHandbook"})
    public String systemHandbook(Model model){

        return "system_handbook";
    }


    @RequestMapping({"/pdfDetail"})
    public String showPDF(Model model,Integer type){

        if(type == 1){
            model.addAttribute("pdfURL", "http://media.zhongmeiyunfu.com/system_frame.pdf");
            return "show_pdf";
        }else if(type == 2){
            model.addAttribute("pdfURL", "http://media.zhongmeiyunfu.com/pdf商户基本信息配置.pdf");
            return "show_pdf";
        }else if(type == 3){
            model.addAttribute("pdfURL", "http://media.zhongmeiyunfu.com/pdf员工管理.pdf");
            return "show_pdf";
        }
        else{
            return "mading";
        }


    }
}
