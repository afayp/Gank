package com.app.pfh.gank.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.pfh.gank.R;
import com.app.pfh.gank.model.Good;
import com.app.pfh.gank.utils.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;


public class FuliAdapter extends RecyclerView.Adapter<FuliAdapter.MyViewHolder> {

    private Context mContext;
    private List<Good> mFuliList ;
    private OnItemClickListener mOnItemClickListener;
    private ImageLoader mImageloader;
    private DisplayImageOptions options;

    public FuliAdapter(Context context){
        mContext = context;
        mFuliList = new ArrayList<>();
        mImageloader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.drawable.item_default_img)
                .build();

    }

    public interface OnItemClickListener{
        void onItemClick(Good good,int position);
    }
    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickListener = mOnItemClickLitener;
    }

    public void addData(List<Good> goodList){
        mFuliList.addAll(goodList);
        notifyDataSetChanged();
    }

    public void removeData(){
        mFuliList.clear();
        notifyDataSetChanged();
    }

    public List<Good> getFuliList(){
        return mFuliList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_fuli,parent,false));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                Good good = mFuliList.get(position);
                mOnItemClickListener.onItemClick(good,position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Good good = mFuliList.get(position);
//        ImageSize imageSize = new ImageSize(ScreenUtils.getScreenWidth(mContext), 100);
        mImageloader.displayImage(good.getUrl(), holder.imageView,options);
//        Glide.with(mContext).load(good.getUrl()).error(R.drawable.item_default_img).override(500,400)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mFuliList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_fuli_iv);
        }
    }
}
