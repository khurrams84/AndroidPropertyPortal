package com.mhaseeb.property.property.propertydetail;

import com.mhaseeb.property.property.model.FavoritesModel;

/**
 * Created by muhammadmoiz on 11/20/18.
 */

public interface IPropertyDetailContract {

    interface Presenter {
        void markFavourite(FavoritesModel model);
    }

    interface View {
        void onMarkFavouriteSuccess();
        void onMarkFavouriteFailure(String error);
    }


}
