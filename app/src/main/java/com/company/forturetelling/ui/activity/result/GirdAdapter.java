package com.company.forturetelling.ui.activity.result;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.forturetelling.R;

import java.util.ArrayList;


/**
 * Created by wangcy on 2017/11/23.
 * 名称:
 */

public class GirdAdapter extends RecyclerView.Adapter<GirdAdapter.MyViewHolder> {
    private Context mContext;
    public OnItemClickListener mOnItemClickListener = null;
    private ArrayList<String> mDatas = new ArrayList<>();

    //    private int[] mDrawable = new int[]{R.drawable.item_0,R.drawable.item_1,R.drawable.item_2,R.drawable.item_3,
//            R.drawable.item_4,R.drawable.item_5,R.drawable.item_6,R.drawable.item_7};
    public GirdAdapter(Context context, ArrayList<String> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onitemClick(int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_six_result, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(position<6) {
            holder.item_title.setTextColor(Color.parseColor("#FF0000"));
        }else if (position==10){
            holder.item_title.setTextColor(Color.parseColor("#FF0000"));
        }
        holder.item_title.setText(mDatas.get(position) + "");
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_title;
        LinearLayout item_title_all;

        public MyViewHolder(View view) {
            super(view);
            item_title = (TextView) view.findViewById(R.id.item_title);
            item_title_all = (LinearLayout) view.findViewById(R.id.item_title_all);
        }
    }


}
