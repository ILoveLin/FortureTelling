package com.company.forturetelling.ui.activity.sixfunction.namedetails.presenter;

/**
 * Created by Lovelin on 2019/12/23
 * <p>
 * Describe:
 */
public interface NameDetailsView {
    void showLoadingView();
    void showContentView();
    void showEmptyView();
    void showErrorView();
    void showToast(String string);

    void updateFinish(String oid, String title, String wechat_price, String ali_price);
}
