package com.company.forturetelling.global;

/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public class HttpConstants {
    /**
     * Common  http://192.168.2.40:2001
     */
    public static final String Common = "http://testbazi.zgszfy.com";    //线上--测试地址
//    public static final String Common = "http://192.168.2.40:2001";    //本地--测试地址
    /**
     * 版本更新  http://testbazi.zgszfy.com/static.k366.com/uploads/allimg/190807/10-1ZPG4434L32.jpg
     */
    public static final String Update = Common + "/api/bs/genxin";

    public static final String Login = Common + "/api/login/users";
//    public static final String Register = Common + "/api/login/index";
    public static final String Register = Common + "/api/dx/registered";
    public static final String Register_CheckPhone = Common + "/api/dx/PhoneNumbe";
    public static final String Image = Common + "/api/login/img";      //上传图片   /api/login/img
    public static final String InformationSetting = Common + "/api/login/updateinfo";
    public static final String Update_Password = Common + "/api/login/updatepassword";
    public static final String Information = Common + "/api/login/info";
    public static final String TodayDetails = Common + "/api/login/jmsg";
    public static final String Knowledge = Common + "/api/login/mlzs";
    public static final String LiftFortune = Common + "/api/login/mpfx";   //get
    public static final String WeChat_Login = Common + "/api/login/weixinlogin";   //get


    //添加取名接口  get
    public static final String AddName = Common + "/api/Quming/index";
    //取名详情接口  post
    public static final String NameDetails = Common + "/api/Quming/mlist";


    //八字精批----接口--三步
    public static final String EightNumber01 = Common + "/api/bs/css";                //get
    public static final String EightNumber02 = Common + "/sm2/index.php?ac=bazijp2";  //post
    public static final String EightNumber03 = Common + "/sm2/index.php?ac=bazijp2xq";//post

    //今年运程----接口--三步
    public static final String Fortune02 = Common + "/sm2/index.php?ac=jinnian2";                //post
    public static final String Fortune03 = Common + "/sm2/index.php?ac=jinnian2xq";                //post
    // 今年运程----接口--三步
    public static final String MarriageTest02 = Common + "/sm2/index.php?ac=yinyuancs2";                //post
    public static final String MarriageTest03 = Common + "/sm2/index.php?ac=yinyuancs2xq";                //post

    // 综合分析----接口--三步
    public static final String Synthesize02 = Common + "/sm2/index.php?ac=bazi2";                //post
    public static final String Synthesize03 = Common + "/sm2/index.php?ac=bazi2xq";                //post


    // 姓名详批----接口--三步
    public static final String NameDetails01 = Common + "/api/bs/xmfx";                //get
    public static final String NameDetails02 = Common + "/sm2/index.php?ac=xmfx33";                //post
    public static final String NameDetails03 = Common + "/sm2/index.php?ac=xmfx33xq";                //post
//    http://testbazi.zgszfy.com/api/aop/index
    //支付
    public static final String WXPay = Common + "/api/Wxpay/wx_pay";                //post
    public static final String WXPay_Second = Common + "/api/Wxpay/wx_notify";                //post

    public static final String ALIPay = Common + "/api/aop/index";                //post
    public static final String ALIPay_Second = Common + "/api/aop/infostatus";


    //我的订单
    public static final String Order = Common + "/api/quming/lists";

}
