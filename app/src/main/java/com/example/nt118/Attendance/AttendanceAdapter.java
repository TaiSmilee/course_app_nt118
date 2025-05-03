package com.example.nt118.Attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nt118.R;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    private List<AttendanceRecord> attendanceList;

    public AttendanceAdapter(List<AttendanceRecord> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance_record, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        AttendanceRecord record = attendanceList.get(position);
        holder.tvDate.setText(record.getDate());
        holder.tvStatus.setText(record.isPresent() ? "✔️" : "❌");
        holder.tvNote.setText(record.getNote());
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvStatus, tvNote;

        AttendanceViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvNote = itemView.findViewById(R.id.tvNote);
        }
    }
}