package com.example.nt118.UI.Deadline;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nt118.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DeadlineAdapter extends RecyclerView.Adapter<DeadlineAdapter.ViewHolder> {
    private List<DeadlineItem> deadlines;
    private Context context;
    private SimpleDateFormat dateFormat;

    public DeadlineAdapter(Context context, List<DeadlineItem> deadlines) {
        this.context = context;
        this.deadlines = new ArrayList<>(deadlines);
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        sortDeadlines();
    }

    private void sortDeadlines() {
        Collections.sort(deadlines, new Comparator<DeadlineItem>() {
            @Override
            public int compare(DeadlineItem d1, DeadlineItem d2) {
                // Đưa các deadline đã hoàn thành xuống cuối
                if ("completed".equalsIgnoreCase(d1.status) && !"completed".equalsIgnoreCase(d2.status)) {
                    return 1;
                }
                if (!"completed".equalsIgnoreCase(d1.status) && "completed".equalsIgnoreCase(d2.status)) {
                    return -1;
                }
                
                // Sắp xếp theo thời gian cho các deadline chưa hoàn thành
                if (!"completed".equalsIgnoreCase(d1.status) && !"completed".equalsIgnoreCase(d2.status)) {
                    long days1 = getDaysRemaining(d1.deadlineDate);
                    long days2 = getDaysRemaining(d2.deadlineDate);
                    
                    // Đưa deadline quá hạn xuống sau deadline sắp đến
                    if (days1 < 0 && days2 >= 0) return 1;
                    if (days1 >= 0 && days2 < 0) return -1;
                    
                    // Sắp xếp theo thời gian còn lại
                    return Long.compare(days1, days2);
                }
                
                // Nếu cả hai đều đã hoàn thành, sắp xếp theo deadline
                return d1.deadlineDate.compareTo(d2.deadlineDate);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deadline, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DeadlineItem deadline = deadlines.get(position);
        
        // Set basic information
        holder.subjectTextView.setText(deadline.course.courseId + " - " + deadline.course.courseName);
        holder.nameTextView.setText(deadline.title);
        holder.descriptionTextView.setText(deadline.description);
        holder.deadlineTextView.setText("Due: " + dateFormat.format(deadline.deadlineDate));
        
        // Calculate days remaining
        long daysRemaining = getDaysRemaining(deadline.deadlineDate);
        setDaysRemainingText(holder.daysRemainingTextView, daysRemaining);
        
        // Set card color and style based on urgency and status
        setCardStyle(holder.cardView, daysRemaining, deadline.status, deadline.priority);
        
        // Set status with color
        setStatusText(holder.statusTextView, deadline.status);
        
        // Set priority with color
        setPriorityText(holder.priorityTextView, deadline.priority);
    }

    private long getDaysRemaining(Date deadline) {
        long diff = deadline.getTime() - System.currentTimeMillis();
        return TimeUnit.MILLISECONDS.toDays(diff);
    }

    private void setDaysRemainingText(TextView textView, long days) {
        if (days < 0) {
            textView.setText("Overdue");
            textView.setTextColor(Color.RED);
        } else if (days == 0) {
            textView.setText("Due today");
            textView.setTextColor(Color.parseColor("#FF9800")); // Orange
        } else {
            textView.setText(days + " days left");
            textView.setTextColor(days <= 2 ? Color.parseColor("#FF9800") : Color.parseColor("#4CAF50"));
        }
    }

    private void setCardStyle(CardView cardView, long daysRemaining, String status, String priority) {
        int backgroundColor;
        float elevation = 4f; // Default elevation

        if ("COMPLETED".equalsIgnoreCase(status)) {
            backgroundColor = Color.parseColor("#F5F5F5"); // Light gray
            elevation = 2f; // Lower elevation for completed items
        } else {
            if (daysRemaining < 0 || "OVERDUE".equalsIgnoreCase(status)) {
                backgroundColor = Color.parseColor("#FFEBEE"); // Light red for overdue
                elevation = 6f;
            } else if (daysRemaining == 0) {
                backgroundColor = Color.parseColor("#FFF3E0"); // Light orange for due today
                elevation = 8f;
            } else if (daysRemaining <= 2) {
                backgroundColor = Color.parseColor("#FFF8E1"); // Light yellow for upcoming
                elevation = 6f;
            } else {
                if ("HIGH".equalsIgnoreCase(priority)) {
                    backgroundColor = Color.parseColor("#FCE4EC"); // Light pink for high priority
                } else if ("MEDIUM".equalsIgnoreCase(priority)) {
                    backgroundColor = Color.parseColor("#F3E5F5"); // Light purple for medium priority
                } else {
                    backgroundColor = Color.parseColor("#E8F5E9"); // Light green for low priority
                }
            }
        }

        cardView.setCardBackgroundColor(backgroundColor);
        cardView.setCardElevation(elevation);
    }

    private void setStatusText(TextView textView, String status) {
        String displayStatus = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        textView.setText(displayStatus);
        int backgroundColor;
        int textColor;
        switch (status.toUpperCase()) {
            case "COMPLETED":
                backgroundColor = Color.parseColor("#E8F5E9");
                textColor = Color.parseColor("#4CAF50");
                break;
            case "OVERDUE":
                backgroundColor = Color.parseColor("#FFEBEE");
                textColor = Color.RED;
                break;
            default: // PENDING
                backgroundColor = Color.parseColor("#E3F2FD");
                textColor = Color.parseColor("#2196F3");
                break;
        }
        textView.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
        textView.setTextColor(textColor);
    }

    private void setPriorityText(TextView textView, String priority) {
        String displayPriority = priority.substring(0, 1).toUpperCase() + priority.substring(1).toLowerCase();
        textView.setText(displayPriority);
        int backgroundColor;
        int textColor;
        switch (priority.toUpperCase()) {
            case "HIGH":
                backgroundColor = Color.parseColor("#FFEBEE");
                textColor = Color.RED;
                break;
            case "MEDIUM":
                backgroundColor = Color.parseColor("#FFF3E0");
                textColor = Color.parseColor("#FF9800");
                break;
            default: // LOW
                backgroundColor = Color.parseColor("#E8F5E9");
                textColor = Color.parseColor("#4CAF50");
                break;
        }
        textView.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
        textView.setTextColor(textColor);
    }

    @Override
    public int getItemCount() {
        return deadlines.size();
    }

    public void updateDeadlines(List<DeadlineItem> newDeadlines) {
        this.deadlines = new ArrayList<>(newDeadlines);
        sortDeadlines();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectTextView;
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView deadlineTextView;
        public TextView statusTextView;
        public TextView priorityTextView;
        public TextView daysRemainingTextView;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            subjectTextView = view.findViewById(R.id.tv_subject);
            nameTextView = view.findViewById(R.id.tv_name);
            descriptionTextView = view.findViewById(R.id.tv_description);
            deadlineTextView = view.findViewById(R.id.tv_deadline);
            statusTextView = view.findViewById(R.id.tv_status);
            priorityTextView = view.findViewById(R.id.tv_priority);
            daysRemainingTextView = view.findViewById(R.id.tv_days_remaining);
        }
    }
} 