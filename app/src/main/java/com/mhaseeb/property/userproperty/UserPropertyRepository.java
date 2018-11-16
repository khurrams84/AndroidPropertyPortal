package com.mhaseeb.property.userproperty;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.mhaseeb.property.common.api.ApiClient;
import com.mhaseeb.property.property.api.IPropertyAPIService;
import com.mhaseeb.property.property.model.GetUserPropertiesResponseModel;
import com.mhaseeb.property.property.model.MarkFavouriteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by muhammadmoiz on 11/16/18.
 */

public class UserPropertyRepository {

    IPropertyAPIService iPropertyAPIService;

    private static class SingletonHelper {
        private static final UserPropertyRepository INSTANCE = new UserPropertyRepository();
    }

    public static UserPropertyRepository getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private UserPropertyRepository() {
        iPropertyAPIService = ApiClient.getClient().create(IPropertyAPIService.class);
    }

    public LiveData<GetUserPropertiesResponseModel> getUserProperty(int userId) {
        final MutableLiveData<GetUserPropertiesResponseModel> data = new MutableLiveData<>();
        iPropertyAPIService.getUserProperties(userId)
                .enqueue(new Callback<GetUserPropertiesResponseModel>() {
                    @Override
                    public void onResponse(Call<GetUserPropertiesResponseModel> call, Response<GetUserPropertiesResponseModel> response) {
                            data.setValue(response.body());

                    }

                    @Override
                    public void onFailure(Call<GetUserPropertiesResponseModel> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

}
