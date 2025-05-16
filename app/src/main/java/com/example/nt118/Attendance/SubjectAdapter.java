package com.example.nt118.Attendance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.CourseDetailActivity;
import com.example.nt118.R;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;




import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Subject subject);
    }

    private Context context;
    private List<Subject> subjectList;
    private OnItemClickListener listener;

    public SubjectAdapter(List<Subject> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.tvName.setText(subject.getName());
        holder.tvID.setText(subject.getID());
        holder.tvSummary.setText(subject.getAbsent() + "buổi vắng"); // Có thể thay bằng dữ liệu thật

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AttendanceActivity.class);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvID, tvName, tvSummary;

        SubjectViewHolder(View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvClassID);
            tvName = itemView.findViewById(R.id.tvClassName);
            tvSummary = itemView.findViewById(R.id.tvAttendanceSummary);
        }
    }
}