package com.company.forturetelling.ui.activity.pay.order.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.company.forturetelling.R;
import com.company.forturetelling.bean.OrderBean;
import com.company.forturetelling.view.SettingBar;

import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


    private String payType;

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack {
        void onItemClick(OrderBean.DataBean.ListBean bean);
    }

    public List<OrderBean.DataBean.ListBean> datas = null;
    private ItemClickCallBack clickCallBack;

    private Context applicationContext;

    public OrderAdapter(List<OrderBean.DataBean.ListBean> newBeanList, Context applicationContext) {
        this.datas = newBeanList;
        this.applicationContext = applicationContext;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    //将数据与界面进行绑定的操作
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        OrderBean.DataBean.ListBean listBean = datas.get(position);
        viewHolder.bar_product_name.setRightText("" + listBean.getTitle());
        viewHolder.bar_my_name.setRightText("" + listBean.getName());
        viewHolder.bar_order_num.setRightText("" + listBean.getOrder_no());
        String type = listBean.getType();
//        支付类型 0 未支付 1 微信支付 2支付宝支付 3 苹果支付
        payType = "已支付";
        switch (type) {
            case "0":
                payType = "未支付";
                break;
            case "1":
                payType = "微信支付";
                break;
            case "2":
                payType = "支付宝支付";
                break;
            case "3":
                payType = "苹果支付";
                break;
        }
        viewHolder.bar_order_statue.setRightText("" + payType);

        viewHolder.linear_all_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.onItemClick(listBean);
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
        public SettingBar bar_product_name;
        public SettingBar bar_my_name;
        public SettingBar bar_order_num;
        public SettingBar bar_order_statue;
        public LinearLayout linear_all_new;

        public ViewHolder(View view) {
            super(view);
            bar_product_name = (SettingBar) view.findViewById(R.id.bar_product_name);
            bar_my_name = (SettingBar) view.findViewById(R.id.bar_my_name);
            bar_order_num = (SettingBar) view.findViewById(R.id.bar_order_num);
            bar_order_statue = (SettingBar) view.findViewById(R.id.bar_order_statue);
            linear_all_new = (LinearLayout) view.findViewById(R.id.linear_all_new);
        }
    }
}





















