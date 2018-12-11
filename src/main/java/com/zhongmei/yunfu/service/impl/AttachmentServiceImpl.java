package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.AttachmentEntity;
import com.zhongmei.yunfu.domain.mapper.AttachmentMapper;
import com.zhongmei.yunfu.service.AttachmentService;
import com.zhongmei.yunfu.thirdlib.UploadFile;
import com.zhongmei.yunfu.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

/**
 * <p>
 * 文件附件表（用于临时存储） 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-10-20
 */

@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, AttachmentEntity> implements AttachmentService {

    @Override
    public AttachmentEntity addFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString() + "." + FileUtils.getExtensionName(filename);

        /*
         */
/*var path = File("uploadfile/")

        if (!path.exists()) {
            path.mkdirs()
        }
        var newPath = File(path.absoluteFile, newFileName)
        file.transferTo(newPath)*//*
         */

        UploadFile uploadFile = UploadFile.create();
        String url = uploadFile.uploadFile(file.getInputStream(), newFileName);

        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setName(filename);
        attachmentEntity.setUrl(url);
        insert(attachmentEntity);

        return attachmentEntity;
    }

    @Value("${upload.file.path:C:/用户/WxSecret}")
    private String uploadFilePath; //微信秘钥文件路径

    @Override
    public AttachmentEntity addLocalFile(MultipartFile file, String filename) throws Exception {
        String originalFilename = file.getOriginalFilename();
        //String newFileName = UUID.randomUUID().toString() + "." + FileUtils.getExtensionName(originalFilename);

        File path = new File(uploadFilePath);
        if (!path.exists()) {
            path.mkdirs();
        }

        File dest = new File(path, filename);
        if (dest.exists()) {
            dest.delete();
        }

        FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(dest.toPath()));
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setName(originalFilename);
        attachmentEntity.setUrl(dest.getAbsolutePath());
        insert(attachmentEntity);

        return attachmentEntity;
    }
}
