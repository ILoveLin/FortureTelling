package com.company.forturetelling.ui.activity.result;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.forturetelling.R;
import com.company.forturetelling.base.BaseActivity;
import com.company.forturetelling.bean.bus.SixTabResultEvent;
import com.company.forturetelling.bean.sixtab.EightNumBean03;
import com.company.forturetelling.bean.sixtab.MarriageTestBean;
import com.company.forturetelling.bean.sixtab.NameDetailsBean;
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
 * Describe:5个统一的界面
 */
public class ResultCommonActivity extends BaseActivity {
    @BindView(R.id.tv_info_sex)
    TextView tvInfoSex;
    @BindView(R.id.tv_info_zodiac)
    TextView tvInfoZodiac;
    @BindView(R.id.tv_info_bron)
    TextView tvInfoBron;
    @BindView(R.id.tv_info_brithday)
    TextView tvInfoBrithday;
    @BindView(R.id.six_head_recyclerView)
    RecyclerView sixHeadRecyclerView;
    @BindView(R.id.tv_love01)
    TextView tvLove01;
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
    @BindView(R.id.im_header_pic)
    ImageView im_header_pic;
    @BindView(R.id.linear_recycle)
    LinearLayout linear_recycle;
    private String oid;
    private String birthday;
    private String sex = "男";
    private String username;
    private ArrayList<String> mListData;
    private String born;
    private String sx;
    private EightNumBean03.DataBeanXX.RglmBean rglmBean;
    private SixTabResultEvent.CharacterBean characterBean;
    private SixTabResultEvent.LoveBean loveBean;
    private SixTabResultEvent.BusinessBean businessBean;
    private SixTabResultEvent.RichBean richBean;
    private SixTabResultEvent.HealthBean healthBean;
    private String title;
    private String position;

    @Override
    public int getContentViewId() {
        return R.layout.activity_result_common;
    }

    @Override
    public void init() {
        initView();
        responseListener();
    }

    private void responseListener() {
        Log.e("mImageUri", "=========sendNo===03======" + oid);

        switch (title) {
            case "八字精批"://八字精批
                sendRequest(HttpConstants.EightNumber03);
                im_header_pic.setImageResource(R.mipmap.icon_gb_six_bazijinpi);
                break;
            case "姓名详批"://姓名详批
                sendRequest(HttpConstants.NameDetails03);
                im_header_pic.setImageResource(R.mipmap.icon_gb_six_name);
                break;
            case "婚姻测算"://婚姻测算
                sendRequest(HttpConstants.MarriageTest03);
                im_header_pic.setImageResource(R.mipmap.icon_gb_six_text);
                break;
            case "今年运势"://今年运势
                sendRequest(HttpConstants.Fortune03);
                im_header_pic.setImageResource(R.mipmap.icon_gb_six_fortune);
                break;
            case "综合分析"://综合分析
                sendRequest(HttpConstants.Synthesize03);
                im_header_pic.setImageResource(R.mipmap.icon_gb_six_zonghe);
                break;

        }


    }

