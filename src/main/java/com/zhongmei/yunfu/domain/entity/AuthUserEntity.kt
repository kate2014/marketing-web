package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("auth_user")
class AuthUserEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 角色id : 角色id
     */
    var roleId: Long? = null
    /**
     * 用户名称 : 用户名称
     */
    var name: String? = null
    /**
     * 性别 : 未知：-1 ；女：0； 男：1
     */
    var gender: Int? = null
    /**
     * 生日 : 生日
     */
    var birthday: Date? = null
    /**
     * 身份证
     */
    var identityCard: String? = null
    /**
     * 学历
     */
    var education: String? = null
    /**
     * 毕业学校
     */
    var graduateSchool: String? = null
    /**
     * 参数工作时间
     */
    var intoWorkDate: String? = null
    /**
     * 婚姻状况 1：已婚  2：未婚
     */
    var isMarry: String? = null
    /**
     * 手机号
     */
    var mobile: String? = null
    /**
     * 邮箱
     */
    var email: String? = null
    /**
     * QQ : QQ账号
     */
    var qq: Long? = null
    /**
     * 地址
     */
    var address: String? = null
    /**
     * 头像
     */
    var icon: String? = null
    /**
     * 紧急联系人-姓名
     */
    var ecName: String? = null
    /**
     * 紧急联系人-关系
     */
    var ecRelation: String? = null
    /**
     * 紧急联系人-电话
     */
    var ecMobile: String? = null
    /**
     * 紧急联系人-备用电话
     */
    var ecMobileReserve: String? = null
    /**
     * 工号
     */
    var jobNumber: String? = null
    /**
     * 员工类型 1.正式员工  2、试用期员工 3、外聘员工  4、停用
     */
    var jobEmployeeType: String? = null
    /**
     * 入职时间
     */
    var jobEntryTime: String? = null
    /**
     * 转正时间
     */
    var jobPositiveTime: String? = null
    /**
     * 职位
     */
    var jobPosition: String? = null
    /**
     * 职级
     */
    var jobGrade: String? = null
    /**
     * 工作地址
     */
    var jobAddress: String? = null
    /**
     * 薪资-计算方式
     */
    var salaryCalcMode: String? = null
    /**
     * 薪资-基本工资
     */
    var salaryBase: String? = null
    /**
     * 薪资-岗位工资
     */
    var salaryPost: String? = null
    /**
     * 登陆账号 : 登陆账号 限制未数字
     */
    var account: String? = null
    /**
     * 密码 限制为数字
     */
    var password: String? = null
    /**
     * 数字型密码
     */
    var passwordNum: String? = null
    /**
     * 1:用户(b.kry用户创建,) 2:系统(b.kry不可见,系统自动创建,如营销发布员) 3:品牌(只读模式,系统自动创建如admin) 4:erp同步用户
     */
    var sourceFlag: Int? = null
    /**
     * 盐值 : 用户创建时随机生成
     */
    var salt: String? = null
    /**
     * 如果归属门店，则为门店id；如果归属品牌，则为品牌id.
新的权限体系下，全部为品牌id
就是登录标示!!仅登录使用
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    var enabledFlag: Int? = null
    /**
     * 账号归属：1：品牌，2：门店，3：配送站
     */
    var assignedGroup: Int? = null
    /**
     * 账号归属ID
     */
    var assignedId: Long? = null
    var mobileStatus: Int? = null


    override fun toString(): String {
        return "AuthUserEntity{" +
        ", id=" + id +
        ", roleId=" + roleId +
        ", name=" + name +
        ", gender=" + gender +
        ", birthday=" + birthday +
        ", identityCard=" + identityCard +
        ", education=" + education +
        ", graduateSchool=" + graduateSchool +
        ", intoWorkDate=" + intoWorkDate +
        ", isMarry=" + isMarry +
        ", mobile=" + mobile +
        ", email=" + email +
        ", qq=" + qq +
        ", address=" + address +
        ", icon=" + icon +
        ", ecName=" + ecName +
        ", ecRelation=" + ecRelation +
        ", ecMobile=" + ecMobile +
        ", ecMobileReserve=" + ecMobileReserve +
        ", jobNumber=" + jobNumber +
        ", jobEmployeeType=" + jobEmployeeType +
        ", jobEntryTime=" + jobEntryTime +
        ", jobPositiveTime=" + jobPositiveTime +
        ", jobPosition=" + jobPosition +
        ", jobGrade=" + jobGrade +
        ", jobAddress=" + jobAddress +
        ", salaryCalcMode=" + salaryCalcMode +
        ", salaryBase=" + salaryBase +
        ", salaryPost=" + salaryPost +
        ", account=" + account +
        ", password=" + password +
        ", passwordNum=" + passwordNum +
        ", sourceFlag=" + sourceFlag +
        ", salt=" + salt +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        ", enabledFlag=" + enabledFlag +
        ", assignedGroup=" + assignedGroup +
        ", assignedId=" + assignedId +
        ", mobileStatus=" + mobileStatus +
        "}"
    }
}
