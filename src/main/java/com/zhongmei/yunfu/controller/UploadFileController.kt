package com.zhongmei.yunfu.controller


import com.zhongmei.yunfu.api.ApiResult
import com.zhongmei.yunfu.service.AttachmentService
import com.zhongmei.yunfu.service.LoginManager
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.imageio.ImageIO


/**
 *
 *
 * 文件上传 前端控制器
 *

 * @author pigeon88
 * *
 * @since 2018-09-07
 */
@Controller
@RequestMapping("/upload")
class UploadFileController {

    @RequestMapping
    fun index(): String {
        return "ckedit_demo"
    }

    @RequestMapping("/demo")
    fun uploadDemo(): String {
        return "demo_uploadfile"
    }

    @Autowired
    lateinit var attachmentService: AttachmentService

    @RequestMapping("/image")
    @ResponseBody
    fun uploadImage(@RequestParam(value = "file", required = false) file: MultipartFile?, request: HttpServletRequest, response: HttpServletResponse): Any {
        // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
        /*String types = Arrays.toString(ImageIO.getReaderFormatNames());
        String suffix = null;

        // 获取图片后缀
        if (imgFile.getName().indexOf(".") > -1) {
            suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
        }

        // 类型和图片后缀全部小写，然后判断后缀是否合法
        if (suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0) {
            //System.out.println("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
            return;
        }*/

        /*val qiniu = UploadFile.create()
        val newFileName = UUID.randomUUID().toString() + "." + FileUtils.getExtensionName(file.originalFilename)
        var path = qiniu.uploadFile(file.inputStream, newFileName)*/

        var entity = attachmentService.addFile(file)

        return ApiResult.newSuccess(mapOf("id" to entity.id, "url" to entity.url))
    }

    @RequestMapping("/file")
    @ResponseBody
    fun uploadFile(@RequestParam(value = "file", required = false) file: MultipartFile?, request: HttpServletRequest, response: HttpServletResponse): Any {
        var user = LoginManager.get().user
        //var extensionName = FileUtils.getExtensionName(file?.originalFilename)
        var newFileName = "%06d%08d".format(user?.brandIdenty, user?.shopIdenty)
        //newFileName = Hex.encodeToString(Base64.getEncoder().encode(newFileName.toByteArray()))
        var entity = attachmentService.addLocalFile(file, newFileName)

        return ApiResult.newSuccess(mapOf("id" to entity.id, "name" to entity.name, "url" to entity.url))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UploadFileController::class.java)
    }
}

