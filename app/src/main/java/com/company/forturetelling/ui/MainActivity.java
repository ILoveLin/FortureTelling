package com.company.forturetelling.ui;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.forturetelling.R;
import com.company.forturetelling.base.ActivityCollector;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.UpdateBean;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.activity.information.LoginActivity;
import com.company.forturetelling.ui.fragment.calculate.CalculateFragment;
import com.company.forturetelling.ui.fragment.fortune.FortuneFragment;
import com.company.forturetelling.ui.fragment.knowledge.KnowledgeFragment;
import com.company.forturetelling.ui.fragment.mine.MineFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yun.common.contant.Constants;
import com.yun.common.utils.CommonUtils;
import com.yun.common.utils.CustomToast;
import com.yun.common.utils.SharePreferenceUtil;
import com.yun.common.utils.StatusBarUtil;
import com.yun.common.utils.StatusBarUtils;
import com.yun.common.utils.popupwindow.PopupWindowTwoButton;
import com.yun.common.utils.updateutils.UpdateIntentService;
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

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_tab_homepage)
    TextView tvTabFirstPage;   //首页
    @BindView(R.id.tv_tab_drug_query)
    TextView tvTabSecondPage;  //药品查询
    @BindView(R.id.tv_tab_recomment)
    TextView tvTabThirdPage;  //咨询
    @BindView(R.id.tv_tab_mine)
    TextView tvTabFourthPage;        //我的
    @BindView(R.id.iv_tab_mine)
    ImageView ivTabMine;        //我的小红点
    @BindView(R.id.rel_tab_mine)
    RelativeLayout relTabMine;      //整体的我的relative　　　
    @BindView(R.id.layout_bottom_lin)
    LinearLayout layoutBottomLin;
    @BindView(R.id.ll_main_bottomm)
    LinearLayout linearBottom;
    @BindView(R.id.linear_main)
    LinearLayout linear_main;
    private FragmentManager fragmentManager;
    private Integer valTab;
    private String localVersionCode;
    private String version_code;
    private String downUrl;
    private Gson gson;

    @Override
    public int getContentViewId() {
        EventBus.getDefault().register(this);
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        setTitleBarVisibility(View.GONE);
        linearBottom.setVisibility(View.VISIBLE);
        fragmentManager = getSupportFragmentManager();
        valTab = (Integer) SharePreferenceUtil.get(this, SharePreferenceUtil.DYNAMIC_SWITCH_TAB, Constants.TAB_HOME);
        setChoiceItem(valTab);
        requestUpdateVersion("Android");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //    private FortuneFragment firstFragment;
    private CalculateFragment firstFragment;
    private FortuneFragment secondFragment;
    private KnowledgeFragment thirdFragment;
    private MineFragment fourthFragment;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ExitEvent(ExitEvent messageEvent) {
        setChoiceItem(Constants.TAB_MINE);
    }

    private void setChoiceItem(Integer index) {
        if (index < 0) {
            index = Constants.TAB_HOME;
        }

        SharePreferenceUtil.put(this, SharePreferenceUtil.DYNAMIC_SWITCH_TAB, index);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case Constants.TAB_HOME:   //1　　
                hideFragments(transaction);
                StatusBarUtils.setColor(this, getResources().getColor(R.color.color_transparent), 0);
                StatusBarUtil.darkMode(this, true);  //设置了状态栏文字的颜色
                if (firstFragment == null) {
                    firstFragment = new CalculateFragment();
                    transaction.add(R.id.ll_content, firstFragment);
                } else {
                    transaction.show(firstFragment);
                }
                tvTabFirstPage.setSelected(true);
                tvTabSecondPage.setSelected(false);
                tvTabThirdPage.setSelected(false);
                tvTabFourthPage.setSelected(false);
                overAnim(tvTabFirstPage);
                break;
            case Constants.TAB_DRUGS_QUERY://药品查询  //2
                hideFragments(transaction);
                StatusBarUtils.setColor(this, getResources().getColor(R.color.color_transparent), 0);
                StatusBarUtil.darkMode(this, true);  //设置了状态栏文字的颜色
                if (secondFragment == null) {
                    secondFragment = new FortuneFragment();
                    transaction.add(R.id.ll_content, secondFragment);
                } else {
                    transaction.show(secondFragment);
                }
                tvTabFirstPage.setSelected(false);
                tvTabSecondPage.setSelected(true);
                tvTabThirdPage.setSelected(false);
                tvTabFourthPage.setSelected(false);
                overAnim(tvTabSecondPage);
                break;

            case Constants.TAB_NEWS:   //3
                hideFragments(transaction);
                StatusBarUtils.setColor(this, getResources().getColor(R.color.color_transparent), 0);
                StatusBarUtil.darkMode(this, true);  //设置了状态栏文字的颜色
                if (thirdFragment == null) {
                    thirdFragment = new KnowledgeFragment();
                    transaction.add(R.id.ll_content, thirdFragment);
                } else {
                    transaction.show(thirdFragment);
                }
                tvTabFirstPage.setSelected(false);
                tvTabSecondPage.setSelected(false);
                tvTabThirdPage.setSelected(true);
                tvTabFourthPage.setSelected(false);
                overAnim(tvTabThirdPage);
                break;

            case Constants.TAB_MINE:   //4
                hideFragments(transaction);
                StatusBarUtils.setColor(this, getResources().getColor(R.color.color_transparent), 0);
                StatusBarUtil.darkMode(this, true);  //设置了状态栏文字的颜色
                if (fourthFragment == null) {
                    fourthFragment = new MineFragment();
                    transaction.add(R.id.ll_content, fourthFragment);
                } else {
                    transaction.show(fourthFragment);
                }
                tvTabFirstPage.setSelected(false);
                tvTabSecondPage.setSelected(false);
                tvTabThirdPage.setSelected(false);
                tvTabFourthPage.setSelected(true);
                overAnim(tvTabFourthPage);
                break;
        }
        transaction.commit();
    }

    @OnClick({R.id.tv_tab_homepage, R.id.tv_tab_drug_query, R.id.tv_tab_recomment, R.id.rel_tab_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tab_homepage:    //1
                setChoiceItem(Constants.TAB_HOME);
                break;
            case R.id.tv_tab_drug_query:   // 2
                setChoiceItem(Constants.TAB_DRUGS_QUERY);
                break;
            case R.id.tv_tab_recomment:    //3
                setChoiceItem(Constants.TAB_NEWS);
                break;
            case R.id.rel_tab_mine:   //4
                setChoiceItem(Constants.TAB_MINE);
                break;
            default:
        }
    }

    public static boolean isTabBiyao = false;

    // 隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {
        if (firstFragment != null) {
            transaction.hide(firstFragment);

        }
