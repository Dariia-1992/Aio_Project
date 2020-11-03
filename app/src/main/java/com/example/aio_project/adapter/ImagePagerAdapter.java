package com.example.aio_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aio_project.R;
import com.example.aio_project.model.Category;
import com.example.aio_project.model.DataRepository;
import com.example.aio_project.utils.ImageHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ImagePagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> urls;
    private boolean isSkin;

    public ImagePagerAdapter(Context context, List<String> urls, boolean isSkin) {
        this.context = context;
        this.urls = urls;
        this.isSkin = isSkin;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_viewpager, null);
        ImageView backgroundImage = view.findViewById(R.id.backgroundImage);
        ImageView imageView = view.findViewById(R.id.image);

        int imagePadding = isSkin ? 20 : 0;
        imageView.setImageDrawable(null);
        imageView.setScaleType(isSkin ? ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(0, imagePadding, 0, imagePadding);

        backgroundImage.setAlpha(1.0f);
        imageView.setAlpha(0.0f);

        ImageHelper.loadImageWithoutThumbnail(context, urls.get(position), imageView, () -> {
            backgroundImage.setAlpha(0.0f);
            imageView.setAlpha(1.0f);
//            backgroundImage.animate()
//                    .alpha(0.0f)
//                    .setDuration(300);
//
//            imageView.animate()
//                    .alpha(1.0f)
//                    .setDuration(300);
        });

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
