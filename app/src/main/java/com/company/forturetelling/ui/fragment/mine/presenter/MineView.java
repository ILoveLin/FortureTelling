package com.company.forturetelling.ui.fragment.mine.presenter;

/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public interface MineView {
    void showLoadingView();
    void showContentView();
    void showEmptyView();
    void showErrorView();
    void showToast(String string);
}
