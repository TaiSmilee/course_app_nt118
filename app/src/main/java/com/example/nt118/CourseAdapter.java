package com.example.nt118;

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
import com.example.nt118.Course;
import com.example.nt118.CourseDetailActivity;

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
        holder.tvClassName.setText(course.getClassName());
        holder.tvTheory.setText("Lý thuyết: " + course.getTheoryTime());
        holder.tvPractice.setText("Thực hành: " + course.getPracticeTime());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CourseDetailActivity.class);
            intent.putExtra("className", course.getClassName());
            intent.putExtra("theoryTime", course.getTheoryTime());
            intent.putExtra("practiceTime", course.getPracticeTime());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassName, tvTheory, tvPractice;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvTheory = itemView.findViewById(R.id.tvTheory);
            tvPractice = itemView.findViewById(R.id.tvPractice);
        }
    }
}
