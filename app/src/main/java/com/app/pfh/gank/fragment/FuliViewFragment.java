package com.app.pfh.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.pfh.gank.R;
import com.app.pfh.gank.view.TouchImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class FuliViewFragment extends Fragment {

    private String url;
    private ImageLoader imageLoader;
    private TouchImageView imageView;

    public static FuliViewFragment newInstance(String url){
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        FuliViewFragment fuliViewFragment = new FuliViewFragment();
        fuliViewFragment.setArguments(bundle);
        return fuliViewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
        imageLoader = ImageLoader.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuli_viewer, container, false);
        imageView = (TouchImageView) view.findViewById(R.id.image);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.drawable.item_default_img)
                .build();
        imageLoader.displayImage(url,imageView,options);
    }
}
