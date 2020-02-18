package com.company.forturetelling.ui.fragment.mine;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseFragment;
import com.company.forturetelling.bean.UpdateBean;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.information.InforBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.activity.NewsActivity;
import com.company.forturetelling.ui.activity.information.InforSettingActivity;
import com.company.forturetelling.ui.activity.information.LoginActivity;
import com.company.forturetelling.ui.activity.information.login.LoginAnimatorActivity;
import com.company.forturetelling.ui.activity.pay.order.OrderActivity;
import com.company.forturetelling.ui.fragment.mine.presenter.MineView;
import com.company.forturetelling.utils.CacheUtil;
import com.company.forturetelling.view.CircleImageView;
import com.company.forturetelling.view.SettingBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yun.common.utils.CommonUtils;
import com.yun.common.utils.CustomToast;
import com.yun.common.utils.GlideUtils;
import com.yun.common.utils.SharePreferenceUtil;
import com.yun.common.utils.popupwindow.PopupWindowTwoButton;
import com.yun.common.utils.updateutils.UpdateIntentService;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Lovelin on 2019/4/27
 * <p>
 * Describe:我的
 */
public class MineFragment extends BaseFragment implements MineView {

    @BindView(R.id.fake_status_bar)
    View mFakeStatusBar;
    @BindView(R.id.current_pic)
    CircleImageView currentPic;
    @BindView(R.id.tv_current_name)
    TextView tvCurrentName;
    @BindView(R.id.linear_current_info)
    LinearLayout linearCurrentInfo;
    @BindView(R.id.linear_current_info_unlogin)
    LinearLayout linear_current_info_unlogin;
    @BindView(R.id.bar_clan_data)
    SettingBar barClanData;
    @BindView(R.id.bar_update)
    SettingBar barUpdate;
    @BindView(R.id.bar_about_us)
    SettingBar barAboutUs;
    @BindView(R.id.bar_payed_order)
    SettingBar bar_payed_order;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_current_name_unlogin)
    TextView tv_current_name_unlogin;
    @BindView(R.id.relate_mine_all)
    RelativeLayout relate_mine_all;
    Unbinder unbinder;
    private String formatSize;
    private Boolean isLogined;
    private String localVersionCode;
    private Gson gson;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_04;
    }

    @Override
    public void init(ViewGroup rootView) {

        initView();
        initData();
        responseListener();
//        showLoading();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ExitEvent(ExitEvent messageEvent) {
        if (messageEvent.getMessage().equals("退出")) {
            initView();
        } else if ("登入".equals(messageEvent.getMessage())) {
            initView();
            responseListener();
        }
    }

    private void initData() {
        linearCurrentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(InforSettingActivity.class);
            }
        });
    }

    private void responseListener() {
        String userid = (String) SharePreferenceUtil.get(getActivity(), Constants.USERID, "");
        if (userid.equals("")) {
            return;
        } else {
            showLoadingView();
            OkHttpUtils.post()
                    .url(HttpConstants.Information)
                    .addParams("userid", userid)
//                    .addParams("birthday", "")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            showToast("回调错误404");
                            showErrorView();

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            showContentView();

                            Type type = new TypeToken<InforBean>() {
                            }.getType();
                            Gson gson = new Gson();
                            InforBean inforBean = gson.fromJson(response, type);

                            if (inforBean.getStatus().equals("0")) {
//                                Log.e("mImageUri", "=========sendNo===昵称=="+response);
//                                Log.e("mImageUri", "=========sendNo===昵称==" + inforBean.getData().getInfo().getName());

                                tvCurrentName.setText("" + inforBean.getData().getInfo().getName());
                                GlideUtils.LogadCustomCircleImage(getActivity(), HttpConstants.Common + inforBean.getData().getInfo().getHeadimg(), currentPic);

                            } else {
                                showToast("请求失败");

                            }
                        }
                    });
        }


    }

    private void initView() {
        isLogined = (Boolean) SharePreferenceUtil.get(getActivity(), Constants.Is_Logined, false);
        String USERID = (String) SharePreferenceUtil.get(getActivity(), Constants.USERID, "");
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.GONE);
        setTitleName("我的");
        setPageStateView();
        if ("".equals(USERID)) {
            linear_current_info_unlogin.setVisibility(View.VISIBLE);
            linearCurrentInfo.setVisibility(View.INVISIBLE);
        } else {
            linear_current_info_unlogin.setVisibility(View.INVISIBLE);
            linearCurrentInfo.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.linear_current_info_unlogin,R.id.bar_payed_order, R.id.bar_clan_data, R.id.linear_current_info, R.id.bar_update, R.id.bar_about_us})
    public void multipleOnclick(View view) {
        switch (view.getId()) {
            case R.id.linear_current_info: //个人设置
                openActivity(InforSettingActivity.class);
                break;
            case R.id.linear_current_info_unlogin: // 未登录状态----个人设置
//                openActivity(LoginActivity.class);
                openActivity(LoginAnimatorActivity.class);
                break;
            case R.id.bar_clan_data: //清理缓存
                showPop();
                break;
            case R.id.bar_payed_order: //我的订单
                openActivity(OrderActivity.class);
                break;
            case R.id.bar_update:  //版本更新
                requestUpdateVersion();
                break;
            case R.id.bar_about_us: //关于我们
//                http://jxsccm.com/
                Bundle bundle = new Bundle();
//                bundle.putString("url", "http://jxsccm.com/");
                bundle.putString("url", "http://testbazi.zgszfy.com/gwdemo/index.html");
                bundle.putString("title", "关于我们");
                openActivity(NewsActivity.class, bundle);
                break;

        }

    }

    private void showPop() {

        final PopupWindowTwoButton twoButton = new PopupWindowTwoButton((Activity) getActivity());
        twoButton.getTv_content().setText("是否清除缓存?");
        twoButton.getTv_ok().setText("确定");
        twoButton.getTv_cancel().setText("取消");
        twoButton.getTv_ok().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File cacheDir = getActivity().getCacheDir();
                long folderSize = 0;
                try {
                    folderSize = CacheUtil.getFolderSize(cacheDir);
                    formatSize = CacheUtil.getFormatSize(folderSize);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                barClanData.setRightText("" + 0);
                twoButton.dismiss();

            }
        });
        twoButton.getTv_cancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoButton.dismiss();

            }
        });
        twoButton.showPopupWindow(relate_mine_all, Gravity.CENTER);
    }


    /**
     * 版本更新
     */
    private void requestUpdateVersion() {
//        CustomToast.showToast(getActivity(), HttpConstants.Update);

        localVersionCode = CommonUtils.getVersionName(getActivity());
        OkHttpUtils.get()
                .url(HttpConstants.Update)
                .addParams("type", 1 + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CustomToast.showToast(getActivity(), "版本更新-网络请求错误");
                        showError();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Type updateType = new TypeToken<UpdateBean>() {
                        }.getType();
                        gson = new Gson();
                        UpdateBean mBean = gson.fromJson(response, updateType);
                        String version_code = mBean.getVersion() + "";
//                        updateBean.getAllDate(response);
//                        version_code = updateBean.getData().getVersion_code();
////                        Log.e("update==========", "localVersionCode=====" + localVersionCode);
////                        Log.e("update==========", "version_code=====" + version_code);
//                        // 如果指定的数与参数相等返回0。
//                        //如果指定的数小于参数返回 -1。
//                        //如果指定的数大于参数返回 1。
                        if (localVersionCode.compareTo(version_code) < 0) {

                            Log.e("mImageUri", "=========update===01======" + response);

                            downUrl = HttpConstants.Common + mBean.getDownurl();
                            updateVersion(downUrl);
                        } else {
                            showToast("已经是最新版本~~");
                        }
                    }
                });

    }

    //我就说一句话，这是坠吼的！
    private void updateVersion(final String url) {
        // 如果指定的数与参数相等返回0。
        //如果指定的数小于参数返回 -1。
        //如果指定的数大于参数返回 1。

        final PopupWindowTwoButton twoButton = new PopupWindowTwoButton(getActivity());
        twoButton.getTv_content().setText("发现新版本，是否更新?");
        twoButton.getTv_ok().setText("确定");
        twoButton.getTv_cancel().setText("取消");
        twoButton.getTv_ok().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("开始下载,请稍后...");
                beforeUpdateWork();
                twoButton.dismiss();

            }
        });
        twoButton.getTv_cancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //版本更新

                twoButton.dismiss();

            }
        });
        twoButton.showPopupWindow(relate_mine_all, Gravity.CENTER);

    }

    private void beforeUpdateWork() {

        if (!isEnableNotification()) {
            showNotificationAsk();
//            Log.e("mImageUri", "=========update===02======");

            return;
        }
//        Log.e("mImageUri", "=========update===03======");

        toIntentServiceUpdate();

    }

    private void showNotificationAsk() {
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(getActivity());
        dialog.setTitle("通知权限");
        dialog.setMessage("打开通知权限");
        dialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toSetting();
                    }
                });

        dialog.setNeutralButton("跳过", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toIntentServiceUpdate();
            }
        });
        dialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do　
                    }
                });
        dialog.show();
    }

    private boolean isEnableNotification() {
        boolean ret = true;
        try {
            NotificationManagerCompat manager = NotificationManagerCompat.from(getActivity());
            ret = manager.areNotificationsEnabled();
        } catch (Exception e) {
            return true;
        }
        return ret;
    }

    public String appName = "update-";

    private void toIntentServiceUpdate() {
        Intent updateIntent = new Intent(getActivity(), UpdateIntentService.class);
        updateIntent.setAction(UpdateIntentService.ACTION_UPDATE);
        updateIntent.putExtra("appName", appName + version_code);
        //随便一个apk的url进行模拟
        updateIntent.putExtra("downUrl", downUrl);
        getActivity().startService(updateIntent);
    }

    private void toSetting() {
        try {
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
            startActivity(localIntent);
        } catch (Exception e) {

        }
    }

    private String version_code;
    private String downUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);

        unbinder.unbind();
    }


    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void showContentView() {
        showContent();
    }

    @Override
    public void showEmptyView() {
        showEmpty();
    }

    @Override
    public void showErrorView() {
        showError();
    }
}
