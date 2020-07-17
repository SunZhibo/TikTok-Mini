package com.example.tiktok.lib;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ImageHelper {
    public static void displayWebImage(String url, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .transition(withCrossFade())
                .into(imageView);
    }
}