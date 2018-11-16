package com.mhaseeb.property.register.api;

import com.mhaseeb.property.common.config.IAPIConstants;
import com.mhaseeb.property.register.models.RegisterRequestModel;
import com.mhaseeb.property.register.models.RegisterResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Anus Wahaj Ali on 11/22/2017.
 */

public interface IRegisterAPIService {

    @POST(IAPIConstants.URL_REGISTER)
    Call<RegisterResponseModel> registerUser(@Body RegisterRequestModel model);
//    Observable<BaseResponse> logoutUser(@HeaderMap Map<String, String> headers);
}
