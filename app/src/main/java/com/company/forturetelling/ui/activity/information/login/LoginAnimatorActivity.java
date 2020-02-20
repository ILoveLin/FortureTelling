package com.company.forturetelling.ui.activity.information.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.company.forturetelling.R;
import com.company.forturetelling.base.App;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.base.help.InputTextHelper;
import com.company.forturetelling.base.help.KeyboardWatcher;
import com.company.forturetelling.bean.LoginBean;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.bus.WeChartEvent;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.MainActivity;
import com.company.forturetelling.ui.activity.information.LoginActivity;
import com.company.forturetelling.ui.activity.information.RegisterActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Lovelin on 2020/2/18
 * <p>
 * Describe:带动画的登入界面
 */
public class LoginAnimatorActivity extends BaseActivity implements KeyboardWatcher.SoftKeyboardStateListener {
    @BindView(R.id.iv_login_logo)
    ImageView mLogoView;
    @BindView(R.id.ll_login_body)
    LinearLayout mBodyLayout;
    @BindView(R.id.et_login_phone)
    EditText mPhoneView;
    @BindView(R.id.et_login_password)
    EditText mPasswordView;
    @BindView(R.id.btn_login_commit)
    Button mCommitView;
    @BindView(R.id.v_login_blank)
    View mBlankView;
    @BindView(R.id.ll_login_other)
    View mOtherView;
    @BindView(R.id.btn_login_wechat)
    View btn_login_wechat;
    /**
     * logo 缩放比例
     */
    private final float mLogoScale = 0.8f;
    /**
     * 动画时间
     */
    private final int mAnimTime = 300;
    private String username;
    private String password;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login_ainm;
    }

    @Override
    public void init() {
        initView();
        initData();
    }


    //右边的点击事件
    @Override
    public void onClickTitleRightTvBtn(View v) {
        super.onClickTitleRightTvBtn(v);
        Bundle bundle = new Bundle();
        bundle.putString("title", "注册");
        openActivity(RegisterAnimatorActivity.class,bundle);
    }

    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        setTitleRightBtnVisibility(View.VISIBLE);
        setTitleRightTvbtnName("注册", getResources().getColor(R.color.color_3375D5));
        setTitleName("");
        setPageStateView();
