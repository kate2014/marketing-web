package com.zhongmei.yunfu.controller.api;

import com.zhongmei.yunfu.controller.api.model.CKEditorUploadResp;
import com.zhongmei.yunfu.domain.entity.AttachmentEntity;
import com.zhongmei.yunfu.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
@RequestMapping("/upload/d")
@Slf4j
public class CKEditorUploadApiController {

    @Autowired
    AttachmentService attachmentService;

    @RequestMapping("/image")
    @ResponseBody
    public CKEditorUploadResp uploadFile(@RequestParam("upload") MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response) {
        CKEditorUploadResp resp = new CKEditorUploadResp();
        resp.setUploaded(1);

        if (imgFile.isEmpty()) {
            resp.setError("上传图片不能为空！");
            return resp;
        }

        // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
        String[] types = new String[]{"GIF", "JPG", "PNG"}; //Arrays.toString(ImageIO.getReaderFormatNames());
        String suffix = null;

        // 获取图片后缀
        if (imgFile.getOriginalFilename().indexOf(".") > -1) {
            suffix = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf(".") + 1);
        }

        // 类型和图片后缀全部小写，然后判断后缀是否合法
        final String finalSuffix = suffix;
        if ((suffix == null) || Arrays.stream(types).noneMatch(s -> s.equals(finalSuffix.toUpperCase()))) {
            resp.setError("只支持GIF、JPG、PNG格式的图片！");
            return resp;
        }

        try {
            AttachmentEntity entity = attachmentService.addFile(imgFile);
            resp.setFileName(entity.getName());
            resp.setUrl(entity.getUrl());
            return resp;
        } catch (Exception e) {
            //log.info("You failed to upload " + imgFile.getOriginalFilename() + " => " + e.getMessage());
            resp.setError("上传出错拉！" + e.getMessage());
            return resp;
        }
    }
}
