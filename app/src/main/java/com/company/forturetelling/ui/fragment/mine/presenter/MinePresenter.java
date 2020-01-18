package com.company.forturetelling.ui.fragment.mine.presenter;
import android.content.Context;


/**
 * Created by Lovelin on 2019/12/2
 * <p>
 * Describe:
 */
public class MinePresenter {

    private MineView mView;
    private Context mContext;

    public MinePresenter(MineView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }
}
