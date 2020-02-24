package com.company.forturetelling.ui.activity.sixfunction.synthesize.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.company.forturetelling.bean.sixtab.EightNumBean01;
import com.company.forturetelling.bean.sixtab.EightNumBean02;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.activity.information.LoginActivity;
import com.company.forturetelling.ui.activity.information.login.LoginAnimatorActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yun.common.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by Lovelin on 2019/12/23
 * <p>
 * Describe:
 */
public class SynthesizePresenter {
    private Context mContext;
    private SynthesizeView mView;
    private final Gson mGson;

    public SynthesizePresenter(Context mContext, SynthesizeView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mGson = new Gson();

    }

    public void sendNo1Request(String datadate, String username, String gender, String h) {
//
//        String userid = (String) SharePreferenceUtil.get(mContext, Constants.USERID, "");
//        if ("".equals(userid)) {
//            mView.showToast("请先登入~~  ");
//            Intent intent = new Intent(mContext, LoginAnimatorActivity.class);
//            mContext.startActivity(intent);
//        } else {
        mView.showLoadingView();
        OkHttpUtils.get()
                .url(HttpConstants.EightNumber01)
                .addParams("datadate", datadate)
                .addParams("username", username)
                .addParams("gender", gender)
                .addParams("h", h)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mView.showErrorView();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("mImageUri", "=========sendNo===fortune01======" + response);

                        Type type = new TypeToken<EightNumBean01>() {
                        }.getType();
                        EightNumBean01 mBean01 = mGson.fromJson(response, type);

                        if ("0".equals(mBean01.getStatus())) {
                            sendNo2Request(mBean01.getData());

                        }


                    }
                });

//        }

    }


    //第二步
    private void sendNo2Request(EightNumBean01.DataBean bean) {
        OkHttpUtils.post()
                .url(HttpConstants.Synthesize02)
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
                        Log.e("mImageUri", "=========sendNo===fortune02======" + response);
                        Type type = new TypeToken<EightNumBean02>() {
                        }.getType();
                        EightNumBean02 mBean02 = mGson.fromJson(response, type);

                        if ("0".equals(mBean02.getStatus())) {
                            mView.showContentView();
                            mView.updateFinish(mBean02.getData().getOid(), "综合分析");
                        }


                    }
                });

    }


}
