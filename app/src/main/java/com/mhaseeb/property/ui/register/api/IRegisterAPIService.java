package com.mhaseeb.property.ui.register.api;

import com.mhaseeb.property.ui.common.config.IAPIConstants;
import com.mhaseeb.property.ui.common.model.APIResponseModel;
import com.mhaseeb.property.ui.register.models.RegisterRequestModel;
import com.mhaseeb.property.ui.register.models.RegisterResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Anus Wahaj Ali on 11/22/2017.
 */

public interface IRegisterAPIService {

    @POST(IAPIConstants.URL_REGISTER)
    Call<RegisterResponseModel> registerUser(@Body RegisterRequestModel model);
//    Observable<BaseResponse> logoutUser(@HeaderMap Map<String, String> headers);
}
