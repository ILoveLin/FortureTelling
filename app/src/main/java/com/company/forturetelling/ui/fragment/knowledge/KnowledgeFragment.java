package com.company.forturetelling.ui.fragment.knowledge;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseFragment;
import com.company.forturetelling.bean.KnowledgeBean;
import com.company.forturetelling.ui.activity.NewsActivity;
import com.company.forturetelling.ui.fragment.knowledge.adapter.KnowledgeAdapter;
import com.company.forturetelling.ui.fragment.knowledge.persenter.KnowledgePresenter;
import com.company.forturetelling.ui.fragment.knowledge.persenter.KnowledgeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lovelin on 2019/4/27
 * <p>
 * Describe:命理-知识
 */
public class KnowledgeFragment extends BaseFragment implements KnowledgeView {

    @BindView(R.id.fake_status_bar)
    View mFakeStatusBar;
    @BindView(R.id.knowRecycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;
    private ArrayList<KnowledgeBean.DataBean.WencontentBean> mDataList = new ArrayList<>();
    private KnowledgeAdapter mAdapter;
    private int page = 1;
    private KnowledgePresenter mPresenter;


    @Override
    public int getContentViewId() {
        return R.layout.fragment_03;
    }

    @Override
    public void init(ViewGroup rootView) {
        initView();
        responseListener();
    }

    private void responseListener() {
        mPresenter.sendRequest(1, "refresh");

        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.sendRequest(page, "refresh");
            }
        });

        mSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.sendRequest(page, "loadMore");
            }

        });

        mAdapter.setClickCallBack(new KnowledgeAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(KnowledgeBean.DataBean.WencontentBean bean) {
                Bundle bundle = new Bundle();
                bundle.putString("url", bean.getHome());
                bundle.putString("title", "新闻界面");
                openActivity(NewsActivity.class, bundle);
            }
        });
    }

    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.GONE);
        setTitleName("知识");
        setPageStateView();
        mPresenter = new KnowledgePresenter(this, getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new KnowledgeAdapter(mDataList,getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void refreshData(List<KnowledgeBean.DataBean.WencontentBean> newBeanList, String statue) {
        switch (statue) {
            case "refresh":
                mDataList.clear();
                if (newBeanList != null && newBeanList.size() != 0) {
                    mDataList.addAll(newBeanList);
                }
                mSmartRefresh.finishRefresh();
                mAdapter.notifyDataSetChanged();

                break;
            case "loadMore":
                if (null != newBeanList) {
                    mDataList.addAll(newBeanList);
                    mSmartRefresh.setNoMoreData(false);
                } else {
                    mSmartRefresh.setNoMoreData(true);
                }
                mSmartRefresh.finishLoadMore();
                mAdapter.notifyDataSetChanged();
                break;

        }


    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void showContentView() {
        showContent();
    }

    @Override
    public void showEmptyView() {
        showEmpty();
    }

    @Override
    public void showErrorView() {
        showError();
    }


}
