package com.zhongmei.yunfu.domain.enums;

public enum AuthPerCodeEnum {

    WINXIN_SHOP_PIC, //小程序图片上传权限
    USER_MODIFY, //员工编辑权限（包含启动、禁用操作）
    USER_DELETE, //员工删除权限
    USER_ADD, //员工添加权限
    TELENT_SEE, //人效提成核算查看权限
    TELENT_PLAN_MODIFY, //人效方案编辑权限（包含启用、停用）
    TELENT_PLAN_DELETE, //人效方案删除权限
    TELENT_PLAN_ADD, //人效方案创建权限
    REPORT_SEE, //报表查看权限
    PUSH_ACTIVITY, //服务推送权限
    DISK_MODIFY, //工作台编辑权限（只要有编辑权限，可对区域、桌台做什么操作）
    DISH_MODIFY, //编辑品项权限
    DISH_DELETE, //删除品项权限
    DISH_ADD_GROUP, //添加品项分类权限
    DISH_ADD, //添加品项权限
    CUSTOMER_SETTING, //会员设置权限（只要有会员设置权限，可以对里面信息进行操作）
    CUSTOMER_SEE, //查看会员权限
    CUSTOMER_MODIFY, //编辑会员权限
    CUSTOMER_ADD, //添加会员权限
}
