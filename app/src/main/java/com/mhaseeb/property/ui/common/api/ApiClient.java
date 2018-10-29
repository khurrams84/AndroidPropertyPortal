package com.mhaseeb.property.ui.common.api;


import com.mhaseeb.property.ui.common.config.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by waqas on 2/2/2017.
 */

public class ApiClient {

    private static Retrofit retrofit = null;
//    private static OkHttpClient okHttpClient = null;
//    private static HttpLoggingInterceptor httpLoggingInterceptor = null;

//    public static Retrofit getClient() {
//
//        if (retrofit == null) {
//
//            httpLoggingInterceptor = new HttpLoggingInterceptor();
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            try {
//                final TrustManager[] trustAllCerts = new TrustManager[]{
//                        new X509TrustManager() {
//                            @Override
//                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
//                                                           String authType) throws CertificateException {
//                            }
//
//
//                            @Override
//                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
//                                                           String authType) throws CertificateException {
//                            }
//
//
//                            @Override
//                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                                return new java.security.cert.X509Certificate[]{};
//                            }
//                        }
//                };
//
//                // Install the all-trusting trust manager
//                final SSLContext sslContext = SSLContext.getInstance("SSL");
//                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//                // Create an ssl socket factory with our all-trusting manager
//                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//
//                okHttpClient = new OkHttpClient.Builder()
//                        .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
//                        .hostnameVerifier(new HostnameVerifier() {
//                            @Override
//                            public boolean verify(String s, SSLSession sslSession) {
//                                return true;
//                            }
//                        })
//                        .readTimeout(30, TimeUnit.SECONDS)
//                        .writeTimeout(30, TimeUnit.SECONDS)
//                        .connectTimeout(30, TimeUnit.SECONDS)
//                        .addInterceptor(new Interceptor() {
//                            @Override
//                            public Response intercept(Chain chain) throws IOException {
//                                Request original = chain.request();
//                                Request.Builder ongoing = chain.request().newBuilder();
//                                ongoing.header("Content-Type", "application/json");
//                                ongoing.method(original.method(), original.body());
//
////                                Request request = original.newBuilder()
////                                        .header("Content-Type", "application/json")
////                                        .method(original.method(), original.body())
////                                        .build();
////                                return chain.proceed(request);
//                                return chain.proceed(ongoing.build());
//                            }
//                        })
//                        .addInterceptor(httpLoggingInterceptor)
//                        .build();
//
//                retrofit = new Retrofit.Builder()
//                        .baseUrl(AppConstants.BASE_URL)
//                        .client(okHttpClient)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return retrofit;
//    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20000, TimeUnit.SECONDS)
                    .readTimeout(20000, TimeUnit.SECONDS).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
