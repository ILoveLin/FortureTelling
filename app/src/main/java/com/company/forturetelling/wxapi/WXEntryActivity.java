package com.company.forturetelling.wxapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.company.forturetelling.base.App;
import com.company.forturetelling.bean.WechartBeanUpdate;
import com.company.forturetelling.bean.WechatLoginBean;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.bus.WeChartEvent;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.MainActivity;
import com.company.forturetelling.ui.activity.information.LoginActivity;
import com.company.forturetelling.ui.activity.information.RegisterActivity;
import com.company.forturetelling.ui.activity.information.login.RegisterAnimatorActivity;
import com.company.forturetelling.utils.DeviceIdUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yun.common.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author fantasychong
 * @date 2018/11/5
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI iwxapi;
    private String unionid;
    private String openid;
    private ProgressBar progressBar;
    private WXEntryActivity mContext;
    private ProgressDialog mProgressDialog;
    private WechatLoginBean mWXBean;

    //    AppID：wx4b9e09ae470dc4ce
//    AppSecret： ebb2251808c184f72cb7e3d6d272a1cf
    //签名 fe99d480f0571524a4ddea6f0c8a613f
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        // 隐藏状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
//        iwxapi = WXAPIFactory.createWXAPI(this, "wx4b9e09ae470dc4ce", false);
//        iwxapi.handleIntent(getIntent(), this);
        //如果没回调onResp，八成是这句没有写
        Log.e("Wetchat", "login==01=");
        EventBus.getDefault().register(this);
        App.msgApi.handleIntent(getIntent(), this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ExitEvent(WeChartEvent WeChartEvent) {
        String message = WeChartEvent.getMessage();
        if ("全部关闭".equals(message)) {
            this.finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    private void createProgressDialog() {
        mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转盘
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage("登录中，请稍后");
        mProgressDialog.show();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    //请求回调结果处理
    @Override
    public void onResp(BaseResp baseResp) {
        Log.e("Wetchat", "login==02=" + baseResp.errCode);

        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
                getAccessToken(code);
                Log.e("Wetchat", "login==03===ok=获取授权" + code.toString());

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                Log.e("Wetchat", "login==03===ok=用户拒绝授权");

//                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                Log.e("Wetchat", "login==03===ok=用户取消");

//                finish();
                break;
            default:
//                finish();
                break;
        }
    }

    public String access = null;
    public String openId = null;

    private void getAccessToken(String code) {
        Log.e("Wetchat", "login==04===getAccessToken=");

        createProgressDialog();
        //获取授权
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append(Constants.APP_ID_WECHART)
                .append("&secret=")
                .append(Constants.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        Log.d("urlurl", loginUrl.toString());

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(loginUrl.toString())
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fan12", "onFailure: ");
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
                Log.d("fan12", "onResponse: " + responseInfo);
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getUserInfo(access, openId);
            }
        });


    }

    private void getUserInfo(String access, String openid) {
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openid;
        Log.e("Wetchat", "login==05===getUserInfo=");

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(getUserInfoUrl)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fan12", "onFailure: ");
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
                Log.e("Wetchat", "login==05===responseInfo=====" + responseInfo);


                Gson gson = new Gson();

                Type type = new TypeToken<WechatLoginBean>() {
                }.getType();
                mWXBean = gson.fromJson(responseInfo, type);

                Log.d("fan123", "onResponse: " + responseInfo);
                SharedPreferences.Editor editor = getSharedPreferences("userInfo", MODE_PRIVATE).edit();
                editor.putString("responseInfo", responseInfo);
                //在这里把数据提交给后台
                editor.commit();
                requestData(mWXBean);

            }
        });
    }


    private void requestData(WechatLoginBean mBean) {
        String deviceid = DeviceIdUtil.getDeviceId(this);

        OkHttpUtils.post()
                .url(HttpConstants.WeChat_Login)
                .addParams("deviceid", deviceid)
                .addParams("type", "1")
                .addParams("openid", mBean.getOpenid())
                .addParams("nickname", mBean.getNickname())
                .addParams("sex", mBean.getSex())
                .addParams("headimgurl", mBean.getHeadimgurl())
                .addParams("unionid", mBean.getUnionid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        finish();
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<WechartBeanUpdate>() {
                        }.getType();
                        WechartBeanUpdate mBean = gson.fromJson(response, type);
                        Log.e("Wetchat", "login==end===数据保持后台完毕=response=00000==" + response + "");
                        Log.e("Wetchat", "login==end===数据保持后台完毕==mBean.getData().getPerfect()==" + mBean.getData().getPerfect());

//                        1 登录    0 跳转注册完善信息
                        if ("0".equals(mBean.getStatus() + "")) {
//                            EventBus.getDefault().post(new WeChartEvent("登入"));
                            SharePreferenceUtil.put(WXEntryActivity.this, Constants.WX_Openid, mWXBean.getOpenid());
                            SharePreferenceUtil.put(WXEntryActivity.this, Constants.WX_Perfect, mBean.getData().getPerfect());
                            Bundle bundle = new Bundle();
                            bundle.putString("title", openId);
                            bundle.putString("type", "");
                            Log.e("Wetchat", "login==end===数据保持后台完毕==00000==" + mBean.getStatus() + "");
                            EventBus.getDefault().post(new ExitEvent("登入"));
                            openActivity(RegisterAnimatorActivity.class, bundle);
                            Toast.makeText(WXEntryActivity.this, "请您完善信息", Toast.LENGTH_SHORT).show();


                        } else if ("1".equals(mBean.getStatus() + "")) {
                            String userid = mBean.getData().getUserid() + "";
                            SharePreferenceUtil.put(WXEntryActivity.this, Constants.WX_Openid, mWXBean.getOpenid());
                            SharePreferenceUtil.put(WXEntryActivity.this, Constants.USERID, userid + "");
                            SharePreferenceUtil.put(WXEntryActivity.this, Constants.Device, "android");
                            SharePreferenceUtil.put(WXEntryActivity.this, Constants.Is_Logined, true);
                            SharePreferenceUtil.put(WXEntryActivity.this, Constants.WX_Perfect, mBean.getData().getPerfect() + "");
                            EventBus.getDefault().post(new ExitEvent("登入"));
                            Toast.makeText(WXEntryActivity.this, "登入成功", Toast.LENGTH_SHORT).show();

                            Bundle bundle = new Bundle();
                            bundle.putString("title", "完善信息");
                            openActivity(MainActivity.class, bundle);
                            Log.e("Wetchat", "login==end===数据保持后台完毕==11111==" + mBean.getStatus() + "");
                            finish();
                        }
//                        else {
//                            Log.e("Wetchat", "login==end===数据保持后台完毕==else==");
//                            Toast.makeText(WXEntryActivity.this, "后台数据保存失败", Toast.LENGTH_SHORT).show();
//                        }

                        finish();
                        mProgressDialog.dismiss();
                    }
                });


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

