package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 门店菜品
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("dish_shop")
class DishShopEntity : BaseEntity() {

    /**
     * 自增id : 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 门店菜品uuid : 唯一标识
     */
    var uuid: String? = null
    /**
     * 品牌菜品id : 品牌菜品id
     */
    var brandDishId: Long? = null
    /**
     * 品牌菜品uuid : 品牌菜品uuid
     */
    var brandDishUuid: String? = null
    /**
     * 菜品类型id : 菜品类型id
     */
    var dishTypeId: Long? = null
    /**
     * 菜品编码 : 菜品编码
     */
    var dishCode: String? = null
    /**
     * 菜品类型 : 菜品种类 0:单菜 1:套餐 2:加料 3:次数卡 4:无限次次数卡
     */
    var type: Int? = null
    /**
     * 菜品名称 : 菜品名称
     */
    var name: String? = null
    /**
     * 别名 : 别名
     */
    var aliasName: String? = null
    /**
     * 短名称
     */
    var shortName: String? = null
    /**
     * 别名：短名称
     */
    var aliasShortName: String? = null
    /**
     * 菜品名称索引(首字母) : 菜品名称索引(首字母)
     */
    var dishNameIndex: String? = null
    /**
     * 条形码 : 条形码
     */
    var barcode: String? = null
    /**
     * 单位id : 单位id
     */
    var unitId: Long? = null
    /**
     * 单位名称
     */
    var unitName: String? = null
    /**
     * 单位换算称重
     */
    var weight: BigDecimal? = null
    /**
     * 原价 : 原价
     */
    var marketPrice: BigDecimal? = null
    /**
     * 排序 : 排序
     */
    var sort: Int? = null
    /**
     * 菜品描述 : 菜品描述
     */
    var dishDesc: String? = null
    /**
     * 视频地址 : 视频地址
     */
    var videoUrl: String? = null
    /**
     * 库存类型 : 1、预制商品2、现制商品3、外购商品4、原物料5、半成品
     */
    var wmType: Int? = null
    /**
     * 销售类型 :0 默认单次销售  1 称重销售  2 次卡销售  3 计时销售
     */
    var saleType: Int? = null
    /**
     * 起卖份数 : 起卖份数
     */
    var dishIncreaseUnit: BigDecimal? = null
    /**
     * 是否允许单点 : 1 允许 2不允许
     */
    var isSingle: Int? = null
    /**
     * 是否允许整单打折 : 1 允许 2不允许
     */
    var isDiscountAll: Int? = null
    /**
     * 来源：1  商家后台，2 on_mobile
     */
    var source: Int? = null
    /**
     * 是否允许上门服务 : 1 允许 2不允许
     */
    var isSendOutside: Int? = null
    /**
     * 是否允店内使用1.允许2.不允许
     */
    var isOrder: Int? = null
    /**
     * 商品自定义属性：1.普通商品；2.自定义商品
     */
    var defProperty: Int? = null
    /**
     * 增量设置
     */
    var stepNum: BigDecimal? = null
    /**
     * 适合人群（小） : 适合人群（小）  ====PS：现在暂时使用minNum来标记时间数据：-1表示无限时间, 其他表示使用时长
     */
    var minNum: Int? = null
    /**
     * 适合人群（大） : 适合人群（大）====PS：现在暂时使用maxNum来标记时间单位：1：天   2：周  3：月
     */
    var maxNum: Int? = null
    /**
     * 估清 : 1：在售 ，2：卖光
     */
    var clearStatus: Int? = null
    /**
     * 是否手动操作在售或者估清
     */
    var isManual: Int? = null
    /**
     * 每日售卖总数 : 每日售卖总数
     */
    var saleTotal: BigDecimal? = null
    /**
     * 剩余总数 : 剩余总数
     */
    var residueTotal: BigDecimal? = null
    /**
     * 外送可售数量 : 外送可售数量
     */
    var saleTotalWechat: BigDecimal? = null
    /**
     * 外送剩余数量
     */
    var residueTotalWechat: BigDecimal? = null
    /**
     * 时间段起始时间 : 时间段起始时间
     */
    var validTime: Date? = null
    /**
     * 时间段截止时间 : 时间段截止时间
     */
    var unvalidTime: Date? = null
    /**
     * 销售场景 : 商户终端、微信、自助点餐。（3位二进制组合，“1”为可售，“0”为不可售，如“110”表示“商户终端-可售、微信-可售、自助-不可售”）
     */
    var scene: String? = null
    /**
     * 商户id : 商户id
     */
    var shopIdenty: Long? = null
    /**
     * 品牌id : 品牌id
     */
    var brandIdenty: Long? = null
    /**
     * 启用停用标识 : 区别与StatusFlag，启用停用的作用是该数据是有效数据，但是被停用。 1:启用;2:停用
     */
    var enabledFlag: Int? = null
    /**
     * 商品唯一编号 : 商品唯一编号，用算法生成
     */
    var skuKey: String? = null
    /**
     * 是否有规格 : 是否有规格 1 是  2  不是
     */
    var hasStandard: Int? = null
    /**
     * 商品数量
     */
    var dishQty: BigDecimal? = null
    /**
     * 包装盒数量
     */
    var boxQty: Int? = null
    /**
     * 当前可销售数量，NULL代表没有限制
     */
    var currRemainTotal: Long? = null
    /**
     * 是否允许变价: 1 允许 2不允许
     */
    var isChangePrice: Int? = null
    /**
     * 所属SPU id
     */
    var productId: Long? = null
    var maxUum: Long? = null


