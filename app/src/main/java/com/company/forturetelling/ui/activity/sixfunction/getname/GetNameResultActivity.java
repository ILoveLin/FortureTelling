package com.company.forturetelling.ui.activity.sixfunction.getname;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.calculate.NameDetailsBean;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.activity.sixfunction.getname.adapter.NameDetailsAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Lovelin on 2019/12/18
 * <p>
 * Describe:取名单独的结果界面
 */
public class GetNameResultActivity extends BaseActivity {
    @BindView(R.id.tv_namedetails_name)
    TextView tvNamedetailsName;
    @BindView(R.id.tv_namedetails_date)
    TextView tvNamedetailsDate;
    @BindView(R.id.tv_namedetails_date02)
    TextView tv_namedetails_date02;
    @BindView(R.id.tv_namedetails_bazi)
    TextView tv_namedetails_bazi;
    @BindView(R.id.tv_name_empty)
    TextView tv_name_empty;
    @BindView(R.id.nameRecycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;
    private ArrayList<NameDetailsBean.DataBean.FullnameBean> mDataList = new ArrayList<>();
    private NameDetailsAdapter mAdapter;
    private int page = 1;
    private Gson gson;
    private NameDetailsBean mBean;
    private String orderNo;
    private List<NameDetailsBean.DataBean.FullnameBean> newBeanList;
    private boolean isNongliNull = false;
    private boolean isBaziNull = false;
    private String bazisubstring;
    private String nonglinamesubstring;
    private String nongliname = "";
    private String bazi = "";
    private Boolean isFristIn = true;
    public String addName;


    @Override
    public int getContentViewId() {
        return R.layout.activity_name_details;
    }

    @Override
    public void init() {
        initView();
        responseListener();
    }

    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        setTitleName("姓名详情");
        setPageStateView();
        gson = new Gson();
        isNongliNull = false;
        isBaziNull = false;
        tv_name_empty.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);


        orderNo = getIntent().getStringExtra("orderNo");
        orderNo = getIntent().getStringExtra("oid");
        Log.e("getName=====", "Exception===orderNo===="+orderNo);
        Log.e("getName=====", "Exception===addName===="+addName);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(GetNameResultActivity.this, 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new NameDetailsAdapter(mDataList, this,addName);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void responseListener() {
        sendRequest(page, "refresh");
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                sendRequest(page, "refresh");
            }
        });

        mSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                sendRequest(page, "loadMore");
            }

        });

        mAdapter.setClickCallBack(new NameDetailsAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(NameDetailsBean.DataBean.FullnameBean bean) {
//                Bundle bundle = new Bundle();
//                bundle.putString("url", bean.getHome());
//                openActivity(NewsActivity.class, bundle);
            }
        });
    }

    private void sendRequest(int page, String statue) {
        showLoading();
        OkHttpUtils.get()
                .url(HttpConstants.NameDetails)
                .addParams("orderno", orderNo)  //
//                .addParams("page", page + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("mImageUri", "=========file==订单界面=orderNo======" + response);
                        Log.e("mImageUri", "=========file==订单界面=orderNo======" + orderNo);

                        showContent();
                        Type type = new TypeToken<NameDetailsBean>() {
                        }.getType();
                        mBean = gson.fromJson(response, type);
                        mAdapter.setName(mBean.getData().getInfo().getName());
                        if ("0".equals(mBean.getStatus())) {
                            List<NameDetailsBean.DataBean.FullnameBean> fullname = mBean.getData().getFullname();

                            if (fullname!=null && mBean.getData().getFullname().size() != 0) {
                                newBeanList = mBean.getData().getFullname();
                                refreshData(newBeanList, statue);
                            } else {
                                mSmartRefresh.finishRefresh();
                                tv_name_empty.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.INVISIBLE);
                            }


                        } else {
                            showError();
                        }

                    }
                });


    }


    private void refreshUserInformation() {
        if (isFristIn) {
            isFristIn = false;
            NameDetailsBean.DataBean.InfoBean info = mBean.getData().getInfo();
            tvNamedetailsName.setText("姓名:  " + info.getName());
            tvNamedetailsDate.setText("阳历:  " + info.getBirthday());
            List<String> nongli = mBean.getData().getNongli();
            List<String> bazi1 = mBean.getData().getBazi();
            for (int i = 0; i < nongli.size(); i++) {
                if (nongli.get(i) != null) {
                    if (i == nongli.size() - 1) {
                        nongliname += nongli.get(i);
                    } else {
                        nongliname += nongli.get(i) + "-";
                    }
                } else {
                    isNongliNull = true;

                    nongliname += "";
                    nonglinamesubstring = nongliname.substring(0, nongliname.length() - 1);
                }
            }
            for (int i = 0; i < bazi1.size(); i++) {
                if (bazi1.get(i) != null) {
                    if (i == bazi1.size() - 1) {
                        bazi += bazi1.get(i);
                    } else {
                        bazi += bazi1.get(i) + "-";
                    }
                } else {
                    bazi += "";
                    isBaziNull = true;
                    bazisubstring = bazi.substring(0, bazi.length() - 1);
                    Log.e("mImageUri", "=========file===substring======" + bazisubstring);
                }
            }
            if (isNongliNull) {
                tv_namedetails_date02.setText("农历:  " + nonglinamesubstring);
            } else {
                tv_namedetails_date02.setText("农历:  " + nongliname);
            }
            if (isBaziNull) {
                tv_namedetails_bazi.setText("八字:  " + bazisubstring);
            } else {
                tv_namedetails_bazi.setText("八字:  " + bazi);
            }
        }

    }

    private void refreshData(List<NameDetailsBean.DataBean.FullnameBean> newBeanList, String statue) {
        refreshUserInformation();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


}
