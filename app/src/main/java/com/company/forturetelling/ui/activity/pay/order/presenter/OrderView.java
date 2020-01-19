package com.company.forturetelling.ui.activity.pay.order.presenter;

import com.company.forturetelling.bean.KnowledgeBean;
import com.company.forturetelling.bean.OrderBean;

import java.util.List;

/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public interface OrderView {
    void showLoadingView();

    void showContentView();

    void showEmptyView();

    void showErrorView();

    void showToast(String string);


    void refreshData(List<OrderBean.DataBean.ListBean> listBean, String statue);
}
