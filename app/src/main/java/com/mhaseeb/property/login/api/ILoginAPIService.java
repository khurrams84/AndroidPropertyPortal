package com.mhaseeb.property.login.api;

import com.mhaseeb.property.common.config.IAPIConstants;
import com.mhaseeb.property.login.model.LoginRequestModel;
import com.mhaseeb.property.login.model.LoginResponseModel;

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
