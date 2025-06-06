package com.example.nt118.api;

import com.example.nt118.Model.Attendance.AttendanceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AttendanceApi {
    @GET("api/attendance/student/{studentId}")
    Call<AttendanceResponse> getAttendance(@Path("studentId") String studentId);

    @GET("api/attendance/student/{studentId}/course/{courseId}")
    Call<AttendanceResponse> getCourseAttendance(
        @Path("studentId") String studentId,
        @Path("courseId") String courseId
    );
} 