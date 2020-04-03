package com.company.forturetelling.ui.fragment.fortune;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseFragment;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.information.InforBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.MainActivity;
import com.company.forturetelling.ui.activity.FortuneDetailsActivity;
import com.company.forturetelling.ui.activity.LifeDetailsActivity;
import com.company.forturetelling.ui.activity.information.LoginActivity;
import com.company.forturetelling.ui.activity.information.login.LoginAnimatorActivity;
import com.company.forturetelling.ui.activity.information.login.RegisterAnimatorActivity;
import com.company.forturetelling.ui.fragment.fortune.presenter.FortunePresenter;
import com.company.forturetelling.ui.fragment.fortune.presenter.FortuneView;
import com.company.forturetelling.utils.NetworkUtil;
import com.company.forturetelling.view.CircleImageView;
import com.company.forturetelling.view.CircularProgressView;
import com.company.forturetelling.view.RatingBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shehuan.niv.NiceImageView;
import com.squareup.picasso.Picasso;
import com.yun.common.utils.GlideUtils;
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
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Lovelin on 2019/4/27
 * <p>
 * Describe:个人-运程
 */
public class FortuneFragment extends BaseFragment implements FortuneView {
    @BindView(R.id.fake_status_bar)
    View mFakeStatusBar;
    @BindView(R.id.circularprogressview)
    CircularProgressView mProgress;
    Unbinder unbinder;
    @BindView(R.id.tv01_pic)
    CircleImageView tv01Pic;
    @BindView(R.id.tv01_name)
    TextView tv01Name;
    @BindView(R.id.tv01_name_type)
    TextView tv01_name_type;
    @BindView(R.id.tv01_birthday)
    TextView tv01Birthday;
    @BindView(R.id.tv01_point01)
    TextView tv01Point01;
    @BindView(R.id.tv01_point02)
    TextView tv01Point02;
    @BindView(R.id.tv01_point03)
    TextView tv01Point03;
    @BindView(R.id.tv01_point04)
    TextView tv01Point04;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.linear_fortune_part)
    LinearLayout linearFortunePart;
    @BindView(R.id.tv_part1_logo)
    TextView tvPart1Logo;
    @BindView(R.id.tv02_point)
    TextView tv02Point;
    @BindView(R.id.tv02_title01)
    TextView tv02Title01;
    @BindView(R.id.tv02_title02)
    TextView tv02Title02;
    @BindView(R.id.tv02_title03)
    TextView tv02Title03;
    @BindView(R.id.tv02_title04)
    TextView tv02Title04;
    @BindView(R.id.tv02_color)
    TextView tv02Color;
    @BindView(R.id.tv02_num)
    TextView tv02Num;
    @BindView(R.id.tv02_orientation)
    TextView tv02Orientation;
    @BindView(R.id.tv02_food)
    TextView tv02Food;
    @BindView(R.id.tv022_food)
    TextView tv022_food;
    @BindView(R.id.tv03_current_start_pic)
    NiceImageView tv03CurrentStartPic;
    @BindView(R.id.tv03_current_start)
    TextView tv03CurrentStart;
    @BindView(R.id.tv03_ratingbar01)
    RatingBar tv03Ratingbar01;
    @BindView(R.id.tv03_ratingbar02)
    RatingBar tv03Ratingbar02;
    @BindView(R.id.tv03_ratingbar03)
    RatingBar tv03Ratingbar03;
    @BindView(R.id.tv03_ratingbar04)
    RatingBar tv03Ratingbar04;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;
    @BindView(R.id.linear_all_login)
    LinearLayout linear_all_login;
    @BindView(R.id.linar_all_data)
    LinearLayout linear_all_data;
    @BindView(R.id.tv_02_login_now)
    TextView tv_02_login_now;
    private FortunePresenter mPresenter;
    private InforBean.DataBean.InfoBean infoBean;
    private InforBean.DataBean.JinriyunsiBean tadayBean;
    private InforBean.DataBean.XinZhuoBean starBean;
    private String userid;
    private String userid1;
    public static String title01;
    public static String title02;
    public static String title03;
    public static String title04;
    public static String username;
    private String birthday;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_01;
    }

    @Override
    public void init(ViewGroup rootView) {
        initTitle();
        mPresenter = new FortunePresenter(getActivity(), this);
        responseListener();

    }


    private void responseListener() {
        sendRequest();
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mHandler.sendEmptyMessage(1);

            }
        });


    }

    private void sendRequest() {
        String userid = (String) SharePreferenceUtil.get(getActivity(), Constants.USERID, "");
        if (userid.equals("")) {

        } else {
//            showLoadingView();
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
                            showContent();
                            Type type = new TypeToken<InforBean>() {
                            }.getType();
                            Gson gson = new Gson();
                            InforBean mBean = gson.fromJson(response, type);
                            Log.e("mImageUri", "response======refreshData01====dsate===" + response);
                            if (mBean.getStatus().equals("0")) {
                                infoBean = mBean.getData().getInfo();
                                tadayBean = mBean.getData().getJinriyunsi();
                                starBean = mBean.getData().getXin_zhuo();
                                refreshData01();
                                refreshData02();
                                refreshData03();

                            } else {
                                showToast("请求失败");

                            }
                        }
                    });
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            mProgress.setProgress(0);
//            mProgress.setProgress(tadayBean.getSu1(), 2000);
            sendRequest();
            mSmartRefresh.finishRefresh();

        }

    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ExitEvent(ExitEvent messageEvent) {
        initTitle();
        sendRequest();

    }


    private void refreshData01() {
        String headimg = infoBean.getHeadimg() + "";
        birthday = infoBean.getBirthday() + "";

        if (headimg.contains("http://")) {
            Picasso.with(getActivity()).load(infoBean.getHeadimg())
                    .placeholder(R.drawable.icon_mine_pic).error(R.drawable.icon_mine_pic).into(tv01Pic);

        } else {
            Picasso.with(getActivity()).load(HttpConstants.Common + infoBean.getHeadimg())
                    .placeholder(R.drawable.icon_mine_pic).error(R.drawable.icon_mine_pic).into(tv01Pic);
        }

        username = infoBean.getUsername();
        String username = infoBean.getName();
        tv01Name.setText(username + "");
        if ("".equals(infoBean.getJmsht())) {
            tv01_name_type.setText("喜神用" + infoBean.getJmsht() + "土");

        } else {
            tv01_name_type.setText("喜神用" + infoBean.getJmsht() + "");

        }
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    title01 = infoBean.getZhongheids().get(i);
                    tv01Point01.setText(title01 + "");
                    break;
                case 1:
                    title02 = infoBean.getZhongheids().get(i);
                    tv01Point02.setText(title02 + "");
                    break;
                case 2:
                    title03 = infoBean.getZhongheids().get(i);
                    tv01Point03.setText(title03 + "");
                    break;
                case 3:
                    title04 = infoBean.getZhongheids().get(i);
                    tv01Point04.setText(title04 + "");
                    break;
            }
        }
        tv01Birthday.setText("阳历:  " + infoBean.getBirthday() + "");
    }


    private void refreshData02() {
        tv02Point.setText(tadayBean.getSu1() + "");
        mProgress.setProgress(tadayBean.getSu1());
        tv02Color.setText(tadayBean.getColor() + "");
        tv02Num.setText(tadayBean.getSu2() + "");
        tv02Orientation.setText(tadayBean.getCw() + "");
        tv022_food.setText(tadayBean.getSw() + "");
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    tv02Title01.setText(tadayBean.getCy().get(i) + "");
                    break;
                case 1:
                    tv02Title01.setText(tadayBean.getCy().get(i) + "");
                    break;
                case 2:
                    tv02Title01.setText(tadayBean.getCy().get(i) + "");
                    break;
                case 3:
                    tv02Title01.setText(tadayBean.getCy().get(i) + "");
                    break;
            }
        }
    }

    private void refreshData03() {
        tv03CurrentStart.setText(starBean.getXin_name() + "");
        GlideUtils.LoadUserImageView(getActivity(), HttpConstants.Common + starBean.getStar_url(), tv03CurrentStartPic);
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    tv03Ratingbar01.setStarFillDrawable(getResources().getDrawable(R.drawable.icon_gb_start));
                    tv03Ratingbar01.setStar(Float.parseFloat(starBean.getYs().get(i)));
                    break;
                case 1:
                    tv03Ratingbar02.setStarFillDrawable(getResources().getDrawable(R.drawable.icon_gb_start));
                    tv03Ratingbar02.setStar(Float.parseFloat(starBean.getYs().get(i)));
                    break;
                case 2:
                    tv03Ratingbar03.setStarFillDrawable(getResources().getDrawable(R.drawable.icon_gb_start));
                    tv03Ratingbar03.setStar(Float.parseFloat(starBean.getYs().get(i)));
                    break;
                case 3:
                    tv03Ratingbar04.setStarFillDrawable(getResources().getDrawable(R.drawable.icon_gb_start));
                    tv03Ratingbar04.setStar(Float.parseFloat(starBean.getYs().get(i)));
                    break;
            }
        }

    }

    @Override
    protected void onClickRetry() {
        super.onClickRetry();
        if (NetworkUtil.CheckConnection(getActivity())) {
            mPresenter.sendRequest();
        }
    }

    private void initTitle() {
        userid1 = (String) SharePreferenceUtil.get(getActivity(), Constants.USERID, "");
        Boolean isLogin = (Boolean) SharePreferenceUtil.get(getActivity(), com.company.forturetelling.global.Constants.Is_Logined, false);
        String Perfect = (String) SharePreferenceUtil.get(getActivity(), com.company.forturetelling.global.Constants.WX_Perfect, "false");
        linear_all_login.setVisibility(View.INVISIBLE);
        linear_all_data.setVisibility(View.VISIBLE);
        if (isLogin) {   //登入了
            if ("true".equals(Perfect)) {  //已经完善.
                linear_all_login.setVisibility(View.INVISIBLE);
                linear_all_data.setVisibility(View.VISIBLE);
            }
        } else {//未登录
            linear_all_login.setVisibility(View.VISIBLE);
            linear_all_data.setVisibility(View.INVISIBLE);

        }
        setTitleName("分析");
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.GONE);
        setPageStateView();


    }


    @OnClick({R.id.part_fortune_01, R.id.part_fortune_02, R.id.part_fortune_03, R.id.tv_02_login_now})
    public void multipleOnclick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_02_login_now:
                SharePreferenceUtil.put(getActivity(), com.company.forturetelling.global.Constants.Is_Main_To_Login, "yes");
                bundle.putString("02", "02");
                openActivity(LoginAnimatorActivity.class, bundle);
                break;
            case R.id.part_fortune_01: //命盘分析
                if ("".equals(userid)) {
                    showToast("立即登登入,更多体验");
                    openActivity(LoginAnimatorActivity.class);
                } else {
                    bundle.putString("birthday", birthday + "");
                    bundle.putString("userid", userid1 + "");
                    bundle.putString("title01", title01 + "");
                    bundle.putString("title02", title02 + "");
                    bundle.putString("title03", title03 + "");
                    bundle.putString("title04", title04 + "");
                    openActivity(LifeDetailsActivity.class, bundle);
                }
                break;
            case R.id.part_fortune_02: //今日运势
                String userid = (String) SharePreferenceUtil.get(getActivity(), Constants.USERID, "");
                if ("".equals(userid)) {
                    showToast("立即登登入,更多体验");
                    openActivity(LoginAnimatorActivity.class);
                } else {
                    bundle.putString("name", infoBean.getUsername() + "");
                    bundle.putString("data", infoBean.getBirthday() + "");
                    openActivity(FortuneDetailsActivity.class, bundle);
                }

                break;
            case R.id.part_fortune_03: //今日运势

                break;
        }

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
}
