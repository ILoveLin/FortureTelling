package com.company.forturetelling.ui.activity.sixfunction.synthesize;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.TextView;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.ui.activity.information.LoginActivity;
import com.company.forturetelling.ui.activity.pay.SelectPayActivity;
import com.company.forturetelling.ui.activity.result.ResultCommonActivity;
import com.company.forturetelling.ui.activity.sixfunction.eightnumber.EightNumberActivity;
import com.company.forturetelling.ui.activity.sixfunction.fortune.presenter.FortunePresenter;
import com.company.forturetelling.ui.activity.sixfunction.synthesize.presenter.SynthesizePresenter;
import com.company.forturetelling.ui.activity.sixfunction.synthesize.presenter.SynthesizeView;
import com.company.forturetelling.utils.ClearEditText;
import com.company.forturetelling.view.calendar.ChineseCalendar;
import com.company.forturetelling.view.calendar.DialogGLC;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yun.common.utils.KeyBoardUtils;
import com.yun.common.utils.SharePreferenceUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lovelin on 2019/12/19
 * <p>
 * Describe:八字-结婚
 */
public class SynthesizeActivity extends BaseActivity implements SynthesizeView {

    @BindView(R.id.tv_three_input_name)
    ClearEditText tvThreeInputName;
    @BindView(R.id.selector_sex_three_boy)
    TextView selectorSexThreeBoy;
    @BindView(R.id.selector_sex_three_girl)
    TextView selectorSexThreeGirl;
    @BindView(R.id.tv_three_input_date)
    TextView tvThreeInputDate;
    @BindView(R.id.iv_three_submit)
    TextView ivThreeSubmit;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private String hour;
    private int StatueSex = 0;
    private String username;
    private String datadate;
    private SynthesizePresenter mPresenter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_sixfuntion_getmarried;
    }

    @Override
    public void init() {
        initView();
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
    public void updateFinish(String oid, String title) {
        String userid = (String) SharePreferenceUtil.get(SynthesizeActivity.this, Constants.USERID, "");
        if ("".equals(userid)) {
            openActivity(LoginActivity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("oid", oid);
            bundle.putString("title", title);
            //TODO  获取到订单号 跳转到支付界面
//        openActivity(  ResultCommonActivity.class, bundle);
            openActivity(SelectPayActivity.class, bundle);
//        this.finish();
        }
    }

    @OnClick({R.id.selector_sex_three_boy, R.id.selector_sex_three_girl, R.id.iv_three_submit,
            R.id.tv_three_input_date})
    public void multipleOnclick(View view) {
        switch (view.getId()) {
            case R.id.selector_sex_three_boy: //男
                selectorSexThreeBoy.setSelected(true);
                selectorSexThreeGirl.setSelected(false);
                StatueSex = 0;
                break;
            case R.id.selector_sex_three_girl:  //女
                selectorSexThreeGirl.setSelected(true);
                selectorSexThreeBoy.setSelected(false);
                StatueSex = 1;
                break;
            case R.id.tv_three_input_date: //选择生日
                showInDialog();
                break;
            case R.id.iv_three_submit: //提交
                checkData();
                break;

        }


    }

    private void checkData() {
        KeyBoardUtils.closeKeybord(tvThreeInputName, this);
        username = tvThreeInputName.getText().toString().trim();
        datadate = tvThreeInputDate.getText().toString();
        if ("".equals(tvThreeInputName.getText().toString().trim())) {
            showToast("姓名不能为空");
        } else if ("请输入".equals(tvThreeInputDate.getText().toString()) || "".equals(tvThreeInputDate.getText().toString())) {
            showToast("出生年月日不能为空");
        } else {
//            进入相对于的界面
            sendCurrentRequest();
        }


    }

    private void sendCurrentRequest() {
        mPresenter.sendNo1Request(datadate, username, StatueSex + "", hour);
    }

    private DialogGLC mDialog;
    private String bornData;

    private void showInDialog() {
        if (mDialog == null) {
            mDialog = new DialogGLC(this, new DialogGLC.onCilckListener() {
                @Override
                public void onGetDataResult(ChineseCalendar calendar) {
                    String showToast = "Gregorian : " + calendar.get(Calendar.YEAR) + "年"
                            + (calendar.get(Calendar.MONTH) + 1) + "月"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                            + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                            + calendar.get(Calendar.MINUTE) + "分------------" +
                            "\n"
                            + "Lunar     : " + calendar.getChinese(ChineseCalendar.CHINESE_YEAR)
                            + (calendar.getChinese(ChineseCalendar.CHINESE_MONTH))
                            + calendar.getChinese(ChineseCalendar.CHINESE_DATE)
                            + calendar.getChinese(ChineseCalendar.CHINESE_HOUR)
                            +
                            "\n" + "\n" + "\n" + "-----------上传的日期---------" +
                            calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "-"
                            + calendar.get(Calendar.HOUR_OF_DAY) + "-"
                            + calendar.get(Calendar.MINUTE);
                    int intyear = calendar.get(Calendar.YEAR);
                    if (intyear > 2019) {
                        showToast("年份超出范围");
                        tvThreeInputDate.setText("");
                        return;
                    }
                    bornData = calendar.get(Calendar.YEAR) + "-"
                            + (calendar.get(Calendar.MONTH) + 1) + "-"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "-"
                            + calendar.get(Calendar.HOUR_OF_DAY) + "-"
                            + calendar.get(Calendar.MINUTE);
                    hour = calendar.get(Calendar.HOUR_OF_DAY) + "";
                    tvThreeInputDate.setText(bornData + "");
                    mDialog.dismiss();

                }
            });
        }
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        } else {
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.show();
            mDialog.initCalendar();
        }
    }

    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        setTitleName("" + getIntent().getStringExtra("title"));
        setPageStateView();
        mPresenter = new SynthesizePresenter(this, this);
        selectorSexThreeBoy.setSelected(true);
        KeyBoardUtils.closeKeybord(tvThreeInputName, this);
        tvThreeInputName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
