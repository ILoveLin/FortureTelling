package com.company.forturetelling.ui.activity.sixfunction.eightnumber.presenter;

/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public interface EightNumberView {
    void showLoadingView();
    void showContentView();
    void showEmptyView();
    void showErrorView();
    void showToast(String string);

    void updateFinish(String oid, String title, String wechat_price, String ali_price);

}
