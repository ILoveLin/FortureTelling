package com.company.forturetelling.ui.fragment.knowledge.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.company.forturetelling.R;
import com.company.forturetelling.bean.KnowledgeBean;
import com.company.forturetelling.global.HttpConstants;
import com.company.forturetelling.utils.PicassoUtil;
import com.company.forturetelling.view.RoundImageView;
import com.yun.common.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.ViewHolder> {


    public void setData(ArrayList<KnowledgeBean.DataBean.WencontentBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack {
        void onItemClick(KnowledgeBean.DataBean.WencontentBean bean);
    }

    public List<KnowledgeBean.DataBean.WencontentBean> datas = null;
    private ItemClickCallBack clickCallBack;

    private Context applicationContext;

    public KnowledgeAdapter(List<KnowledgeBean.DataBean.WencontentBean> newBeanList, Context applicationContext) {
        this.datas = newBeanList;
        this.applicationContext = applicationContext;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_message, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    //将数据与界面进行绑定的操作
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        KnowledgeBean.DataBean.WencontentBean wencontentBean = datas.get(position);
        viewHolder.new_title.setText(wencontentBean.getTitle() + "");
        viewHolder.tv_item_type.setText(wencontentBean.getBq() + "");
        viewHolder.tv_item_time.setText(wencontentBean.getData() + "");
        GlideUtils.LoadUrlImageView(applicationContext,
                wencontentBean.getImg(), viewHolder.new_picc);


        viewHolder.linear_all_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.onItemClick(wencontentBean);
            }
        });

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView new_title;
        public TextView tv_item_type;
        public TextView tv_item_time;
        public RoundImageView new_picc;
        public LinearLayout linear_all_new;

        public ViewHolder(View view) {
            super(view);
            new_title = (TextView) view.findViewById(R.id.new_title);
            tv_item_type = (TextView) view.findViewById(R.id.tv_item_type);
            tv_item_time = (TextView) view.findViewById(R.id.tv_item_time);
            new_picc = (RoundImageView) view.findViewById(R.id.new_picc);
            linear_all_new = (LinearLayout) view.findViewById(R.id.linear_all_new);
        }
    }
}





















