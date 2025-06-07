package com.example.nt118.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.nt118.R;
import com.example.nt118.UI.CourseDetail.CourseDetailActivity;
import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.api.models.deadline.DeadlineItem;
import com.example.nt118.api.models.deadline.DeadlineResponse;
import com.example.nt118.Model.CourseDetail.Notification;
import com.example.nt118.api.models.notification.NotificationResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private static final String DEADLINE_CHANNEL_ID = "deadline_notification_channel";
    private static final String COURSE_CHANNEL_ID = "course_notification_channel";
    private static final String DEADLINE_CHANNEL_NAME = "Deadline Notifications";
    private static final String COURSE_CHANNEL_NAME = "Course Notifications";
    private static final String DEADLINE_CHANNEL_DESCRIPTION = "Notifications for upcoming deadlines";
    private static final String COURSE_CHANNEL_DESCRIPTION = "Notifications for new course announcements";
    private static final int CHECK_INTERVAL = 30000; // 30 seconds

    // New constants for consolidated notifications
    private static final int DEADLINE_SINGLE_NOTIFICATION_ID = 1001;
    private static final int COURSE_SINGLE_NOTIFICATION_ID = 1002;
    private static final String PREF_DEADLINE_MESSAGES = "deadline_notification_messages";
    private static final String PREF_COURSE_MESSAGES = "course_notification_messages";
    private static final int MAX_INBOX_LINES = 5; // Max lines for InboxStyle

    private Handler handler;
    private Runnable checkNotificationsRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        createNotificationChannels();
        startNotificationChecks();
    }

    private void startNotificationChecks() {
        checkNotificationsRunnable = new Runnable() {
            @Override
            public void run() {
                checkDeadlines();
                checkCourseNotifications();
                handler.postDelayed(this, CHECK_INTERVAL);
            }
        };
        handler.post(checkNotificationsRunnable);
    }

    private void checkDeadlines() {
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
        String studentId = prefs.getString("STUDENT_ID", "");
        String token = prefs.getString("TOKEN", "");

        if (studentId.isEmpty() || token.isEmpty()) {
            return;
        }

        RetrofitClient.getApiService().getDeadlines("Bearer " + token, studentId).enqueue(new Callback<DeadlineResponse>() {
            @Override
            public void onResponse(Call<DeadlineResponse> call, Response<DeadlineResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DeadlineResponse deadlineResponse = response.body();
                    if (deadlineResponse.isSuccess()) {
                        List<DeadlineItem> deadlines = deadlineResponse.getDeadlines();
                        for (DeadlineItem deadline : deadlines) {
                            if (deadline.getDeadlineDate() != null) {
                                long minutesRemaining = calculateMinutesRemaining(deadline.getDeadlineDate());
                                if (minutesRemaining > 0 && minutesRemaining <= 24 * 60 &&
                                    !"SUBMITTED".equals(deadline.getStatus()) &&
                                    !"GRADED".equals(deadline.getStatus())) {
                                    sendDeadlineNotification(deadline, minutesRemaining);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DeadlineResponse> call, Throwable t) {
                Log.e(TAG, "Error checking deadlines: " + t.getMessage());
            }
        });
    }

    private void checkCourseNotifications() {
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
        String studentId = prefs.getString("STUDENT_ID", "");
        String token = prefs.getString("TOKEN", "");
        String lastNotificationId = prefs.getString("LAST_NOTIFICATION_ID", "");

        if (studentId.isEmpty() || token.isEmpty()) {
            return;
        }

        RetrofitClient.getApiService().getNotifications("Bearer " + token, studentId).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NotificationResponse notificationResponse = response.body();
                    if (notificationResponse.isSuccess()) {
                        List<Notification> notifications = notificationResponse.getNotifications();
                        for (Notification notification : notifications) {
                            int lastId = Integer.parseInt(lastNotificationId.isEmpty() ? "0" : lastNotificationId);
                            if (notification.getId() != lastId) {
                                sendCourseNotification(notification);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("LAST_NOTIFICATION_ID", String.valueOf(notification.getId()));
                                editor.apply();
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.e(TAG, "Error checking notifications: " + t.getMessage());
            }
        });
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Deadline channel
            NotificationChannel deadlineChannel = new NotificationChannel(
                    DEADLINE_CHANNEL_ID,
                    DEADLINE_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            deadlineChannel.setDescription(DEADLINE_CHANNEL_DESCRIPTION);

            // Course channel
            NotificationChannel courseChannel = new NotificationChannel(
                    COURSE_CHANNEL_ID,
                    COURSE_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            courseChannel.setDescription(COURSE_CHANNEL_DESCRIPTION);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(deadlineChannel);
                notificationManager.createNotificationChannel(courseChannel);
            }
        }
    }

    private void sendDeadlineNotification(DeadlineItem deadline, long minutesRemaining) {
        String contentText = formatDeadlineMessage(deadline.getTitle(), minutesRemaining);

        // Get existing messages
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(PREF_DEADLINE_MESSAGES, null);
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> messages = json == null ? new ArrayList<>() : gson.fromJson(json, type);

        // Add new message and limit size
        if (!messages.contains(contentText)) { // Prevent duplicate messages
            messages.add(0, contentText); // Add to the beginning
        }
        while (messages.size() > MAX_INBOX_LINES) {
            messages.remove(messages.size() - 1); // Remove oldest
        }

        // Save updated messages
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_DEADLINE_MESSAGES, gson.toJson(messages));
        editor.apply();

        Intent intent = new Intent(this, DeadlineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                DEADLINE_SINGLE_NOTIFICATION_ID, // Use fixed ID for single notification
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, DEADLINE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Upcoming Deadlines") // Consolidated title
                .setContentText(contentText) // Show the latest message in summary
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Upcoming Deadlines"); // Big title for expanded view
        for (String msg : messages) {
            inboxStyle.addLine(msg);
        }
        if (messages.size() > 1) {
            inboxStyle.setSummaryText(messages.size() + " new deadlines");
        }

        builder.setStyle(inboxStyle); // Apply InboxStyle

        NotificationManagerCompat.from(this).notify(DEADLINE_SINGLE_NOTIFICATION_ID, builder.build());
    }

    private void sendCourseNotification(Notification notification) {
        // Get existing messages
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
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

        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("COURSE_ID", notification.getCourseId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                COURSE_SINGLE_NOTIFICATION_ID, // Use fixed ID for single notification
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, COURSE_CHANNEL_ID)
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

        NotificationManagerCompat.from(this).notify(COURSE_SINGLE_NOTIFICATION_ID, builder.build());
    }

    private String formatDeadlineMessage(String title, long minutesRemaining) {
        long days = minutesRemaining / (24 * 60);
        long hours = (minutesRemaining % (24 * 60)) / 60;
        long minutes = minutesRemaining % 60;

        if (days == 0) {
            if (hours == 0) {
                if (minutes <= 5) {
                    return "CẢNH BÁO: Deadline " + title + " sắp hết hạn trong " + minutes + " phút!";
                } else {
                    return "Deadline " + title + " còn " + minutes + " phút nữa là tới hạn!";
                }
            } else {
                return "Deadline " + title + " còn " + hours + " giờ " + minutes + " phút nữa là tới hạn!";
            }
        } else if (days == 1) {
            return "Deadline " + title + " còn 1 ngày " + hours + " giờ nữa là tới hạn!";
        } else {
            return "Deadline " + title + " còn " + days + " ngày " + hours + " giờ nữa là tới hạn!";
        }
    }

    private long calculateMinutesRemaining(Date dueDate) {
        Calendar now = Calendar.getInstance();
        Calendar dueCal = Calendar.getInstance();
        dueCal.setTime(dueDate);

        long diff = dueCal.getTimeInMillis() - now.getTimeInMillis();
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null && checkNotificationsRunnable != null) {
            handler.removeCallbacks(checkNotificationsRunnable);
        }
    }
} 