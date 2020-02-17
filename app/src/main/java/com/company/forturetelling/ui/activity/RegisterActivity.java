package com.company.forturetelling.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.CityBean;
import com.company.forturetelling.bean.RegisterBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.MainActivity;
import com.company.forturetelling.ui.activity.information.InforSettingActivity;
import com.company.forturetelling.ui.activity.information.presenter.InforPresenter;
import com.company.forturetelling.utils.ClearEditText;
import com.company.forturetelling.view.CircleImageView;
import com.company.forturetelling.view.SettingBar;
import com.company.forturetelling.view.calendar.ChineseCalendar;
import com.company.forturetelling.view.calendar.DialogGLC;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yun.common.utils.CommonUtils;
import com.yun.common.utils.GetJsonDataUtil;
import com.yun.common.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Lovelin on 2019/12/12
 * <p>
 * Describe:
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.register_roundiv)
    CircleImageView registerRoundiv;
    @BindView(R.id.register_username)
    ClearEditText registerUsername;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.register_password)
    ClearEditText registerPassword;
    @BindView(R.id.view02)
    View view02;
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.test)
    TextView test;
    @BindView(R.id.phone_register)
    TextView phoneRegister;
    @BindView(R.id.bar_setting_birthday)
    SettingBar bar_setting_birthday;
    @BindView(R.id.bar_setting_address)
    SettingBar bar_setting_address;
    @BindView(R.id.bar_setting_sex)
    SettingBar bar_setting_sex;
    @BindView(R.id.register_relative)
    RelativeLayout registerRelative;
    private int StatueSex;
    private String currentBirthday;
    private Thread thread;
    private boolean isLoaded = false;

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    //判断地址数据是否获取成功
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        Log.i("addr", "地址数据开始解析");
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Log.i("addr", "地址数据获取成功");
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Log.i("addr", "地址数据获取失败");
                    break;


            }
        }
    };
    private String token;

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String CityData = new GetJsonDataUtil().getJson(RegisterActivity.this, "city.json");//获取assets目录下的json文件数据

        ArrayList<CityBean> jsonBean = parseData(CityData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCity_list().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCity_list().get(c);
                CityList.add(CityName);//添加城市

            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<CityBean> parseData(String result) {//Gson 解析
        ArrayList<CityBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {
        initView();
    }

    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        String weChat = getIntent().getStringExtra("WeChat");
        setTitleName("" + weChat);
        setPageStateView();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

    }


    @OnClick({R.id.bar_setting_birthday, R.id.bar_setting_address, R.id.bar_setting_sex,
            R.id.btn_register})
    public void multipleOnclick(View view) {
        switch (view.getId()) {
            case R.id.bar_setting_birthday: //生日
                showInDialog();
                break;
            case R.id.bar_setting_address:  //地址
                ShowPickerView();
                break;
            case R.id.bar_setting_sex: //性别   性别 0 女 1男
                showUpPop(registerRelative);
                break;
            case R.id.btn_register: //提交
                CommonUtils.closeKeyboard(RegisterActivity.this);
                checkData();
                break;

        }


    }

    private ArrayList<CityBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private String province;
    private String city;

    private void ShowPickerView() {// 弹出地址选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(RegisterActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);

                bar_setting_address.setRightText(province + "省 " + city + "市");
//                        + options3Items.get(options1).get(options2).get(options3);
//                mPresenter.sendAddressRequest(province, city, bar_setting_address);
            }
        })

                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();


        pvOptions.setPicker(options1Items, options2Items);//二级选择器（市区）
//pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private DialogGLC mDialog;

    //我的生日
    private void showInDialog() {
        if (mDialog == null) {
            mDialog = new DialogGLC(this, new DialogGLC.onCilckListener() {
                @Override
                public void onGetDataResult(ChineseCalendar calendar) {
                    String data = calendar.get(Calendar.YEAR) + "年"
                            + (calendar.get(Calendar.MONTH) + 1) + "月"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                            + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                            + calendar.get(Calendar.MINUTE) + "分";
                    currentBirthday = calendar.get(Calendar.YEAR) + "-"
                            + (calendar.get(Calendar.MONTH) + 1) + "-"
                            + calendar.get(Calendar.DAY_OF_MONTH);
                    int intyear = calendar.get(Calendar.YEAR);
                    if (intyear > 2019) {
                        showToast("年份超出范围");
                        bar_setting_birthday.setRightText("");
                        return;
                    }
//                    mPresenter.sendDataRequest(barSettingBirthday, birthday);

                    bar_setting_birthday.setRightText("" + currentBirthday);
                    String showToast = "Gregorian : " + calendar.get(Calendar.YEAR) + "年"
                            + (calendar.get(Calendar.MONTH) + 1) + "月"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                            +
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

    private PopupWindow popupWindow;
    private TranslateAnimation animation;
    private View popupView;
    private TextView icon_sex_man;
    private TextView icon_sex_wonman;

    /**
     * 修改性别
     *
     * @param
     * @param info_setting_linear
     */
    public void showUpPop(RelativeLayout info_setting_linear) {
        if (popupWindow == null) {
            popupView = View.inflate(this, R.layout.popup_sex_select, null);
            // 参数2,3：指明popupwindow的宽度和高度
            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //隐藏遮罩
                    setBGAlpha(1);


                }
            });
            // 设置背景图片， 必须设置，不然动画没作用
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);
            // 设置点击popupwindow外屏幕其它地方消失
            popupWindow.setOutsideTouchable(true);
            // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(200);
        }

        //设置按钮点击监听
        popupView.findViewById(R.id.sex_man).setOnClickListener(this);
        popupView.findViewById(R.id.sex_wonman).setOnClickListener(this);
        popupView.findViewById(R.id.cancel).setOnClickListener(this);
        icon_sex_man = popupView.findViewById(R.id.icon_sex_man);
        icon_sex_wonman = popupView.findViewById(R.id.icon_sex_wonman);
        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        popupWindow.showAtLocation(info_setting_linear, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupView.startAnimation(animation);
        //显示遮罩
        setBGAlpha(0.5f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sex_man: //男  性别 0 女 1男
                icon_sex_man.setVisibility(View.VISIBLE);
                icon_sex_wonman.setVisibility(View.GONE);
                popupWindow.dismiss();
                StatueSex = 1;
                bar_setting_sex.setRightText("" + "男");

                break;
            case R.id.sex_wonman: //女
                icon_sex_man.setVisibility(View.GONE);
                icon_sex_wonman.setVisibility(View.VISIBLE);
                popupWindow.dismiss();
                StatueSex = 0;
                bar_setting_sex.setRightText("" + "女");
                break;
            case R.id.cancel:
                popupWindow.dismiss();
                break;

        }
    }

    public void setBGAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


    private String userid;
    private RegisterBean bean;
    private String username;
    private String password;

    private void checkData() {
        username = registerUsername.getText().toString().trim();
        password = registerPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            showToast("用户名不能为空");
        } else if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
        } else if (TextUtils.isEmpty(currentBirthday)) {
            showToast("出生年月不能为空");
        } else if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city)) {
            showToast("地址不能未空");
        } else if (TextUtils.isEmpty(StatueSex + "")) {
            showToast("性别不能未空");
        } else {
            showLoading();
//            开始请求注册
            Log.e("Net", "response==username=1==" + username + "==" + password
                    + "==" + StatueSex + "==" + currentBirthday + "==" + province + "==" + city);

            OkHttpUtils.post()
                    .url(HttpConstants.Register)
                    .addParams("username", username)
                    .addParams("password", password)
                    .addParams("gender", StatueSex + "")
                    .addParams("birthday", currentBirthday)
                    .addParams("province", province)
                    .addParams("city", city)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            showToast("请求错误");
                            showError();

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Type type = new TypeToken<RegisterBean>() {
                            }.getType();
                            bean = mGson.fromJson(response, type);
//                            Log.e("Net", "response==username=1==" + bean.getStatus());
//                            Log.e("Net", "response==username=1==" + bean.getMsg());

                            Log.e("Net", "login==RegisterBean===" + response);

                            if ("0".equals(bean.getStatus())) {
                                //sp存token
                                showContent();
//                                        token = bean.getData().getToken();
                                userid = bean.getData().getUserid() + "";
//                                token = bean.getData().getToken() + "";


                                //TODO 在这个界面提交 验证码 绑定手机号码,并且关闭微信登入界面,Login界面,直接在Mainfragment,并且保持当前的userid

                                SharePreferenceUtil.put(RegisterActivity.this, Constants.USERID, userid + "");
//                                SharePreferenceUtil.put(RegisterActivity.this, Constants.Token, token + "");
                                SharePreferenceUtil.put(RegisterActivity.this, Constants.Device, "android");
                                SharePreferenceUtil.put(RegisterActivity.this, Constants.Is_Logined, true);
                                showToast("注册成功");
                                openActivity(MainActivity.class);
                                finish();
//                                        SharePreferenceUtil.put(getContext(), Constants.Info_Headimg, "1");
//                                        SharePreferenceUtil.put(getContext(), Constants.Info_Name, "1");　　
//                                        SharePreferenceUtil.put(getContext(), Constants.Info_Sign, "1");
//                                          初始化我的界面原始数据
//                                        EventBus.getDefault().post(new CloseFragmentEvent(bean, 1));
//                                        EventBus.getDefault().post(MineFragment.newInstance());
//                                        EventBus.getDefault().post(new RefreshEvent(new LoginBean(),""));


                            } else {
                                showContent();
                                showToast(bean.getMsg() + "");
                            }


                        }
                    });
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
