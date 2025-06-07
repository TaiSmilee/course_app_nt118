package com.example.nt118.UI.CourseDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.nt118.R;
import com.example.nt118.Model.CourseDetail.Notification;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends ArrayAdapter<Notification> {
    private Context context;
    private List<Notification> notifications;

    public NotificationAdapter(Context context, List<Notification> notifications) {
        super(context, 0, notifications);
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        }

        Notification notification = getItem(position);
        if (notification != null) {
            TextView tvTitle = convertView.findViewById(R.id.tvTitle);
            TextView tvContent = convertView.findViewById(R.id.tvContent);
            TextView tvDate = convertView.findViewById(R.id.tvDate);
            TextView tvType = convertView.findViewById(R.id.tvType);

            tvTitle.setText(notification.getTitle());
            tvContent.setText(notification.getContent());
            
            // Format date
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                Date date = inputFormat.parse(notification.getCreatedAt());
                if (date != null) {
                    tvDate.setText(outputFormat.format(date));
                }
            } catch (ParseException e) {
                tvDate.setText(notification.getCreatedAt());
            }

            // Set notification type
            String type = notification.getType();
            if ("ANNOUNCEMENT".equals(type)) {
                tvType.setText("Thông báo");
            } else if ("DEADLINE".equals(type)) {
                tvType.setText("Deadline");
            } else if ("GRADE".equals(type)) {
                tvType.setText("Điểm số");
            } else {
                tvType.setText(type);
            }

            // Set background color based on read status
            if (notification.isRead()) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.unread_notification));
            }
        }

        return convertView;
    }
} 