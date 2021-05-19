package com.example.buddypersonal;

import retrofit.RestAdapter;

public class RestService {

    private static final String URL = "https://localhost:44346/api/";
    private retrofit.RestAdapter restAdapter;
    private UserService apiService;

    public RestService(){

        restAdapter = new retrofit.RestAdapter.Builder().setEndpoint(URL).setLogLevel(RestAdapter.LogLevel.FULL).build();
        apiService = restAdapter.create(UserService.class);
    }
    public UserService getService(){
        return apiService;
    }
}
