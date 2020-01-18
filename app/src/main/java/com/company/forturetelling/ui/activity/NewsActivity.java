package com.company.forturetelling.ui.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.view.WebLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

/**
 * Created by Lovelin on 2019/12/10
 * <p>
 * Describe:新闻界面
 */
public class NewsActivity extends BaseActivity {

    private LinearLayout mLinearLayout;
    private WebLayout webLayout;

    @Override
    public int getContentViewId() {
        return R.layout.activity_news;
    }

    @Override
    public void init() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        setPageStateView();
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        setTitleName(title + "");

        mLinearLayout = (LinearLayout) this.findViewById(R.id.container_web);
        webLayout = new WebLayout(this);
        if ("关于我们".equals(title)) {
            loadUpCurrentWebView(url);

        } else {
            loadUpCurrentWebView(HttpConstants.Common + url);

        }

    }

    protected AgentWeb mAgentWeb;

    private void loadUpCurrentWebView(String url) {

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(webLayout)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(url);


    }


    private com.just.agentweb.WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private com.just.agentweb.WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    };

    private AlertDialog mAlertDialog;

    private void showDialog() {

        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("您确定要关闭该页面吗?")
                    .setNegativeButton("再逛逛", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                        }
                    })//
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                            NewsActivity.this.finish();
                        }
                    }).create();
        }
        mAlertDialog.show();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
