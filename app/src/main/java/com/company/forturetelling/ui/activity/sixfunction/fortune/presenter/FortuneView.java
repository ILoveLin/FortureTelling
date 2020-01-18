package com.company.forturetelling.ui.activity.sixfunction.fortune.presenter;

/**
 * Created by Lovelin on 2019/12/23
 * <p>
 * Describe:
 */
public interface FortuneView {
    void showLoadingView();
    void showContentView();
    void showEmptyView();
    void showErrorView();
    void showToast(String string);
    void updateFinish( String oid,String title);

}
