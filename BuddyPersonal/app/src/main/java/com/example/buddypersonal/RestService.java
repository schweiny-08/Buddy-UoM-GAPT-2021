package com.example.buddypersonal;

import retrofit.RestAdapter;

public class RestService {

    private static final String URL = "https://localhost:44346/api/";
    private retrofit.RestAdapter restAdapter;
    private BuddyService apiService;

    public RestService(){

        restAdapter = new retrofit.RestAdapter.Builder().setEndpoint(URL).setLogLevel(RestAdapter.LogLevel.FULL).build();
        apiService = restAdapter.create(BuddyService.class);
    }
    public BuddyService getService(){
        return apiService;
    }
}
