package com.company.forturetelling.ui.activity.information;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.CheckPhoneBean;
import com.company.forturetelling.bean.PasswordBean;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.information.InforSettingBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.activity.information.login.RegisterAnimatorActivity;
import com.company.forturetelling.utils.CacheUtil;
import com.company.forturetelling.utils.ClearEditText;
import com.company.forturetelling.view.CountdownView;
import com.company.forturetelling.view.PasswordEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yun.common.utils.KeyBoardUtils;
import com.yun.common.utils.SharePreferenceUtil;
import com.yun.common.utils.popupwindow.PopupWindowTwoButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Lovelin on 2019/12/13
 * <p>
 * Describe:
 */
public class PasswordActivity extends BaseActivity {
    @BindView(R.id.et_repassworld)
    ClearEditText et_repassworld;
    @BindView(R.id.register_password)
    ClearEditText register_password;
    @BindView(R.id.et_register_password1)
    PasswordEditText et_register_password1;
    @BindView(R.id.linear_passworld_all)
    LinearLayout linear_passworld_all;
    @BindView(R.id.btn_login_commit)
    Button btnLoginCommit;
    @BindView(R.id.cv_test_countdown)
    CountdownView cv_test_countdown;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private String userid;
    private String bizId;

    @Override
    public int getContentViewId() {
        return R.layout.activity_password;
    }

    @Override
    public void init() {
        initView();
        responseListener();
    }

    private void responseListener() {
        btnLoginCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
            }
        });
        cv_test_countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();

            }
        });


    }

    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        setTitleName("修改密码");
        setPageStateView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private void showPop() {
        KeyBoardUtils.closeKeybord(et_repassworld, PasswordActivity.this);
        final PopupWindowTwoButton twoButton = new PopupWindowTwoButton(PasswordActivity.this);
        twoButton.getTv_content().setText("是否修改密码?");
        twoButton.getTv_ok().setText("确定");
        twoButton.getTv_cancel().setText("取消");
        twoButton.getTv_ok().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChangePassword(bizId);
                twoButton.dismiss();

            }
        });
        twoButton.getTv_cancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoButton.dismiss();

            }
        });
        twoButton.showPopupWindow(linear_passworld_all, Gravity.CENTER);
    }

    private void sendRequest() {
        userid = (String) SharePreferenceUtil.get(PasswordActivity.this, Constants.USERID, "");
        if (userid.equals("")) {
            return;
        } else {
            String phone = et_repassworld.getText().toString().trim();
            //先调用短信验证码接口
            if (TextUtils.isEmpty(phone)) {
                showToast("手机号码不能为空");
            } else {
                OkHttpUtils.get()
                        .url(HttpConstants.Register_CheckPhone)
                        .addParams("PhoneNumbers", phone + "") //	手机号
                        .addParams("type", "2")   //分类 1 注册 2 忘记密码
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                showToast("请求错误");
                                showError();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Type type = new TypeToken<CheckPhoneBean>() {
                                }.getType();
                                CheckPhoneBean bean = mGson.fromJson(response, type);
//                            0 成功 -1失败
                                Log.e("Net", "response===bean===  getStatus    0 成功 -1失败=====" + bean.getStatus());
                                Log.e("Net", "response===response=== ====" + response);
                                bizId = bean.getData().getBizId();
                            }
                        });
            }

        }


    }

    /**
     * 获取精确到秒的时间戳
     *
     * @param date
     * @return
     */
    public static int getSecondTimestampTwo(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    private void sendChangePassword(String bizId) {
        String phone = et_repassworld.getText().toString().trim();
        String password = et_register_password1.getText().toString().trim();
        String vcode = register_password.getText().toString().trim();
        String ytime = getSecondTimestampTwo(new Date()) + "";

        if (TextUtils.isEmpty(phone)) {
            showToast("手机号码不能为空");
        } else if (TextUtils.isEmpty(vcode)) {
            showToast("验证码不能为空");
        } else if (TextUtils.isEmpty(password)) {
            showToast("新密码不能为空");
        } else if (TextUtils.isEmpty(bizId) && bizId == null) {
            showToast("请输入正确的验证码");
        } else {
            Log.e("Net", "response===response=== =修改密码的参数==phone=" + phone + "===BizId==" + bizId + "===password==" + password + "===vcode==" + vcode + "===ytime==" + ytime);
            showLoading();
            OkHttpUtils.post()
                    .url(HttpConstants.Update_Password)
                    .addParams("PhoneNumbers", phone)
                    .addParams("BizId", bizId)
                    .addParams("vcode", vcode)    //验证码
                    .addParams("ytime", ytime)   //验证码时间 传时 间戳（注意是秒）
                    .addParams("password", password)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            showError();

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            showContent();

                            Type type = new TypeToken<PasswordBean>() {
                            }.getType();
                            Gson gson = new Gson();
                            PasswordBean passwordBean = gson.fromJson(response, type);

                            if (passwordBean.getStatus().equals("0")) {  //	0成功 -1 失败
                                showToast("修改成功");
//                                SharePreferenceUtil.put(PasswordActivity.this, Constants.Is_Logined, true);
//                                SharePreferenceUtil.put(PasswordActivity.this, Constants.Logined, true);
//                                SharePreferenceUtil.put(PasswordActivity.this, Constants.USERID,"");
                                EventBus.getDefault().post(new ExitEvent("登录"));
                                finish();
                            } else {
                                showToast("修改失败");

                            }
                        }
                    });
        }

    }
}


