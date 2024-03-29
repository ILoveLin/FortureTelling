package com.company.forturetelling.ui.activity.pay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.bus.ExitEvent;
import com.company.forturetelling.bean.bus.RefreshPayTypeEvent;
import com.company.forturetelling.bean.paybean.AliPayInforBean;
import com.company.forturetelling.bean.paybean.AliPayOrderBean;
import com.company.forturetelling.bean.paybean.AliPaySecondResultBean;
import com.company.forturetelling.bean.paybean.PayBean;
import com.company.forturetelling.bean.paybean.PayResult;
import com.company.forturetelling.bean.paybean.WXParamsBean;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.ui.activity.pay.order.EventOrderMessage;
import com.company.forturetelling.ui.activity.result.ResultCommonActivity;
import com.company.forturetelling.ui.activity.sixfunction.getname.GetNameResultActivity;
import com.company.forturetelling.view.dialog.PayBottomDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yun.common.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Lovelin on 2020/1/9
 * <p>
 * Describe:支付选择界面
 */
public class SelectPayActivity extends BaseActivity {

    @BindView(R.id.shop_name)
    TextView shop_name;
    @BindView(R.id.ll_pay_weichat)
    LinearLayout ll_pay_weichat;
    @BindView(R.id.ll_pay_ali)
    LinearLayout ll_pay_ali;
    private String title;
    private String oid;
    private TextView tv_price;
    private View dialogView;
    private PayBean payBean;
    private String notify_url;
    private Gson gson;
    private String total_fee;
    private String text_surname;
    private String text_name;
    private String text_all_name;
    private TextView tv_confirm;
    private TextView tv_cancel;
    private String price_wechat;
    private String price_ali;

    @Override
    public int getContentViewId() {
        return R.layout.activity_pay_select;
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
        title = getIntent().getStringExtra("title");
        oid = getIntent().getStringExtra("oid");


        price_wechat = getIntent().getStringExtra("price_wechar") + "";
        price_ali = getIntent().getStringExtra("price_ali") + "";
        total_fee = getIntent().getStringExtra("total_fee");
        text_surname = getIntent().getStringExtra("text_surname");
        text_name = getIntent().getStringExtra("text_name");
        text_all_name = getIntent().getStringExtra("text_all_name");
        Log.e("mImageUri", "=========sendNo===02===wechat_price===" + price_wechat+"======ali_price====="+price_ali);
        currentPrice = price_wechat + "";
        //微信支付的选择
        mWeichatSelect = findViewById(R.id.iv_buy_weichat_select);
        tv_price = findViewById(R.id.tv_price);
        //支付宝的选择
        mAliPaySelect = findViewById(R.id.iv_buy_alipay_select);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_price = findViewById(R.id.tv_price);
        shop_name.setText("商品名称:" + title);
        tv_price.setText("￥" + price_ali);
        gson = new Gson();

    }

    private void responseListener() {

        ll_pay_weichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("微信支付");
                if (PAY_TYPE_WECHAT != payType) {
                    mWeichatSelect.setSelected(true);
                    mAliPaySelect.setSelected(false);
                    mWeichatSelect.setImageDrawable(getResources().getDrawable(R.mipmap.paytype_select));
                    mAliPaySelect.setImageDrawable(getResources().getDrawable(R.mipmap.paytype_unselect));
                    payType = PAY_TYPE_WECHAT;

                }

            }
        });
        ll_pay_ali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("支付宝支付");
                if (PAY_TYPE_ALIPAY != payType) {
                    mWeichatSelect.setSelected(false);
                    mAliPaySelect.setSelected(true);
                    mWeichatSelect.setImageDrawable(getResources().getDrawable(R.mipmap.paytype_unselect));
                    mAliPaySelect.setImageDrawable(getResources().getDrawable(R.mipmap.paytype_select));
                    payType = PAY_TYPE_ALIPAY;
                }
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("确认支付");
                //重置
                if (0 == payType) {
                    GetParamsFromBackground(payType);
                } else {
                    GetParamsFromBackground(payType);

                }
