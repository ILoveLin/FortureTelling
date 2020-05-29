//package com.company.forturetelling.ui.webview;//package com.company.forturetelling.ui.fragment;
//
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.company.forturetelling.R;
//import com.company.forturetelling.base.BaseFragment;
//import com.company.forturetelling.global.Constants;
//import com.company.forturetelling.global.HttpConstants;
//import com.just.agentweb.AgentWeb;
//import com.just.agentweb.DefaultWebClient;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.header.ClassicsHeader;
//import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//import com.yun.common.utils.SharePreferenceUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Lovelin on 2019/5/6
// * <p>
// * Describe:
// */
//public class LifeingFragment extends BaseFragment {
//    private String userid;
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.activity_market;
//    }
//
//    @Override
//    protected void init(ViewGroup rootView) {
//        initView(rootView);
//        responseListener();
//    }
//
//    private int refreshTime = 0;
//    private int times = 0;
//    final int itemLimit = 8;
//
//    private void responseListener() {
//
//
//    }
//
//    private void initView(ViewGroup rootView) {
//        userid = (String) SharePreferenceUtil.get(getActivity(), Constants.USERID, "");
//        setTitleBarVisibility(View.VISIBLE);
//        setTitleLeftBtnVisibility(View.GONE);
//        setTitleName("我的测试界面");
//    }
//    protected AgentWeb mAgentWeb;
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mAgentWeb = AgentWeb.with(getActivity())//这里需要把 Activity 、 和 Fragment  同时传入
//                .setAgentWebParent((ViewGroup) view, new LinearLayout.LayoutParams(-1, -1))// 设置 AgentWeb 的父控件 ， 这里的view 是 LinearLayout ， 那么需要传入 LinearLayout.LayoutParams
//                .useDefaultIndicator()// 使用默认进度条
////                .setReceivedTitleCallback(mCallback) //标题回调
////                .setSecurityType(AgentWeb.SecurityType.strict) //注意这里开启 strict 模式 ， 设备低于 4.2 情况下回把注入的 Js 全部清空掉 ， 这里推荐使用 onJsPrompt 通信
//                .createAgentWeb()//
//                .ready()//
//                .go(HttpConstants.ReallyLife + userid);
//
//
//
//
//    }
//}
