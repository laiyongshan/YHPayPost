package com.yzf.Cpaypos.net;

import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.model.servicesmodels.AddRepaymentResults;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.FindPwdResults;
import com.yzf.Cpaypos.model.servicesmodels.GankResults;
import com.yzf.Cpaypos.model.servicesmodels.GetAccountInfoResult;
import com.yzf.Cpaypos.model.servicesmodels.GetBannerListResult;
import com.yzf.Cpaypos.model.servicesmodels.GetCodeResults;
import com.yzf.Cpaypos.model.servicesmodels.GetH5Results;
import com.yzf.Cpaypos.model.servicesmodels.GetMerchFeeResult;
import com.yzf.Cpaypos.model.servicesmodels.GetMerchInfoResult;
import com.yzf.Cpaypos.model.servicesmodels.GetMerchLevelResults;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.model.servicesmodels.GetPlanCardResults;
import com.yzf.Cpaypos.model.servicesmodels.GetPreviewPlanResult;
import com.yzf.Cpaypos.model.servicesmodels.GetProfitListResult;
import com.yzf.Cpaypos.model.servicesmodels.GetProfitResult;
import com.yzf.Cpaypos.model.servicesmodels.GetPushMsgResults;
import com.yzf.Cpaypos.model.servicesmodels.GetRepaymentResults;
import com.yzf.Cpaypos.model.servicesmodels.GetSubmerchResult;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.model.servicesmodels.LoginResult;
import com.yzf.Cpaypos.model.servicesmodels.RegiestResult;
import com.yzf.Cpaypos.model.servicesmodels.SearchBankResult;
import com.yzf.Cpaypos.model.servicesmodels.TradeResult;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * ClaseName：APPService
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/10 18:02
 * Modified By：
 * Fixtime：2017/5/10 18:02
 * FixDescription：
 */
public interface APPService {

    @GET("data/{type}/{number}/{page}")
    Observable<GankResults> getGankData(@Path("type") String type,
                                        @Path("number") int pageSize,
                                        @Path("page") int pageNum);


    /**
     * 获取验证码
     *
     * @param phoneNo
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETCODES_URL)
    Observable<GetCodeResults> getCode(@Field("phoneNo") String phoneNo,
                                       @Field("type") String type);

    /**
     * 登陆
     *
     * @param merchId
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.LOGIN_URL)
    Observable<LoginResult> login(@Field("merchId") String merchId,
                                  @Field("password") String password,
                                  @Field("topBranchNo") String topBranchNo);

    /**
     * 刷新用户状态
     *
     * @param merchId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.REFRESH_URL)
    Observable<LoginResult> refresh(@Field("merchId") String merchId);

    /**
     * 注册
     *
     * @param merchId
     * @param password
     * @param code
     * @param branchNo
     * @param userType
     * @param planCode
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.REGIEST_URL)
    Observable<RegiestResult> regist(@Field("merchId") String merchId,
                                     @Field("password") String password,
                                     @Field("code") String code,
                                     @Field("branchNo") String branchNo,
                                     @Field("userType") String userType,
                                     @Field("planCode") String planCode,
                                     @Field("topBranchNo") String topBranchNo);

    /**
     * 找回密码
     *
     * @param merchId
     * @param password
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.FINDPASSWORD_URL)
    Observable<FindPwdResults> findpassword(@Field("merchId") String merchId,
                                            @Field("password") String password,
                                            @Field("code") String code);

    /**
     * 搜索银行总行
     *
     * @param bankName
     * @param page
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.SEARCHBANK_URL)
    Observable<SearchBankResult> searchBank(@Field("bankName") String bankName,
                                            @Field("page") int page,
                                            @Field("pageNum") int pageNum);

    /**
     * 搜索银行支行
     *
     * @param bankName
     * @param page
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.SEARCHSUBBANK_URL)
    Observable<SearchBankResult> searchSubBank(@Field("bankName") String bankName,
                                               @Field("page") int page,
                                               @Field("pageNum") int pageNum,
                                               @Field("bankType") String bankType);


    /**
     * 注册上传资料
     *
     * @param merchId
     * @param idCard
     * @param bankName
     * @param acctNo
     * @param phoneNo
     * @param acctName
     * @param bankNo
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.UPLOADDATA_URL)
    Observable<BaseResults> upLoadData(@Field("merchId") String merchId,
                                       @Field("idCard") String idCard,
                                       @Field("bankName") String bankName,
                                       @Field("acctNo") String acctNo,
                                       @Field("phoneNo") String phoneNo,
                                       @Field("acctName") String acctName,
                                       @Field("bankNo") String bankNo,
                                       @Field("sign") String sign);

    /**
     * 注册上传资料
     *
     * @param merchId
     * @param idCard
     * @param acctNo
     * @param phoneNo
     * @param acctName
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.VERIFIED_URL)
    Observable<BaseResults> upLoadData(@Field("merchId") String merchId,
                                       @Field("idCard") String idCard,
                                       @Field("acctNo") String acctNo,
                                       @Field("phoneNo") String phoneNo,
                                       @Field("acctName") String acctName,
                                       @Field("sign") String sign);

    /**
     * 上传图片
     *
     * @param file
     * @param fileType
     * @param merchId
     * @return
     */
    @Multipart
    @POST(AppConfig.UPLOADPHOTOS_URL)
    Observable<BaseResults> uploadPhoto(@Part MultipartBody.Part file,
                                        @Query("fileType") String fileType,
                                        @Query("merchId") String merchId);


