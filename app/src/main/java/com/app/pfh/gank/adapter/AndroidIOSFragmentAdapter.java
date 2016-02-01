package com.app.pfh.gank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pfh.gank.R;
import com.app.pfh.gank.db.RealmHelper;
import com.app.pfh.gank.model.Good;
import com.app.pfh.gank.model.Image;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.RealmResults;


/**
 * 分类下android和ios的recyclerview的adapter
 */
public class AndroidIOSFragmentAdapter extends RecyclerView.Adapter<AndroidIOSFragmentAdapter.MyViewHolder> implements View.OnClickListener{


    private List<Good> mGoodList = new ArrayList<Good>();
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    @Override
    public void onClick(View v) {
        //点击打开网址
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }


    }

    //回调接口：
    public interface OnItemClickListener {
        void onItemClick(View view, String url);

        void onItemLongClick(View view, int position);
    }



    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickListener = mOnItemClickLitener;
    }

    //构造方法：把需要的数据传进来
    public AndroidIOSFragmentAdapter(Context context,List<Good> goodList){
        this.context = context;
        this.mGoodList = goodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_android_ios,parent,false));
        //为item里的view设置点击事件,点击查看详细信息
        holder.imageView.setOnClickListener(this);
        //收藏功能：
        //holder.heart.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Good good = mGoodList.get(position);
        holder.tv_title.setText(good.getDesc());
        holder.tv_good_author_time.setText(getGoodAuthorAndTime(good));
        loadItemImage(holder);
        holder.imageView.setTag(good.getUrl());

        //如果收藏过要显示红心
//        holder.heart.setImageResource();

    }

    private void loadItemImage(MyViewHolder holder) {
        RealmResults<Image> images = RealmHelper.getRealm(context).where(Image.class).findAll();
        //从数据库中随机加载一张图片
        Random random = new Random();
        int positon = random.nextInt(images.size()-1);
        String url = images.get(positon).getUrl();
        if(url != null){
            Glide.with(context).load(url)
                    .centerCrop().into(holder.imageView);
        }else {
            loadItemImage(holder);
        }

    }

    private String getGoodAuthorAndTime(Good good) {
        StringBuilder builder = new StringBuilder();
        String time = good.getPublishedAt().substring(0,10);
        String author = good.getWho();
        builder.append(time).append(" ").append(author);
        return builder.toString();
    }

    @Override
    public int getItemCount() {
        return mGoodList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ImageView heart;
        TextView tv_good_author_time;
        TextView tv_title;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_goods_img);
            heart = (ImageView) itemView.findViewById(R.id.img_like_goods);
            tv_good_author_time = (TextView) itemView.findViewById(R.id.txt_goods_author);
            tv_title = (TextView) itemView.findViewById(R.id.txt_goods_title);
        }
    }
}
