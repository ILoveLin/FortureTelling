package com.company.forturetelling.ui.fragment.knowledge.persenter;

import android.content.Context;
import android.util.Log;

import com.company.forturetelling.bean.KnowledgeBean;
import com.company.forturetelling.global.HttpConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;


/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public class KnowledgePresenter {

    private KnowledgeView mView;
    private Context mContext;
    private final Gson gson;

    public KnowledgePresenter(KnowledgeView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
        gson = new Gson();
    }

    public void sendRequest(int page, final String statue) {

        mView.showLoadingView();
        OkHttpUtils.get()
                .url(HttpConstants.Knowledge)
                .addParams("page", page + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mView.showErrorView();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("mImageUri", "=========file===新闻======" + response);

                        mView.showContentView();
                        Type type = new TypeToken<KnowledgeBean>() {
                        }.getType();
                        KnowledgeBean mBean = gson.fromJson(response, type);
                        if ("0".equals(mBean.getStatus())) {
                            List<KnowledgeBean.DataBean.WencontentBean> newBean = mBean.getData().getWencontent();
                            mView.refreshData(newBean, statue);
                        } else {
                            mView.showErrorView();
                        }

                    }
                });


    }


}
