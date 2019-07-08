package com.zhongmei.yunfu.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.FeedbackModel;
import com.zhongmei.yunfu.domain.entity.FeedbackEntity;
import com.zhongmei.yunfu.domain.entity.StarRatingEntity;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 顾客反馈
 */
@Controller
@RequestMapping("/customer/feedback")
public class CustomerFeedbackController extends BaseController{

    @Autowired
    FeedbackService mFeedbackService;
    @Autowired
    StarRatingService mStarRatingService;

    @RequestMapping("/list")
    public String feedbackList(Model model, FeedbackModel mFeedbackModel) {

        try {

            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            FeedbackEntity mFeedbackEntity = new FeedbackEntity();
            mFeedbackEntity.setBrandIdenty(brandIdentity);
            mFeedbackEntity.setShopIdenty(shopIdentity);

            Page<FeedbackEntity> listPage = mFeedbackService.queryFeedbackPage(mFeedbackEntity,mFeedbackModel.getPageNo(),mFeedbackModel.getPageSize());

            setWebPage(model, "/customer/feedback/list", listPage, mFeedbackEntity);

            List<FeedbackEntity> listFeedback = listPage.getRecords();

            String tradeIds = "";
            if(listFeedback != null){
                for(FeedbackEntity feedback : listFeedback){
                    if(tradeIds.equals("")){
                        tradeIds = feedback.getTradeId()+"";
                    }else{
                        tradeIds = tradeIds+","+feedback.getTradeId();
                    }
                }
            }

            List<StarRatingEntity> listStar = mStarRatingService.queryStarRatingList(tradeIds,brandIdentity,shopIdentity);

            List<FeedbackModel> listFeedbackModel = new LinkedList<>();
            if(listFeedback != null) {
                for (FeedbackEntity feedback : listFeedback) {
                    FeedbackModel tempFeedbackModel = new FeedbackModel();
                    List<StarRatingEntity> feedbackStar = new LinkedList<>();
                    for (StarRatingEntity star : listStar) {
                        if(feedback.getTradeId().longValue() == star.getTradeId().longValue()){
                            feedbackStar.add(star);
                        }
                    }
                    tempFeedbackModel.setFeedbackId(feedback.getId());
                    tempFeedbackModel.setType(feedback.getType());
                    tempFeedbackModel.setStart(feedback.getStart());
                    tempFeedbackModel.setContent(feedback.getContent());
                    tempFeedbackModel.setReplayContent(feedback.getReplayContent());
                    tempFeedbackModel.setTradeId(feedback.getTradeId());
                    tempFeedbackModel.setCustomerId(feedback.getCustomerId());
                    tempFeedbackModel.setCustomerName(feedback.getCustomerName());
                    tempFeedbackModel.setUserName(feedback.getUserName());
                    tempFeedbackModel.setServerCreateTime(DateFormatUtil.format(feedback.getServerCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                    tempFeedbackModel.setServerUpdateTime(DateFormatUtil.format(feedback.getServerUpdateTime(),"yyyy-MM-dd HH:mm:ss"));

                    tempFeedbackModel.setListStar(feedbackStar);
                    listFeedbackModel.add(tempFeedbackModel);
                }
            }

            model.addAttribute("listFeedback",listFeedbackModel);

            return "feedback_list";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }


    @RequestMapping("/replayAction")
    public String replayAction(Model model, FeedbackModel mFeedbackModel) {

        try {
            Long creatorId = LoginManager.get().getUser().getCreatorId();
            String creatorname = LoginManager.get().getUser().getCreatorName();

            FeedbackEntity mFeedbackEntity = new FeedbackEntity();
            mFeedbackEntity.setId(mFeedbackModel.getFeedbackId());
            mFeedbackEntity.setReplayContent(mFeedbackModel.getReplayContent());
            mFeedbackEntity.setStart(2);
            mFeedbackEntity.setUserId(creatorId);
            mFeedbackEntity.setUserName(creatorname);
            mFeedbackEntity.setServerUpdateTime(new Date());

            mFeedbackService.midfityFeedback(mFeedbackEntity);

            return "success";

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

}
