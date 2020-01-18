package com.company.forturetelling.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.company.forturetelling.R;
import com.company.forturetelling.global.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付回调
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WX-Pay";
    private IWXAPI api;

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
        Log.e("PayUtils----WX", "WXPayEntryActivity====onReq===" );

//        if (paymentWechat == null) {
//            paymentWechat = new PaymentWechat();
//        }
//        paymentWechat.setOpenId(req.openId);
//        paymentWechat.setTransaction(req.transaction);
//        paymentWechat.setType(req.getType());
//        paymentWechat.setCheckArgs(req.checkArgs());
//        Log.e(TAG, "onReq#" + paymentWechat.toString());
    }


    //	处理回调数据
    @Override
    public void onResp(BaseResp resp) {

//        Log.e("WXPayEntryActivity回调微信支付的结果errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            int errCode = resp.errCode;
            Log.e("PayUtils----WX", "WXPayEntryActivity========response==errCode==errCode===" +errCode);

            if (errCode == -1) {/*支付失败*/
                Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
            } else if (errCode == 0) {/*支付成功*/
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();

                //TODU走二次校验,然后跳转结果界面


//				EventBusUtils.post(new EventBusWechatGoldBean());
            } else if (errCode == -2) {/*取消支付*/
                Toast.makeText(this, "取消支付", Toast.LENGTH_LONG).show();
            }
            finish();

        }
    }

}
