package com.karanbrahmaxatriya.getit.model;


import com.obtain25.api.RetrofitHelper;
import com.obtain25.model.error.Errors;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static Errors parseError(Response<?> response) {
//        RetrofitHelper.createService(RetrofitHelper.Service.class)
        Converter<ResponseBody, Errors> converter = RetrofitHelper.retrofit.responseBodyConverter(Errors.class, new Annotation[0]);
//                ServiceGenerator.retrofit()


        Errors error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new Errors();
        }

        return error;
    }
}