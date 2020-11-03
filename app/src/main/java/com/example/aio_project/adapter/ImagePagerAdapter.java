package com.example.aio_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aio_project.R;
import com.example.aio_project.utils.ImageHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ImagePagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> urls;

    public ImagePagerAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_viewpager, null);
        ImageView imageView = view.findViewById(R.id.image);
        ImageHelper.loadImageWithoutThumbnail(context, urls.get(position), imageView, () -> {});
        /*imageView.setImageDrawable(GradientHelper.generateFundsTopGradient(
                context,
                context.getResources(),
                ids[position]));*/
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() { return urls == null ? 0 : urls.size(); }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }
}
