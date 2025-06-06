package com.example.nt118.UI.CourseDetail;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.example.nt118.R;
import com.example.nt118.Model.Deadline.DeadlineItem;
import com.example.nt118.UI.submission.SubmissionActivity;
import com.example.nt118.UI.submission.SubmissionDetailActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CourseDeadlineAdapter extends ArrayAdapter<DeadlineItem> {
    private static final String TAG = "CourseDeadlineAdapter";
    private Context context;
    private List<DeadlineItem> deadlines;
    private SimpleDateFormat dateFormat;

    public CourseDeadlineAdapter(@NonNull Context context, @NonNull List<DeadlineItem> deadlines) {
        super(context, R.layout.item_course_deadline, deadlines);
        this.context = context;
        this.deadlines = deadlines;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Log.d(TAG, "Adapter created with " + deadlines.size() + " items");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_deadline, parent, false);
        }

        DeadlineItem deadline = getItem(position);
        if (deadline != null) {
            TextView tvTitle = convertView.findViewById(R.id.tvTitle);
            TextView tvCourse = convertView.findViewById(R.id.tvCourse);
            TextView tvDescription = convertView.findViewById(R.id.tvDescription);
            TextView tvDeadline = convertView.findViewById(R.id.tvDeadline);
            TextView tvStatus = convertView.findViewById(R.id.tvStatus);
            TextView tvTeacher = convertView.findViewById(R.id.tvTeacher);
            Button btnSubmit = convertView.findViewById(R.id.btnSubmit);
            Button btnViewSubmission = convertView.findViewById(R.id.btnViewSubmission);

            // Set title and course
            tvTitle.setText(deadline.getTitle());
            tvCourse.setText(deadline.getCourseName());
            
            // Set description
            tvDescription.setText(deadline.getDescription());
            
            // Format due date
            String dueDateStr = dateFormat.format(deadline.getDueDate());
            tvDeadline.setText("Hạn nộp: " + dueDateStr);

            // Set status text and color
            String statusText;
            int statusColor;
            switch (deadline.getStatus()) {
                case "SUBMITTED":
                    statusText = "Đã nộp";
                    statusColor = ContextCompat.getColor(context, R.color.green);
                    btnSubmit.setVisibility(View.GONE);
                    btnViewSubmission.setVisibility(View.VISIBLE);
                    break;
                case "GRADED":
                    statusText = "Đã chấm điểm";
                    statusColor = ContextCompat.getColor(context, R.color.blue);
                    btnSubmit.setVisibility(View.GONE);
                    btnViewSubmission.setVisibility(View.VISIBLE);
                    break;
                case "LATE":
                    statusText = "Nộp muộn";
                    statusColor = ContextCompat.getColor(context, R.color.orange);
                    btnSubmit.setVisibility(View.GONE);
                    btnViewSubmission.setVisibility(View.VISIBLE);
                    break;
                default:
                    statusText = "Chưa nộp";
                    statusColor = ContextCompat.getColor(context, R.color.red);
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnViewSubmission.setVisibility(View.GONE);
                    break;
            }
            tvStatus.setText(statusText);
            tvStatus.setTextColor(statusColor);

            // Set teacher info if available
            if (deadline.getTeacherName() != null) {
                tvTeacher.setText("Giảng viên: " + deadline.getTeacherName());
                tvTeacher.setVisibility(View.VISIBLE);
            } else {
                tvTeacher.setVisibility(View.GONE);
            }

            // Set up submit button
            if ("NOT_SUBMITTED".equals(deadline.getStatus())) {
                btnSubmit.setVisibility(View.VISIBLE);
                btnSubmit.setOnClickListener(v -> {
                    Intent intent = new Intent(context, SubmissionActivity.class);
                    intent.putExtra("courseId", deadline.getCourseId());
                    intent.putExtra("notificationId", deadline.getId());
                    intent.putExtra("deadline", deadline.getDueDate().getTime());
                    context.startActivity(intent);
                });
            } else {
                btnSubmit.setVisibility(View.GONE);
            }

            // Set up view submission button
            if (!"NOT_SUBMITTED".equals(deadline.getStatus())) {
                btnViewSubmission.setVisibility(View.VISIBLE);
                btnViewSubmission.setOnClickListener(v -> {
                    Log.d(TAG, "View submission clicked for deadline: " + deadline.getId() + 
                        ", submissionId: " + deadline.getSubmissionId());
                    if (deadline.getSubmissionId() == null || deadline.getSubmissionId().isEmpty()) {
                        Log.e(TAG, "Missing submissionId for deadline: " + deadline.getId());
                        Toast.makeText(context, "Không tìm thấy thông tin bài nộp", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, SubmissionDetailActivity.class);
                    intent.putExtra("submissionId", deadline.getSubmissionId());
                    context.startActivity(intent);
                });
            } else {
                btnViewSubmission.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return deadlines.size();
    }

    @Override
    public DeadlineItem getItem(int position) {
        return deadlines.get(position);
    }
} 