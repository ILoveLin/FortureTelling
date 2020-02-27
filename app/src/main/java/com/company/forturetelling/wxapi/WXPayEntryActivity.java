package com.company.forturetelling.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.company.forturetelling.R;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.bus.RefreshPayTypeEvent;
import com.company.forturetelling.bean.paybean.AliPaySecondResultBean;
import com.company.forturetelling.bean.paybean.WXOrderCheckoutBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.activity.pay.SelectPayActivity;
import com.company.forturetelling.ui.activity.pay.order.EventOrderMessage;
import com.company.forturetelling.ui.activity.result.ResultCommonActivity;
import com.company.forturetelling.ui.activity.sixfunction.getname.GetNameResultActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yun.common.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * 微信支付回调
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WX-Pay";
    private IWXAPI api;
    private String out_trade_no;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        Log.e(TAG, "##############onCreate##########");
        //注册API 没有这个不会执行onResp
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WECHART);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "##############onNewIntent##########");
        setIntent(intent);
        api.handleIntent(intent, this);
        Log.e(TAG, "##############api.handleIntent(intent, this);##########");
    }

    /**
     * 0	成功	展示成功页面
     * -1签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
     * -2无需处理。发生场景：用户不支付了，点击取消，返回APP。
     *
     * @param req
     */
    @Override
    public void onReq(BaseReq req) {
        Log.e("PayUtils----WX", "WXPayEntryActivity====onReq===");

    }


    //	处理回调数据
    @Override
    public void onResp(BaseResp resp) {

//        Log.e("WXPayEntryActivity回调微信支付的结果errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            int errCode = resp.errCode;
            Log.e("PayUtils----WX", "WXPayEntryActivity========response==errCode==errCode===" + errCode);

            if (errCode == -1) {/*支付失败*/
                Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
            } else if (errCode == 0) {/*支付成功*/
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();

                //TODU走二次校验,然后跳转结果界面
                checkoutSecondWeChartPay();

            } else if (errCode == -2) {/*取消支付*/
                Toast.makeText(this, "取消支付", Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(new RefreshPayTypeEvent("微信"));
            }
            finish();

        }
    }


    private void checkoutSecondWeChartPay() {
        String mAppid = SelectPayActivity.payWXBean.getAppid();
        String mPartnerid = SelectPayActivity.payWXBean.getPartnerid();
        String mPrepayid = SelectPayActivity.payWXBean.getPrepayid();
        String mNoncestr = SelectPayActivity.payWXBean.getNoncestr();
        String mTimestamp = SelectPayActivity.payWXBean.getTimestamp();
        String mPackagee = SelectPayActivity.payWXBean.getPackagee();
        String mSign = SelectPayActivity.payWXBean.getSign();
        out_trade_no = String.valueOf(SharePreferenceUtil.get(getApplicationContext(), Constants.WECHAT_SECOND_ORDERID, ""));
        Log.e("PayUtils----ALI-Second", "response==ALIPay=request==Second==" + out_trade_no);
        Log.e("PayUtils----ALI-Second", "response==ALIPay=request==prepayid==" + mPrepayid);
        Log.e("PayUtils----ALI-Second", "response==ALIPay=request==timestamp==" + mTimestamp);

        if ("".equals(out_trade_no)) {
            Toast.makeText(getApplicationContext(), "二次校验orderid为空", Toast.LENGTH_SHORT).show();
        } else {
            OkHttpUtils.post()
                    .url(HttpConstants.WXPay_Second)
                    .addParams("out_trade_no", out_trade_no)
                    .addParams("appid", mAppid)
                    .addParams("partnerid", mPartnerid)
                    .addParams("prepayid", mPrepayid)
                    .addParams("noncestr", mNoncestr)
                    .addParams("timestamp", mTimestamp)
                    .addParams("package", mPackagee)
                    .addParams("sign", mSign)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("PayUtils----ALI-Second", "Exception===ALIPay====");

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("PayUtils----ALI-Second", "response==ALIPay===Second==" + response);
                            Type type = new TypeToken<WXOrderCheckoutBean>() {
                            }.getType();
                            Gson gson = new Gson();
                            WXOrderCheckoutBean mWXOrderCheckoutBean = gson.fromJson(response, type);
                            //status  = 1 成功   =0 失败  -1没有获取到回调
                            String status = mWXOrderCheckoutBean.getStatus() + "";
                            if ("0".equals(status)) {  //成功
//                                Toast.makeText(getApplicationContext(), "微信订单二次校验SUCCESS", Toast.LENGTH_SHORT).show();
                                String title = mWXOrderCheckoutBean.getData().getBody() + "";
                                String out_trade_no = mWXOrderCheckoutBean.getData().getOut_trade_no() + "";
                                Bundle bundle = new Bundle();
                                bundle.putString("oid", out_trade_no);
                                bundle.putString("title", title);
                                if("取名".equals(title)) {
                                    openActivity(GetNameResultActivity.class, bundle);
                                }else{
                                    openActivity(ResultCommonActivity.class, bundle);
                                }
                            } else {    //失败
                                Toast.makeText(getApplicationContext(), "微信订单二次校验失败", Toast.LENGTH_SHORT).show();
//                            showToast("支付宝订单二次校验失败");
//                            showToast("" + status);
                            }


                        }
                    });
        }

    }


    /**
     * 打开activity
     *
     * @param ActivityClass
     * @param b
     */
    public void openActivity(Class<? extends Activity> ActivityClass, Bundle b) {
        Intent intent = new Intent(this, ActivityClass);
        intent.putExtras(b);
        startActivity(intent);
    }


}
