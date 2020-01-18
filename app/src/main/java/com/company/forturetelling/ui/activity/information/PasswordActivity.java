package com.company.forturetelling.ui.activity.information;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.information.InforSettingBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.utils.CacheUtil;
import com.company.forturetelling.utils.ClearEditText;
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
    ClearEditText etRepassworld;
    @BindView(R.id.linear_passworld_all)
    LinearLayout linear_passworld_all;
    @BindView(R.id.btn_login_commit)
    Button btnLoginCommit;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

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
        KeyBoardUtils.closeKeybord(etRepassworld,PasswordActivity.this);
        final PopupWindowTwoButton twoButton = new PopupWindowTwoButton(PasswordActivity.this);
        twoButton.getTv_content().setText("是否修改密码?");
        twoButton.getTv_ok().setText("确定");
        twoButton.getTv_cancel().setText("取消");
        twoButton.getTv_ok().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
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
        String userid = (String) SharePreferenceUtil.get(PasswordActivity.this, Constants.USERID, "");
        if (userid.equals("")) {
            return;
        } else {
            showLoading();
            OkHttpUtils.post()
                    .url(HttpConstants.Update_Password)
                    .addParams("userid", userid)
                    .addParams("password", etRepassworld.getText().toString().trim())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            showError();

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            showContent();

                            Type type = new TypeToken<InforSettingBean>() {
                            }.getType();
                            Gson gson = new Gson();
                            InforSettingBean inforSettingBean = gson.fromJson(response, type);

                            if (inforSettingBean.getStatus().equals("0")) {
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
