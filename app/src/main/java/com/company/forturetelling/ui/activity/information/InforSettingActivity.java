package com.company.forturetelling.ui.activity.information;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.CityBean;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.information.InforBean;
import com.company.forturetelling.bean.information.InforSettingBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.activity.information.presenter.InforPresenter;
import com.company.forturetelling.ui.activity.information.presenter.InforView;
import com.company.forturetelling.view.CircleImageView;
import com.company.forturetelling.view.SettingBar;
import com.company.forturetelling.view.calendar.ChineseCalendar;
import com.company.forturetelling.view.calendar.DialogGLC;
import com.company.forturetelling.view.dialog.BaseDialog;
import com.company.forturetelling.view.dialog.InputDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yun.common.utils.GetJsonDataUtil;
import com.yun.common.utils.GlideUtils;
import com.yun.common.utils.SharePreferenceUtil;
import com.yun.common.utils.popupwindow.PhotoPopupWindow;
import com.yun.common.utils.popupwindow.PopupWindowTwoButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Lovelin on 2019/12/6
 * <p>
 * Describe:个人你信息界面设置
 */
public class InforSettingActivity extends BaseActivity implements View.OnClickListener, InforView {

    @BindView(R.id.tv_current_name)
    TextView tvCurrentName;
    @BindView(R.id.current_pic)
    CircleImageView currentPic;
    @BindView(R.id.linear_current_info)
    LinearLayout linearCurrentInfo;
    @BindView(R.id.bar_setting_birthday)
    SettingBar barSettingBirthday;
    @BindView(R.id.bar_setting_maname)
    SettingBar bar_setting_maname;
    @BindView(R.id.bar_setting_sex)
    SettingBar barSettingSex;
    @BindView(R.id.bar_setting_account_phone)
    SettingBar bar_setting_account_phone;    //我的账号
    @BindView(R.id.bar_setting_change_password)
    SettingBar barSettingChangePassword;
    @BindView(R.id.bar_setting_address)
    SettingBar bar_setting_address;
    @BindView(R.id.linear_exit)
    TextView linearExit;
    @BindView(R.id.linear_setting_all)
    LinearLayout linear_setting_all;
    private PhotoPopupWindow mPhotoPopupWindow;
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_BIG_IMAGE_CUTTING = 3;
    private static final String IMAGE_FILE_NAME = "icon.jpg";
    private Uri mImageUri;
    private Thread thread;
    private InforPresenter mPresenter;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ExitEvent(ExitEvent messageEvent) {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_inforsetting;
    }

    @Override
    public void init() {
        initView();
        responseListener();
    }

