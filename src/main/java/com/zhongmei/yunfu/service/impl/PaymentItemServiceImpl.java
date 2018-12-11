package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.api.PayOrderApiController;
import com.zhongmei.yunfu.controller.model.PaymentItemModel;
import com.zhongmei.yunfu.controller.weixinPay.*;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.CommercialPaySettingEntity;
import com.zhongmei.yunfu.domain.entity.PaymentItemEntity;
import com.zhongmei.yunfu.domain.entity.TradeEntity;
import com.zhongmei.yunfu.domain.entity.WxTradeCustomerEntity;
import com.zhongmei.yunfu.domain.mapper.PaymentItemMapper;
import com.zhongmei.yunfu.service.CommercialPaySettingService;
import com.zhongmei.yunfu.service.PaymentItemService;
import com.zhongmei.yunfu.service.TradeService;
import com.zhongmei.yunfu.util.HttpMessageConverterUtils;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.List;

@Service
public class PaymentItemServiceImpl extends ServiceImpl<PaymentItemMapper, PaymentItemEntity> implements PaymentItemService {

    private static Logger log = LoggerFactory.getLogger(PaymentItemServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TradeService mTradeService;

    @Autowired
    CommercialPaySettingService mCommercialPaySettingService;

    @Override
    public Boolean installPaymentItem(PaymentItemEntity mPaymentItemEntity) throws Exception{
        Boolean isSuccess = insert(mPaymentItemEntity);
        return isSuccess;
    }

    @Override
    public Boolean updatePaymentItem(PaymentItemEntity mPaymentItemEntity) throws Exception{
        EntityWrapper<PaymentItemEntity> eWrapper = new EntityWrapper<>(new PaymentItemEntity());
        eWrapper.eq("id",mPaymentItemEntity.getId());
        Boolean isSuccess = update(mPaymentItemEntity,eWrapper);
        return isSuccess;
    }

    @Override
    public List<PaymentItemEntity> queryPaymentItem(Long paymentId) throws Exception{
        EntityWrapper<PaymentItemEntity> eWrapper = new EntityWrapper<>(new PaymentItemEntity());
        eWrapper.eq("payment_id",paymentId);
        eWrapper.eq("status_flag",1);
        eWrapper.setSqlSelect("id,uuid,pay_mode_id,pay_mode_name,pay_status,useful_amount,pay_source");
        eWrapper.orderBy("server_create_time",false);
        List<PaymentItemEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public List<PaymentItemModel> queryPaymentItemReport(PaymentItemModel mPaymentItemModel) throws Exception{
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", mPaymentItemModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mPaymentItemModel.getShopIdenty());
        eWrapper.eq("pay_status",3);
        eWrapper.eq("status_flag",1);
        eWrapper.between("server_create_time", mPaymentItemModel.getStartDate(), mPaymentItemModel.getEndDate());
        List<PaymentItemModel> listData = baseMapper.querySalesAmount(eWrapper);

        return listData;
    }

    @Override
    public PaymentItemEntity queryPaymentItemByTradeId(Long tradeId) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("p.relate_id", tradeId);
        eWrapper.eq("p.status_flag", 1);
        eWrapper.eq("i.status_flag", 1);
        PaymentItemEntity mPaymentItemEntity = baseMapper.queryPaymentItemByTradeId(eWrapper);
        return mPaymentItemEntity;
    }

    @Override
    public List<PaymentItemEntity> queryPaymentItemsByTradeId(Long tradeId) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("p.relate_id", tradeId);
        eWrapper.eq("p.status_flag", 1);
        eWrapper.eq("i.status_flag", 1);
        List<PaymentItemEntity> listData = baseMapper.queryPaymentItemsByTradeId(eWrapper);
        return listData;
    }

    @Override
    public PaymentItemEntity queryPaymentItemById(Long id) throws Exception {
        PaymentItemEntity mPaymentItemEntity = selectById(id);
        return mPaymentItemEntity;
    }