//        if (reimbursementFragment != null) {
//            transaction.hide(reimbursementFragment);
//        }
        if (secondFragment != null) {
            transaction.hide(secondFragment);

        }
        if (thirdFragment != null) {
            transaction.hide(thirdFragment);
        }
        if (fourthFragment != null) {
            transaction.hide(fourthFragment);
        }

    }

    private void overAnim(View view) {
        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.8f, 1.0f);
        final ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.8f, 1.0f);
        final ObjectAnimator translationY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY, translationY);
        set.setDuration(200);
        set.start();
    }


    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                exitTime = System.currentTimeMillis();
                showToast("再按一次退出程序");
            } else {
                SharePreferenceUtil.put(this, SharePreferenceUtil.DYNAMIC_SWITCH_TAB, 0);
                ActivityCollector.removeAll();
                System.exit(0);

            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    /**
     * 三个地方更改包名
     * 清单文件FileProvider---------------FileProvider
     * 清单文件的 FileProvider和UriUtil
     * uri = FileProvider.getUriForFile(context.getApplicationContext(),
     * "com.company.yun.fileProvider", file);
     * <p>
     * 的FileProvider必须一样
     * UpdateIntentService----ACTION_UPDATE   字段
     * UriUtil-----getUriForFile   方法里面
     */

    private void requestUpdateVersion(String type) {
        localVersionCode = CommonUtils.getVersionName(this);
        showLoading();
        OkHttpUtils.get()
                .url(HttpConstants.Update)
                .addParams("type", 1 + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CustomToast.showToast(getApplicationContext(), "版本更新-网络请求错误");
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
                        Log.e("mImageUri", "=========update===01======" + response);

                        if (localVersionCode.compareTo(version_code) < 0) {
                            Log.e("mImageUri", "=========update===localVersionCode======" + localVersionCode);
                            Log.e("mImageUri", "=========update===version_code======" + version_code);

                            downUrl = HttpConstants.Common + mBean.getDownurl();
                            updateVersion(downUrl);
                        }
                    }
                });

    }


    //我就说一句话，这是坠吼的！
    private void updateVersion(final String url) {
        // 如果指定的数与参数相等返回0。
        //如果指定的数小于参数返回 -1。
        //如果指定的数大于参数返回 1。

        final PopupWindowTwoButton twoButton = new PopupWindowTwoButton((Activity) this);
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
        twoButton.showPopupWindow(linear_main, Gravity.CENTER);

    }

    private void beforeUpdateWork() {

        if (!isEnableNotification()) {
            showNotificationAsk();
            Log.e("mImageUri", "=========update===02======");

            return;
        }
        Log.e("mImageUri", "=========update===03======");

        toIntentServiceUpdate();

    }

    private void showNotificationAsk() {
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(this);
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
            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            ret = manager.areNotificationsEnabled();
        } catch (Exception e) {
            return true;
        }
        return ret;
    }

    public String appName = "update-";

    private void toIntentServiceUpdate() {
        Intent updateIntent = new Intent(this, UpdateIntentService.class);
        updateIntent.setAction(UpdateIntentService.ACTION_UPDATE);
        updateIntent.putExtra("appName", appName + version_code);
        //随便一个apk的url进行模拟
        updateIntent.putExtra("downUrl", downUrl);
        this.startService(updateIntent);
    }

    private void toSetting() {
        try {
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", this.getPackageName(), null));
            startActivity(localIntent);
        } catch (Exception e) {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}