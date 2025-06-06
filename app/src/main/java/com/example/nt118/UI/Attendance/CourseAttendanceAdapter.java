package com.example.nt118.UI.Attendance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.Model.Attendance.AttendanceResponse;
import com.example.nt118.R;

import java.util.List;

public class CourseAttendanceAdapter extends RecyclerView.Adapter<CourseAttendanceAdapter.ViewHolder> {
    private List<AttendanceResponse.CourseAttendance> courses;
    private Context context;
    private String studentId;

    public CourseAttendanceAdapter(Context context, List<AttendanceResponse.CourseAttendance> courses, String studentId) {
        this.context = context;
        this.courses = courses;
        this.studentId = studentId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttendanceResponse.CourseAttendance course = courses.get(position);
        
        holder.tvCourseName.setText(course.getCourseName());
        holder.tvCourseId.setText(course.getCourseId());
        holder.tvTotalSessions.setText(String.valueOf(course.getTotalSessions()));
        holder.tvAttendedSessions.setText(String.valueOf(course.getAttendedSessions()));
        holder.tvAttendanceRate.setText(String.format("%.1f%%", course.getAttendanceRate()));

        holder.btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, AttendanceDetailActivity.class);
            intent.putExtra("studentId", studentId);
            intent.putExtra("courseId", course.getCourseId());
            intent.putExtra("courseName", course.getCourseName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName, tvCourseId, tvTotalSessions, tvAttendedSessions, tvAttendanceRate;
        com.google.android.material.button.MaterialButton btnViewDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvCourseId = itemView.findViewById(R.id.tvCourseId);
            tvTotalSessions = itemView.findViewById(R.id.tvTotalSessions);
            tvAttendedSessions = itemView.findViewById(R.id.tvAttendedSessions);
            tvAttendanceRate = itemView.findViewById(R.id.tvAttendanceRate);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
} 