package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("product")
    Call<List<Product>> getProducts();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://68931a76c49d24bce869717c.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);
}