    private void sendRequest(String url) {
        Log.e("mImageUri", "=========sendNo===03======" + oid);
        showLoading();
        OkHttpUtils.post()
                .url(url)
                .addParams("oid", oid + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        showContent();
                        Log.e("mImageUri", "=========sendNo===03======" + response);
                        switch (title) {
                            case "八字精批"://八字精批
                                getSetSeamData(response);
                                break;
                            case "姓名详批"://姓名详批   不同的Bean   02
                                getSetTab02Data(response);
                                break;
                            case "婚姻测算"://婚姻测算   不同的Bean    03　　　　
                                getSetTab03Data(response);
                                break;
                            case "今年运势"://今年运势
                                getSetSeamData(response);

                                break;
                            case "综合分析"://综合分析
                                getSetSeamData(response);
                                break;

                        }


                    }
                });
    }

    //    姓名详批
    private void getSetTab02Data(String response) {
        Type type = new TypeToken<NameDetailsBean>() {
        }.getType();
        NameDetailsBean mNameDetailsBean = mGson.fromJson(response, type);
        if ("0".equals(mNameDetailsBean.getStatus())) {
            List<String> bazi = mNameDetailsBean.getData().getReturnX().getUser().getBazi();
            mListData = getListData(bazi);
            SetTab02Data(mNameDetailsBean);
        }

    }

    //    婚姻测试
    private void getSetTab03Data(String response) {

        Type type = new TypeToken<MarriageTestBean>() {
        }.getType();
        MarriageTestBean mMarriageTestBean = mGson.fromJson(response, type);
        if ("0".equals(mMarriageTestBean.getStatus())) {
            List<String> bazi = mMarriageTestBean.getData().getBazi();
            mListData = getListData(bazi);
            SetTab03Data(mMarriageTestBean);
        }

    }

    private void getSetSeamData(String response) {
        Type type = new TypeToken<EightNumBean03>() {
        }.getType();
        EightNumBean03 mBean03 = mGson.fromJson(response, type);
        if ("0".equals(mBean03.getStatus())) {
            List<String> baziList = mBean03.getData().getCookies().getBazi();

            mListData = getListData(baziList);
            getSetSomeData(mBean03);
        }
    }


    private void initView() {
        setTitleBarVisibility(View.VISIBLE);
        setTitleLeftBtnVisibility(View.VISIBLE);
        oid = getIntent().getStringExtra("oid");
        title = getIntent().getStringExtra("title");
        position = getIntent().getStringExtra("position");
        Log.e("mImageUri", "=========sendNo=wx==common==oid====" + oid);
        Log.e("mImageUri", "=========sendNo=wx==common==title====" + title);
        setTitleName(title + "");
        setPageStateView();
    }

    private void SetTab03Data(MarriageTestBean mBean03) {
        MarriageTestBean.DataBeanX.UserBean userBean = mBean03.getData().getUser();

        username = userBean.getXingming().getXing() + userBean.getXingming().getMing();
        birthday = mBean03.getData().getData().getY() + "年" +
                mBean03.getData().getData().getM() + "月" +
                mBean03.getData().getData().getD() + "日  " +
                mBean03.getData().getData().getH() + "时";

        born = mBean03.getData().getData().getLDate();
        String sexNum = mBean03.getData().getData().getGender();
        if ("0".equals(sexNum)) {
            sex = "男";
        } else {
            sex = "女";

        }
        sx = mBean03.getData().getUser().getSx();
        MarriageTestBean.DataBeanX.RglmBean rglmBean = mBean03.getData().getRglm();
//        MarriageTestBean.DataBeanXXX.RglmBean rglmBean = mBean03.getData().getRglm();


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

        //设置recycle
        sixHeadRecyclerView.setVisibility(View.VISIBLE);
        GridLayoutManager layoutManager = new GridLayoutManager(ResultCommonActivity.this, 5);
        sixHeadRecyclerView.setLayoutManager(layoutManager);
        sixHeadRecyclerView.addItemDecoration(new GridDivider(ResultCommonActivity.this, 2,
                this.getResources().getColor(R.color.color_b59487)));
        GirdAdapter adapter = new GirdAdapter(this, mListData);
        sixHeadRecyclerView.setAdapter(adapter);

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

    private void SetTab02Data(NameDetailsBean mBean03) {
        username = mBean03.getData().getData().getXing() + mBean03.getData().getData().getUsername();
        birthday = mBean03.getData().getData().getY() + "年" +
                mBean03.getData().getData().getM() + "月" +
                mBean03.getData().getData().getD() + "日  " +
                mBean03.getData().getData().getH() + "时";

        born = mBean03.getData().getData().getLDate();
        String sexNum = mBean03.getData().getData().getGender();
        if ("0".equals(sexNum)) {
            sex = "男";
        } else {
            sex = "女";

        }
        sx = mBean03.getData().getReturnX().getUser().getSx();
        NameDetailsBean.DataBeanXX.RglmBean rglmBean = mBean03.getData().getRglm();


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

        //设置recycle
        sixHeadRecyclerView.setVisibility(View.VISIBLE);
        GridLayoutManager layoutManager = new GridLayoutManager(ResultCommonActivity.this, 5);
        sixHeadRecyclerView.setLayoutManager(layoutManager);
        sixHeadRecyclerView.addItemDecoration(new GridDivider(ResultCommonActivity.this, 2,
                this.getResources().getColor(R.color.color_b59487)));
        GirdAdapter adapter = new GirdAdapter(this, mListData);
        sixHeadRecyclerView.setAdapter(adapter);

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

    private void getSetSomeData(EightNumBean03 mBean03) {
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
        sx = mBean03.getData().getSxgx().getSx();
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

        //设置recycle
        sixHeadRecyclerView.setVisibility(View.VISIBLE);
        GridLayoutManager layoutManager = new GridLayoutManager(ResultCommonActivity.this, 5);
        sixHeadRecyclerView.setLayoutManager(layoutManager);
        sixHeadRecyclerView.addItemDecoration(new GridDivider(ResultCommonActivity.this, 2,
                this.getResources().getColor(R.color.color_b59487)));
        GirdAdapter adapter = new GirdAdapter(this, mListData);
        sixHeadRecyclerView.setAdapter(adapter);

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

    private ArrayList<String> getListData(List<String> baziList) {


        ArrayList<String> strList = new ArrayList<>();
        strList.add("八字");
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
