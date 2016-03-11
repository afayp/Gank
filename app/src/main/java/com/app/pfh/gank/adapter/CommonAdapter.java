package com.app.pfh.gank.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.pfh.gank.R;
import com.app.pfh.gank.model.Good;

import java.util.ArrayList;
import java.util.List;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.MyViewHolder> {


    private List<Good> mGoodList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public CommonAdapter(Context context) {
        mGoodList = new ArrayList<Good>();
        mContext = context;
    }

    public interface OnItemClickListener {
        void onItemClick(Good good);

        void onItemLongClick(Good good);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void addData(List<Good> goodList) {
        mGoodList.addAll(goodList);
        notifyDataSetChanged();
    }

    public void refreshData(List<Good> goodList) {
        mGoodList.clear();
        mGoodList.addAll(goodList);
        notifyDataSetChanged();
    }

    public void removeData() {
        mGoodList.clear();
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_common, parent, false));
        if (onItemClickListener != null) {
            holder.item_linearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    Good good = mGoodList.get(position);
                    onItemClickListener.onItemClick(good);
                }
            });
            holder.item_linearlayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    Good good = mGoodList.get(position);
                    onItemClickListener.onItemLongClick(good);
                    return true;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Good good = mGoodList.get(position);
//        if(good.get_id() != null){
        holder.tv_title.setText(good.getDesc());
        holder.tv_time.setText(getTime(good));
        holder.tv_author.setText(good.getWho());
//        }
    }

    private String getTime(Good good) {
        String str = good.getPublishedAt();
        return str.substring(0, 10);
    }

    @Override
    public int getItemCount() {
        return mGoodList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View item_linearlayout;
        TextView tv_title;
        TextView tv_time;
        TextView tv_author;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_linearlayout = itemView.findViewById(R.id.item_linearlayout);
            tv_title = (TextView) itemView.findViewById(R.id.txt_goods_title);
            tv_time = (TextView) itemView.findViewById(R.id.txt_goods_time);
            tv_author = (TextView) itemView.findViewById(R.id.txt_goods_author);
        }
    }
}
