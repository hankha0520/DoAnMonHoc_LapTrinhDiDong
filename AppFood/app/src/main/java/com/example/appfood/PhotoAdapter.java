package com.example.appfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.appfood.classs.Photo;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {
    Context mContext;
    List<Photo> mListPhoto;

    public PhotoAdapter(Context mContext, List<Photo> mListPhoto) {
        this.mContext = mContext;
        this.mListPhoto = mListPhoto;
    }

    @Override
    public int getCount() {
        if(mListPhoto!= null)
            return mListPhoto.size();
        return  0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container, false);
        ImageView imgPT = view.findViewById(R.id.img_pt);
        Photo photo = mListPhoto.get(position);
        if(photo != null)
            Glide.with(mContext).load(photo.getResourceID()).into(imgPT);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
