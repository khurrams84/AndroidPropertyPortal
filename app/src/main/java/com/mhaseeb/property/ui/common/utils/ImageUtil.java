package com.mhaseeb.property.ui.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mhaseeb.property.R;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

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

    public static Bitmap getImageAsBitmap(Context context, String url) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap =  Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get();

            return bitmap;

        } catch (InterruptedException e) {
            Log.e("ERROR", e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("ERROR", e.getMessage());
            e.printStackTrace();
        }

        return BitmapFactory.decodeResource(context.getResources(), R.drawable.house1);
    }

}
