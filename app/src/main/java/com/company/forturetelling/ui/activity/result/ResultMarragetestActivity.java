package com.company.forturetelling.ui.activity.result;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.bus.SixTabResultEvent;
import com.company.forturetelling.bean.sixtab.EightNumBean03;
import com.company.forturetelling.bean.sixtab.MarrageResultBean;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.view.GridDivider;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Lovelin on 2019/12/24
 * <p>
 * Describe:
 */
public class ResultMarragetestActivity extends BaseActivity {
    @BindView(R.id.im_header_pic)
    ImageView imHeaderPic;
    @BindView(R.id.tv_info_sex)
    TextView tvInfoSex;
    @BindView(R.id.tv_info_zodiac)
    TextView tvInfoZodiac;
    @BindView(R.id.tv_info_bron)
    TextView tvInfoBron;
    @BindView(R.id.tv_info_brithday)
    TextView tvInfoBrithday;
    @BindView(R.id.tv_love01)
    TextView tvLove01;
    @BindView(R.id.iv_left_01)
    ImageView ivLeft01;
    @BindView(R.id.iv_left_02)
    ImageView ivLeft02;
    @BindView(R.id.iv_left_03)
    ImageView ivLeft03;
    @BindView(R.id.iv_right_01)
    ImageView ivRight01;
    @BindView(R.id.iv_right_02)
    ImageView ivRight02;
    @BindView(R.id.iv_right_03)
    ImageView ivRight03;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tv_details_01)
    TextView tvDetails01;
    @BindView(R.id.tv_love02)
    TextView tvLove02;
    @BindView(R.id.tv_details_02)
    TextView tvDetails02;
    @BindView(R.id.tv_love03)
    TextView tvLove03;
    @BindView(R.id.tv_details_03)
    TextView tvDetails03;
    @BindView(R.id.tv_love04)
    TextView tvLove04;
    @BindView(R.id.tv_details_04)
    TextView tvDetails04;
    @BindView(R.id.tv_love05)
    TextView tvLove05;
    @BindView(R.id.tv_details_05)
    TextView tvDetails05;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.relate_mine_all)
    RelativeLayout relateMineAll;
    private String oid;
    private String birthday;
    private String sex = "男";
    private String username;
    private ArrayList<String> mListData;
    private String born;
    private String sx;
    //    private EightNumBean03.DataBeanXX.RglmBean rglmBean;
    private MarrageResultBean.DataBeanXXX.RglmBean rglmBean;
    private SixTabResultEvent.CharacterBean characterBean;
    private SixTabResultEvent.LoveBean loveBean;
    private SixTabResultEvent.BusinessBean businessBean;
    private SixTabResultEvent.RichBean richBean;
    private SixTabResultEvent.HealthBean healthBean;
    private String title;

    @Override
    public int getContentViewId() {
        return R.layout.activity_result_text;
    }

    @Override
    public void init() {
        initView();
        responseListener();
    }

    private void responseListener() {
        imHeaderPic.setImageResource(R.mipmap.icon_gb_six_text);
        sendRequest(HttpConstants.MarriageTest03);


    }

    private void sendRequest(String url) {
        OkHttpUtils.post()
                .url(url)
//                .addParams("oid", oid + "")
                .addParams("oid", "201912251655227723834634")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("mImageUri", "=========sendNo===03======" + response);

                        Type type = new TypeToken<MarrageResultBean>() {
                        }.getType();
                        MarrageResultBean mBean03 = mGson.fromJson(response, type);
                        if ("0".equals(mBean03.getStatus())) {
                            getSetSomeData(mBean03);


                        }


                    }
                });
    }


    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        oid = getIntent().getStringExtra("oid");
        title = getIntent().getStringExtra("title");
        setTitleName(title + "");
        setPageStateView();
    }

    private void getSetSomeData(MarrageResultBean mBean03) {
        username = mBean03.getData().getData().getData().getUsername();
        birthday = mBean03.getData().getData().getData().getY() + "年" +
                mBean03.getData().getData().getData().getM() + "月" +
                mBean03.getData().getData().getData().getD() + "日  " +
                mBean03.getData().getData().getData().getH() + "时";

        born = mBean03.getData().getData().getData().getLDate();
        String sexNum = mBean03.getData().getData().getData().getGender();
        if ("0".equals(sexNum)) {
            sex = "男";
        } else {
            sex = "女";

        }
        sx = mBean03.getData().getReturnX().getUser().getSx();
        rglmBean = mBean03.getData().getRglm();
        //性格-爱情-事业-财运-健康
        characterBean = new SixTabResultEvent.CharacterBean();
        loveBean = new SixTabResultEvent.LoveBean();
        businessBean = new SixTabResultEvent.BusinessBean();
        richBean = new SixTabResultEvent.RichBean();
        healthBean = new SixTabResultEvent.HealthBean();

        characterBean.setTitle("性格分析");
        characterBean.setContent(rglmBean.getXgfx() + "");
        loveBean.setTitle("爱情分析");
        loveBean.setContent(rglmBean.getAqfx() + "");
        businessBean.setTitle("事业分析");
        businessBean.setContent(rglmBean.getSyfx() + "");
        richBean.setTitle("财运分析");
        richBean.setContent(rglmBean.getCyfx() + "");
        healthBean.setTitle("健康分析");
        healthBean.setContent(rglmBean.getJkfx() + "");

        //设置个人信息
        tvInfoSex.setText(sex + ""); //性别
        tvInfoZodiac.setText(sx + ""); //生肖
        tvInfoBron.setText(birthday + ""); //阳历
        tvInfoBrithday.setText(born + ""); //阴历


        //设置title和文本
        tvLove01.setText("" + characterBean.getTitle());
        tvLove02.setText("" + loveBean.getTitle());
        tvLove03.setText("" + businessBean.getTitle());
        tvLove04.setText("" + richBean.getTitle());
        tvLove05.setText("" + healthBean.getTitle());


        CharSequence result1 = Html.fromHtml(characterBean.getContent());
        CharSequence result2 = Html.fromHtml(loveBean.getContent());
        CharSequence result3 = Html.fromHtml(businessBean.getContent());
        CharSequence result4 = Html.fromHtml(richBean.getContent());
        CharSequence result5 = Html.fromHtml(healthBean.getContent());


        tvDetails01.setText("" + result1);
        tvDetails02.setText("" + result2);
        tvDetails03.setText("" + result3);
        tvDetails04.setText("" + result4);
        tvDetails05.setText("" + result5);


        showContent();


    }

    private ArrayList<String> getListData(MarrageResultBean mBean03) {

        List<String> baziList = mBean03.getData().getReturnX().getUser().getBazi();

        ArrayList<String> strList = new ArrayList<>();
        strList.add(" * ");
        strList.add("年柱");
        strList.add("月柱");
        strList.add("日柱");
        strList.add("时柱");
        strList.add("天干");
//        if (baziList.size()>=7){
//
//        }
        for (int i = 0; i < 4; i++) {
            if (baziList.get(i) != null) {
                strList.add(baziList.get(i) + "");
            } else {
                strList.add("/");
            }
        }
//        strList.add(baziList.get(0) + "");
//        strList.add(baziList.get(1));
//        strList.add(baziList.get(2));
//        strList.add(baziList.get(3));
        strList.add("地支");
        for (int i = 4; i < 8; i++) {
            if (baziList.get(i) != null) {
                strList.add(baziList.get(i) + "");
            } else {
                strList.add("/");
            }
        }
//        strList.add(baziList.get(4));
//        strList.add(baziList.get(5));
//        strList.add(baziList.get(6));
//        strList.add(baziList.get(7));
        Log.e("mImageUri", "=========sendNo===strList====" + strList.size());

        for (int i = 0; i < strList.size(); i++) {
            Log.e("mImageUri", "=========sendNo===strList====" + strList.get(i));

        }
        return (ArrayList<String>) strList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
