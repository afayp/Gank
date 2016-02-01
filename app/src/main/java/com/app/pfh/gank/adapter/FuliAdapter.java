package com.app.pfh.gank.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.pfh.gank.R;
import com.app.pfh.gank.model.Good;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class FuliAdapter extends RecyclerView.Adapter<FuliAdapter.MyViewHolder> implements View.OnClickListener{

    private Context mContext;
    private List<Good> mFuliList = new ArrayList<Good>();
    private OnItemClickListener mOnItemClickListener;

    public FuliAdapter(Context context,List<Good> list){
        mContext = context;
        mFuliList = list;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int position);

    }
    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickListener = mOnItemClickLitener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_fuli,parent,false));
        holder.imageView.setOnClickListener(this);
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String url = mFuliList.get(position).getUrl();
        Glide.with(mContext).load(url).centerCrop().into(holder.imageView);
        holder.imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mFuliList.size();
    }


    @Override
    public void onClick(View v) {
        //点击查看大图浏览
        mOnItemClickListener.onItemClick(v,(int)v.getTag());
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.id_fuli_img);
        }
    }
}
