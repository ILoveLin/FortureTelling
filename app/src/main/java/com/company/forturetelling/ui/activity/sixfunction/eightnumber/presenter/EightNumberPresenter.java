package com.company.forturetelling.ui.activity.sixfunction.eightnumber.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.bus.SixTabResultEvent;
import com.company.forturetelling.bean.sixtab.EightNumBean01;
import com.company.forturetelling.bean.sixtab.EightNumBean02;
import com.company.forturetelling.bean.sixtab.EightNumBean03;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.activity.sixfunction.getname.GetNameActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Lovelin on 2019/12/23
 * <p>
 * Describe:
 */
public class EightNumberPresenter {
    private Context mContext;
    private EightNumberView mView;
    private final Gson mGson;

    public EightNumberPresenter(Context mContext, EightNumberView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mGson = new Gson();

    }

    public void sendNo1Request(String datadate, String username, String gender, String h) {
        mView.showLoadingView();
        OkHttpUtils.get()
                .url(HttpConstants.EightNumber01)
                .addParams("datadate", datadate)
                .addParams("username", username)
                .addParams("gender", gender)
                .addParams("h", h)
//                .addParams("datadate", "2012-12-4")
//                .addParams("username", "童毛")
//                .addParams("gender", "0")
//                .addParams("h", "15")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mView.showErrorView();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("mImageUri", "=========sendNo===01======" + response);

                        Type type = new TypeToken<EightNumBean01>() {
                        }.getType();
                        EightNumBean01 mBean01 = mGson.fromJson(response, type);

                        if ("0".equals(mBean01.getStatus())) {
                            sendNo2Request(mBean01.getData());

                        }


                    }
                });


    }


    //第二步
    private void sendNo2Request(EightNumBean01.DataBean bean) {
        OkHttpUtils.post()
                .url(HttpConstants.EightNumber02)
                .addParams("y", bean.getY())
                .addParams("m", bean.getM())
                .addParams("d", bean.getD())
                .addParams("cY", bean.getCY() + "")
                .addParams("cM", bean.getCM() + "")
                .addParams("cD", bean.getCD() + "")
                .addParams("cH", bean.getCH() + "")
                .addParams("term1", bean.getTerm1())
                .addParams("term2", bean.getTerm2())
                .addParams("start_term", bean.getStart_term() + "")
                .addParams("end_term", bean.getEnd_term() + "")
                .addParams("start_term1", bean.getStart_term1() + "")
                .addParams("end_term1", bean.getEnd_term1() + "")
                .addParams("lDate", bean.getLDate())
                .addParams("username", bean.getUsername())
                .addParams("datadate", bean.getDatadate())
                .addParams("gender", bean.getGender())
                .addParams("h", bean.getH())
                .addParams("i", bean.getI() + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mView.showErrorView();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("mImageUri", "=========sendNo===02======" + response);

                        Type type = new TypeToken<EightNumBean02>() {
                        }.getType();
                        EightNumBean02 mBean02 = mGson.fromJson(response, type);


                        if ("0".equals(mBean02.getStatus())) {
                            //TODO  获取到订单号 跳转到支付界面
                            Bundle bundle = new Bundle();
                            bundle.putString("oid",mBean02.getData().getOid());
                            bundle.putString("title","八字精批");
                            mView.showContentView();
                            mView.updateFinish(mBean02.getData().getOid(), "八字精批");

                        }


                    }
                });

    }

    private void sendNo3Request(String oid) {
        Log.e("mImageUri", "=========sendNo===03====参数=========" + oid);

        OkHttpUtils.post()
                .url(HttpConstants.EightNumber03)
                .addParams("oid", oid + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mView.showErrorView();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("mImageUri", "=========sendNo===03======" + response);

                        Type type = new TypeToken<EightNumBean03>() {
                        }.getType();
                        EightNumBean03 mBean03 = mGson.fromJson(response, type);

                        if ("0".equals(mBean03.getStatus())) {
                            mView.showContentView();
                            ArrayList<String> listData = getListData(mBean03);
                            String username = mBean03.getData().getData().getData().getUsername();
                            String birthday = mBean03.getData().getData().getData().getY() + "年" +
                                    mBean03.getData().getData().getData().getM() + "月" +
                                    mBean03.getData().getData().getData().getD() + "日  " +
                                    mBean03.getData().getData().getData().getH() + "时";

                            String born = mBean03.getData().getData().getData().getLDate();
                            String sx = mBean03.getData().getSxgx().getSx();
                            EightNumBean03.DataBeanXX.RglmBean rglmBean = mBean03.getData().getRglm();
                            Log.e("mImageUri", "=========sendNo===rglmBean==性格分析====" + rglmBean.getXgfx());
                            Log.e("mImageUri", "=========sendNo===rglmBean==爱情分析====" + rglmBean.getAqfx());
                            Log.e("mImageUri", "=========sendNo===rglmBean==事业分析====" + rglmBean.getSyfx());
                            Log.e("mImageUri", "=========sendNo===rglmBean==财运分析====" + rglmBean.getCyfx());

                            //性格-爱情-事业-财运-健康
                            SixTabResultEvent.CharacterBean characterBean = new SixTabResultEvent.CharacterBean();
                            SixTabResultEvent.LoveBean loveBean = new SixTabResultEvent.LoveBean();
                            SixTabResultEvent.BusinessBean businessBean = new SixTabResultEvent.BusinessBean();
                            SixTabResultEvent.RichBean richBean = new SixTabResultEvent.RichBean();
                            SixTabResultEvent.HealthBean healthBean = new SixTabResultEvent.HealthBean();

                            characterBean.setTitle("性格分析");
                            characterBean.setContent(rglmBean.getXgfx() + "");
                            loveBean.setTitle("爱情分析");
                            loveBean.setContent(rglmBean.getAqfx() + "");
                            businessBean.setTitle("事业分析");
                            businessBean.setContent(rglmBean.getSyfx() + "");
                            richBean.setTitle("财运分析");
                            richBean.setContent(rglmBean.getCyfx() + "");
                            healthBean.setTitle("健康分析");
                            healthBean.setContent(rglmBean.getJkfx() + "");
                            SixTabResultEvent sixTabResultEventData = new SixTabResultEvent(username, birthday, born, sx, listData,
                                    characterBean, loveBean, businessBean, richBean, healthBean);

                        }


                    }
                });
    }


    private ArrayList<String> getListData(EightNumBean03 mBean03) {

        List<String> baziList = mBean03.getData().getCookies().getBazi();

        ArrayList<String> strList = new ArrayList<>();
        strList.add("八字");
        strList.add("年柱");
        strList.add("月柱");
        strList.add("日柱");
        strList.add("时柱");
        strList.add("天干");
//        if (baziList.size()>=7){
//
//        }
        for (int i = 0; i < 4; i++) {
            if (baziList.get(i) != null) {
                strList.add(baziList.get(i) + "");
            } else {
                strList.add("/");
            }
        }
//        strList.add(baziList.get(0) + "");
//        strList.add(baziList.get(1));
//        strList.add(baziList.get(2));
//        strList.add(baziList.get(3));
        strList.add("地支");
        for (int i = 4; i < 8; i++) {
            if (baziList.get(i) != null) {
                strList.add(baziList.get(i) + "");
            } else {
                strList.add("/");
            }
        }
//        strList.add(baziList.get(4));
//        strList.add(baziList.get(5));
//        strList.add(baziList.get(6));
//        strList.add(baziList.get(7));

        return (ArrayList<String>) strList;
    }


}
