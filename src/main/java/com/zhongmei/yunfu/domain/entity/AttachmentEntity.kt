package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.baomidou.mybatisplus.enums.IdType
import com.zhongmei.yunfu.domain.entity.base.BaseEntity

/**
 * <p>
 * 文件附件表（用于临时存储）
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("attachment")
class AttachmentEntity : BaseEntity() {

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 附件名
     */
    var name: String? = null
    /**
     * 访问地址
     */
    var url: String? = null


    override fun toString(): String {
        return "AttachmentEntity{" +
                ", id=" + id +
                ", name=" + name +
                ", url=" + url +
                "}"
    }
}
