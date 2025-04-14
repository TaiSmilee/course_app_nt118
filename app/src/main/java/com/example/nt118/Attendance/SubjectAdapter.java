package com.example.nt118.Attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    private List<Subject> subjectList;
    private OnItemClickListener listener;

    public SubjectAdapter(List<Subject> subjectList, OnItemClickListener listener) {
        this.subjectList = subjectList;
        this.listener = listener;
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
        holder.tvSummary.setText("Điểm danh mẫu"); // Có thể thay bằng dữ liệu thật

        holder.itemView.setOnClickListener(v -> listener.onItemClick(subject));
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSummary;

        SubjectViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvSubjectName);
            tvSummary = itemView.findViewById(R.id.tvAttendanceSummary);
        }
    }
}