package com.company.forturetelling.ui.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.yun.common.utils.LogUtils;
import com.yun.common.utils.SharePreferenceUtil;

import butterknife.BindView;

/**
 * Created by Lovelin on 2020/4/2
 * <p>
 * Describe:商场
 */
public class MarketActivity extends BaseActivity {


    @BindView(R.id.linear_all)
    RelativeLayout linear_all;
    protected AgentWeb mAgentWeb;
    private String userid;

    @Override
    public int getContentViewId() {
        return R.layout.activity_market;
    }

    @Override
    public void init() {
        initView();
    }

    private void initView() {
        userid = (String) SharePreferenceUtil.get(this, Constants.USERID, "");
//        Boolean isLogin = (Boolean) SharePreferenceUtil.get(this, com.company.forturetelling.global.Constants.Is_Logined, false);
//        String Perfect = (String) SharePreferenceUtil.get(this, com.company.forturetelling.global.Constants.WX_Perfect, "false");
//        linear_all_login.setVisibility(View.INVISIBLE);
//        linear_all.setVisibility(View.VISIBLE);
//        if (isLogin) {   //登入了
//            if ("true".equals(Perfect)) {  //已经完善.
//                linear_all_login.setVisibility(View.INVISIBLE);
//                linear_all.setVisibility(View.VISIBLE);
//            }
//        } else {//未登录
//            linear_all_login.setVisibility(View.VISIBLE);
//            linear_all.setVisibility(View.INVISIBLE);
//
//        }
        setTitleName("商场");
        setTitleBarVisibility(View.GONE);
        setTitleLeftBtnVisibility(View.GONE);
        setPageStateView();

        LogUtils.e("typeUrl================userid==商城===" + userid);

        gotoMarket(HttpConstants.Market + userid);
        LogUtils.e("typeUrl=====================" + HttpConstants.Market + userid);

    }


    public void gotoMarket(String typeUrl) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(linear_all, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(typeUrl);
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            LogUtils.e("typeUrl================userid==商城===" + request.getUrl().toString());
            if (request.getUrl().toString().contains("http://testbazi.zgszfy.com//konbai/")) {
                finish();
                return false;
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            if (mTitleTextView != null) {
//                mTitleTextView.setText(title);
//            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    protected void onPause() {
//        mAgentWeb.getWebLifeCycle().onPause();
//        super.onPause();
//
//    }
//
//    @Override
//    protected void onResume() {
//        mAgentWeb.getWebLifeCycle().onResume();
//        super.onResume();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("Info", "onResult:" + requestCode + " onResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
