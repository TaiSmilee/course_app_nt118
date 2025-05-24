package com.example.nt118.api;

import com.example.nt118.Model.LoginRequest;
import com.example.nt118.Model.LoginResponse;
import com.example.nt118.Model.StudentProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApi {
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("api/students/profile/{studentId}")
    Call<StudentProfileResponse> getStudentProfile(
        @Header("Authorization") String token,
        @Path("studentId") String studentId
    );
} 