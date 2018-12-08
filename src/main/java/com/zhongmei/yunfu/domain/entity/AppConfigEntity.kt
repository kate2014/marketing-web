package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("app_config")
class AppConfigEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null
    var appName: String? = null
    var configName: String? = null
    var configValue: String? = null
    var appendDate: Date? = null
    var appendString: String? = null
    var appendInt: Int? = null
    var version: Int? = null


    override fun toString(): String {
        return "AppConfigEntity{" +
        ", id=" + id +
        ", appName=" + appName +
        ", configName=" + configName +
        ", configValue=" + configValue +
        ", appendDate=" + appendDate +
        ", appendString=" + appendString +
        ", appendInt=" + appendInt +
        ", version=" + version +
        "}"
    }
}