//                payType = PAY_TYPE_WECHAT;
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("取消支付");
                refreshPayTypeStatue(5);
                //重置
                finish();
            }
        });


    }

    private ImageView mWeichatSelect;
    private ImageView mAliPaySelect;
    private static final int PAY_TYPE_WECHAT = 0;  //微信支付,默认支付方式
    private static final int PAY_TYPE_ALIPAY = 1;  //支付宝支付
    private int payType = 0;
    private String orderInfo;
    private String currentPrice = "6800";

    public void GetParamsFromBackground(int payType) {
        switch (payType) {
            //金额 （分） 比如 1 = 1分 10 = 1毛 100=1元
            case 0:         //微信
                if ("取名".equals(title)) {
                    currentPrice = price_wechat + "";
//                    currentPrice = "1";

                } else {
//                    currentPrice = "1";
                    currentPrice = price_wechat + "";
                }
                OkHttpUtils.post()
                        .url(HttpConstants.WXPay)
                        .addParams("body", title)
                        .addParams("out_trade_no", oid)
                        .addParams("total_fee", currentPrice)
//                        .addParams("total_fee", "1")
                        .addParams("text_surname", text_surname)   //姓
                        .addParams("text_name", text_name)//名
                        .addParams("text_all_name", text_all_name)//姓名
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("PayUtils----WX", "PayUtils===Exception");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("PayUtils----WX", "response====PayUtils===" + response);
                                if (response != "" && response != null) {
                                    Type type = new TypeToken<WXParamsBean>() {
                                    }.getType();
                                    WXParamsBean wxParamsBean = gson.fromJson(response, type);
                                    payWXBean.setAppid(wxParamsBean.getData().getAppid());
                                    payWXBean.setPartnerid(wxParamsBean.getData().getPartnerid());
                                    payWXBean.setPrepayid(wxParamsBean.getData().getPrepayid());
                                    payWXBean.setPackagee(wxParamsBean.getData().getPackageX());
                                    payWXBean.setNoncestr(wxParamsBean.getData().getNoncestr());
                                    payWXBean.setTimestamp(wxParamsBean.getData().getTimestamp());
                                    payWXBean.setSign(wxParamsBean.getData().getSign());
                                    if (isWeixinAvilible(SelectPayActivity.this)) {
                                        toWXPay();
                                    } else {
                                        showToast("请先安装微信");
                                    }
                                } else {
                                    showToast("微信返回参数未空");
                                }
                            }
                        });
                break;
            case 1:         //支付宝
                //价格 比如 0.01 = 1分 0.1 = 1毛 1 = 1元
                if ("取名".equals(title)) {
                    currentPrice = price_ali;
//                    currentPrice = "0.01";

                } else {
                    currentPrice = price_ali;
//                    currentPrice = "0.01";
                }
                OkHttpUtils.get()
                        .url(HttpConstants.ALIPay)
                        .addParams("body", title)
                        .addParams("out_trade_no", oid)
//                        .addParams("total_fee", "5")
                        .addParams("total_fee", currentPrice)
