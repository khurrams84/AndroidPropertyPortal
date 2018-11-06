package com.mhaseeb.property.ui.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mhaseeb.property.R;

/**
 * Created by muhammadmoiz on 11/4/18.
 */

public class ImageUtil {

    public static void showPropertyImage(ImageView imageView, Context context, String url) {

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.house1);

        Glide.with(context).load(url).apply(options).into(imageView);
    }

}
