package com.example.nt118.api;

import com.example.nt118.api.models.schedule.ScheduleResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ScheduleApi {
    @GET("api/schedule/student/{studentId}/date/{date}")
    Call<ScheduleResponse> getScheduleByDate(
        @Path("studentId") String studentId,
        @Path("date") String date
    );
} 