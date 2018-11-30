package com.mhaseeb.property.property.propertydetail.presenter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mhaseeb.property.common.api.ApiClient;
import com.mhaseeb.property.common.persistence.AppDatabase;
import com.mhaseeb.property.common.persistence.FavouriteEntity;
import com.mhaseeb.property.common.service.ServiceLocator;
import com.mhaseeb.property.property.api.IPropertyAPIService;
import com.mhaseeb.property.property.model.FavoritesModel;
import com.mhaseeb.property.property.model.MarkFavouriteResponse;
import com.mhaseeb.property.property.model.PropertyModel;
import com.mhaseeb.property.property.propertydetail.IPropertyDetailContract;
import com.mhaseeb.property.property.propertydetail.PropertyDetailActivity;
import com.mhaseeb.property.widget.FavouriteAppWidget;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by muhammadmoiz on 11/20/18.
 */

public class PropertyDetailPresenterImpl implements IPropertyDetailContract.Presenter {

    private IPropertyDetailContract.View view;
    private Context context;
    private IPropertyAPIService iPropertyAPIService;

    public PropertyDetailPresenterImpl(Context context, IPropertyDetailContract.View view) {
        this.context = context;
        this.view = view;
        iPropertyAPIService = ApiClient.getClient().create(IPropertyAPIService.class);
    }


    @Override
    public void markFavourite(FavoritesModel model) {

        Call<MarkFavouriteResponse> call = iPropertyAPIService.markFavoriteProperty(model);

        call.enqueue(new Callback<MarkFavouriteResponse>() {
            @Override
            public void onResponse(Call<MarkFavouriteResponse> call, final Response<MarkFavouriteResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == true && response.body().getData() != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                PropertyModel propertyModel = response.body().getData().getProperty();

                                //if property is marked favourite
                                if (response.body().getData().getStatus()) {
                                    FavouriteEntity favouriteEntity = new FavouriteEntity(propertyModel);
                                    ServiceLocator.getService(AppDatabase.class).FavouriteDao().deleteByPropertyId(propertyModel.getId());
                                    ServiceLocator.getService(AppDatabase.class).FavouriteDao().insertOnlySingleFavourite(favouriteEntity);
                                } else {
                                    ServiceLocator.getService(AppDatabase.class).FavouriteDao().deleteByPropertyId(propertyModel.getId());
                                }

                                Intent intent = new Intent(context, FavouriteAppWidget.class);
                                intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                                int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, FavouriteAppWidget.class));
                                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                                context.sendBroadcast(intent);

                                view.onMarkFavouriteSuccess();

                            }
                        }).start();

                    } else if (response.body().getStatus() == false) {

                    }
                } else {


                }
            }

            @Override
            public void onFailure(Call<MarkFavouriteResponse> call, Throwable t) {
                Log.e("Error", "Error in marking favourite");
                view.onMarkFavouriteFailure("Error in marking favourite");
            }
        });


    }
}
