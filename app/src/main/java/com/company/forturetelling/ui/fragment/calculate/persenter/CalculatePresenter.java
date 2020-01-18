package com.company.forturetelling.ui.fragment.calculate.persenter;

import android.content.Context;

import com.company.forturetelling.bean.fortune.FortunerDetalisBean;

/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public class CalculatePresenter {
    private Context mContext;
    private CalculateView mView;
    private final FortunerDetalisBean mBean;

    public CalculatePresenter(Context mContext, CalculateView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mBean = new FortunerDetalisBean();
    }
}