//        mPresenter = new NameDetailsPresenter(this, this);
//        //默认选择男
//        selectorSexThreeBoy.setSelected(true);
//        KeyBoardUtils.closeKeybord(tv_addname_name01, this);
//
//        tv_addname_name01.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
//        tv_addname_name02.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});


        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .setListener(new InputTextHelper.OnInputTextListener() {

                    @Override
                    public boolean onInputChange(InputTextHelper helper) {
                        return mPhoneView.getText().toString().length() == 11 &&
                                mPasswordView.getText().toString().length() >= 6;
                    }
                })
                .build();

        post(new Runnable() {

            @Override
            public void run() {
                // 因为在小屏幕手机上面，因为计算规则的因素会导致动画效果特别夸张，所以不在小屏幕手机上面展示这个动画效果
                if (mBlankView.getHeight() > mBodyLayout.getHeight()) {
                    // 只有空白区域的高度大于登录框区域的高度才展示动画
                    KeyboardWatcher.with(LoginAnimatorActivity.this)
                            .setListener(LoginAnimatorActivity.this);
                }
            }
        });


    }

    public void initData() {
        // 判断用户当前有没有安装微信
        if (!isWeixinAvilible(this)) {
            btn_login_wechat.setVisibility(View.GONE);
        }

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
        Log.e("Wetchat", "login==end===数据保持后台完毕=login==");


    }

    private void sendRequest() {
        showLoading();
        OkHttpUtils.post()
                .url(HttpConstants.Login)
                .addParams("username", mPhoneView.getText().toString().trim() + "")
                .addParams("password", mPasswordView.getText().toString().trim())
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
                            showContent();
                            String userid = mBean.getData().getUserid() + "";
//                            String token = mBean.getData().getToken() + "";
                            SharePreferenceUtil.put(LoginAnimatorActivity.this, Constants.USERID, userid + "");
//                            SharePreferenceUtil.put(LoginActivity.this, Constants.Token, token + "");
                            SharePreferenceUtil.put(LoginAnimatorActivity.this, Constants.Device, "android");
                            SharePreferenceUtil.put(LoginAnimatorActivity.this, Constants.Is_Logined, true);
                            EventBus.getDefault().post(new ExitEvent("登入"));
//                            Bundle bundle = new Bundle();
//                            bundle.putString("WeChat", "注册界面");
//                            openActivity(RegisterActivity.class, bundle);
//                            startActivityFinish(MainActivity.class);

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


    @OnClick({R.id.tv_login_forget, R.id.btn_login_commit, R.id.btn_login_wechat})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_forget:
//                startActivity(PasswordForgetActivity.class);
                break;
            case R.id.btn_login_commit:
                if (mPhoneView.getText().toString().length() != 11) {
                    showToast("手机号输入不正确");
                } else {
                    checkData();

//                    postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            showContent();
//                            // 处理登录
//                        }
//                    }, 2000);
                }
                break;
            case R.id.btn_login_wechat:
//                toast("记得改好第三方 AppID 和 AppKey，否则会调不起来哦");
                switch (v.getId()) {
                    case R.id.btn_login_wechat:
                        wxLogin();

                        break;
                    default:
                        throw new IllegalStateException("are you ok?");
                }
                break;
            default:
                break;
        }
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


    private void checkData() {
        username = mPhoneView.getText().toString().trim();
        password = mPasswordView.getText().toString().trim();
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

    /**
     * 实现方法
     *
     * @param keyboardHeight 软键盘高度
     */

    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int[] location = new int[2];
        // 获取这个 View 在屏幕中的坐标（左上角）
        mBodyLayout.getLocationOnScreen(location);
        //int x = location[0];
        int y = location[1];
        int bottom = screenHeight - (y + mBodyLayout.getHeight());
        if (keyboardHeight > bottom) {
            // 执行位移动画
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", 0, -(keyboardHeight - bottom));
            objectAnimator.setDuration(mAnimTime);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.start();

            // 执行缩小动画
            mLogoView.setPivotX(mLogoView.getWidth() / 2f);
            mLogoView.setPivotY(mLogoView.getHeight());
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", 1.0f, mLogoScale);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", 1.0f, mLogoScale);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", 0.0f, -(keyboardHeight - bottom));
            animatorSet.play(translationY).with(scaleX).with(scaleY);
            animatorSet.setDuration(mAnimTime);
            animatorSet.start();
        }

    }

    @Override
    public void onSoftKeyboardClosed() {

        // 执行位移动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", mBodyLayout.getTranslationY(), 0);
        objectAnimator.setDuration(mAnimTime);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

        if (mLogoView.getTranslationY() == 0) {
            return;
        }
        // 执行放大动画
        mLogoView.setPivotX(mLogoView.getWidth() / 2f);
        mLogoView.setPivotY(mLogoView.getHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", mLogoScale, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", mLogoScale, 1.0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", mLogoView.getTranslationY(), 0);
        animatorSet.play(translationY).with(scaleX).with(scaleY);
        animatorSet.setDuration(mAnimTime);
        animatorSet.start();

    }


    /**
     * 延迟执行
     */
    public final boolean post(Runnable r) {
        return postDelayed(r, 0);
    }

    /**
     * 延迟一段时间执行
     */
    public final boolean postDelayed(Runnable r, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
    }


    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    public final Object mHandlerToken = hashCode();

    /**
     * 在指定的时间执行
     */
    public final boolean postAtTime(Runnable r, long uptimeMillis) {
        // 发送和这个 Activity 相关的消息回调
        return HANDLER.postAtTime(r, mHandlerToken, uptimeMillis);
    }


}
