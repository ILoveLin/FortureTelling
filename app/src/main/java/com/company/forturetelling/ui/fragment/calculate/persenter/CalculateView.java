package com.company.forturetelling.ui.fragment.calculate.persenter;

/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public interface CalculateView {
    void showLoadingView();
    void showContentView();
    void showEmptyView();
    void showErrorView();
    void showToast(String string);

}
