package com.mhaseeb.property.common.api;

/**
 * Created by Mohammad Haseeb on 2/2/2017.
 */

public interface ApiInterface {

    /*@GET(AppConstants.URL_GET_METADATA)
    Call<MetaDataResponseModel> getMetaData();

    @POST(AppConstants.URL_ADD_PRODUCT)
    @FormUrlEncoded
    Call<APIResponseModel> addProduct(@Field("category") String category,
                              @Field("sub_category1") String sub_category1,
                              @Field("sub_category2") String sub_category2,
                              @Field("username") String username,
                              @Field("product_name") String product_name,
                              @Field("premises") String premises,
                              @Field("price") String price

    );

    @GET(AppConstants.URL_GET_PRICE)
    Call<APIResponseModel> getProductPrices(@Path("productName") String productName);

    @POST(AppConstants.URL_REGISTER_VOLUNTEER)
    Call<RegisterationResponseModel> registerVolunteer(@Body VolunteerRegistrationModel mrModel);

    @GET(AppConstants.URL_LOGIN)
    Call<LoginResponseModel> loginAPI(@Path("username") String username, @Path("password") String password);

    @GET(AppConstants.URL_AUTHENTICATE_MEMBERSHIP_ID)
    Call<RegisterationResponseModel> authMembershipAPI(@Path("MemberID") String MemberID);

    @GET(AppConstants.URL_GET_PUBLIC_POLLS)
    Call<PublicPollModel> getPublicPolls();

    @POST(AppConstants.URL_GET_SUBMIT_PUBLIC_POLLS)
    Call<CustomResponseModel> submitPublicPolls(@Body PublicPollRequestModel pprm);

    @GET(AppConstants.URL_GET_PARTY_POLLS)
    Call<PartyPollModel> getPartyPolls();

    @POST(AppConstants.URL_GET_SUBMIT_PARTY_POLLS)
    Call<CustomResponseModel> submitPartyPolls(@Body PartyPollRequestModel pprm);

    @POST(AppConstants.URL_SPEAKUP_MESSAGE)
    Call<CustomResponseModel> speakUpMessage(@Body SpeakupMessageRequestModel smrm);

    @POST(AppConstants.URL_EXPORT_MEMBERS)
    Call<List<String>> ExportMembers(@Body ArrayList<CMemberOffline> exportModel);

    @GET(AppConstants.URL_GET_CITIES)
    Call<List<SpinnerCitiesModel>> getCities(@Path("countryCode") String countryCode);

    @GET(AppConstants.URL_GET_REGISTRATION_META_DATA)
    Call<RegistrationTerritoryModel> getMetaData();

    @GET(AppConstants.URL_GET_MASTER_DATA)
    Call<MasterDataModel> getMasterData();*/

//    @GET(AppConstants.URL_IMPORT_PO_RECEIVING_DATA)
//    Call<List<POReceivingModel>> getPOReceivingData(@Path("storeID") String storeID);
//
////    @GET(AppConstants.URL_IMPORT_PO_ITEM)
////    Call<List<POItemModel>> getPOItemData(@Path("storeID") String storeID);
//
//
//    @POST(AppConstants.URL_EXPORT_PO_RECEIVING_DATA)
//    Call<ResponseBody> uploadPOReceivingData(@Path("storeID") String storeID, @Body List<POReceivingExportModel> poieList);

    //    @GET("/download/{storeId}/getItemMaster")
//    Call<List<AppConfig.ItemMasterRepo>> repoForItemMaster(@Header("username") String username, @Header("password") String password, @Path("storeId") String storeId);

//    @POST(AppConstants.URL_EXPORT_METER_DATA)
//    Call<ResponseBody> uploadMeterData(@Body List<ExportDataModel> cmdm);

//    @GET(AppConstants.URL_GET_ORDER_HISTORY)
//    Call<List<OrderHistoryModel>> getOrderHistory(@Query("cusCode") String cusCode, @Query("companyCode") String companyCode);
//
//    @POST(AppConstants.URL_CREATE_ORDER)
//    Call<ConfirmOrderResponseModel> createOrder(@Body ConfirmOrderRequestModel confirmOrderRequestModel);

//    @POST("api/getProducts{productsId}")
//    Call<MetaDataResponseModel> getProducts(@Path("productsId") int productsId, @Header("Authorization") String token);
//
//    @POST("api/studentdata/get/{schoolId}/{dateFrom}")
//    Call<StudentDataResponseModel> getStudentData(@Path("schoolId") int schoolId, @Path("dateFrom") String dateFrom, @Header("Authorization") String token);
//
//    @POST(AppConstants.URL_UPLOAD_STUDENTS)
//    Call<ResponseBody> uploadStudents(@Body StudentModel sm, @Header("Authorization") String token);
//
//    @POST(AppConstants.URL_UPLOAD_ENROLLMENTS)
//    Call<ResponseBody> uploadEnrollments(@Body EnrollmentUploadModel eum, @Header("Authorization") String token);
//
//    @POST(AppConstants.URL_UPLOAD_ATTENDANCE)
//    Call<ResponseBody> uploadAttendance(@Body AttendanceUploadModel aum, @Header("Authorization") String token);
//
//    @POST(AppConstants.URL_UPLOAD_PROMOTION)
//    Call<ResponseBody> uploadPromotion(@Body UploadPromotionModel upm, @Header("Authorization") String token);
//
//    @POST(AppConstants.URL_UPLOAD_WITHDRAWAL)
//    Call<ResponseBody> uploadWithdrawal(@Body WithdrawalModel withdrawalModels, @Header("Authorization") String token);
//
//    @POST(AppConstants.URL_UPLOAD_SCHOOL_AUDITS)
//    Call<ResponseBody> uploadSchoolAudits(@Body UploadStudentsAuditModel saModel, @Header("Authorization") String token);
}
