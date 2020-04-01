package com.company.forturetelling.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.global.Constants;
import com.company.forturetelling.ui.MainActivity;
import com.company.forturetelling.ui.activity.information.LoginActivity;
import com.company.forturetelling.utils.NetworkUtil;
import com.yun.common.utils.SharePreferenceUtil;
import com.yun.common.utils.StatusBarUtil;
import com.yun.common.utils.StatusBarUtils;
import com.yun.common.utils.popupwindow.PopupWindowTwoButton;
import com.yun.common.viewpagerlib.bean.PageBean;
import com.yun.common.viewpagerlib.callback.PageHelperListener;
import com.yun.common.viewpagerlib.indicator.TransIndicator;
import com.yun.common.viewpagerlib.view.GlideViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by Lovelin on 2019/3/27
 * <p>
 * Describe:新手引导页
 */
public class GuideActivity extends BaseActivity {
    private String userUrl = "http://testbazi.zgszfy.com/api/bs/yc.html";  //用户协议
    private String privacyUrl = "http://jdyapi.jxsccm.com//api/index/yc.html";  //隐私条款
    private static final Integer[] RES = {R.mipmap.guide11, R.mipmap.guide22, R.mipmap.guide33,
            R.mipmap.guide44};
    private Boolean isLogined;
    private RelativeLayout relative_guide;

    @Override
    public int getContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    public void init() {
        initView();
        initData();
        showUserDialog();

    }

    public void initView() {

        StatusBarUtils.setColor(this, getResources().getColor(R.color.red), 0);
        StatusBarUtil.darkMode(this, true);  //设置了状态栏文字的颜色
        setTitleBarVisibility(View.GONE);
        GlideViewPager viewPager = (GlideViewPager) findViewById(R.id.splase_viewpager);
        TransIndicator linearLayout = (TransIndicator) findViewById(R.id.splase_bottom_layout);
        //点击跳转的按钮
        Button button = (Button) findViewById(R.id.splase_start_btn);
        relative_guide = (RelativeLayout) findViewById(R.id.relative_guide);
        isLogined = (Boolean) SharePreferenceUtil.get(this, Constants.Is_Logined, false);


        //先把本地的图片 id 装进 list 容器中
        List<Integer> imagesList = new ArrayList<>();
        for (int i = 0; i < RES.length; i++) {
            imagesList.add(RES[i]);

        }
        //配置pagerbean，这里主要是为了viewpager的指示器的作用，注意记得写上泛型
        PageBean bean = new PageBean.Builder<Integer>()
                .setDataObjects(imagesList)
                .setIndicator(linearLayout)
                .setOpenView(button)
                .builder();

        // 把数据添加到 viewpager中，并把view提供出来，这样除了方便调试，也不会出现一个view，多个
        // parent的问题，这里在轮播图比较明显
        viewPager.setPageListener(bean, R.layout.guide_image_layout, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object data) {
                ImageView imageView = view.findViewById(R.id.icon);
                imageView.setImageResource((Integer) data);
            }
        });


        //点击实现跳转功能
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (!isLogined) {  //登入成功
//                    Intent intent = new Intent();
//                    intent.setClass(GuideActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
                SharePreferenceUtil.put(GuideActivity.this, Constants.SP_IS_FIRST_IN, false);   //是否第一次登录
                SharePreferenceUtil.put(GuideActivity.this, Constants.Is_Logined, false);
                SharePreferenceUtil.put(GuideActivity.this, Constants.WX_Perfect, "false");
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
//                    finish();
//                }


            }
        });


    }

    /**
     * 第一次进入必须弹出  用户协议
     */
    public void showUserDialog() {
        //第一个角标是   58//                                                                                                                                                 //58  ---- 66 68--75
        SpannableString textSpanned1 = new SpannableString("欢迎您使用巨思八字!我们非常重视您的个人信息和隐私保护,我们会竭尽全力保护您的隐私,为了帮助您了解相关权利,请认真阅读<<用户协议>>及<<隐私条款>>,当您点击同意时,可以开始使用我们的服务。");
        //58  ---- 66 68--75
        //设置颜色
        textSpanned1.setSpan(new ForegroundColorSpan(Color.BLUE),
                59, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpanned1.setSpan(new ForegroundColorSpan(Color.BLUE),
                68, 76, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置下划线
        textSpanned1.setSpan(new UnderlineSpan(),
                59, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpanned1.setSpan(new UnderlineSpan(),
                68, 76, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置Hello World前三个字符有点击事件
//        SpannableStringBuilder textSpanned4 = new SpannableStringBuilder("Hello World");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GuideActivity.this, "Hello clickableSpan1", Toast.LENGTH_SHORT).show();
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GuideActivity.this, "Hello clickableSpan2", Toast.LENGTH_SHORT).show();
            }
        };
        textSpanned1.setSpan(clickableSpan,
                59, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpanned1.setSpan(clickableSpan2,
                68, 76, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//注意：此时必须加这一句，不然点击事件不会生效
//        text4.setMovementMethod(LinkMovementMethod.getInstance());
//        text4.setText(textSpanned4);


        final PopupWindowTwoButton twoButton = new PopupWindowTwoButton(this);
        twoButton.getTv_title().setVisibility(View.VISIBLE);
        twoButton.getTv_title().setText("温馨提示");
        twoButton.getTv_content().setMovementMethod(LinkMovementMethod.getInstance());
        twoButton.getTv_content().setText(textSpanned1);
//        twoButton.getTv_content().setText("欢迎您使用巨思八字!我们非常重视您的个人信息和隐私保护,我们会竭尽全力保护您的隐私,为了帮助您了解相关权利,请认真阅读<<用户协议>>及<<隐私条款>>,当您点击同意时,可以开始使用我们的服务。");
        twoButton.getTv_content().setText(textSpanned1);
        twoButton.getTv_ok().setText("同意");
        twoButton.getTv_cancel().setText("不同意并退出");
        twoButton.getTv_cancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceUtil.put(GuideActivity.this, Constants.SP_IS_FIRST_IN, true);
                finish();
                twoButton.dismiss();
            }
        });
        twoButton.getTv_ok().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                twoButton.dismiss();
            }
        });
        //修正后代码
        relative_guide.post(new Runnable() {
            @Override
            public void run() {
                twoButton.showAtLocation(relative_guide, Gravity.CENTER, 0, 0);

            }
        });

//        twoButton.showPopupWindow(relative_guide, Gravity.CENTER);
    }


    public void initData() {

    }


}
