package com.company.forturetelling.ui.activity.information.presenter;

import android.widget.TextView;

import com.company.forturetelling.view.CircleImageView;

/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public interface InforView {
    void showLoadingView();
    void showContentView();
    void showEmptyView();
    void showErrorView();
    void showToast(String string);

    CircleImageView getTouxiang();
}
