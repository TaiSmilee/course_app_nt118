package com.example.nt118.api;

import com.example.nt118.api.models.grades.GradeResponse;
import com.example.nt118.api.models.semester.SemesterListResponse;
import com.example.nt118.api.models.course.CourseDetailResponse;
import com.example.nt118.api.models.deadline.DeadlineResponse;
import com.example.nt118.Model.Tuition.TuitionResponse;
import com.example.nt118.Model.CourseDetail.Notification;
import com.example.nt118.api.models.notification.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ApiService {
    @GET("api/semesters")
    Call<SemesterListResponse> getSemesters(@Header("Authorization") String token);

    @GET("api/semesters/{semesterId}/grades")
    Call<GradeResponse> getSemesterGrades(
        @Header("Authorization") String token,
        @Path("semesterId") String semesterId
    );

    @GET("api/semesters/{semesterId}/courses/{courseId}")
    Call<CourseDetailResponse> getCourseDetail(
        @Header("Authorization") String token,
        @Path("semesterId") String semesterId,
        @Path("courseId") String courseId
    );

    @GET("api/academic-report/{studentId}/semester/{semesterId}")
    Call<GradeResponse> getStudentGrades(
        @Header("Authorization") String token,
        @Path("studentId") String studentId,
        @Path("semesterId") String semesterId
    );

    @GET("api/deadlines/student/{studentId}")
    Call<DeadlineResponse> getDeadlines(
        @Header("Authorization") String token,
        @Path("studentId") String studentId
    );

    @GET("api/student/tuition")
    Call<TuitionResponse> getTuitionInfo(
        @Query("semester") String semester,
        @Header("Authorization") String token
    );

    @GET("api/courses/{courseId}/notifications")
    Call<NotificationResponse> getCourseNotifications(
        @Header("Authorization") String token,
        @Path("courseId") String courseId
    );
} 