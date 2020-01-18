package com.company.forturetelling.ui.activity.sixfunction.getname.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.forturetelling.R;
import com.company.forturetelling.bean.calculate.NameDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class NameDetailsAdapter extends RecyclerView.Adapter<NameDetailsAdapter.ViewHolder> {


    public void setData(ArrayList<NameDetailsBean.DataBean.FullnameBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack {
        void onItemClick(NameDetailsBean.DataBean.FullnameBean bean);
    }

    public List<NameDetailsBean.DataBean.FullnameBean> datas = null;
    private ItemClickCallBack clickCallBack;

    private Context applicationContext;
    private String addName;

    public NameDetailsAdapter(List<NameDetailsBean.DataBean.FullnameBean> newBeanList, Context applicationContext, String addName) {
        this.datas = newBeanList;
        this.addName = addName;
        this.applicationContext = applicationContext;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_name_details, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    //将数据与界面进行绑定的操作
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        NameDetailsBean.DataBean.FullnameBean nameBean = datas.get(position);
        viewHolder.tv_namedetails_tv_left.setText(addName + "");
        viewHolder.tv_namedetails_tv_right.setText(nameBean.getMing() + "");
        viewHolder.linear_name_details_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.onItemClick(nameBean);
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
        public TextView tv_namedetails_tv_left;
        public TextView tv_namedetails_tv_right;
        public LinearLayout linear_name_details_all;

        public ViewHolder(View view) {
            super(view);
            tv_namedetails_tv_left = (TextView) view.findViewById(R.id.tv_namedetails_tv_left);
            tv_namedetails_tv_right = (TextView) view.findViewById(R.id.tv_namedetails_tv_right);
            linear_name_details_all = (LinearLayout) view.findViewById(R.id.linear_name_details_all);
        }
    }
}





















