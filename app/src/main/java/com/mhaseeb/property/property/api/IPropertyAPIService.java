package com.mhaseeb.property.property.api;

import com.mhaseeb.property.common.config.IAPIConstants;
import com.mhaseeb.property.property.model.FavoritesModel;
import com.mhaseeb.property.property.model.FavoritesResponseModel;
import com.mhaseeb.property.property.model.GetUserPropertiesResponseModel;
import com.mhaseeb.property.property.model.MarkFavouriteResponse;
import com.mhaseeb.property.property.model.PropertyModel;
import com.mhaseeb.property.property.model.PropertyResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Mohammad Haseeb on 10/31/2018.
 */

public interface IPropertyAPIService {

//    @POST(IAPIConstants.URL_UPLOAD_PROPERTY_IMAGE)
//    @FormUrlEncoded
//    Call<APIResponseModel> uploadPropertyImage(@Field("image") String image,
//                                               @Field("propertyId") String propertyId
//    );


    @Multipart
    @POST(IAPIConstants.URL_UPLOAD_PROPERTY_IMAGE)
    Call<PropertyResponseModel> uploadImage(@Part MultipartBody.Part image, @Part("propertyId") RequestBody propertyId);

    @POST(IAPIConstants.URL_ADD_PROPERTY)
    Call<PropertyResponseModel> addproperty(@Body PropertyModel model);

    @PUT(IAPIConstants.URL_ADD_PROPERTY)
    Call<PropertyResponseModel> updateproperty(@Body PropertyModel model);

    @GET(IAPIConstants.URL_GET_USER_PROPERTIES)
    Call<GetUserPropertiesResponseModel> getUserProperties(@Query("userId") int userId);

    @GET(IAPIConstants.URL_SEARCH)
    Call<GetUserPropertiesResponseModel> getAllProperties(@Query("query") String query);

    @GET(IAPIConstants.URL_SEARCH)
    Call<GetUserPropertiesResponseModel> getAllUserProperties(@Query("userId") int userId, @Query("query") String query);

    @PUT(IAPIConstants.URL_MARK_FAVORITE)
    Call<MarkFavouriteResponse> markFavoriteProperty(@Body FavoritesModel model);

    @GET(IAPIConstants.URL_GET_FAVORITE_PROPERTY)
    Call<FavoritesResponseModel> getFavoriteProperties(@Query("userId") int userId);
}
