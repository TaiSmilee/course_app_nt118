package com.example.nt118.UI.Deadline;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nt118.R;
import com.example.nt118.UI.CourseDetailActivity;
import com.example.nt118.api.models.deadline.DeadlineItem;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DeadlineAdapter extends RecyclerView.Adapter<DeadlineAdapter.DeadlineViewHolder> {
    private static final String TAG = "DeadlineAdapter";
    private final Context context;
    private List<DeadlineItem> deadlines;
    private final SimpleDateFormat dateFormat;

    public DeadlineAdapter(Context context, List<DeadlineItem> deadlines) {
        this.context = context;
        this.deadlines = deadlines;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Log.d(TAG, "Adapter created with " + (deadlines != null ? deadlines.size() : 0) + " items");
    }

    @NonNull
    @Override
    public DeadlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_deadline, parent, false);
        return new DeadlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeadlineViewHolder holder, int position) {
        DeadlineItem deadline = deadlines.get(position);
        Log.d(TAG, "Binding deadline at position " + position + ": " + deadline.getTitle());
        
        holder.tvTitle.setText(deadline.getTitle());
        holder.tvCourse.setText(String.format("%s - %s", 
            deadline.getCourse().getCourseId(), 
            deadline.getCourse().getCourseName()));
        holder.tvDescription.setText(deadline.getDescription());
        holder.tvDeadline.setText(dateFormat.format(deadline.getDeadlineDate()));
        holder.tvStatus.setText(deadline.getStatus());
        holder.tvTeacher.setText(deadline.getTeacher().getName());
        holder.tvPriority.setText(deadline.getPriority());
        
        // Set card color based on status
        int cardColor = getStatusColor(deadline.getStatus());
        holder.cardView.setCardBackgroundColor(cardColor);
        
        // Set text colors based on card background
        boolean isDarkBackground = isDarkColor(cardColor);
        int textColor = isDarkBackground ? Color.WHITE : Color.BLACK;
        holder.tvTitle.setTextColor(textColor);
        holder.tvDescription.setTextColor(textColor);
        holder.tvCourse.setTextColor(textColor);
        holder.tvDeadline.setTextColor(textColor);
        holder.tvStatus.setTextColor(textColor);
        holder.tvTeacher.setTextColor(textColor);
        holder.tvPriority.setTextColor(textColor);

        // Add click listener to navigate to CourseDetailActivity
        holder.cardView.setOnClickListener(v -> {
            String courseId = deadline.getCourse().getCourseId();
            Intent intent = new Intent(context, CourseDetailActivity.class);
            intent.putExtra("courseId", courseId);
            intent.putExtra("deadlineId", deadline.getId());
            intent.putExtra("deadlineTitle", deadline.getTitle());
            intent.putExtra("deadlineDescription", deadline.getDescription());
            intent.putExtra("deadlineDate", deadline.getDeadlineDate().getTime());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return deadlines != null ? deadlines.size() : 0;
    }

    public void updateDeadlines(List<DeadlineItem> newDeadlines) {
        Log.d(TAG, "Updating adapter with " + (newDeadlines != null ? newDeadlines.size() : 0) + " items");
        this.deadlines = newDeadlines;
        notifyDataSetChanged();
    }

    private int getStatusColor(String status) {
        switch (status.toUpperCase()) {
            case "PENDING":
                return Color.parseColor("#FFF9C4"); // Light yellow
            case "COMPLETED":
                return Color.parseColor("#C8E6C9"); // Light green
            case "OVERDUE":
                return Color.parseColor("#FFCDD2"); // Light red
            default:
                return Color.WHITE;
        }
    }

    private boolean isDarkColor(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness >= 0.5;
    }

    static class DeadlineViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTitle;
        TextView tvCourse;
        TextView tvDescription;
        TextView tvDeadline;
        TextView tvStatus;
        TextView tvTeacher;
        TextView tvPriority;

        public DeadlineViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCourse = itemView.findViewById(R.id.tvCourse);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDeadline = itemView.findViewById(R.id.tvDeadline);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTeacher = itemView.findViewById(R.id.tvTeacher);
            tvPriority = itemView.findViewById(R.id.tvPriority);
        }
    }
}
