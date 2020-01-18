package com.company.forturetelling.utils;

import android.util.Log;

import com.company.forturetelling.bean.paybean.PayBean;
import com.company.forturetelling.bean.paybean.WXParamsBean;
import com.company.forturetelling.global.HttpConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by Lovelin on 2020/1/15
 * <p>
 * Describe:
 */
public class PayUtils {

    /**
     * 先请求本地服务器拿到调用支付使用到的各种参数
     *
     * @param payType 　　0-微信 1-支付宝
     * @return
     */
    public static PayBean GetParamsFromBackground(int payType) {
        PayBean payBean = new PayBean();
        switch (payType) {
            case 0:         //微信
                OkHttpUtils.post()
                        .url(HttpConstants.WXPay)
                        .addParams("", "")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                                Log.e("PayUtils----WX", "PayUtils===Exception");
                            }

                            //                            private  String appid;    //应用id： 是你申请应用后就有的
//                            private  String partnerid;    //商户号
//                            private  String prepayid;    //预付单号：是走统一下单的接口后，微信返回的一个预支付单号
//                            private  String packagee;    //扩展字段：这个字段暂时没啥用，微信是让我们填写固定值Sign=WXPay就行
//                            private  String noncestr;    //随机字符串：这个是后台自己随便生成的一个随机字符串，但是不要超过32位，微信官方提供给了我们一个随机生成算法
//                            private  String timestamp;    //时间戳： 没啥好说
//                            private  String sign;    //后台参考微信提供给我们的
                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("PayUtils----WX", "response====PayUtils===" + response);


//                                WXParamsBean wxParamsBean = new WXParamsBean();
                                Type type = new TypeToken<WXParamsBean>() {
                                }.getType();
                                Gson gson = new Gson();
                                WXParamsBean wxParamsBean = gson.fromJson(response, type);
                                payBean.setAppid(wxParamsBean.getData().getAppid());
                                payBean.setPartnerid(wxParamsBean.getData().getPartnerid());
                                payBean.setPrepayid(wxParamsBean.getData().getPrepayid());
                                payBean.setPackagee(wxParamsBean.getData().getPackageX());
                                payBean.setNoncestr(wxParamsBean.getData().getNoncestr());
                                payBean.setTimestamp(wxParamsBean.getData().getTimestamp());
                                payBean.setSign(wxParamsBean.getData().getSign());


                            }
                        });
                break;
            case 1:         //支付宝
                OkHttpUtils.post()
                        .url(HttpConstants.LiftFortune)
                        .addParams("", "")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("PayUtils----ALI", "Exception=======");

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("PayUtils----ALI", "response=======" + response);

                            }
                        });
                break;

        }
        return payBean;

    }
}
