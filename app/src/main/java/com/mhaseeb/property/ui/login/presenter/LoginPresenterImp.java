package com.mhaseeb.property.ui.login.presenter;

import android.content.Context;
import android.widget.Toast;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.common.api.ApiClient;
import com.mhaseeb.property.ui.common.preferences.PreferenceManager;
import com.mhaseeb.property.ui.home.HomeActivity;
import com.mhaseeb.property.ui.login.LoginActivity;
import com.mhaseeb.property.ui.login.LoginContract;
import com.mhaseeb.property.ui.login.api.ILoginAPIService;
import com.mhaseeb.property.ui.login.model.LoginRequestModel;
import com.mhaseeb.property.ui.login.model.LoginResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImp implements LoginContract.Presenter {
    private Context context;
    private LoginContract.View loginView;

    public LoginPresenterImp(Context context, LoginContract.View loginView) {

        this.context = context;
        this.loginView = loginView;
    }


    @Override
    public void onRegisterClick() {
        loginView.onRegisterClick();
    }

    @Override
    public void onLoginClick(final LoginRequestModel model) {
        ILoginAPIService iLoginAPIService = ApiClient.getClient().create(ILoginAPIService.class);
        Call<LoginResponseModel> call = iLoginAPIService.loginUser(model);
        ((LoginActivity) context).showLoading("Registering user", "Please wait...");
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStats() == true && response.body().getData() != null) {

                        ((LoginActivity) context).hideLoading();
                        PreferenceManager.getInstance().disposeAll(context);
                        PreferenceManager.getInstance().setUserSession(context,
                                response.body().getData().getFirstName(),
                                response.body().getData().getLastName(),
                                response.body().getData().getEmail(),
                                response.body().getData().getPhone(),
                                response.body().getData().getGender());
                        PreferenceManager.getInstance().setId(context, String.valueOf(response.body().getData().getId()));
                        PreferenceManager.getInstance().setIsLoggedIn(context, true);
                        loginView.onLoginSuccess(response.body().getMessage());
                    } else if (response.body().getStats() == false) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        ((LoginActivity) context).hideLoading();
                    }
                } else {
                    Toast.makeText(context, R.string.toast_Cannot_connect_to_the_server, Toast.LENGTH_SHORT).show();
                    ((LoginActivity) context).hideLoading();
                    loginView.onLoginFailure();
                }


            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(context, R.string.toast_Cannot_connect_to_the_server, Toast.LENGTH_SHORT).show();
                ((LoginActivity) context).hideLoading();
                loginView.onLoginFailure();
            }
        });

    }

    @Override
    public void onGuestLoginClick() {
        PreferenceManager.getInstance().setIsGuestLogin(context, true);
//        loginView.onLoginSuccess();

    }
}
