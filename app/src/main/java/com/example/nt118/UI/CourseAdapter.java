package com.example.nt118.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.nt118.R; // Thay `yourappname` bằng tên app của bạn


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courseList;
    private Context context;

    public CourseAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.tvCourseId.setText(course.getCourseId());
        holder.tvCourseName.setText(course.getCourseName());
        holder.tvCredit.setText("" + course.getCredit());
        holder.tvStartDate.setText(course.getStartDate());
        holder.tvEndDate.setText(course.getEndDate());
        holder.tvSchedule.setText(course.getSchedule());
        holder.tvInstructor.setText(course.getInstructor());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CourseDetailActivity.class);
            intent.putExtra("courseId", course.getCourseId());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseId, tvCourseName, tvCredit, tvStartDate, tvEndDate, tvSchedule, tvInstructor;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseId = itemView.findViewById(R.id.tv_course_id);
            tvCourseName = itemView.findViewById(R.id.tv_course_name);
            tvCredit = itemView.findViewById(R.id.tv_credit);
            tvStartDate = itemView.findViewById(R.id.tv_start_date);
            tvEndDate = itemView.findViewById(R.id.tv_end_date);
            tvSchedule = itemView.findViewById(R.id.tv_schedule);
            tvInstructor = itemView.findViewById(R.id.tv_instructor);
        }
    }
}
