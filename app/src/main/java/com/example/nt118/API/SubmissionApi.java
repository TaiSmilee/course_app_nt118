package com.example.nt118.api;

import com.example.nt118.Model.Submission.SubmissionDetailResponse;
import com.example.nt118.Model.Submission.SubmissionResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface SubmissionApi {
    @Multipart
    @POST("submissions")
    Call<SubmissionResponse> submitAssignment(
        @Part MultipartBody.Part file,
        @Part("notificationId") RequestBody notificationId,
        @Part("courseId") RequestBody courseId,
        @Part("studentId") RequestBody studentId,
        @Part("comment") RequestBody comment,
        @Part("submittedAt") RequestBody submittedAt
    );

    @GET("submissions/{submissionId}")
    Call<SubmissionDetailResponse> getSubmissionDetail(
        @Path("submissionId") String submissionId
    );

    @GET("submissions")
    Call<SubmissionResponse> getSubmissions();
} 