package com.mhaseeb.property.register.presenter;

import android.content.Context;
import android.widget.Toast;

import com.mhaseeb.property.common.api.ApiClient;
import com.mhaseeb.property.common.preferences.PreferenceManager;
import com.mhaseeb.property.register.RegisterActivity;
import com.mhaseeb.property.register.RegisterContract;
import com.mhaseeb.property.register.api.IRegisterAPIService;
import com.mhaseeb.property.register.models.RegisterRequestModel;
import com.mhaseeb.property.register.models.RegisterResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenterImp implements RegisterContract.Presenter {
    private Context context;
    private RegisterContract.View registerView;

    public RegisterPresenterImp(Context context, RegisterContract.View registerView) {

        this.context = context;
        this.registerView = registerView;
    }


    @Override
    public void onRegisterButtonClicked(final RegisterRequestModel model) {

        IRegisterAPIService iRegisterAPIService = ApiClient.getClient().create(IRegisterAPIService.class);
        Call<RegisterResponseModel> call = iRegisterAPIService.registerUser(model);
        ((RegisterActivity) context).showLoading("Registering user", "Please wait...");
        call.enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStats() == true && response.body().getData() != null) {

                        ((RegisterActivity) context).hideLoading();
                        PreferenceManager.getInstance().setUserSession(context,
                                response.body().getData().getFirstName(),
                                response.body().getData().getLastName(),
                                response.body().getData().getEmail(),
                                response.body().getData().getGender(),
                                model.getPhone());
                        PreferenceManager.getInstance().setId(context, String.valueOf(response.body().getData().getId()));
                        PreferenceManager.getInstance().setIsLoggedIn(context, true);
                        registerView.onRegisterSuccess(response.body().getMessage());

//
                    } else if (response.body().getStats() == false) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        ((RegisterActivity) context).hideLoading();
                    }
                } else {
                    Toast.makeText(context, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                    ((RegisterActivity) context).hideLoading();
                    registerView.onRegisterFailure();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                Toast.makeText(context, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                ((RegisterActivity) context).hideLoading();
                registerView.onRegisterFailure();
            }
        });

    }
}
