package com.company.forturetelling.ui.fragment.calculate;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseFragment;
import com.company.forturetelling.bean.LoopBean;
import com.company.forturetelling.ui.activity.sixfunction.synthesize.SynthesizeActivity;
import com.company.forturetelling.ui.activity.sixfunction.namedetails.NameDetailsActivity;
import com.company.forturetelling.ui.activity.sixfunction.eightnumber.EightNumberActivity;
import com.company.forturetelling.ui.activity.sixfunction.fortune.FortuneActivity;
import com.company.forturetelling.ui.activity.sixfunction.getname.GetNameActivity;
import com.company.forturetelling.ui.activity.sixfunction.marriagetest.MarriageTestActivity;
import com.company.forturetelling.ui.fragment.calculate.adapter.CalculateAdapter;
import com.company.forturetelling.ui.fragment.calculate.persenter.CalculatePresenter;
import com.company.forturetelling.ui.fragment.calculate.persenter.CalculateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yun.common.viewpagerlib.bean.PageBean;
import com.yun.common.viewpagerlib.callback.PageHelperListener;
import com.yun.common.viewpagerlib.indicator.TransIndicator;
import com.yun.common.viewpagerlib.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Lovelin on 2019/4/27
 * <p>
 * Describe:命理-测算
 */
public class CalculateFragment extends BaseFragment implements CalculateView {
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.loop_viewpager)
    BannerViewPager loop_viewpager;
    @BindView(R.id.bottom_indicator)
    TransIndicator bottom_indicator;
    @BindView(R.id.fake_status_bar)
    View mFakeStatusBar;
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private static final Integer[] RES = {R.mipmap.banner_01, R.mipmap.gb_function_getmarriage_01, R.mipmap.banner_03,
            R.mipmap.banner_04, R.mipmap.banner_05, R.mipmap.banner_06};
    private List<LoopBean> loopBeensList;
    private ArrayList<String> mDataList;
    private CalculateAdapter mAdapter;
    private CalculatePresenter mPresenter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_02;
    }

    @Override
    public void init(ViewGroup rootView) {
        mPresenter = new CalculatePresenter(getActivity(), this);
        initView();
        responseListener();
    }


    private void responseListener() {

        mAdapter.setClickCallBack(new CalculateAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(String title, int position) {
//                showToast("第----" + position + "---个被点击" + "title====" + title);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putInt("position", position);
                switch (title) {
                    case "八字精批":  //0
                        openActivity(EightNumberActivity.class, bundle);
                        break;
                    case "姓名详批":  //1
                        openActivity(NameDetailsActivity.class, bundle);
                        break;
                    case "婚姻测算":  //2
                        openActivity(MarriageTestActivity.class, bundle);
                        break;
                    case "取名":      //3
                        openActivity(GetNameActivity.class, bundle);
                        break;
                    case "今年运势":  //4
                        openActivity(FortuneActivity.class, bundle);
                        break;
                    case "综合分析":  //5
                        openActivity(SynthesizeActivity.class, bundle);
                        break;


                }
            }
        });
    }


    private void initData() {
        /**
         * 轮播图的
         */
        loopBeensList = new ArrayList<>();
        for (int i = 0; i < RES.length; i++) {
            LoopBean bean = new LoopBean();
            bean.url = RES[i];
            loopBeensList.add(bean);

        }

        //配置pagerbean，这里主要是为了viewpager的指示器的作用，注意记得写上泛型
        PageBean bean = new PageBean.Builder<LoopBean>()
                .setDataObjects(loopBeensList)
                .setIndicator(bottom_indicator)
                .builder();

        // 把数据添加到 viewpager中，并把view提供出来，这样除了方便调试，也不会出现一个view，多个
        // parent的问题，这里在轮播图比较明显

        loop_viewpager.setPageListener(bean, R.layout.image_layout, new PageHelperListener<LoopBean>() {
            @Override
            public void getItemView(View view, LoopBean data) {
                ImageView imageView = view.findViewById(R.id.icon);
                int url = data.url;

                Glide.with(getActivity())
                        .load(url)
                        .apply(new RequestOptions().placeholder(R.mipmap.image_loop1))
                        .into(imageView);


            }
        });


//        //配置pagerbean，这里主要是为了viewpager的指示器的作用，注意记得写上泛型
//        PageBean bean = new PageBean.Builder<LoopPicBean.DataBean.ImgBean>()
//                .setDataObjects(imgUrlList)
//                .setIndicator(bottom_scale_layout)
//                .builder();
//
//        // 把数据添加到 viewpager中，并把view提供出来，这样除了方便调试，也不会出现一个view，多个
//        // parent的问题，这里在轮播图比较明显
//        loop_viewpager.setPageListener(bean, R.layout.image_layout, new PageHelperListener<LoopPicBean.DataBean.ImgBean>() {
//            @Override
//            public void getItemView(View view, LoopPicBean.DataBean.ImgBean data) {
//                ImageView imageView = view.findViewById(R.id.icon);
//                String url = data.getUrl();
//                Glide.with(getActivity())
//                        .load(url)
//                        .error(R.mipmap.image_loop1)
//                        .placeholder(R.mipmap.image_loop1)   //占位图
//                        .into(imageView);
//            }
//
//        });
    }

    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.GONE);
        setTitleName("命理测算");
        setPageStateView();


        //设置2行 3列
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mDataList = new ArrayList<>();
        add10Nums();
        mAdapter = new CalculateAdapter(mDataList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        initData();
    }

    public void add10Nums() {
        ArrayList<String> refreshFirstList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            refreshFirstList.add(i + "----条数据");
        }
        mDataList.addAll(refreshFirstList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
