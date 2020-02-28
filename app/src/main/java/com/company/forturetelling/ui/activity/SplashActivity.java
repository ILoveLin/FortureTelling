package com.company.forturetelling.ui.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.company.forturetelling.GetUseridBean;
import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.RegisterBean;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.MainActivity;
import com.company.forturetelling.ui.activity.information.LoginActivity;
import com.company.forturetelling.ui.activity.information.login.RegisterAnimatorActivity;
import com.company.forturetelling.utils.DeviceIdUtil;
import com.company.forturetelling.wxapi.WXEntryActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yun.common.utils.SharePreferenceUtil;
import com.yun.common.utils.StatusBarUtil;
import com.yun.common.utils.StatusBarUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;

import okhttp3.Call;


/**
 * Created by Lovelin on 2019/3/27
 * <p>
 * Describe:启动页
 */
public class SplashActivity extends BaseActivity {

    private Boolean isFirstIn;
    private ImageView ivSplash;
    private Boolean isLogined;
    private Gson mGson = new Gson();
    public static String[] stringTabArray;

    @Override
    public int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void initView() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.red), 0);
        StatusBarUtil.darkMode(this, false);  //设置了状态栏文字的颜色
        setTitleBarVisibility(View.GONE);
        ivSplash = findViewById(R.id.iv_splash);
        //是否第一次进入app
        isFirstIn = (Boolean) SharePreferenceUtil.get(this, Constants.SP_IS_FIRST_IN,
                true);
        //是否登入
        // 从浅到深,从百分之10到百分之百
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(1500);// 设置动画时间
        ivSplash.setAnimation(aa);// 给image设置动画
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void initData() {

        switchGoing();

    }


    //判断进入那个activity
    private void switchGoing() {
        sendRequestForUserID();
        if (isFirstIn) {
//            第一次进入-- 走引导页，否则进入MainActivity
            SharePreferenceUtil.put(SplashActivity.this, Constants.SP_IS_FIRST_IN,
                    false);
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, GuideActivity.class);
            startActivity(intent);

            finish();

        } else {
//            if (!isLogined) {  //登入成功
//                Intent intent = new Intent();
//                intent.setClass(SplashActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            } else {
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, MainActivity.class);
            startActivity(intent);
//                finish();
//            }

        }
    }

    private void sendRequestForUserID() {
        String deviceid = DeviceIdUtil.getDeviceId(this);
        String userid = (String) SharePreferenceUtil.get(SplashActivity.this, Constants.USERID, "");
        if("".equals(userid)) {  //为空的时候去请求
            OkHttpUtils.post()
                    .url(HttpConstants.GetUserID)
                    .addParams("deviceid", deviceid + "")
                    .addParams("type", "1")   //分类 1 安卓 2 ios
                    .build()
                    .execute(new StringCallback() {

                        private String userid;

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            showToast("请求错误");
                            showError();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Type type = new TypeToken<GetUseridBean>() {
                            }.getType();
                            GetUseridBean bean = mGson.fromJson(response, type);
                            Log.e("Net", "login==GetUserID===" + response);
                            Log.e("Net", "login==deviceid===" + deviceid);
                            if ("0".equals(bean.getStatus()+"")) {
                                //sp存token
                                showContent();
                                userid = bean.getData().getUserid() + "";
                                SharePreferenceUtil.put(SplashActivity.this, Constants.USERID, userid + "");
                                String userid = (String) SharePreferenceUtil.get(SplashActivity.this, Constants.USERID, "");
                                Log.e("Net", "login==GetUserID====调试头====" + userid + "");
                                finish();
                            } else {
                                showContent();

                            }
                        }
                    });
        }


    }


//    private void toArray() {
//
//        ArrayList<String> mTitleList = new ArrayList<String>();
//        for (int i = 0; i < tabSportsList.size(); i++) {
//            mTitleList.add(tabSportsList.get(i).getTitle_0());
//        }
//        String[] strings = new String[mTitleList.size()];
//
//        stringTabArray = mTitleList.toArray(strings);
//        for (int i = 0; i < stringTabArray.length; i++) {
//            LogUtils.e("测试===============" + stringTabArray[i]);
//        }
//    }


}
