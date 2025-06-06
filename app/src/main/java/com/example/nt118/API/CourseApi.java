package com.example.nt118.api;

import com.example.nt118.UI.Course;
import com.example.nt118.Model.CourseDetail.CourseDetailResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CourseApi {
    @GET("api/courses/student/{studentId}")
    Call<List<Course>> getCoursesByStudentId(@Path("studentId") String studentId);

    @GET("api/courses/{studentId}/{courseId}")
    Call<CourseDetailResponse> getCourseDetail(
        @Path("studentId") String studentId,
        @Path("courseId") String courseId
    );
}
