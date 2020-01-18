package com.company.forturetelling.ui.activity.sixfunction.marriagetest.presenter;

/**
 * Created by Lovelin on 2019/12/23
 * <p>
 * Describe:
 */
public interface MarriageTexstView {
    void showLoadingView();
    void showContentView();
    void showEmptyView();
    void showErrorView();
    void showToast(String string);

    void updateFinish(String oid, String title);
}
