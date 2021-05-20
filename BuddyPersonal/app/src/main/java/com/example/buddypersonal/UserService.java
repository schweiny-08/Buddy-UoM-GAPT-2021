//package com.example.buddypersonal;
//
//import java.util.List;
//
//import retrofit.Callback;
//import retrofit.http.Body;
//import retrofit.http.DELETE;
//import retrofit.http.GET;
//import retrofit.http.POST;
//import retrofit.http.PUT;
//import retrofit.http.Path;
//
//public interface UserService {
//
//    @GET("Users/getAllUsers")
//    public void getAllUsers(Callback<List<User>> callback);
//
//    @GET("Users/getUserById/{id}")
//    public void getUserById(@Path("id")Integer id, Callback<User> callback);
//
//    @PUT("Users/editUserById/{id}")
//    public void editUserById(@Path("id")Integer id, @Body User user, Callback<User> callback);
//
//    @POST("Users/addUser")
//    public void addUser(@Body User user, Callback<User> callback);
//
//    @DELETE("Users/deleteUserById/{id}")
//    public void deleteUserById(@Path("id")Integer id, Callback<User> callback);
//
//    @POST("Users/Register")
//    public void Register(Callback<User> callback);
//
//    @POST("Users/Login")
//    public void Login(Callback<User> callback);
//
//    @GET("Users/GetSession")
//    public void GetSession(Callback<User> callback);
//
//    @GET("Users/Logout")
//    public void Logout(Callback<User> callback);
//}