    override fun toString(): String {
        return "DishShopEntity{" +
        ", id=" + id +
        ", uuid=" + uuid +
        ", brandDishId=" + brandDishId +
        ", brandDishUuid=" + brandDishUuid +
        ", dishTypeId=" + dishTypeId +
        ", dishCode=" + dishCode +
        ", type=" + type +
        ", name=" + name +
        ", aliasName=" + aliasName +
        ", shortName=" + shortName +
        ", aliasShortName=" + aliasShortName +
        ", dishNameIndex=" + dishNameIndex +
        ", barcode=" + barcode +
        ", unitId=" + unitId +
        ", unitName=" + unitName +
        ", weight=" + weight +
        ", marketPrice=" + marketPrice +
        ", sort=" + sort +
        ", dishDesc=" + dishDesc +
        ", videoUrl=" + videoUrl +
        ", wmType=" + wmType +
        ", saleType=" + saleType +
        ", dishIncreaseUnit=" + dishIncreaseUnit +
        ", isSingle=" + isSingle +
        ", isDiscountAll=" + isDiscountAll +
        ", source=" + source +
        ", isSendOutside=" + isSendOutside +
        ", isOrder=" + isOrder +
        ", defProperty=" + defProperty +
        ", stepNum=" + stepNum +
        ", minNum=" + minNum +
        ", maxNum=" + maxNum +
        ", clearStatus=" + clearStatus +
        ", isManual=" + isManual +
        ", saleTotal=" + saleTotal +
        ", residueTotal=" + residueTotal +
        ", saleTotalWechat=" + saleTotalWechat +
        ", residueTotalWechat=" + residueTotalWechat +
        ", validTime=" + validTime +
        ", unvalidTime=" + unvalidTime +
        ", scene=" + scene +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        ", enabledFlag=" + enabledFlag +
        ", skuKey=" + skuKey +
        ", hasStandard=" + hasStandard +
        ", dishQty=" + dishQty +
        ", boxQty=" + boxQty +
        ", currRemainTotal=" + currRemainTotal +
        ", isChangePrice=" + isChangePrice +
        ", productId=" + productId +
        ", maxUum=" + maxUum +
        "}"
    }
}
