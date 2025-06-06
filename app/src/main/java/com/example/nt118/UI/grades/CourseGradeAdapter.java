package com.example.nt118.UI.grades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CourseGradeAdapter extends RecyclerView.Adapter<CourseGradeAdapter.CourseViewHolder> {

    private List<CourseGradeModel> courses;
    private OnCourseClickListener listener;

    public interface OnCourseClickListener {
        void onCourseClick(CourseGradeModel course);
    }

    public CourseGradeAdapter(List<CourseGradeModel> courses, OnCourseClickListener listener) {
        this.courses = courses;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grades, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseGradeModel course = courses.get(position);

        holder.tvCourseCode.setText(course.getCourseCode());
        holder.tvCourseName.setText(course.getCourseName());
        holder.tvCredits.setText(String.valueOf(course.getCredits()) + " credits");
        holder.tvGrade.setText(course.getLetterGrade());
        holder.tvPoint.setText(String.format("%.1f", course.getNumericGrade()));
        holder.tvLecturer.setText(course.getLecturer());

        // Set color based on grade
        int gradeColor = holder.itemView.getContext().getResources().getColor(android.R.color.black);
        try {
            gradeColor = android.graphics.Color.parseColor(course.getGradeColor());
        } catch (Exception e) {
            // Use default color if parsing fails
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCourseClick(course);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView tvCourseCode;
        TextView tvCourseName;
        TextView tvCredits;
        TextView tvGrade;
        TextView tvPoint;
        TextView tvLecturer;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseCode = itemView.findViewById(R.id.tvCourseCode);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvCredits = itemView.findViewById(R.id.tvCredits);
            tvGrade = itemView.findViewById(R.id.tvGrade);
            tvPoint = itemView.findViewById(R.id.tvPoint);
            tvLecturer = itemView.findViewById(R.id.tvLecturer);
        }
    }
}