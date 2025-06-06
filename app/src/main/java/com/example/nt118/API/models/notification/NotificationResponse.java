package com.example.nt118.api.models.notification;

import com.google.gson.annotations.SerializedName;
import com.example.nt118.Model.CourseDetail.Notification;
import java.util.List;

public class NotificationResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("notifications")
    private List<Notification> notifications;

    @SerializedName("totalUnread")
    private int totalUnread;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public int getTotalUnread() {
        return totalUnread;
    }
} 