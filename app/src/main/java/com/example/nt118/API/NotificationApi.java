package com.example.nt118.api;

import com.example.nt118.api.models.notification.NotificationResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NotificationApi {
    @GET("api/courses/{courseId}/notifications")
    Call<NotificationResponse> getCourseNotifications(
        @Header("Authorization") String token,
        @Path("courseId") String courseId
    );
} 