package com.example.nt118.API;
import com.example.nt118.Model.LoginRequest;
import com.example.nt118.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
