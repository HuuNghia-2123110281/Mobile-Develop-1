package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface UserApi {
    @GET("users") // endpoint
    Call<List<User>> getUsers();
}

