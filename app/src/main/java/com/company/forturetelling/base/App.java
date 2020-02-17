package com.company.forturetelling.base;

import android.app.Application;

import com.company.forturetelling.global.Constants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by Lovelin on 2019/4/27
 * <p>
 * Describe:
 */
public class App extends Application {

    private static App app;
    public static String AppFilePath = "app";
    public static IWXAPI msgApi;

    public App() {
        app = this;
    }

    public static synchronized App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 做一些sdk等等的init
         */
        //微信支付
        msgApi = WXAPIFactory.createWXAPI(getApplicationContext(), null);
        // 将该app注册到微信   AppID:步骤二申请到的AppID
        // msgApi.registerApp("wx4b9e09ae470dc4ce");
        msgApi.registerApp(Constants.APP_ID_WECHART);


        //Okhttp请求头
        //请求工具的拦截器  ,可以设置证书,设置可访问所有的https网站,参考https://www.jianshu.com/p/64cc92c52650
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))                  //内存存储cookie
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new MyInterceptor(this))                      //拦截器,可以添加header 一些信息
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .hostnameVerifier(new HostnameVerifier() {//允许访问https网站,并忽略证书
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

        OkHttpUtils.initClient(okHttpClientBuilder.build());





















    }
}










