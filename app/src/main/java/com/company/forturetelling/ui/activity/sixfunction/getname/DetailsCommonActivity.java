package com.company.forturetelling.ui.activity.sixfunction.getname;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.utils.ClearEditText;
import com.company.forturetelling.view.SexBox;
import com.company.forturetelling.view.calendar.ChineseCalendar;
import com.company.forturetelling.view.calendar.DialogGLC;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yun.common.utils.KeyBoardUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lovelin on 2019/12/9
 * <p>
 * Describe:5个tab的结果详情界面
 */
public class DetailsCommonActivity extends BaseActivity {

    @BindView(R.id.edittext_person)
    ClearEditText edittext_person;
    @BindView(R.id.tv_born_data)
    TextView tv_born_data;
    @BindView(R.id.sex)
    SexBox sex;
    @BindView(R.id.iv_submit)
    ImageView iv_submit;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private String title;
    private String position;

    @Override
    public int getContentViewId() {
        return R.layout.activity_details;
    }

    @Override
    public void init() {
        initView();
        initData();
        responseListener();
        KeyBoardUtils.closeKeybord(edittext_person, this);

    }


    @OnClick({R.id.edittext_person, R.id.linear_born_data, R.id.iv_submit})
    public void multipleOnclick(View view) {
        switch (view.getId()) {
            case R.id.edittext_person: //输入姓名
                break;
            case R.id.linear_born_data: //输入出生日期
                showInDialog();
                break;
            case R.id.iv_submit: //提交
                checkData();
                break;


        }


    }

    private void checkData() {
        KeyBoardUtils.closeKeybord(edittext_person, this);
        String name = edittext_person.getText().toString().trim();
        String bornData = tv_born_data.getText().toString().trim();
        getSex();
        if ("".equals(name)) {
            showToast("请输入姓名");
        } else if ("未选择".equals(sexName) || "错误".equals(sexName)) {
            showToast("请输入性别");
        } else if ("".equals(bornData)) {
            showToast("请输入出生年月日");
        } else {
            //请求数据
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("sexName", sexName);
            bundle.putString("bornData", bornData);
            openActivity(GetNameResultActivity.class, bundle);
        }

    }

    private DialogGLC mDialog;
    private String sexName;

    //我的生日
    private void showInDialog() {
        if (mDialog == null) {
            mDialog = new DialogGLC(this, new DialogGLC.onCilckListener() {
                @Override
                public void onGetDataResult(ChineseCalendar calendar) {
                    tv_born_data.setText(calendar.get(Calendar.YEAR) + "年"
                            + (calendar.get(Calendar.MONTH) + 1) + "月"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                            + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                            + calendar.get(Calendar.MINUTE) + "分");
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
                    Toast.makeText(DetailsCommonActivity.this.getApplicationContext(), showToast,
                            Toast.LENGTH_LONG).show();
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

    private void responseListener() {
        iv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSex();
            }
        });

    }

    private void getSex() {
        switch (sex.getstatu()) {
            case 0:
                sexName = "未选择";
                break;
            case 1:
                sexName = "男";
                break;
            case 2:
                sexName = "女";
                break;
            default:
                sexName = "错误";
                break;

        }

        Toast.makeText(DetailsCommonActivity.this, sexName, Toast.LENGTH_SHORT).show();
    }

    private void initData() {
    }

    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        title = getIntent().getStringExtra("title");
        position = getIntent().getStringExtra("position");
        setTitleName("" + "详情界面");
        setPageStateView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