//                        .addParams("total_fee", "0.01")
                        .addParams("text_surname", text_surname)   //姓
                        .addParams("text_name", text_name)//名
                        .addParams("text_all_name", text_all_name)//姓名
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("PayUtils----ALI", "Exception===ALIPay====");

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("PayUtils----ALI", "response==ALIPay=====" + response);
                                Type type = new TypeToken<AliPayOrderBean>() {
                                }.getType();
                                AliPayOrderBean mAliPayOrderBean = gson.fromJson(response, type);
                                orderInfo = mAliPayOrderBean.getData().getSing();
                                notify_url = mAliPayOrderBean.getData().getNotify_url();
                                Log.e("PayUtils----ALI", "response==ALIPay=====" + orderInfo);
                                //返还给我一个orderInfo
                                if (orderInfo != "" && orderInfo != null) {
                                    toAliPay(orderInfo);

                                } else {
                                    showToast("支付宝orderInfo未空");
                                }
                            }
                        });
                break;

        }

    }

    private IWXAPI iwxapi; //微信支付api

    /**
     * 调起微信支付的方法
     * 需要后台返回7个参数
     **/
    public static PayBean payWXBean = new PayBean();

    private void toWXPay() {
        iwxapi = WXAPIFactory.createWXAPI(this, null); //初始化微信api
        iwxapi.registerApp(Constants.APP_ID_WECHART); //注册appid
        //一定注意要放在子线程
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信的对象
                //这几个参数的值，正是上面我们说的统一下单接口后返回来的字段，我们对应填上去即可
                request.appId = payWXBean.getAppid();
                request.partnerId = payWXBean.getPartnerid();
                request.prepayId = payWXBean.getPrepayid();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = payWXBean.getNoncestr();
                request.timeStamp = payWXBean.getTimestamp();
                request.sign = payWXBean.getSign();
                Log.e("PayUtils----WX", "请求参数===" + "appId=" + request.appId + ",partnerId="
                        + request.partnerId + ",prepayId=" + request.prepayId + ",packageValue=" +
                        request.packageValue + ",nonceStr=" + request.nonceStr + ",timeStamp=" +
                        request.timeStamp + ",sign=" + request.sign);

                SharePreferenceUtil.put(getApplicationContext(), Constants.WECHAT_SECOND_ORDERID, oid);
                iwxapi.sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    //判断是否安装 微信
    public boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ExitEvent(RefreshPayTypeEvent messageEvent) {
        if (messageEvent.getMessage().equals("微信")) {
            refreshPayTypeStatue(PAY_TYPE_WECHAT);

        }
    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private static final int SDK_PAY_CANCEL = -2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.e("PayUtils----ALI", "response==ALIPay===resultInfo=1111111=" + resultInfo);
                    Log.e("PayUtils----ALI", "response==ALIPay===resultStatus==" + resultStatus);
//                    9000 订单支付成功
//                    8000 正在处理中
//                    4000 订单支付失败
//                    6001 用户中途取消
//                    6002 网络连接出错
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {    //6001  支付宝---取消支付
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Type type = new TypeToken<AliPayInforBean>() {
                        }.getType();
                        AliPayInforBean mAliPayInforBean = gson.fromJson(resultInfo, type);
                        String out_trade_no = mAliPayInforBean.getAlipay_trade_app_pay_response().getOut_trade_no();
                        checkoutSecondAliPay(out_trade_no);

                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        refreshPayTypeStatue(PAY_TYPE_ALIPAY);
                    } else if (TextUtils.equals(resultStatus, "6002")) {
                        refreshPayTypeStatue(PAY_TYPE_ALIPAY);
                        showToast("暂无网络链接");
                    }
                    break;
                }

                case SDK_AUTH_FLAG: {

                    Log.e("PayUtils----ALI", "response==ALIPay===resultInfo=1111111=" + SDK_AUTH_FLAG);

                    break;
                }
                case SDK_PAY_CANCEL: {
                    Log.e("PayUtils----ALI", "response==ALIPay===resultInfo=1111111=" + SDK_PAY_CANCEL);

                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    //更改取消支付后的支付方式显示bug
    private void refreshPayTypeStatue(int Type) {
        payType = Type;
        if (payType == PAY_TYPE_WECHAT) {
            mWeichatSelect.setSelected(true);
            mAliPaySelect.setSelected(false);
            payType = PAY_TYPE_WECHAT;
            mWeichatSelect.setImageDrawable(getResources().getDrawable(R.mipmap.paytype_select));
            mAliPaySelect.setImageDrawable(getResources().getDrawable(R.mipmap.paytype_unselect));
        } else if (payType == PAY_TYPE_ALIPAY) {
            payType = PAY_TYPE_ALIPAY;
            mWeichatSelect.setSelected(false);
            mAliPaySelect.setSelected(true);
            mWeichatSelect.setImageDrawable(getResources().getDrawable(R.mipmap.paytype_unselect));
            mAliPaySelect.setImageDrawable(getResources().getDrawable(R.mipmap.paytype_select));
        }
    }

    //阿里支付二次校验
    private void checkoutSecondAliPay(String out_trade_no) {
        OkHttpUtils.get()
                .url(HttpConstants.ALIPay_Second)
                .addParams("out_trade_no", out_trade_no)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("PayUtils----ALI-Second", "response==ALIPay===Second==" + response);
                        Log.e("PayUtils----ALI-Second", "response==ALIPay=oid==Second==" + oid);
                        Log.e("PayUtils----ALI-Second", "response==ALIPay=title==Second==" + title);
                        Type type = new TypeToken<AliPaySecondResultBean>() {
                        }.getType();
                        AliPaySecondResultBean mAliPaySecondResultBean = gson.fromJson(response, type);
                        //status  = 1 成功   =0 失败  -1没有获取到回调
                        String status = mAliPaySecondResultBean.getData().getStatus() + "";
//                        showContent();
                        if ("0".equals(status)) {  //成功
                            Bundle bundle = new Bundle();
                            bundle.putString("oid", oid);
                            bundle.putString("title", title);
                            if ("取名".equals(title)) {
                                openActivity(GetNameResultActivity.class, bundle);
                            } else {
                                openActivity(ResultCommonActivity.class, bundle);

                            }

                        } else {    //失败
                            showToast("支付宝订单二次校验失败");
                        }


                    }
                });
    }

    /**
     * 调起阿里Pay支付的方法
     * 需要后台返回orderinfo参数
     **/
    private void toAliPay(String orderInfo) {
        //下面的orderInfo就是咱自己的服务端返回的订单信息，里面除了订单ID等，还有签名等安全信息
        //使用方式基本按照支付宝的DEMO里面就行了

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(SelectPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("PayUtils----ALI", "PayUtils----result====toAliPay=" + result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
