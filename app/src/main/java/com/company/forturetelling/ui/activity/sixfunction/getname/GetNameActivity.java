package com.company.forturetelling.ui.activity.sixfunction.getname;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.calculate.AddNameBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.activity.information.LoginActivity;
import com.company.forturetelling.ui.activity.information.login.LoginAnimatorActivity;
import com.company.forturetelling.ui.activity.pay.SelectPayActivity;
import com.company.forturetelling.ui.activity.sixfunction.eightnumber.EightNumberActivity;
import com.company.forturetelling.utils.ClearEditText;
import com.company.forturetelling.view.calendar.ChineseCalendar;
import com.company.forturetelling.view.calendar.DialogGLC;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yun.common.utils.KeyBoardUtils;
import com.yun.common.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Lovelin on 2019/12/9
 * <p>
 * Describe:取名详情界面
 */
public class GetNameActivity extends BaseActivity {

    @BindView(R.id.selector_statue_born)
    TextView selector_statue_born;
    @BindView(R.id.selector_statue_unborn)
    TextView selector_statue_unborn;
    @BindView(R.id.selector_sex_boy)
    TextView selector_sex_boy;
    @BindView(R.id.selector_sex_girl)
    TextView selector_sex_girl;
    @BindView(R.id.tv_addname_data)
    TextView tv_addname_data;
    @BindView(R.id.iv_submit)
    TextView iv_submit;
    @BindView(R.id.et_phone)
    ClearEditText ClearEditText;
    @BindView(R.id.linear_brithday)
    LinearLayout linear_brithday;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.tv_addname_name)
    ClearEditText tvAddnameName;
    @BindView(R.id.tv_addname_zibei)
    ClearEditText tvAddnameZibei;
    private String title;
    private String position;

    private int StatueBorn = 1;  //1--已出生  0--未出生
    private int StatueSex = 1;  //性别 1 男 0女
    private String randomIntPhone;
    private String addName;
    private String addNameZibei;
    private String bornData;
    private Gson gson;
    private String currentTime;
    private String mPhoneParams;


    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        title = getIntent().getStringExtra("title");
        position = getIntent().getStringExtra("position");
        setTitleName("" + "取名界面");
        setPageStateView();
        //默认已出生  男
        selector_statue_born.setSelected(true);
        selector_sex_boy.setSelected(true);
        gson = new Gson();
        tvAddnameName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        tvAddnameZibei.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

    }

    private void checkData() {
        currentTime = System.currentTimeMillis() + "";
        randomIntPhone = getRandomInt(1000, 9999) + "";
        mPhoneParams = currentTime + randomIntPhone;
        KeyBoardUtils.closeKeybord(ClearEditText, this);
        addName = tvAddnameName.getText().toString().trim();
        addNameZibei = tvAddnameZibei.getText().toString().trim();

        if ("".equals(addName)) {
            showToast("姓氏不能为空");
        } else if ("请输入".equals(tv_addname_data.getText().toString()) || "".equals(tv_addname_data.getText().toString())) {
            showToast("出生年月日不能为空");
        } else {
//            进入相对于的界面
            sendOrderRequest();
        }
    }


    private void sendOrderRequest() {
        showLoading();
        OkHttpUtils.get()
                .url(HttpConstants.AddName)
                .addParams("cszt", StatueBorn + "")  // 出生状态
                .addParams("name", addName + "")  // 姓氏
                .addParams("zibei", addNameZibei + "")  // 字辈
                .addParams("gender", StatueSex + "")  // 性别 1 男 0女
                .addParams("b_input", 0 + "")  //  默认公历
                .addParams("xing", addName + "")  //姓氏 (和name一样的意思)
                .addParams("ver", "")  //
                .addParams("date", bornData + "")  //时间 比如: 2019-12-9-0-0
                .addParams("phone", mPhoneParams + "")  //
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showError();
                        Log.e("mImageUri", "addName====error=onResponse===showError==");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("mImageUri", "addName====error=onResponse=====" + response);
                        Type type = new TypeToken<AddNameBean>() {
                        }.getType();
                        AddNameBean mAddBean = gson.fromJson(response, type);
                        showContent();
                        String wechat_price = mAddBean.getData().getWechat_price();
                        String ali_price = mAddBean.getData().getAli_price();

                        if (mAddBean.getStatus().equals("0")) {
                            String orderNo = mAddBean.getData().getOrderNo();
                            Bundle bundle = new Bundle();
                            bundle.putString("oid", orderNo);
                            bundle.putString("title", title);
                            bundle.putString("text_surname", "");  //姓
                            bundle.putString("text_name", "");     //名
//                            bundle.putString("price", "86");      //价格
                            bundle.putString("price_wechar", wechat_price);      //价格
                            bundle.putString("price_ali", ali_price);      //价格
                            bundle.putString("text_all_name", tvAddnameName.getText().toString().trim() + ""); //姓名
                            //TODO  获取到订单号 跳转到支付界面
                            openActivity(SelectPayActivity.class, bundle);
                        } else {
                            showError();
                        }

                    }
                });


    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_get_name;
    }

    @Override
    public void init() {
        initView();
        KeyBoardUtils.closeKeybord(ClearEditText, this);

    }


    @OnClick({R.id.selector_statue_born, R.id.selector_statue_unborn, R.id.selector_sex_boy,
            R.id.selector_sex_girl, R.id.tv_addname_data, R.id.et_phone, R.id.iv_submit})
    public void multipleOnclick(View view) {
        switch (view.getId()) {
            case R.id.selector_statue_born: //已出生
                selector_statue_born.setSelected(true);
                selector_statue_unborn.setSelected(false);
                StatueBorn = 1;
                break;
            case R.id.selector_statue_unborn: //未出生
                selector_statue_unborn.setSelected(true);
                selector_statue_born.setSelected(false);
                StatueBorn = 0;
                break;
            case R.id.selector_sex_boy: //男
                selector_sex_boy.setSelected(true);
                selector_sex_girl.setSelected(false);
                StatueSex = 1;
                break;
            case R.id.selector_sex_girl: //女
                selector_sex_boy.setSelected(false);
                selector_sex_girl.setSelected(true);
                StatueSex = 0;
                break;
            case R.id.tv_addname_data: //选择日期
                showInDialog();
                break;
            case R.id.iv_submit: //提交
                checkData();
                break;
//            case R.id.iv_submit: //提交
//                checkData();
//                break;
//            case R.id.iv_submit: //提交
//                checkData();
//                break;


        }


    }


    public Double getRandomInt(int min, int max) {
        Double Min = Math.ceil(min); //向上取整
        Double Max = Math.floor(max); //向下取整
        return Math.floor(Math.random() * (max - min)) + min;
    }


    private DialogGLC mDialog;
    private String sexName;

    //我的生日
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
                        tv_addname_data.setText("");
                        return;
                    }
                    bornData = calendar.get(Calendar.YEAR) + "-"
                            + (calendar.get(Calendar.MONTH) + 1) + "-"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "-"
                            + calendar.get(Calendar.HOUR_OF_DAY) + "-"
                            + calendar.get(Calendar.MINUTE);
                    tv_addname_data.setText(bornData + "");

//
//                    Toast.makeText(GetNameActivity.this.getApplicationContext(), showToast,
//                            Toast.LENGTH_LONG).show();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
