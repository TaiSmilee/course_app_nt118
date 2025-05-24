package com.example.nt118.API;

import com.example.nt118.UI.Course;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CourseApi {
    @GET("api/courses/student/{studentId}")
    Call<List<Course>> getCoursesByStudentId(@Path("studentId") String studentId);
}
