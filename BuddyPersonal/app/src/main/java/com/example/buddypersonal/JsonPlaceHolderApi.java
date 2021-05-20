package com.example.buddypersonal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("api/Users/Register")
    Call<User> registerUser(@Body User user);
}