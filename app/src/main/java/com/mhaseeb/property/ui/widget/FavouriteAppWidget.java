package com.mhaseeb.property.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.common.config.IAPIConstants;
import com.mhaseeb.property.ui.common.persistence.AppDatabase;
import com.mhaseeb.property.ui.common.persistence.FavouriteEntity;
import com.mhaseeb.property.ui.common.service.ServiceLocator;
import com.mhaseeb.property.ui.common.utils.ImageUtil;

/**
 * Implementation of App Widget functionality.
 */
public class FavouriteAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favourite_app_widget);

        Log.d("FAV WIDGET", "UPDATE");

        init(context, appWidgetManager, appWidgetId, views);
        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    private static void init(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId, final RemoteViews views) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                FavouriteEntity favouriteEntity = ServiceLocator.getService(AppDatabase.class).FavouriteDao().fetchLastRecord();

                if (favouriteEntity != null) {
                    String imageUrl = IAPIConstants.BASE_URL + favouriteEntity.getImages();
                    views.setTextViewText(R.id.tvPrice, favouriteEntity.getPrice());
                    views.setTextViewText(R.id.tvTitle, favouriteEntity.getTitle());
                    views.setTextViewText(R.id.tvDescription, favouriteEntity.getDescription());
                    views.setTextViewText(R.id.tvType, favouriteEntity.getType());
                    views.setTextViewText(R.id.tvCategory, favouriteEntity.getCategory());
                    views.setImageViewBitmap(R.id.ivMainImage, ImageUtil.getImageAsBitmap(context, imageUrl));
                    views.setViewVisibility(R.id.propertyNotFound, View.GONE);
                    views.setViewVisibility(R.id.propertyLayout, View.VISIBLE);

                } else {
                    views.setViewVisibility(R.id.propertyNotFound, View.VISIBLE);
                    views.setViewVisibility(R.id.propertyLayout, View.GONE);
                    Log.d("WIDGET", "NO DATA");
                }

                appWidgetManager.updateAppWidget(appWidgetId, views);

            }
        }).start();

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

