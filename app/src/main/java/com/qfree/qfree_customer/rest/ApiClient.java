package com.qfree.qfree_customer.rest;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fahad Qureshi on 10/1/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "http:///api/"; // set baseUrl here
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RestError getErrorResponse (ResponseBody errorBody) throws IOException {
        Converter<ResponseBody, RestError> converter
                = retrofit.responseBodyConverter(RestError.class, new Annotation[0]);
        return converter.convert(errorBody);


    }
}
