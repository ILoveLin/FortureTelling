package com.company.forturetelling.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.LifeBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.fragment.fortune.FortuneFragment;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yun.common.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Lovelin on 2019/12/9
 * <p>
 * Describe:命盘分析--详情界面
 */
public class LifeDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_contont01)
    TextView tv_contont01;
    @BindView(R.id.tv_contont02)
    TextView tv_contont02;
    @BindView(R.id.tv_contont03)
    TextView tv_contont03;
    @BindView(R.id.tv_title_01)
    TextView tvTitle01;
    @BindView(R.id.tv_title_02)
    TextView tvTitle02;
    @BindView(R.id.tv_title_03)
    TextView tvTitle03;
    @BindView(R.id.tv_title_04)
    TextView tvTitle04;
    @BindView(R.id.tv_current_name_life)
    TextView tv_current_name_life;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.relate_mine_all)
    RelativeLayout relateMineAll;
    private String userid;
    private String title01;
    private String title02;
    private String title03;
    private String title04;

    @Override
    public int getContentViewId() {
        return R.layout.activity_details_life;
    }

    @Override
    public void init() {
        initView();
        responseListener();
    }

    private void responseListener() {
        String userid = (String) SharePreferenceUtil.get(LifeDetailsActivity.this, Constants.USERID, "");
        showLoading();
        OkHttpUtils.get()
                .url(HttpConstants.LiftFortune)
                .addParams("userid", userid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        showError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Type type = new TypeToken<LifeBean>() {
                        }.getType();
                        LifeBean mBean01 = mGson.fromJson(response, type);
                        if ("0".equals(mBean01.getStatus())) {
                            showContent();
                            refreshData(mBean01);

                        }


                    }
                });

    }

    private void refreshData(LifeBean mBean01) {

        tv_current_name_life.setText(FortuneFragment.username);
        tvTitle01.setText(FortuneFragment.title01);
        tvTitle02.setText(FortuneFragment.title02);
        tvTitle03.setText(FortuneFragment.title03);
        tvTitle04.setText(FortuneFragment.title04);
        tv_contont01.setText(Html.fromHtml(mBean01.getData().getLove()));
        tv_contont02.setText(Html.fromHtml(mBean01.getData().getWealths()));
        tv_contont03.setText(Html.fromHtml(mBean01.getData().getCause()));


    }


    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        setTitleName("命盘分析");
        setPageStateView();

        userid = getIntent().getStringExtra("userid");
        title01 = getIntent().getStringExtra("title01");
        title02 = getIntent().getStringExtra("title02");
        title03 = getIntent().getStringExtra("title03");
        title04 = getIntent().getStringExtra("title04");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
