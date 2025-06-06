package com.example.nt118.api;

import com.example.nt118.Model.Deadline.DeadlineResponse;
import com.example.nt118.Model.Deadline.DeadlineItem;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DeadlineApi {
    @GET("api/deadlines/student/{studentId}/course/{courseId}")
    Call<DeadlineResponse> getDeadlines(@Path("studentId") String studentId, @Path("courseId") String courseId);

    @GET("deadlines/course/{courseId}")
    Call<List<DeadlineItem>> getDeadlinesByCourse(@Path("courseId") String courseId);

    @GET("deadlines")
    Call<List<DeadlineItem>> getAllDeadlines();
} 