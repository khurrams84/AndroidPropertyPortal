package com.mhaseeb.property.ui.login.api;

import com.mhaseeb.property.ui.common.config.IAPIConstants;
import com.mhaseeb.property.ui.login.model.LoginRequestModel;
import com.mhaseeb.property.ui.login.model.LoginResponseModel;
import com.mhaseeb.property.ui.register.models.RegisterRequestModel;
import com.mhaseeb.property.ui.register.models.RegisterResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Anus Wahaj Ali on 11/22/2017.
 */

public interface ILoginAPIService {

    @POST(IAPIConstants.URL_LOGIN)
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel model);
//    Observable<BaseResponse> logoutUser(@HeaderMap Map<String, String> headers);
}
