package com.company.forturetelling.ui.fragment.knowledge.persenter;

import com.company.forturetelling.bean.KnowledgeBean;

import java.util.List;

/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public interface KnowledgeView {
    void showLoadingView();

    void showContentView();

    void showEmptyView();

    void showErrorView();

    void showToast(String string);


    void refreshData(List<KnowledgeBean.DataBean.WencontentBean> newBean,String statue);
}
