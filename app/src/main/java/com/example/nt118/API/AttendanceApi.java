package com.example.nt118.api;

import com.example.nt118.Model.Attendance.AttendanceDetailResponse;
import com.example.nt118.Model.Attendance.AttendanceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AttendanceApi {
    @GET("api/attendance/student/{studentId}")
    Call<AttendanceResponse> getStudentAttendance(@Path("studentId") String studentId);

    @GET("api/attendance/course/{courseId}/student/{studentId}")
    Call<AttendanceDetailResponse> getCourseAttendance(
        @Path("courseId") String courseId,
        @Path("studentId") String studentId
    );
} 