    private void responseListener() {
        String userid = (String) SharePreferenceUtil.get(InforSettingActivity.this, Constants.USERID, "");
        if (userid.equals("")) {
            showToast("用户ID不能未空");
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
                            Log.e("mImageUri", "上传图片测试====用户信息===response==response==" + response);
                            Type type = new TypeToken<InforBean>() {
                            }.getType();
                            Gson gson = new Gson();
                            InforBean inforBean = gson.fromJson(response, type);
                            if (inforBean.getStatus().equals("0")) {
                                InforBean.DataBean.InfoBean info = inforBean.getData().getInfo();
                                tvCurrentName.setText("" + info.getName());
                                GlideUtils.LogadCustomCircleImage(InforSettingActivity.this, HttpConstants.Common + info.getHeadimg(), currentPic);
                                barSettingBirthday.setRightText("" + info.getBirthday());
                                bar_setting_address.setRightText("" + info.getProvince() + "省 " + info.getCity() + "市");
                                bar_setting_account_phone.setRightText("" + info.getUsername());
                                int gender = info.getGender();
                                if (0 == gender) {  //	性别 （0女 1男 ）
                                    barSettingSex.setRightText("女");
                                } else {
                                    barSettingSex.setRightText("男");

                                }
                            } else {
                                showToast("请求失败");

                            }
                        }
                    });
        }


    }


    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        setTitleName("我的资料");
        setPageStateView();
        mPresenter = new InforPresenter(this, this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

    }


    @OnClick({R.id.bar_setting_address, R.id.linear_current_info, R.id.bar_setting_birthday,
            R.id.bar_setting_sex, R.id.bar_setting_maname, R.id.bar_setting_change_password, R.id.linear_exit,
    })
    public void multipleOnclick(View view) {
        switch (view.getId()) {
            case R.id.linear_current_info:     //选择头像
                selectPhoto();
                break;
            case R.id.bar_setting_birthday:  //我的生日
                showInDialog();
                break;
            case R.id.bar_setting_maname:  //我的昵称
                showInputMyNameDialog();
                break;
            case R.id.bar_setting_address:      //我的地址
                ShowPickerView();
                break;
            case R.id.bar_setting_sex:      //我的性别
                showUpPop(linear_setting_all);
                break;
            case R.id.bar_setting_change_password:     //修改密码
                openActivity(PasswordActivity.class);
                break;
            case R.id.linear_exit:     //退出登入
                showPop(linear_setting_all);
                break;

        }

    }

    private void showInputMyNameDialog() {

        new InputDialog.Builder(this)
                // 标题可以不用填写
                .setTitle("修改昵称")
                // 内容可以不用填写
                .setContent("")
                // 提示可以不用填写
                .setHint("请输入昵称")
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setListener(new InputDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseDialog dialog, String content) {
                        showToast("确定了：" + content);

                        sendInputNameRequest(content);


                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        showToast("取消了");
                    }
                })
                .show();


    }

    //修改昵称

    private void sendInputNameRequest(String name) {
        String userid = (String) SharePreferenceUtil.get(this, Constants.USERID, "");
        Log.e("mImageUri", "上传图片测试====02===response==userid==" + userid);

        if (userid.equals("")) {
            return;
        } else {

            showLoadingView();
            OkHttpUtils.post()
                    .url(HttpConstants.InformationSetting)
                    .addParams("name", name)
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
                            Log.e("mImageUri", "上传图片测试====02===response====" + response);
                            showContentView();
                            Gson gson = new Gson();
                            Type type = new TypeToken<InforSettingBean>() {
                            }.getType();
                            InforSettingBean inforSettingBean = gson.fromJson(response, type);

                            if (inforSettingBean.getStatus().equals("0")) {
                                bar_setting_maname.setRightText(inforSettingBean.getData().getName() + "");
                                tvCurrentName.setText("" + inforSettingBean.getData().getName());
                                EventBus.getDefault().post(new ExitEvent("登入"));

                            } else {
                                showToast("更新失败");

                            }
                        }
                    });
        }


