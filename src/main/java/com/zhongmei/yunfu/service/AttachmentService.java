package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.AttachmentEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件附件表（用于临时存储） 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-20
 */
public interface AttachmentService extends IService<AttachmentEntity> {

    AttachmentEntity addFile(MultipartFile file) throws Exception;

    AttachmentEntity addLocalFile(MultipartFile file, String filename) throws Exception;
}
