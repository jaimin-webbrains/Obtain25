package com.obtain25.api;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.obtain25.model.SuccessModel;
import com.obtain25.model.account.ResultGetRestoInfoById;
import com.obtain25.model.coupon.ResultDisplayActiveRestaurantCoupon;
import com.obtain25.model.coupon.ResultDisplayRestaurantCoupon;
import com.obtain25.model.login.LoginModel;
import com.obtain25.model.sendrequest.ResponseAcceptedOrRejected;
import com.obtain25.model.sendrequest.ResponseRestoDisplayUserRequest;
import com.obtain25.model.term.ResultGetTurms;
import com.obtain25.model.uploadphoto.ResultDisplayRestoPhoto;

import java.io.IOException;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by Karan Brahmaxatriya on 20-Sept-18.
 */
public class RetrofitHelper {
    public static OkHttpClient okHttpClient;
    public static Retrofit retrofit, retrofitMatchScore;
    public static CookieManager cookieManager;

    public static OkHttpClient getOkHttpClientInstance() {
        if (okHttpClient != null) {
            return okHttpClient;
        }


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(2000, TimeUnit.SECONDS);
        httpClient.readTimeout(2000, TimeUnit.SECONDS);
        httpClient.writeTimeout(2000, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
              /*  if (!Connectivity.isConnected(Betx11Application.getContext())) {
                    throw new NoConnectivityException();
                }*/
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder(); // Add Device Detail

                Request request = requestBuilder.build();
                Response response = chain.proceed(request);
                String requestedHost = request.url().host();
                assert response.networkResponse() != null;
                String responseHost = response.networkResponse().request().url().host();
                if (!requestedHost.equalsIgnoreCase(responseHost)) {
                    throw new NoConnectivityException();
                }
                return response;
            }
        });
        httpClient.addInterceptor(logging);

        return httpClient.build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .serializeNulls()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConstants.CURRENT_REST_URL)
                    .client(getOkHttpClientInstance())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit.create(serviceClass);
    }

    public static ArrayList<KeyValueModel> getKeyValueInputData(LinkedHashMap<String, String> hm) {
        ArrayList<KeyValueModel> modelList = new ArrayList<>();
        for (String key : hm.keySet()) {
            KeyValueModel obj = new KeyValueModel();
            obj.setKey(key);
            obj.setValue(hm.get(key));
            modelList.add(obj);
        }
        return modelList;
    }


    public interface Service {
        /*---------------------------------------GET METHOD------------------------------------------------------*/





        /*------------------------------------------POST METHOD---------------------------------------------------*/

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("register_restaurant")
        Call<SuccessModel> RegisterModel(@FieldMap HashMap<String, String> hashMap);


        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("login_restaurant")
        Call<LoginModel> LoginModel(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("GetTurms")
        Call<ResultGetTurms> GetTurms(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("forgetpasswordResto")
        Call<SuccessModel> forgetpassword(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("RefreshTocken")
        Call<SuccessModel> RefreshToken(@FieldMap HashMap<String, String> hashMap);

        /////////////////////coupan////////////////////////////
        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("create_coupon_request")
        Call<SuccessModel> CreateCoupon(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("display_restaurant_coupon")
        Call<ResultDisplayRestaurantCoupon> CoupanListModel(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("display_active_restaurant_coupon")
        Call<ResultDisplayActiveRestaurantCoupon> ActiveCoupanListModel(@FieldMap HashMap<String, String> hashMap);


        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("UpdateCouponInfo")
        Call<SuccessModel> EditCouponDetail(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("DeleteCouponInfo")
        Call<SuccessModel> DeleteCoupon(@FieldMap HashMap<String, String> hashMap);

        //////////////my account///////////////////

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("GetRestoInfoById")
        Call<ResultGetRestoInfoById> GetRestoInfoById(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("UpdateRestaurantInfo")
        Call<SuccessModel> UpdateRestaurantInfo(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("UpdateRestaurantBankInfo")
        Call<SuccessModel> UpdateRestaurantBankInfo(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("ChangePassword")
        Call<SuccessModel> ChangePasswordUser(@FieldMap HashMap<String, String> hashMap);


        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("DisplayRestoPhoto")
        Call<ResultDisplayRestoPhoto> DisplayRestoPhoto(@FieldMap HashMap<String, String> hashMap);

        //////////////all request//////////////////

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("RestoDisplayUserRequest")
        Call<ResponseRestoDisplayUserRequest> RestoDisplayUserRequest(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("RestoDisplayUserRequestAcceptedOrRejected")
        Call<ResponseAcceptedOrRejected> RestoDisplayUserRequestAcceptedOrRejected(@FieldMap HashMap<String, String> hashMap);


        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("UpdateRequest")
        Call<SuccessModel> UpdateRequest(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("RestoEditUserRequest")
        Call<SuccessModel> RestoEditUserRequest(@FieldMap HashMap<String, String> hashMap);

        @FormUrlEncoded
        @Headers({"Accept: application/json"})
        @POST("RestoDeleteUserRequest")
        Call<SuccessModel> RestoDeleteUserRequest(@FieldMap HashMap<String, String> hashMap);

    }


}