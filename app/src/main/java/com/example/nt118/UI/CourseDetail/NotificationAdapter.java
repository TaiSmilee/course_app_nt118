package com.example.nt118.UI.CourseDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nt118.Model.CourseDetail.Notification;
import com.example.nt118.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends ArrayAdapter<Notification> {
    private Context context;
    private List<Notification> notifications;
    private SimpleDateFormat dateFormat;

    public NotificationAdapter(Context context, List<Notification> notifications) {
        super(context, R.layout.item_notification, notifications);
        this.context = context;
        this.notifications = notifications;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        }

        Notification notification = notifications.get(position);

        TextView tvTitle = convertView.findViewById(R.id.tvNotificationTitle);
        TextView tvDescription = convertView.findViewById(R.id.tvNotificationDescription);
        TextView tvDate = convertView.findViewById(R.id.tvNotificationDeadline);

        tvTitle.setText(notification.getTitle());
        tvDescription.setText(notification.getDescription());
        tvDate.setText(dateFormat.format(notification.getCreatedAt()));

        return convertView;
    }
} 