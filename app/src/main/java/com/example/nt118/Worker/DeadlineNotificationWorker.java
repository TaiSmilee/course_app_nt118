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

import com.example.nt118.api.models.deadline.DeadlineItem;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.api.models.deadline.DeadlineResponse;
import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeadlineNotificationWorker extends Worker {

    private static final String TAG = "DeadlineNotificationWorker";
    public static final String CHANNEL_ID = "deadline_notification_channel";
    public static final String CHANNEL_NAME = "Deadline Notifications";
    public static final String CHANNEL_DESCRIPTION = "Notifications for upcoming deadlines";
    public static final int NOTIFICATION_ID = 1001;

    public DeadlineNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Performing doWork...");

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
        String studentId = prefs.getString("STUDENT_ID", "");
        String token = prefs.getString("TOKEN", "");

        if (studentId.isEmpty() || token.isEmpty()) {
            Log.e(TAG, "Student ID or Token is empty. Cannot fetch deadlines.");
            return Result.failure();
        }

        createNotificationChannel();

        RetrofitClient.getApiService().getDeadlines("Bearer " + token, studentId).enqueue(new Callback<DeadlineResponse>() {
            @Override
            public void onResponse(Call<DeadlineResponse> call, Response<DeadlineResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                DeadlineResponse deadlineResponse = response.body();
                if (deadlineResponse.isSuccess()) {
                    List<DeadlineItem> deadlines = deadlineResponse.getDeadlines();
                    Log.d(TAG, "Fetched " + deadlines.size() + " deadlines.");

                    for (DeadlineItem deadline : deadlines) {
                        if (deadline.getDeadlineDate() != null) {
                                long minutesRemaining = calculateMinutesRemaining(deadline.getDeadlineDate());
                                // Notify if deadline is within 24 hours and not yet submitted/graded
                                if (minutesRemaining > 0 && minutesRemaining <= 24 * 60 &&
                                !"SUBMITTED".equals(deadline.getStatus()) &&
                                !"GRADED".equals(deadline.getStatus())) {
                                    sendNotification(deadline.getTitle(), minutesRemaining);
                                }
                            }
                        }
                    } else {
                        Log.e(TAG, "API Error: " + deadlineResponse.getMessage());
                    }
                } else {
                Log.e(TAG, "Unsuccessful response: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DeadlineResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching deadlines: " + t.getMessage(), t);
            }
        });

        return Result.success();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Deadline Reminders";
            String description = "Notifications for upcoming assignment deadlines";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void sendNotification(String title, long totalMinutesRemaining) {
        String contentText;
        long days = totalMinutesRemaining / (24 * 60);
        long hours = (totalMinutesRemaining % (24 * 60)) / 60;
        long minutes = totalMinutesRemaining % 60;

        if (days == 0) {
            if (hours == 0) {
                if (minutes <= 5) {
                    contentText = "CẢNH BÁO: Deadline " + title + " sắp hết hạn trong " + minutes + " phút!";
                } else {
                    contentText = "Deadline " + title + " còn " + minutes + " phút nữa là tới hạn!";
                }
            } else {
                contentText = "Deadline " + title + " còn " + hours + " giờ " + minutes + " phút nữa là tới hạn!";
            }
        } else if (days == 1) {
            contentText = "Deadline " + title + " còn 1 ngày " + hours + " giờ nữa là tới hạn!";
        } else {
            contentText = "Deadline " + title + " còn " + days + " ngày " + hours + " giờ nữa là tới hạn!";
        }

        Intent intent = new Intent(getApplicationContext(), DeadlineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private long calculateMinutesRemaining(Date dueDate) {
        Calendar now = Calendar.getInstance();
        Calendar dueCal = Calendar.getInstance();
        dueCal.setTime(dueDate);

        long diff = dueCal.getTimeInMillis() - now.getTimeInMillis();
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
    }
}