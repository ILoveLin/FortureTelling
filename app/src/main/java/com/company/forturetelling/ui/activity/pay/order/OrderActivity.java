package com.company.forturetelling.ui.activity.pay.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.OrderBean;
import com.company.forturetelling.ui.activity.pay.order.adapter.OrderAdapter;
import com.company.forturetelling.ui.activity.pay.order.presenter.OrderPresenter;
import com.company.forturetelling.ui.activity.pay.order.presenter.OrderView;
import com.company.forturetelling.ui.activity.result.ResultCommonActivity;
import com.company.forturetelling.ui.activity.sixfunction.getname.GetNameResultActivity;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lovelin on 2020/1/19
 * <p>
 * Describe:我的订单
 */
public class OrderActivity extends BaseActivity implements OrderView {
    @BindView(R.id.orderRecycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;
    private ArrayList<OrderBean.DataBean.ListBean> mDataList = new ArrayList<>();
    private OrderAdapter mAdapter;
    private int page = 1;
    private int pagecount;
    private OrderPresenter mPresenter;


    @Override
    public int getContentViewId() {
        return R.layout.activity_order;
    }

    @Override
    public void init() {
        initView();
        responseListener();
    }

    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        setTitleName("订单界面");
        setPageStateView();
        mPresenter = new OrderPresenter(this, OrderActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderAdapter(mDataList, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
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
                if (page <= pagecount) {
                    mSmartRefresh.finishLoadMore();
                    mAdapter.notifyDataSetChanged();
                } else {
                    page++;
                    mPresenter.sendRequest(page, "loadMore");
                }
            }

        });

        mAdapter.setClickCallBack(new OrderAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(OrderBean.DataBean.ListBean bean, int position) {
                String order_no = bean.getOrder_no();
                String title = bean.getTitle();
                Bundle bundle = new Bundle();
                Log.e("PayUtils----ALI-Second", "response==ALIPay=order==Second==" + order_no);
                Log.e("PayUtils----ALI-Second", "response==ALIPay=order==Second==" + title);


                bundle.putString("oid", order_no);
                bundle.putString("position", position + "");
                bundle.putString("title", title);

                //添加三个参数
                bundle.putString("text_surname", "");  //姓
                bundle.putString("text_name", "");     //名
                bundle.putString("text_all_name", ""); //姓名
                if ("取名".equals(title)) {
                    openActivity(GetNameResultActivity.class, bundle);
                } else {
                    openActivity(ResultCommonActivity.class, bundle);
                }

            }
        });
    }

    @Override
    public void refreshData(List<OrderBean.DataBean.ListBean> newBeanList, String statue, String pagecount) {
        switch (statue) {
            case "refresh":
                mDataList.clear();
                this.pagecount = Integer.valueOf(pagecount);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
