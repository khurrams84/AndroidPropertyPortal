package com.mhaseeb.property.ui.register;

import com.mhaseeb.property.ui.register.models.RegisterRequestModel;

public interface RegisterContract {

    interface View {
        void onRegisterSuccess(String message);

        void onRegisterFailure();

    }

    interface Presenter {
        void onRegisterButtonClicked(RegisterRequestModel model);

    }
}
