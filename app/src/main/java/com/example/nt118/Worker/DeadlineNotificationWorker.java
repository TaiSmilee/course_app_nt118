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
    public static final String CHANNEL_ID = "deadline_channel";
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

        // Create notification channel (safe to call multiple times)
        createNotificationChannel();

        // Synchronously fetch data (WorkManager runs on a background thread)
        try {
            Response<DeadlineResponse> response = RetrofitClient.getInstance()
                    .getApiService()
                    .getDeadlines("Bearer " + token, studentId)
                    .execute(); // Synchronous call

            if (response.isSuccessful() && response.body() != null) {
                DeadlineResponse deadlineResponse = response.body();
                if (deadlineResponse.isSuccess()) {
                    List<DeadlineItem> deadlines = deadlineResponse.getDeadlines();
                    Log.d(TAG, "Fetched " + deadlines.size() + " deadlines.");

                    for (DeadlineItem deadline : deadlines) {
                        if (deadline.getDeadlineDate() != null) {
                            long daysRemaining = calculateDaysRemaining(deadline.getDeadlineDate());
                            // Notify if deadline is within 3 days and not yet submitted/graded
                            if (daysRemaining >= 0 && daysRemaining <= 3 &&
                                !"SUBMITTED".equals(deadline.getStatus()) &&
                                !"GRADED".equals(deadline.getStatus())) {
                                sendNotification(deadline.getTitle(), daysRemaining);
                            }
                        }
                    }
                    return Result.success();
                } else {
                    Log.e(TAG, "API Error: " + deadlineResponse.getMessage());
                    return Result.failure();
                }
            } else {
                Log.e(TAG, "Unsuccessful response: " + response.code() + " - " + response.message());
                return Result.failure();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching deadlines: " + e.getMessage(), e);
            return Result.failure();
        }
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

    private void sendNotification(String title, long daysRemaining) {
        String contentText;
        if (daysRemaining == 0) {
            contentText = "Hạn chót là HÔM NAY!";
        } else {
            contentText = "Còn " + daysRemaining + " ngày nữa là tới hạn.";
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
                .setSmallIcon(R.drawable.ic_notification) // You'll need an appropriate icon
                .setContentTitle(title)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private long calculateDaysRemaining(Date dueDate) {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Calendar dueCal = Calendar.getInstance();
        dueCal.setTime(dueDate);
        dueCal.set(Calendar.HOUR_OF_DAY, 0);
        dueCal.set(Calendar.MINUTE, 0);
        dueCal.set(Calendar.SECOND, 0);
        dueCal.set(Calendar.MILLISECOND, 0);

        long diff = dueCal.getTimeInMillis() - today.getTimeInMillis();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}