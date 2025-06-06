import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.Date;

private void checkAndNotifyUpcomingDeadlines(List<DeadlineItem> deadlines) {
    for (DeadlineItem deadline : deadlines) {
        long daysRemaining = getDaysRemaining(deadline.deadline);
        if (daysRemaining <= 2 && daysRemaining >= 0 && !"completed".equalsIgnoreCase(deadline.status)) {
            showDeadlineNotification(deadline);
        }
    }
}

private long getDaysRemaining(Date deadline) {
    long diff = deadline.getTime() - System.currentTimeMillis();
    return TimeUnit.MILLISECONDS.toDays(diff);
}

private void showDeadlineNotification(DeadlineItem deadline) {
    createNotificationChannel();

    Intent intent = new Intent(this, DeadlineActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "deadline_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Deadline Approaching!")
            .setContentText(deadline.subject.code + " - " + deadline.name)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
    try {
        notificationManager.notify(deadline.id.hashCode(), builder.build());
    } catch (SecurityException e) {
        Log.e("DeadlineActivity", "Notification permission not granted", e);
        // You might want to request notification permission here
    }
}

private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        CharSequence name = "Deadline Notifications";
        String description = "Notifications for upcoming deadlines";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("deadline_channel", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}

@Override
public void onResponse(Call<DeadlineResponse> call, Response<DeadlineResponse> response) {
    swipeRefreshLayout.setRefreshing(false);
    
    Log.d("DeadlineActivity", "Response code: " + response.code());
    Log.d("DeadlineActivity", "Response URL: " + call.request().url());
    
    if (response.isSuccessful() && response.body() != null) {
        DeadlineResponse deadlineResponse = response.body();
        if (deadlineResponse.isSuccess()) {
            // Update the adapter with new data
            adapter.updateDeadlines(deadlineResponse.deadlines);
            
            // Check for upcoming deadlines and show notifications
            checkAndNotifyUpcomingDeadlines(deadlineResponse.deadlines);
            
            // Update summary
            updateSummary(deadlineResponse);
        } else {
            Log.e("DeadlineActivity", "Response status: " + deadlineResponse.status);
            Toast.makeText(DeadlineActivity.this, 
                deadlineResponse.message, Toast.LENGTH_SHORT).show();
        }
    } else {
        Log.e("DeadlineActivity", "Response not successful");
        Toast.makeText(DeadlineActivity.this, 
            "Failed to load deadlines", Toast.LENGTH_SHORT).show();
    }
}

private void updateSummary(DeadlineResponse response) {
    TextView summaryTextView = findViewById(R.id.tv_deadline_summary);
    String summaryText = String.format("Total: %d | Pending: %d | Completed: %d | Overdue: %d",
        response.totalDeadlines,
        response.pendingDeadlines,
        response.completedDeadlines,
        response.overdueDeadlines);
    summaryTextView.setText(summaryText);
} 