//        EventBus.getDefault().post(new ExitEvent("退出"));

    }

    private ArrayList<CityBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private String province;
    private String city;

    private void ShowPickerView() {// 弹出地址选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(InforSettingActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
//                        + options3Items.get(options1).get(options2).get(options3);
                mPresenter.sendAddressRequest(province, city, bar_setting_address);
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

    private void showPop(LinearLayout linear_setting_all) {

        final PopupWindowTwoButton twoButton = new PopupWindowTwoButton((Activity) InforSettingActivity.this);
        twoButton.getTv_content().setText("是否确认退出?");
        twoButton.getTv_ok().setText("确定");
        twoButton.getTv_cancel().setText("取消");
        twoButton.getTv_ok().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定推出---请求数据
                SharePreferenceUtil.put(InforSettingActivity.this, Constants.Is_Logined, false);
                SharePreferenceUtil.put(InforSettingActivity.this, Constants.Logined, false);
                SharePreferenceUtil.put(InforSettingActivity.this, Constants.USERID, "");
                EventBus.getDefault().post(new ExitEvent("退出"));
                twoButton.dismiss();
                finish();

            }
        });
        twoButton.getTv_cancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoButton.dismiss();

            }
        });
        twoButton.showPopupWindow(linear_setting_all, Gravity.CENTER);
    }


    private Button mButtonShowDialog;
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
                    String birthday = calendar.get(Calendar.YEAR) + "-"
                            + (calendar.get(Calendar.MONTH) + 1) + "-"
                            + calendar.get(Calendar.DAY_OF_MONTH);
                    int intyear = calendar.get(Calendar.YEAR);
                    if (intyear > 2019) {
                        showToast("年份超出范围");
                        barSettingBirthday.setRightText("");
                        return;
                    }
                    mPresenter.sendDataRequest(barSettingBirthday, birthday);

                    barSettingBirthday.setRightText("");
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
    public void showUpPop(LinearLayout info_setting_linear) {
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
            case R.id.sex_man: //男
                icon_sex_man.setVisibility(View.VISIBLE);
                icon_sex_wonman.setVisibility(View.GONE);
                popupWindow.dismiss();
                mPresenter.sendSexRequest("男", 1, barSettingSex);
                break;
            case R.id.sex_wonman: //女
                icon_sex_man.setVisibility(View.GONE);
                icon_sex_wonman.setVisibility(View.VISIBLE);
                popupWindow.dismiss();
                mPresenter.sendSexRequest("女", 0, barSettingSex);
                break;
            case R.id.cancel:
                popupWindow.dismiss();
                break;

        }
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String CityData = new GetJsonDataUtil().getJson(InforSettingActivity.this, "city.json");//获取assets目录下的json文件数据

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

    public void setBGAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    //选择照片还是拍照
    public void selectPhoto() {

        //打开相册
        //检查权限(6.0以上做权限判断)
        mPhotoPopupWindow = new PhotoPopupWindow(InforSettingActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 权限申请
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(InforSettingActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    // 如果权限已经申请过，直接进行图片选择
                    mPhotoPopupWindow.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // 判断系统中是否有处理该 Intent 的 Activity
                    if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_GET);
                    } else {
                        Toast.makeText(InforSettingActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 权限申请
                if (ContextCompat.checkSelfPermission(InforSettingActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(InforSettingActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(InforSettingActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
                } else {
                    // 权限已经申请，直接拍照
                    mPhotoPopupWindow.dismiss();
                    imageCapture();
                }
            }
        });
        View rootView = LayoutInflater.from(InforSettingActivity.this)
                .inflate(R.layout.activity_inforsetting, null);
        mPhotoPopupWindow.showAtLocation(rootView,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


    }

    /**
     * 判断系统及拍照
     */
    private void imageCapture() {
        Intent intent;
        Uri pictureUri;
        File pictureFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
        // 判断当前系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//记得填写自己的包名  别无脑的copy
//            pictureUri = FileProvider.getUriForFile(getActivity(),
//                    "你的包名.fileProvider", pictureFile);
            pictureUri = FileProvider.getUriForFile(InforSettingActivity.this,
                    "com.company.forturetelling.fileProvider", pictureFile);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(pictureFile);
        }
        // 去拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    /**
     * 大图模式切割图片
     * 直接创建一个文件将切割后的图片写入
     */
    public void startBigPhotoZoom(File inputFile) {
        // 创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            mImageUri = imageUri; // 将 uri 传出，方便设置到视图中
        }

        // 开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(InforSettingActivity.this, inputFile), "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    public void startBigPhotoZoom(Uri uri) {
        // 创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            mImageUri = imageUri; // 将 uri 传出，方便设置到视图中
        }

        // 开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    public Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
                // 大图切割
                case REQUEST_BIG_IMAGE_CUTTING:
//                    Bitmap bitmap = BitmapFactory.decodeFile(mImageUri.getEncodedPath());
                    Log.e("mImageUri", "=========mImageUri=========" + mImageUri);
                    File file = new File(mImageUri.getPath());
                    Log.e("mImageUri", "=========file===getPath======" + file.getPath());
                    Log.e("mImageUri", "=========file===getAbsolutePath======" + file.getAbsolutePath());
                    Log.e("mImageUri", "=========file===getName======" + file.getName());


                    //todo  到时候这里去请求(先上传-再获取url数据) 获取头像
                    mPresenter.sendTouxiangRequest(file);
                    //
//                    infoSettingPic.setImageBitmap(bitmap);

                    break;
                // 相册选取
                case REQUEST_IMAGE_GET:
                    try {
                        // startSmallPhotoZoom(data.getData());
                        startBigPhotoZoom(data.getData());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    // startSmallPhotoZoom(Uri.fromFile(temp));
                    startBigPhotoZoom(temp);
            }
        }
    }

    /**
     * 处理权限回调结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // 判断系统中是否有处理该 Intent 的 Activity
                    if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_GET);
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到图片查看器", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
            case 300:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    imageCapture();
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
        }
    }


    /**
     * 小图模式中，保存图片后，设置到视图中
     * 将图片保存设置到视图中
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(storage + "/smallIcon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                    }
                }
                File file = new File(dirFile, System.currentTimeMillis() + ".jpg");

                // 保存图片
                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 在视图中显示图片
            currentPic.setImageBitmap(photo);


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
    public CircleImageView getTouxiang() {
        return currentPic;
    }
}
