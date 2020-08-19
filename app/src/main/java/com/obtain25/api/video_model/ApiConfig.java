package com.obtain25.api.video_model;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ApiConfig {
    @Multipart
    @POST("UpdateRestoPhoto")
    Call<ServerResponse> upload(
            @Header("Accept") String authorization,
            @PartMap Map<String, RequestBody> map
    );

    @Multipart
    @POST("RestomultiPhoto")
    Call<ServerResponse> RestomultiPhotoUpload(
            @Header("Accept") String authorization,
            @PartMap Map<String, RequestBody> map
    );


}
