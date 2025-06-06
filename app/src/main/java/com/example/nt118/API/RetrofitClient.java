package com.example.nt118.api;

import com.example.nt118.api.utils.DateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/"; // URL của Spring Boot API
    private static RetrofitClient instance;
    private final Retrofit retrofit;
    private final AuthApi authApi;
    private final CourseApi courseApi;
    private final ScheduleApi scheduleApi;
    private final ApiService apiService;
    private final NotificationApi notificationApi;

    private RetrofitClient() {
        // Create logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Create OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        // Create Gson with custom type adapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        // Create Retrofit instance
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Create API instances
        authApi = retrofit.create(AuthApi.class);
        courseApi = retrofit.create(CourseApi.class);
        scheduleApi = retrofit.create(ScheduleApi.class);
        apiService = retrofit.create(ApiService.class);
        notificationApi = retrofit.create(NotificationApi.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static ApiService getApiService() {
        return getInstance().apiService;
    }

    public <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public AuthApi getAuthApi() {
        return authApi;
    }

    public CourseApi getCourseApi() {
        return courseApi;
    }

    public ScheduleApi getScheduleApi() {
        return scheduleApi;
    }

    public SubmissionApi getSubmissionApi() {
        return retrofit.create(SubmissionApi.class);
    }

    public DeadlineApi getDeadlineApi() {
        return retrofit.create(DeadlineApi.class);
    }

    public AttendanceApi getAttendanceApi() {
        return retrofit.create(AttendanceApi.class);
    }

    public NotificationApi getNotificationApi() {
        return notificationApi;
    }
}
