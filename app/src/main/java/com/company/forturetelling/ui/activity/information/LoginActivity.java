package com.company.forturetelling.ui.activity.information;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.company.forturetelling.R;
import com.company.forturetelling.base.App;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.LoginBean;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.bus.LoginEvent;
import com.company.forturetelling.bean.bus.WeChartEvent;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.MainActivity;
import com.company.forturetelling.ui.activity.RegisterActivity;
import com.company.forturetelling.utils.ClearEditText;
import com.company.forturetelling.utils.NetworkUtil;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.yun.common.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Lovelin on 2019/5/9
 * <p>
 * Describe:登入界面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_login_phone)
    ClearEditText etLoginPhone;
    @BindView(R.id.et_login_password)
    ClearEditText etLoginPassword;
    @BindView(R.id.tv_login_forget)
    TextView tvLoginForget;
    @BindView(R.id.btn_login_commit)
    Button btnLoginCommit;
    @BindView(R.id.btn_login_wechat)
    Button btn_login_wechat;
    @BindView(R.id.ib_left)
    ImageButton ib_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.phone_register)
    TextView phone_register;
    private String username;
    private String password;
    private LoginBean mBean;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        initView();
        responseListener();

    }

    private void initView() {

        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        setTitleName("登入界面");
        setPageStateView();
//
//        String Remeber_Name = (String) SharePreferenceUtil.get(this, Constants.Remeber_Name, "");
//        String Remeber_passwore = (String) SharePreferenceUtil.get(this, Constants.Remeber_passwore, "");
//        etLoginPhone.setText(Remeber_Name + "");
//        etLoginPassword.setText(Remeber_passwore + "");
        setPageStateView();
    }


    /**
     * 无网络的时候 刷新数据
     */
    @Override
    protected void onClickRetry() {
        super.onClickRetry();
        if (NetworkUtil.CheckConnection(this)) {
            sendRequest();
        } else {
            showToast("请打开网络链接");
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ExitEvent(WeChartEvent WeChartEvent) {
        this.finish();

    }
    private void sendRequest() {
        showLoading();
        OkHttpUtils.post()
                .url(HttpConstants.Login)
                .addParams("username", etLoginPhone.getText().toString().trim() + "")
                .addParams("password", etLoginPassword.getText().toString().trim())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showError();
                        showToast("请求返回错误");
                        Log.e("Net", "login==mBean=Exception=");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("Net", "login==mBean===" + response);
                        Type type = new TypeToken<LoginBean>() {
                        }.getType();
                        LoginBean mBean = mGson.fromJson(response, type);
                        String status = mBean.getStatus() + "";  //0 成功   1失败
                        if ("0".equals(status)) {
//                            Log.e("Net", "login==response===" + mBean.getData().getToken());
//                            Log.e("Net", "login==response===" + "android");
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.ID, mBean.getData().getId());
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.Token, mBean.getData().getToken());
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.Device, "android");
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.UserName, mBean.getData().getUsername());
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.Remeber_Name, etLoginPhone.getText().toString().trim());
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.Remeber_passwore, etLoginPassword.getText().toString().trim());
//                            openActivity(MainActivity.class);
//                            showToast("登入成功");
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.Is_Logined, true);
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.Logined, true);
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.USERID, mBean.getData().getUserid() + "");
                            showContent();
                            String userid = mBean.getData().getUserid() + "";
//                            String token = mBean.getData().getToken() + "";
                            SharePreferenceUtil.put(LoginActivity.this, Constants.USERID, userid + "");
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.Token, token + "");
                            SharePreferenceUtil.put(LoginActivity.this, Constants.Device, "android");
                            SharePreferenceUtil.put(LoginActivity.this, Constants.Is_Logined, true);
                            EventBus.getDefault().post(new ExitEvent("登入"));
                            showToast("登入成功");
                            finish();

                        } else if ("-1".equals(status)) {
                            showContent();
                            showToast("" + mBean.getMsg());
                        } else {
                            showError();

                        }

                    }
                });

    }

    private void responseListener() {

        btnLoginCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
        phone_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("WeChat", "注册界面");
                openActivity(RegisterActivity.class, bundle);
//                openActivity(RegisterActivity.class);
            }
        });


        btn_login_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wxLogin();
            }
        });

    }

    public void wxLogin() {
        if (!App.msgApi.isWXAppInstalled()) {
            showToast("您还未安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        App.msgApi.sendReq(req);
    }

//
//    //判断是否安装 微信
//    public boolean isWeixinAvilible(Context context) {
//        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
//        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
//        if (pinfo != null) {
//            for (int i = 0; i < pinfo.size(); i++) {
//                String pn = pinfo.get(i).packageName;
//                if (pn.equals("com.tencent.mm")) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


    private void checkData() {
        username = etLoginPhone.getText().toString().trim();
        password = etLoginPassword.getText().toString().trim();
        if ("".equals(username) && TextUtils.isEmpty(username)) {
            showToast("用户名--不能为空哦~");
            return;
        } else if ("".equals(password) && TextUtils.isEmpty(password)) {
            showToast("密码--不能为空哦~");
            return;
        } else {
            sendRequest();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

    }
}
