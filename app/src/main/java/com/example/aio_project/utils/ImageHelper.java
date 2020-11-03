package com.example.aio_project.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.aio_project.R;

import androidx.annotation.Nullable;

/**
 * Created by Alexey Matrosov on 03.11.2020.
 */

public class ImageHelper {
    public interface OnImageLoaded {
        void onLoaded();
    }

    private static final RequestOptions requestOptions = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL); // because file name is always same

    /*public static void loadImage(Context context, String url, final ImageView iv) {
        Glide.with(context)
                .load(url)
                .thumbnail(Glide.with(context).load(R.drawable.loading))
                .apply(requestOptions)
                .into(iv);
    }*/

    public static void loadImageWithoutThumbnail(Context context, String url, final ImageView iv, OnImageLoaded callback) {
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (callback != null)
                            callback.onLoaded();

                        return false;
                    }
                })
                .into(iv);

    }
}
