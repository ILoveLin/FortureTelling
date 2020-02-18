//package com.company.forturetelling.ui.activity.result;
//
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.company.forturetelling.R;
//import com.company.forturetelling.base.BaseActivity;
//import com.company.forturetelling.bean.sixtab.GetNameResultBean;
//import com.company.forturetelling.bean.sixtab.NameDetails03;
//import com.company.forturetelling.global.HttpConstants;
//import com.company.forturetelling.ui.fragment.knowledge.adapter.KnowledgeAdapter;
//import com.google.gson.reflect.TypeToken;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import java.lang.reflect.Type;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import okhttp3.Call;
//
///**
// * Created by Lovelin on 2019/12/24
// * <p>
// * Describe:这个是单独的取名 结果界面
// */
//public class ResultNameDetalisActivity extends BaseActivity {
//
//
//    @BindView(R.id.im_header_pic)
//    ImageView imHeaderPic;
//    @BindView(R.id.tv_info_sex)
//    TextView tvInfoSex;
//    @BindView(R.id.tv_info_zodiac)
//    TextView tvInfoZodiac;
//    @BindView(R.id.tv_info_bron)
//    TextView tvInfoBron;
//    @BindView(R.id.tv_info_brithday)
//    TextView tvInfoBrithday;
//    @BindView(R.id.getname_recycle)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.smartRefresh)
//    SmartRefreshLayout smartRefresh;
//    @BindView(R.id.relate_mine_all)
//    RelativeLayout relateMineAll;
//    private String oid;
//    private String title;
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.activity_result_text;
//    }
//
//    @Override
//    public void init() {
//        initView();
//        responseListener();
//    }
//
//    private void responseListener() {
//        sendRequest(HttpConstants.NameDetails);
//    }
//
//    private void sendRequest(String url) {
//        OkHttpUtils.post()
//                .url(url)
//                .addParams("oid", oid + "")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        showError();
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("mImageUri", "=========name===03======" + oid);
//                        Log.e("mImageUri", "=========name===03======" + response);
//
//                        Type type = new TypeToken<GetNameResultBean>() {
//                        }.getType();
//                        GetNameResultBean mBean03 = mGson.fromJson(response, type);
//                        if ("0".equals(mBean03.getStatus())) {
//
//                            getSetSomeData(mBean03);
//
//
//                        }
//
//
//                    }
//                });
//    }
//
//    private void getSetSomeData(GetNameResultBean mBean03) {
//        List<GetNameResultBean.DataBean.FullnameBean> fullname = mBean03.getData().getFullname();
//        if (!(fullname == null)) {   //有数据
//
//        } else {//空数据
//
//        }
//    }
//
//
//    private void initView() {
//        setTitleBarVisibility(View.VISIBLE);
//        setTitleLeftBtnVisibility(View.VISIBLE);
//        oid = getIntent().getStringExtra("oid");
//        title = getIntent().getStringExtra("title");
//        setTitleName(title + "");
//        setPageStateView();
////        GridLayoutManager layoutManager = new GridLayoutManager(ResultCommonActivity.this, 5);
//
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(ResultNameDetalisActivity.this, 2);
//        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(gridLayoutManager);
////        mAdapter = new NameDetalis(mDataList, getActivity().getApplicationContext());
////        mRecyclerView.setAdapter(mAdapter);
//
//
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//
//}
