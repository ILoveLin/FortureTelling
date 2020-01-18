package com.company.forturetelling.ui.fragment.fortune.presenter;

import android.widget.TextView;

/**
 * Created by Lovelin on 2019/4/27
 * <p>
 * Describe:
 */
public interface FortuneView {

    void showLoadingView();
    void showContentView();
    void showEmptyView();
    void showErrorView();
    void showToast(String string);

}