    /**
     * 发起退款
     * @param mPaymentItemEntity
     * @param outRefundNo
     * @return
     */
    public String retrunPayment(Long outRefundNo,PaymentItemEntity mPaymentItemEntity,Long tradeId) throws Exception {

        String actionMessage = "";
            //获取用户支付相关信息
            CommercialPaySettingEntity mCommercialPaySettingEntity = new CommercialPaySettingEntity();
            mCommercialPaySettingEntity.setShopIdenty(mPaymentItemEntity.getShopIdenty());
            mCommercialPaySettingEntity.setBrandIdenty(mPaymentItemEntity.getBrandIdenty());
            mCommercialPaySettingEntity.setType(1);
            mCommercialPaySettingEntity.setStatusFlag(1);
            CommercialPaySettingEntity paySetting =  mCommercialPaySettingService.queryData(mCommercialPaySettingEntity);


            KeyStore keyStore  = KeyStore.getInstance("pkcs12");
            //文件路径 C:/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/apiclient_cert.p12
            String filePath = paySetting.getSecretFilepath();
            log.info("========filePath:"+filePath);
            FileInputStream instream = new FileInputStream(new File(filePath));
            try {
                keyStore.load(instream, paySetting.getWxShopId().toCharArray());
            } finally {
                instream.close();
            }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, paySetting.getWxShopId().toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            try {

                HttpPost httpPost =  new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");


                ReturnPayRequestModel retrunPayparams = retrunPayparams(outRefundNo,paySetting, mPaymentItemEntity);

                String params = HttpMessageConverterUtils.objectToXml(retrunPayparams);
                log.info("=======request params:"+params);
                org.apache.http.HttpEntity httpEntity = new StringEntity(params, "UTF-8");

                httpPost.setEntity(httpEntity);

                CloseableHttpResponse response = httpclient.execute(httpPost);
                try {

                    org.apache.http.HttpEntity entity = response.getEntity();

                    if (entity != null) {


                        String content = EntityUtils.toString(entity);
                        content = new String(content.getBytes("ISO-8859-1"),"UTF-8");
                        log.info("======content:"+content);

                        RefundReponseModel mRefundReponseModel = (RefundReponseModel) HttpMessageConverterUtils.xmlToObject(content, RefundReponseModel.class);

                        //判断申请退款是否成功
                        if(mRefundReponseModel.getResult_code().equalsIgnoreCase("SUCCESS")){
                            actionMessage = "发起退款成功";
                        }else{
                            actionMessage = refundErrCodeDes(mRefundReponseModel.getErr_code());
                        }

                        updateRefundTrade(actionMessage,mPaymentItemEntity.getId(),tradeId);

                    }
                    EntityUtils.consume(entity);
                } finally {
                    response.close();

                }
            } finally {
                httpclient.close();
            }
        return actionMessage;

    }

    public void updateRefundTrade(String message,Long paymentItemId, Long tradeId) throws Exception{
        PaymentItemEntity mPaymentItemEntity = new PaymentItemEntity();
        mPaymentItemEntity.setId(paymentItemId);

        TradeEntity mTradeEntity = new TradeEntity();
        mTradeEntity.setId(tradeId);

        if(!message.equals("发起退款成功")){
            mPaymentItemEntity.setPayStatus(6);

            mTradeEntity.setTradePayStatus(6);
            mTradeService.updateTrade(mTradeEntity);
        }

        mPaymentItemEntity.setPayMemo(message);
        updatePaymentItem(mPaymentItemEntity);

    }

