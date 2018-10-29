package com.mhaseeb.property.ui.login;

import com.mhaseeb.property.ui.login.model.LoginRequestModel;

public interface LoginContract {

    interface View {
        void onRegisterClick();

        void onLoginSuccess(String message);

        void onLoginFailure();

    }

    interface Presenter {
        void onRegisterClick();

        void onLoginClick(LoginRequestModel model);

        void onGuestLoginClick();
    }
}
