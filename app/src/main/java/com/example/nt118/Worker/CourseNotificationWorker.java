package com.example.nt118.Worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.nt118.R;
import com.example.nt118.UI.CourseDetail.CourseDetailActivity;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.api.models.notification.NotificationResponse;
import com.example.nt118.Model.CourseDetail.Notification;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseNotificationWorker extends Worker {
    private static final String TAG = "CourseNotificationWorker";
    public static final String CHANNEL_ID = "course_notification_channel";
    public static final String CHANNEL_NAME = "Course Notifications";
    public static final String CHANNEL_DESCRIPTION = "Notifications for new course announcements";
    private static final int NOTIFICATION_ID = 2001;

    // New constants for consolidated notifications
    private static final int COURSE_SINGLE_NOTIFICATION_ID = 1002;
    private static final String PREF_COURSE_MESSAGES = "course_notification_messages";
    private static final int MAX_INBOX_LINES = 5; // Max lines for InboxStyle

    public CourseNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Performing course notification check...");

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
        String studentId = prefs.getString("STUDENT_ID", "");
        String token = prefs.getString("TOKEN", "");

        if (studentId.isEmpty() || token.isEmpty()) {
            Log.e(TAG, "Student ID or Token is empty. Cannot fetch notifications.");
            return Result.failure();
        }

        createNotificationChannel();

        // Get the last notification ID we've seen
        String lastNotificationId = prefs.getString("LAST_NOTIFICATION_ID", "");

        RetrofitClient.getApiService().getNotifications("Bearer " + token, studentId).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NotificationResponse notificationResponse = response.body();
                    if (notificationResponse.isSuccess()) {
                        List<Notification> notifications = notificationResponse.getNotifications();
                        Log.d(TAG, "Fetched " + notifications.size() + " notifications.");

                        // Check for new notifications
                        for (Notification notification : notifications) {
                            int lastId = Integer.parseInt(lastNotificationId.isEmpty() ? "0" : lastNotificationId);
                            if (notification.getId() != lastId) {
                                // This is a new notification
                                sendNotification(notification);
                                
                                // Update the last seen notification ID
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("LAST_NOTIFICATION_ID", String.valueOf(notification.getId()));
                                editor.apply();
                                
                                break; // Only show the most recent new notification
                            }
                        }
                    } else {
                        Log.e(TAG, "API Error: " + notificationResponse.getMessage());
                    }
                } else {
                    Log.e(TAG, "Unsuccessful response: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching notifications: " + t.getMessage(), t);
            }
        });

        return Result.success();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void sendNotification(Notification notification) {
        // Get existing messages
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(PREF_COURSE_MESSAGES, null);
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> messages = json == null ? new ArrayList<>() : gson.fromJson(json, type);

        String notificationMessage = notification.getTitle() + ": " + notification.getContent();

        // Add new message and limit size
        if (!messages.contains(notificationMessage)) { // Prevent duplicate messages
            messages.add(0, notificationMessage); // Add to the beginning
        }
        while (messages.size() > MAX_INBOX_LINES) {
            messages.remove(messages.size() - 1); // Remove oldest
        }

        // Save updated messages
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_COURSE_MESSAGES, gson.toJson(messages));
        editor.apply();

        // Create an intent to open the specific course
        Intent intent = new Intent(getApplicationContext(), CourseDetailActivity.class);
        intent.putExtra("COURSE_ID", notification.getCourseId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                COURSE_SINGLE_NOTIFICATION_ID, // Use fixed ID for single notification
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("New Course Announcements") // Consolidated title
                .setContentText(notificationMessage) // Show the latest message in summary
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("New Course Announcements"); // Big title for expanded view
        for (String msg : messages) {
            inboxStyle.addLine(msg);
        }
        if (messages.size() > 1) {
            inboxStyle.setSummaryText(messages.size() + " new announcements");
        }

        builder.setStyle(inboxStyle); // Apply InboxStyle

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(COURSE_SINGLE_NOTIFICATION_ID, builder.build());
    }
} 