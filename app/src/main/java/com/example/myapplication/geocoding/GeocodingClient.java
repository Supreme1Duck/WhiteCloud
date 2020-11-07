package com.example.myapplication.geocoding;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeocodingClient {
    private static final String BASE_URL = "https://api.mapbox.com";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Response<Context> getApiService(String address) {
        return getRetrofitInstance().create(ApiService.class).Call(address);
    }
}
