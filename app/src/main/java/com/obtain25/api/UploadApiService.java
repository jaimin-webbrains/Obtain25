package com.obtain25.api;


public interface UploadApiService {
//    @Multipart
//    @PUT("v1/video_upload")
//    Call<ResponseBody> uploadVideo(@Part("description") String description,
//                                   @Part("price") String price,
//                                   @Part("user_id") String user_id,
//                                   @Part("shop_detail") String shop_detail,
//                                   @Part("video") RequestBody video);


    //the base URL for our API
    //make sure you are not using localhost
    //find the ip usinc ipconfig command
    String BASE_URL = BuildConstants.CURRENT_REST_URL;

    //this is our multipart request
    //we have two parameters on is name and other one is description
//    @Multipart
//    @POST("v1/video_upload")
////    Call<MyResponse> uploadImage(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file, @Part("desc") RequestBody desc);
//    Call<MyResponse> uploadVideo(@Part("description") RequestBody description,
//                                 @Part("price") RequestBody price,
//                                 @Part("user_id") RequestBody user_id,
//                                 @Part("shop_detail") RequestBody shop_detail,
//                                 @Part("video\"; filename=\"productvideo.mp4\" ") RequestBody video);

}
