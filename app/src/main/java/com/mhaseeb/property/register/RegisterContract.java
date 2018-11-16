package com.mhaseeb.property.register;

import com.mhaseeb.property.register.models.RegisterRequestModel;

public interface RegisterContract {

    interface View {
        void onRegisterSuccess(String message);

        void onRegisterFailure();

    }

    interface Presenter {
        void onRegisterButtonClicked(RegisterRequestModel model);

    }
}
