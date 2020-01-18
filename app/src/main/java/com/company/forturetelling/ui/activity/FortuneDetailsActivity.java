package com.company.forturetelling.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.YunshiDetalisBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.google.gson.Gson;
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
 * Describe:运势--详情界面
 */
public class FortuneDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_details_name)
    TextView tvDetailsName;
    @BindView(R.id.tv_details_date)
    TextView tvDetailsDate;
    @BindView(R.id.tv_details_today)
    TextView tvDetailsToday;
    @BindView(R.id.tv_details_week)
    TextView tvDetailsWeek;
    @BindView(R.id.tv_details_month)
    TextView tvDetailsMonth;
    @BindView(R.id.tv_details_year)
    TextView tvDetailsYear;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.relate_mine_all)
    RelativeLayout relateMineAll;
    private String name;
    private String data;

    @Override
    public int getContentViewId() {
        return R.layout.activity_details_fortune;
    }

    @Override
    public void init() {
        initView();
        initData();
        responseListener();
    }


    private void responseListener() {
        String userid = (String) SharePreferenceUtil.get(FortuneDetailsActivity.this, Constants.USERID, "");
        if (userid.equals("")) {
            showToast("用户ID不能未空");
            return;
        } else {
            showLoading();
            OkHttpUtils.post()
                    .url(HttpConstants.TodayDetails)
                    .addParams("userid", userid)
//                    .addParams("birthday", "")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            showToast("回调错误404");
                            showError();

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            showContent();

                            Type type = new TypeToken<YunshiDetalisBean>() {
                            }.getType();
                            Gson gson = new Gson();
                            YunshiDetalisBean mBean = gson.fromJson(response, type);
                            if (mBean.getStatus().equals("0")) {
                                tvDetailsName.setText("用户名:" + name);
                                tvDetailsDate.setText("阳历:  " + data);
                                tvDetailsToday.setText("     " + mBean.getData().getToday());
                                tvDetailsWeek.setText("     " + mBean.getData().getWeek());
                                tvDetailsMonth.setText("     " + mBean.getData().getMonth());
                                tvDetailsYear.setText("     " + mBean.getData().getYear());


                            } else {
                                showToast("请求失败");

                            }
                        }
                    });
        }

    }


    private void initData() {

    }

    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        setTitleName("今日运势");
        setPageStateView();
        name = getIntent().getStringExtra("name");
        data = getIntent().getStringExtra("data");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