    /**
     * 交易接口
     *
     * @param orderId
     * @param orderAmt
     * @param userId
     * @param sign
     * @param busCode
     * @param authCode
     * @param pan
     * @param goodsName
     * @param goodsDesc
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.TRADE_URL)
    Observable<TradeResult> trade(@Field("orderId") String orderId,
                                  @Field("orderAmt") String orderAmt,
                                  @Field("userId") String userId,
                                  @Field("sign") String sign,
                                  @Field("busCode") String busCode,
                                  @Field("authCode") String authCode,
                                  @Field("pan") String pan,
                                  @Field("goodsName") String goodsName,
                                  @Field("goodsDesc") String goodsDesc);

    /**
     * 交易接口
     *
     * @param orderId
     * @param orderAmt
     * @param userId
     * @param sign
     * @param busCode
     * @param authCode
     * @param pan
     * @param goodsName
     * @param goodsDesc
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.TRADE_URL)
    Observable<String> quickPay(@Field("orderId") String orderId,
                                @Field("orderAmt") String orderAmt,
                                @Field("userId") String userId,
                                @Field("sign") String sign,
                                @Field("busCode") String busCode,
                                @Field("authCode") String authCode,
                                @Field("pan") String pan,
                                @Field("goodsName") String goodsName,
                                @Field("goodsDesc") String goodsDesc);

    /**
     * 获取交易列表
     *
     * @param page
     * @param pageNum
     * @param beginTime
     * @param endTime
     * @param merchId
     * @param serviceId
     * @param result
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETORDERLIST_URL)
    Observable<GetOrderListResult> getOrderList(@Field("page") int page,
                                                @Field("pageNum") int pageNum,
                                                @Field("beginTime") String beginTime,
                                                @Field("endTime") String endTime,
                                                @Field("merchId") String merchId,
                                                @Field("serviceId") String serviceId,
                                                @Field("result") String result,
                                                @Field("orderId") String orderId,
                                                @Field("notServiceId") String notServiceId);

    /**
     * 获取账户信息
     *
     * @param merchId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETACCTINFO_URL)
    Observable<GetAccountInfoResult> getAcctInfo(@Field("merchId") String merchId);


    /**
     * 获取商户费率信息
     *
     * @param merchId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETMERCHFEE_URL)
    Observable<GetMerchFeeResult> getMerchFee(@Field("merchId") String merchId);

    /**
     * 获取商户信息
     *
     * @param merchId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETMERCHINFO_URL)
    Observable<GetMerchInfoResult> getMerchInfo(@Field("merchId") String merchId);


    /**
     * 获取白名单卡信息
     *
     * @param merchId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETWHITELIST_URl)
    Observable<GetWhiteCardListResult> getWhiteCardList(@Field("merchId") String merchId);

    /**
     * 删除白名单卡
     *
     * @param merchId
     * @param acctNo
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.DELETEBANKCARD_URl)
    Observable<BaseResults> deleteBankCard(@Field("merchId") String merchId,
                                           @Field("acctNo") String acctNo);

    /**
     * 绑定银行卡
     *
     * @param merchId
     * @param acctNo
     * @param phoneNo
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BINDCARD_URL)
    Observable<BaseResults> BindCard(@Field("merchId") String merchId,
                                     @Field("acctNo") String acctNo,
                                     @Field("phoneNo") String phoneNo,
                                     @Field("sign") String sign);

    /**
     * 修改密码
     *
     * @param merchId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.CHANGEPASSWORD_URL)
    Observable<BaseResults> changePassword(@Field("merchId") String merchId,
                                           @Field("oldPwd") String oldPwd,
                                           @Field("newPwd") String newPwd);


    /**
     * 获取微创业收益
     *
     * @param merchId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETPROFIT_URL)
    Observable<GetProfitResult> getProfit(@Field("merchId") String merchId);


    /**
     * 获取子商户
     *
     * @param merchId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETSUBMERCH_URL)
    Observable<GetSubmerchResult> getSubMerch(@Field("merchId") String merchId,
                                              @Field("page") int page,
                                              @Field("pageNum") int pageNum);


    /**
     * 获取收益列表
     *
     * @param page
     * @param pageNum
     * @param beginTime
     * @param endTime
     * @param merchId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETPROFITLIST_URL)
    Observable<GetProfitListResult> getProfitList(
            @Field("merchId") String merchId,
            @Field("page") int page,
            @Field("pageNum") int pageNum,
            @Field("beginTime") String beginTime,
            @Field("endTime") String endTime);

    /**
     * 获取商户等级
     *
     * @param merchId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETMERCHLEVEL_URL)
    Observable<GetMerchLevelResults> getMerchLevel(@Field("merchId") String merchId);

    @FormUrlEncoded
    @POST(AppConfig.GETBANNERLIST_URL)
    Observable<GetBannerListResult> getBannerList(@Field("topBranchNo") String topBranchNo);

    @FormUrlEncoded
    @POST(AppConfig.GETWELCOMEPHOTOS_URL)
    Observable<GetBannerListResult> getLoginBannerList(@Field("topBranchNo") String topBranchNo);

    /**
     * 获取随机养卡规划
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETRANDOMPLANCARD_URL)
    Observable<GetRepaymentResults> RandomPlanCard(@Field("amount") String amount,
                                                   @Field("count") String count,
                                                   @Field("days") String days,
                                                   @Field("times") String times,
                                                   @Field("type") String type,
                                                   @Field("scale") String scale,
                                                   @Field("balance") String balance);

    /**
     * 新增养卡规划
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.CREATEPLANCARD_URL)
    Observable<AddRepaymentResults> CreatePlanCard(@Field("amount") String amount,
                                                   @Field("count") String count,
                                                   @Field("merchId") String merchId,
                                                   @Field("cardId") String cardId,
                                                   @Field("detail") String detail);

    /**
     * 确认养卡规划
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.CONFIRMPLANCARD_URL)
    Observable<BaseResults> ConfirmPlanCard(@Field("merchId") String merchId,
                                            @Field("cardId") String cardId,
                                            @Field("orderId") String orderId,
                                            @Field("confirmType") String confirmType);

    /**
     * 支付保证金
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.PAYPLANCARD_URL)
    Observable<BaseResults> PayPlanCard(@Field("merchId") String merchId,
                                            @Field("cardId") String cardId,
                                            @Field("orderId") String orderId,
                                            @Field("confirmType") String confirmType,
                                            @Field("code") String code);

    /**
     * CGI快捷绑卡
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.CGIBINDCARD_URL)
    Observable<BaseResults> CgiBindCard(@Field("ORDER_ID") String ORDER_ID,
                                        @Field("ORDER_AMT") String ORDER_AMT,
                                        @Field("ORDER_TIME") String ORDER_TIME,
                                        @Field("USER_TYPE") String USER_TYPE,
                                        @Field("USER_ID") String USER_ID,
                                        @Field("SIGN_TYPE") String SIGN_TYPE,
                                        @Field("BUS_CODE") String BUS_CODE,
                                        @Field("CCT") String CCT,
                                        @Field("CARD_NO") String CARD_NO,
                                        @Field("CVN2") String CVN2,
                                        @Field("EXPDATE") String EXPDATE,
                                        @Field("PHONE_NO") String PHONE_NO,
                                        @Field("ID_NO") String ID_NO,
                                        @Field("NAME") String NAME,
                                        @Field("ADD3") String ADD3,
                                        @Field("ADD4") String ADD4);

    /**
     * CGI快捷绑卡确认
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.CONFIRMBINDCARD_URL)
    Observable<BaseResults> ConfirmBindCard(@Field("ORDER_ID") String ORDER_ID,
                                            @Field("ORDER_AMT") String ORDER_AMT,
                                            @Field("ORDER_TIME") String ORDER_TIME,
                                            @Field("USER_TYPE") String USER_TYPE,
                                            @Field("USER_ID") String USER_ID,
                                            @Field("SIGN_TYPE") String SIGN_TYPE,
                                            @Field("BUS_CODE") String BUS_CODE,
                                            @Field("CCT") String CCT,
                                            @Field("CARD_NO") String CARD_NO,
                                            @Field("SMS_CODE") String SMS_CODE,
                                            @Field("ORIG_ORDER_ID") String ORIG_ORDER_ID);

    /**
     * 查询养卡规划
     *
     * @param merchId
     * @param cardId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.QUERYPLAN_URL)
    Observable<GetPlanCardResults> GetPlanCard(@Field("merchId") String merchId,
                                               @Field("cardId") String cardId,
                                               @Field("page") int page,
                                               @Field("pageNum") int pageNum,
                                               @Field("beginTime") String beginTime,
                                               @Field("endTime") String endTime,
                                               @Field("status") String status);

    /**
     * CGI无卡快捷
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.CGIQUICKPAY_URL)
    Observable<BaseResults> cgiQuickPay(@Field("ORDER_ID") String ORDER_ID,
                                        @Field("ORDER_AMT") String ORDER_AMT,
                                        @Field("ORDER_TIME") String ORDER_TIME,
                                        @Field("USER_TYPE") String USER_TYPE,
                                        @Field("USER_ID") String USER_ID,
                                        @Field("SIGN_TYPE") String SIGN_TYPE,
                                        @Field("BUS_CODE") String BUS_CODE,
                                        @Field("CCT") String CCT,
                                        @Field("CARD_NO") String CARD_NO,
                                        @Field("CVN2") String CVN2,
                                        @Field("EXPDATE") String EXPDATE,
                                        @Field("PHONE_NO") String PHONE_NO,
                                        @Field("ID_NO") String ID_NO,
                                        @Field("NAME") String NAME,
                                        @Field("SMS_CODE") String SMS_CODE);


    /**
     * CGI快捷绑卡
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.H5QUICKPAY_URL)
    Observable<String> h5QuickPay(@Field("ORDER_ID") String ORDER_ID,
                                  @Field("ORDER_AMT") String ORDER_AMT,
                                  @Field("ORDER_TIME") String ORDER_TIME,
                                  @Field("USER_TYPE") String USER_TYPE,
                                  @Field("USER_ID") String USER_ID,
                                  @Field("SIGN_TYPE") String SIGN_TYPE,
                                  @Field("BUS_CODE") String BUS_CODE,
                                  @Field("CCT") String CCT,
                                  @Field("CARD_NO") String CARD_NO,
                                  @Field("PAY_TYPE") String PAY_TYPE);

    /**
     * 提现交易
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.WITHDRAW_URL)
    Observable<BaseResults> withDraw(@Field("ORDER_ID") String ORDER_ID,
                                     @Field("ORDER_AMT") String ORDER_AMT,
                                     @Field("ORDER_TIME") String ORDER_TIME,
                                     @Field("USER_TYPE") String USER_TYPE,
                                     @Field("USER_ID") String USER_ID,
                                     @Field("SIGN_TYPE") String SIGN_TYPE,
                                     @Field("BUS_CODE") String BUS_CODE);

    /**
     * 开通卡规划
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.OPENPLANCARD_URL)
    Observable<BaseResults> openPlanCard(@Field("merchId") String merchId,
                                         @Field("code") String code);

    /**
     * 修改结算卡
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.MODIFIEDSETTLECARD_URL)
    Observable<BaseResults> modifySettleCard(@Field("merchId") String merchId,
                                             @Field("acctNo") String acctNo,
                                             @Field("phoneNo") String phoneNo);

    /**
     * 修改结算卡
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.MODIFIEDCREDITCARD_URL)
    Observable<BaseResults> modifyCreditCard(@Field("merchId") String merchId,
                                             @Field("acctNo") String acctNo,
                                             @Field("cardLimit") String cardLimit,
                                             @Field("add3") String add3,
                                             @Field("add4") String add4);


    /**
     * 添加交易记录
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.ADDRECORD_URL)
    Observable<BaseResults> addRecord(@Field("merchId") String merchId,
                                      @Field("topBranchNo") String topBranchNo,
                                      @Field("orderId") String orderId,
                                      @Field("clientIdentify") String clientIdentify,
                                      @Field("systemModel") String systemModel,
                                      @Field("systemPhone") String systemPhone,
                                      @Field("lng") String lng,
                                      @Field("lat") String lat,
                                      @Field("platformCode") String platformCode,
                                      @Field("userId") String userId,
                                      @Field("phone") String phone,
                                      @Field("soureCode") String soureCode);

    /**
     * 获取规划预览
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETPREVIEWPLAN_URL)
    Observable<GetPreviewPlanResult> getPreviewPlan(@Field("merchId") String merchId,
                                                    @Field("cardId") String cardId);

    /**
     * 获取规划预览
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.QUERYPLANDETAIL_URL)
    Observable<GetPlanCardResults> GetPlanCardDetail(@Field("orderId") String orderId);

    /**
     * 获取推送消息
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETPUSHMSG_URL)
    Observable<GetPushMsgResults> getPushMsg(@Field("topBranchNo") String topBranchNo);


    /**
     * 获取贷款H5
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETLOANSH5_URL)
    Observable<GetH5Results> getLoansH5(@Field("merchId") String merchId,
                                        @Field("topBranchNo") String topBranchNo,
                                        @Field("orderId") String orderId,
                                        @Field("clientIdentify") String clientIdentify,
                                        @Field("systemModel") String systemModel,
                                        @Field("systemPhone") String systemPhone,
                                        @Field("lng") String lng,
                                        @Field("lat") String lat,
                                        @Field("platformCode") String platformCode,
                                        @Field("userId") String userId,
                                        @Field("phone") String phone,
                                        @Field("soureCode") String soureCode);

    /**
     * 获取信用卡H5
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.GETCREDITH5_URL)
    Observable<GetH5Results> getCreditH5(@Field("merchId") String merchId,
                                       @Field("topBranchNo") String topBranchNo,
                                       @Field("orderId") String orderId,
                                       @Field("clientIdentify") String clientIdentify,
                                       @Field("systemModel") String systemModel,
                                       @Field("systemPhone") String systemPhone,
                                       @Field("lng") String lng,
                                       @Field("lat") String lat,
                                       @Field("platformCode") String platformCode,
                                       @Field("userId") String userId,
                                       @Field("phone") String phone,
                                       @Field("soureCode") String soureCode);
}
