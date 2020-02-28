package com.company.forturetelling.ui.activity.information.presenter;

import android.content.Context;
import android.util.Log;

import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.information.InforSettingBean;
import com.company.forturetelling.bean.information.UpdateImg;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.view.SettingBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yun.common.utils.GlideUtils;
import com.yun.common.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.Call;


/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public class InforPresenter {

    private InforView mView;
    private Context mContext;
    private Gson gson;
    private String userid;
    private InforSettingBean inforSettingBean;

    public InforPresenter(InforView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
        userid = (String) SharePreferenceUtil.get(mContext, Constants.USERID, "");
        gson = new Gson();
    }

    public void sendTouxiangRequest(File file) {
        if (userid.equals("")) {
            return;
        } else {
            String name = file.getName();
            mView.showLoadingView();
            OkHttpUtils.post()
                    .url(HttpConstants.Image)
                    .addFile("img", name, file)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mView.showToast("回调错误404");
                            mView.showErrorView();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Type type = new TypeToken<UpdateImg>() {
                            }.getType();
                            UpdateImg updateImageBean = gson.fromJson(response, type);

                            if (updateImageBean.getStatus().equals("0")) {
                                sendHeadimg(updateImageBean.getData().getImg());

                            } else {
                                mView.showToast("更新失败");
                            }

                        }
                    });
        }

    }

    //ok之后更新头像
    private void sendHeadimg(final String imglist) {
        if (userid.equals("")) {
            return;
        } else {
            OkHttpUtils.post()
                    .url(HttpConstants.InformationSetting)
                    .addParams("userid", userid)
                    .addParams("headimg", imglist)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mView.showToast("回调错误404");
                            mView.showErrorView();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            EventBus.getDefault().post(new ExitEvent("登入"));
                            mView.showContentView();
                            Type type = new TypeToken<InforSettingBean>() {
                            }.getType();
                            InforSettingBean inforSettingBean = gson.fromJson(response, type);
                            if (inforSettingBean.getStatus().equals("0")) {
                                SharePreferenceUtil.put(mContext, Constants.Info_Headimg, imglist);
                                GlideUtils.LogadCustomCircleImage(mContext, HttpConstants.Common + imglist, mView.getTouxiang());

                            } else {
                                mView.showToast("更新失败");
                            }
                        }
                    });
        }
    }


    //更改年月日
    public void sendDataRequest(SettingBar barSettingBirthday, String birthday) {
        if (userid.equals("")) {
            return;
        } else {
            if ("".equals(birthday)){
               mView.showToast("出生年月日不能为空");
                return;
            }
            mView.showLoadingView();
            OkHttpUtils.post()
                    .url(HttpConstants.InformationSetting)
                    .addParams("userid", userid)
                    .addParams("birthday",birthday )
//                    .addParams("birthday", "")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mView.showToast("回调错误404");
                            mView.showErrorView();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mView.showContentView();
                            Type type = new TypeToken<InforSettingBean>() {
                            }.getType();
                            InforSettingBean inforSettingBean = gson.fromJson(response, type);

                            if (inforSettingBean.getStatus().equals("0")) {
                                barSettingBirthday.setRightText(birthday + "");
                                EventBus.getDefault().post(new ExitEvent("登入"));

                            } else {
                                mView.showToast("更新失败");

                            }
                        }
                    });
        }


    }

    public void sendSexRequest(String sex, int i, SettingBar barSettingSex) {  //0  女    ----1    男
        if (userid.equals("")) {
            return;
        } else {
            mView.showLoadingView();
            OkHttpUtils.post()
                    .url(HttpConstants.InformationSetting)
                    .addParams("userid", userid)
                    .addParams("gender", i + "")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mView.showErrorView();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mView.showContentView();
                            Type type = new TypeToken<InforSettingBean>() {
                            }.getType();
                            InforSettingBean inforSettingBean = gson.fromJson(response, type);

                            if (inforSettingBean.getStatus().equals("0")) {
                                mView.showToast("修改成功");
                                barSettingSex.setRightText("" + sex);
                            } else {
                                mView.showToast("更新失败");

                            }
                        }
                    });
        }


    }


    public void sendAddressRequest(String province, String city, SettingBar bar_setting_address) {
        if (userid.equals("")) {
            return;
        } else {
            mView.showLoadingView();
            OkHttpUtils.post()
                    .url(HttpConstants.InformationSetting)
                    .addParams("userid", userid)
                    .addParams("province", province + "")
                    .addParams("city", city + "")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mView.showErrorView();


                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mView.showContentView();

                            Type type = new TypeToken<InforSettingBean>() {
                            }.getType();
                            InforSettingBean inforSettingBean = gson.fromJson(response, type);

                            if (inforSettingBean.getStatus().equals("0")) {
                                mView.showToast("修改成功");
                                bar_setting_address.setRightText(province + "省 " + city + "市");
                            }
                        }
                    });
        }


    }
}
