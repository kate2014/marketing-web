package com.zhongmei.yunfu.controller

import com.zhongmei.yunfu.api.ApiResult
import com.zhongmei.yunfu.controller.model.ActivitySearchModel
import com.zhongmei.yunfu.controller.model.CouponSearchModel
import com.zhongmei.yunfu.controller.model.CustomerServicePushModel
import com.zhongmei.yunfu.controller.model.NewDishPushSearchModel
import com.zhongmei.yunfu.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * 会员服务推送 前端控制器
 *
 * @author pigeon88
 * @since 2018-08-29
 */
@Controller
@RequestMapping("/customer/service")
class CustomerServicePushController : BaseController() {

    @Autowired
    lateinit var mPushNewDishService: PushPlanNewDishService

    @Autowired
    lateinit var couponService: CouponService

    @Autowired
    lateinit var pushPlanActivityService: PushPlanActivityService

    @Autowired
    lateinit var pushMessageCustomerService: PushMessageCustomerService

    @RequestMapping
    fun modle(): String {
        return "customer_service_push"
    }

    @RequestMapping("/newproduct")
    fun newProduct(model: Model, searchModel: NewDishPushSearchModel): String {
        searchModel.pageSize = 5
        searchModel.setPlanState(1);
        LoginManager.setUserShopIdenty(searchModel)
        val listPage = mPushNewDishService.list(searchModel)
        var result = arrayListOf<Any>()
        listPage.records.forEach {
            result.add(ServicePush(1, it.id, it.name, if (it.planState == 1) "进行中" else "停止", it.dishRemark))
        }

        setWebPage(model, listPage, searchModel)
        model.addAttribute("searchModel", searchModel)
        model.addAttribute("list", result)
        return "customer_service_push_list"
    }

    @RequestMapping("/activity")
    fun activity(model: Model, searchModel: ActivitySearchModel): String? {
        searchModel.pageSize = 5
        searchModel.planState = 1;
        LoginManager.setUserShopIdenty(searchModel)
        val listPage = pushPlanActivityService.findListPage(searchModel)
        var result = arrayListOf<Any>()
        listPage.records.forEach {
            result.add(ServicePush(2, it.id, it.name, if (it.planState == 1) "进行中" else "停止", it.planDesc))
        }

        setWebPage(model, listPage, searchModel)
        model.addAttribute("searchModel", searchModel)
        model.addAttribute("list", result)
        return "customer_service_push_list"
    }

    @RequestMapping("/coupon")
    fun privilege(model: Model, searchModel: CouponSearchModel): String? {
        searchModel.pageSize = 5
        LoginManager.setUser(searchModel)
        searchModel.couponState = 1;
        val listPage = couponService.findListPage(searchModel)
        var result = arrayListOf<Any>()
        listPage.records.forEach {
            result.add(ServicePush(3, it.id, it.name, if (it.couponState == 1) "进行中" else "停止", it.remark))
        }

        setWebPage(model, listPage, searchModel)
        model.addAttribute("searchModel", searchModel)
        model.addAttribute("list", result)
        return "customer_service_push_list"
    }

    @RequestMapping("/push")
    @ResponseBody
    fun push(@RequestBody req: CustomerServicePushModel): ApiResult {
        var user = LoginManager.get().user
        pushMessageCustomerService.installPushBatch(req.serviceType,
                req.customerIds,
                req.serviceId,
                user?.brandIdenty,
                user?.shopIdenty,
                user?.id,
                user?.name)
        return ApiResult.newSuccess(null)
    }
}

class ServicePush(var type: Int, var id: Long?, var name: String?, var statusName: String?, var intro: String?)

