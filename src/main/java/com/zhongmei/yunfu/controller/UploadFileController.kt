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
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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

    private val ckeditorStorageImagePath = "d:/" //存储地址
    private val ckeditorAccessImageUrl = "/image/" //访问地址

    @RequestMapping
    fun index(): String {
        return "ckedit_demo"
    }

    @RequestMapping("/demo")
    fun uploadDemo(): String {
        return "demo_uploadfile"
    }

    @RequestMapping("/d/image")
    @ResponseBody
    fun uploadDemo(@RequestParam("upload") file: MultipartFile, request: HttpServletRequest, response: HttpServletResponse): String {
        val name = ""
        if (!file.isEmpty) {
            try {
                response.contentType = "text/html; charset=UTF-8"
                response.setHeader("Cache-Control", "no-cache")
                //解决跨域问题
                //Refused to display 'http://localhost:8080/upload/mgmt/img?CKEditor=practice_content&CKEditorFuncNum=1&langCode=zh-cn' in a frame because it set 'X-Frame-Options' to 'DENY'.
                response.setHeader("X-Frame-Options", "SAMEORIGIN")
                val out = response.writer

                val fileClientName = file.originalFilename
                val pathName = ckeditorStorageImagePath + fileClientName!!
                val newfile = File(pathName)
                val bytes = file.bytes
                val stream = BufferedOutputStream(FileOutputStream(newfile))
                stream.write(bytes)
                stream.close()

                // 组装返回url，以便于ckeditor定位图片
                val fileUrl = ckeditorAccessImageUrl + File.separator + newfile.name

                // 将上传的图片的url返回给ckeditor
                val callback = request.getParameter("CKEditorFuncNum")
                val script = "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction($callback, '$fileUrl');</script>"

                out.println(script)
                out.flush()
                out.close()
            } catch (e: Exception) {
                logger.info("You failed to upload " + name + " => " + e.message)
            }

        } else {
            logger.info("You failed to upload $name because the file was empty.")
        }
        return "SUCCESS"
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

