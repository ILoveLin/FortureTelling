package com.company.forturetelling.global;

/**
 * Created by Lovelin on 2019/11/28
 * <p>
 * Describe:本App-全局变量
 */
public class Constants {

    //引导页
    public static final String SP_IS_FIRST_IN = "sp_is_first_in";
    public static final String Is_Logined = "is_logined";
    public static final String Logined = "is_logined";


    //系统和请求头
    public static final String Token = "token";
    public static final String Device = "android";
    public static final String USERID = "USERID";
    public static final String Name = "Name";   //昵称


//    个人信息回写

    public static final String Info_Headimg = "Headimg";                   //头像
    public static final String Info_Name = "name";                   //姓名
    public static final String Info_Sign = "Sign";                   //签名


//    public static final String APP_ID_WECHART = "Weixin";
    public static final String APP_ID_ALIPAY = "Alipay";

//    微信资料
//    AppID：wx4b9e09ae470dc4ce
//    签名：fe99d480f0571524a4ddea6f0c8a613f     //正式版本
//
//    商户ID:1574217601
//    API密钥，在商户平台设置:wanzu202020202020202020202020202

    //appid 微信分配的公众账号ID
    public static final String APP_ID_WECHART = "wx4b9e09ae470dc4ce";

    //商户号 微信分配的公众账号ID
    public static final String MCH_ID = "1574217601";

    //  API密钥，在商户平台设置
    public static final  String API_KEY= "wanzu202020202020202020202020202";



    //appid 支付宝分配的ID
    public static final String APP_ID_ALiPay = "2021001106601835";

    //商户号 支付宝分配的ID
    public static final String ALiPay_MCH_ID = "2088731227479183";

    //  API密钥，在商户平台设置
//    public static final  String API_KEY= "wanzu202020202020202020202020202";





    public static final String ACTION_NAME_PAYMENT_ALIPY= "payment_alipay";//支付宝支付回调
    public static final String ACTION_NAME_PAYMENT_WECHAT= "payment_wechat";//微信支付回调
    public static final String ACTION_NAME_PAYMENT_SUCCESS= "payment_success";//支付成功
    public static final String ACTION_NAME_PAYMENT_ERROR= "payment_error";//支付失败
    public static final String KEY_OBJ_PAYMENT_WECHAT = "payment_wechat";



    //微信二次校验的  out_trade_no
    public static final String WECHAT_SECOND_ORDERID= "payment_wechat";//微信支付回调


}
