package com.company.forturetelling.ui.activity.pay.order.presenter;

import android.content.Context;
import android.util.Log;

import com.company.forturetelling.bean.KnowledgeBean;
import com.company.forturetelling.bean.OrderBean;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.fragment.knowledge.persenter.KnowledgeView;
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
public class OrderPresenter {

    private OrderView mView;
    private Context mContext;
    private final Gson gson;

    public OrderPresenter(OrderView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
        gson = new Gson();
    }

    public void sendRequest(int page, final String statue) {

        mView.showLoadingView();
        OkHttpUtils.get()
                .url(HttpConstants.Order)
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
                        Type type = new TypeToken<OrderBean>() {
                        }.getType();
                        OrderBean mBean = gson.fromJson(response, type);
                        if ("0".equals(mBean.getStatus())) {
                            List<OrderBean.DataBean.ListBean> listBean = mBean.getData().getList();
                            String size = listBean.size() + "";
                            if ("0".equals(size)) {
                                mView.showEmptyView();
                            } else {
                                mView.refreshData(listBean, statue,mBean.getData().getPagecount());
                            }
                        } else {
                            mView.showErrorView();
                        }

                    }
                });


    }


}