    @Override
    public PaymentItemEntity refundquery(PaymentItemEntity mPaymentItemEntity,Long tradeId) throws Exception {

        //获取用户支付相关信息
        CommercialPaySettingEntity mCommercialPaySettingEntity = new CommercialPaySettingEntity();
        mCommercialPaySettingEntity.setShopIdenty(mPaymentItemEntity.getShopIdenty());
        mCommercialPaySettingEntity.setBrandIdenty(mPaymentItemEntity.getBrandIdenty());
        mCommercialPaySettingEntity.setType(1);
        mCommercialPaySettingEntity.setStatusFlag(1);
        CommercialPaySettingEntity paySetting =  mCommercialPaySettingService.queryData(mCommercialPaySettingEntity);

        RefundqueryRequestModel mRefundqueryRequestModel = new RefundqueryRequestModel();
        mRefundqueryRequestModel.setAppid(paySetting.getAppid());
        mRefundqueryRequestModel.setMch_id(paySetting.getWxShopId());
        mRefundqueryRequestModel.setNonce_str(mPaymentItemEntity.getUuid());
        mRefundqueryRequestModel.setOut_refund_no(mPaymentItemEntity.getId().toString());

        String stringA =
                "appid="+mRefundqueryRequestModel.getAppid()+
                        "&mch_id="+mRefundqueryRequestModel.getMch_id()+
                        "&nonce_str="+mRefundqueryRequestModel.getNonce_str()+
                        "&out_refund_no="+mRefundqueryRequestModel.getOut_refund_no();

        String stringSignTemp=stringA+"&key="+paySetting.getApiSecret();
        String sign = ToolsUtil.getMd5(stringSignTemp).toUpperCase();

        mRefundqueryRequestModel.setSign(sign);

        String params = HttpMessageConverterUtils.objectToXml(mRefundqueryRequestModel);
        HttpEntity<String> formEntity = new HttpEntity<String>(params);
        String url = "https://api.mch.weixin.qq.com/pay/refundquery";
        String xmlResult = restTemplate.exchange(url, HttpMethod.POST, formEntity, String.class).getBody().toString();

        log.info("=======reponse:"+xmlResult);
        RefundqueryReponseModel mRefundqueryReponseModel = (RefundqueryReponseModel) HttpMessageConverterUtils.xmlToObject(xmlResult, RefundqueryReponseModel.class);

        //判断发起退款结果查询是否成功
        if(mRefundqueryReponseModel.getResult_code().equalsIgnoreCase("SUCCESS")){
            //判断是否退款成功
            if(mRefundqueryReponseModel.getRefund_status_0().equalsIgnoreCase("SUCCESS")){

                updateRefundTrade(mPaymentItemEntity.getId(),tradeId,0,"退款成功");
            }

            //更新退款备注
            if(mRefundqueryReponseModel.getRefund_status_1()!= null && mRefundqueryReponseModel.getRefund_status_1().equalsIgnoreCase("REFUNDCLOSE")){
                String message = "退款关闭";
                updateRefundTrade(mPaymentItemEntity.getId(),tradeId,1,message);
            }else if(mRefundqueryReponseModel.getRefund_status_2()!= null && mRefundqueryReponseModel.getRefund_status_2().equalsIgnoreCase("PROCESSING")){
                String message = "退款处理中";
                updateRefundTrade(mPaymentItemEntity.getId(),tradeId,2,message);
            }else if(mRefundqueryReponseModel.getRefund_status_3()!= null && mRefundqueryReponseModel.getRefund_status_3().equalsIgnoreCase("CHANGE")){
                String message = "用户的卡被作废或者冻结了，导致原路退款银行卡失败，可前往商户平台（pay.weixin.qq.com）-交易中心，手动处理此笔退款";
                updateRefundTrade(mPaymentItemEntity.getId(),tradeId,3,message);
            }

        }else{
            PaymentItemEntity mp = new PaymentItemEntity();
            mp.setId(mPaymentItemEntity.getId());
            mp.setPayMemo(errCodeDes(mRefundqueryReponseModel.getErr_code()));
            updatePaymentItem(mp);
        }

        return mPaymentItemEntity;

    }


    /**
     * 更新退款成功时订单状态
     * @param paymentItemId
     */
    public void updateRefundTrade(Long paymentItemId,Long tradeId,int status,String payMemo) throws Exception{
        PaymentItemEntity mPaymentItemEntity = new PaymentItemEntity();
        mPaymentItemEntity.setId(paymentItemId);
        mPaymentItemEntity.setPayMemo(payMemo);

        TradeEntity mTradeEntity = mTradeService.queryTradeById(tradeId);

        if(status == 0){
            mPaymentItemEntity.setPayStatus(5);
            mTradeEntity.setTradePayStatus(5);
        }else if(status == 1){
            mPaymentItemEntity.setPayStatus(6);
            mTradeEntity.setTradePayStatus(6);
        }else if(status == 2){//退款处理中

        }else if(status == 2){
            mPaymentItemEntity.setPayStatus(6);
            mTradeEntity.setTradePayStatus(6);
        }


        updatePaymentItem(mPaymentItemEntity);
        mTradeService.updateTrade(mTradeEntity);

    }


