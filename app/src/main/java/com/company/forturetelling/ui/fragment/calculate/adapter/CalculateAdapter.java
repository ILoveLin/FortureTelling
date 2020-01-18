package com.company.forturetelling.ui.fragment.calculate.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.forturetelling.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class CalculateAdapter extends RecyclerView.Adapter<CalculateAdapter.ViewHolder> {


    public void setData(ArrayList<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack {
        void onItemClick(String title, int position);
    }

    public ArrayList<String> datas = null;
    private ItemClickCallBack clickCallBack;
    private FragmentActivity activity;

    public CalculateAdapter(ArrayList<String> datas, FragmentActivity activity) {
        this.datas = datas;
        this.activity = activity;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_calculate_tab, viewGroup, false);
        return new ViewHolder(view);
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        switch (position) {
            case 0:
                viewHolder.tv_current_tab_name.setText("" + "八字精批");
                viewHolder.iv_current_pic.setImageResource(R.mipmap.icon_bazi);
                break;
            case 1:
                viewHolder.tv_current_tab_name.setText("" + "姓名详批");
                viewHolder.iv_current_pic.setImageResource(R.mipmap.icon_ziwei);
                break;
            case 2:
                viewHolder.tv_current_tab_name.setText("" + "婚姻测算");
                viewHolder.iv_current_pic.setImageResource(R.mipmap.icon_hehun);
                break;
            case 3:
                viewHolder.tv_current_tab_name.setText("" + "取名");
                viewHolder.iv_current_pic.setImageResource(R.mipmap.icon_quming);
                break;
            case 4:
                viewHolder.tv_current_tab_name.setText("" + "今年运势");
                viewHolder.iv_current_pic.setImageResource(R.mipmap.icon_yunshi);
                break;
            case 5:
                viewHolder.tv_current_tab_name.setText("" + "综合分析");
                viewHolder.iv_current_pic.setImageResource(R.mipmap.icon_cesuan);
                break;
        }
        viewHolder.linear_calculate_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.onItemClick(viewHolder.tv_current_tab_name.getText().toString().trim(), position);
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
        public TextView tv_current_tab_name;
        public ImageView iv_current_pic;
        public LinearLayout linear_calculate_click;

        public ViewHolder(View view) {
            super(view);
            tv_current_tab_name = (TextView) view.findViewById(R.id.tv_current_tab_name);
            iv_current_pic = (ImageView) view.findViewById(R.id.iv_current_pic);
            linear_calculate_click = (LinearLayout) view.findViewById(R.id.linear_calculate_click);
        }
    }
}





