    /**
     * 构建退款请求数据
     * @return
     */
    public ReturnPayRequestModel retrunPayparams(Long outRefundNo,CommercialPaySettingEntity paySetting, PaymentItemEntity mPaymentItemEntity){

        ReturnPayRequestModel mparams = new ReturnPayRequestModel();
        mparams.setAppid(paySetting.getAppid());
        mparams.setMch_id(paySetting.getWxShopId());
        mparams.setNonce_str(mPaymentItemEntity.getUuid());
        mparams.setOut_trade_no(outRefundNo.toString());
        mparams.setOut_refund_no(mPaymentItemEntity.getId().toString());
        mparams.setTotal_fee(mPaymentItemEntity.getUsefulAmount().multiply(new BigDecimal(100)).intValue());
        mparams.setRefund_fee(mPaymentItemEntity.getUsefulAmount().multiply(new BigDecimal(100)).intValue());

        String stringA =
                "appid="+mparams.getAppid()+
                        "&mch_id="+mparams.getMch_id()+
                        "&nonce_str="+mparams.getNonce_str()+
                        "&out_refund_no="+mparams.getOut_refund_no()+
                        "&out_trade_no="+mparams.getOut_trade_no()+
                        "&refund_fee="+mparams.getRefund_fee()+
                        "&total_fee="+mparams.getTotal_fee();

        String stringSignTemp=stringA+"&key="+paySetting.getApiSecret();

        String sign = ToolsUtil.getMd5(stringSignTemp).toUpperCase();

        mparams.setSign(sign);

        return mparams;

    }

    public String refundErrCodeDes(String resultCode){

        if(resultCode.equalsIgnoreCase("SYSTEMERROR")){
            return "系统超时";
        }else if(resultCode.equalsIgnoreCase("BIZERR_NEED_RETRY")){
            return "退款业务流程错误，需要商户触发重试来解决";
        }else if(resultCode.equalsIgnoreCase("TRADE_OVERDUE")){
            return "订单已经超过退款期限";
        }else if(resultCode.equalsIgnoreCase("ERROR")){
            return "业务错误";
        }else if(resultCode.equalsIgnoreCase("USER_ACCOUNT_ABNORMAL")){
            return "退款请求失败";
        }else if(resultCode.equalsIgnoreCase("INVALID_REQ_TOO_MUCH")){
            return "无效请求过多";
        }else if(resultCode.equalsIgnoreCase("NOTENOUGH")){
            return "余额不足";
        }else if(resultCode.equalsIgnoreCase("INVALID_TRANSACTIONID")){
            return "无效transaction_id";
        }else if(resultCode.equalsIgnoreCase("PARAM_ERROR")){
            return "参数错误";
        }else if(resultCode.equalsIgnoreCase("APPID_NOT_EXIST")){
            return "APPID不存在";
        }else if(resultCode.equalsIgnoreCase("MCHID_NOT_EXIST")){
            return "MCHID不存在";
        }else if(resultCode.equalsIgnoreCase("REQUIRE_POST_METHOD")){
            return "请使用post方法";
        }else if(resultCode.equalsIgnoreCase("SIGNERROR")){
            return "签名错误";
        }else if(resultCode.equalsIgnoreCase("XML_FORMAT_ERROR")){
            return "XML格式错误";
        }else if(resultCode.equalsIgnoreCase("FREQUENCY_LIMITED")){
            return "该笔退款未受理，请降低频率后重试";
        }else{
            return "";
        }
    }

    public String errCodeDes(String resultCode){

        if(resultCode.equalsIgnoreCase("SYSTEMERROR")){
            return "系统超时";
        }else if(resultCode.equalsIgnoreCase("REFUNDNOTEXIST")){
            return "订单号错误或订单状态不正确";
        }else if(resultCode.equalsIgnoreCase("INVALID_TRANSACTIONID")){
            return "无效transaction_id";
        }else if(resultCode.equalsIgnoreCase("PARAM_ERROR")){
            return "参数错误";
        }else if(resultCode.equalsIgnoreCase("APPID_NOT_EXIST")){
            return "APPID不存在";
        }else if(resultCode.equalsIgnoreCase("MCHID_NOT_EXIST")){
            return "MCHID不存在";
        }else if(resultCode.equalsIgnoreCase("REQUIRE_POST_METHOD")){
            return "请使用post方法";
        }else if(resultCode.equalsIgnoreCase("SIGNERROR")){
            return "签名错误";
        }else if(resultCode.equalsIgnoreCase("XML_FORMAT_ERROR")){
            return "XML格式错误";
        }else{
            return "";
        }
    }
}
