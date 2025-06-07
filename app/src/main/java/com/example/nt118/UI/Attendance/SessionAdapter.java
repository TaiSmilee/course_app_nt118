package com.example.nt118.UI.Attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.Model.Attendance.AttendanceDetailResponse;
import com.example.nt118.R;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    private List<AttendanceDetailResponse.Session> sessions;

    public SessionAdapter(List<AttendanceDetailResponse.Session> sessions) {
        this.sessions = sessions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttendanceDetailResponse.Session session = sessions.get(position);
        
        holder.tvDate.setText(session.getDate());
        holder.tvTime.setText(session.getTime());
        
        // Set status text and color based on attendance status
        String status = session.getStatus();
        holder.tvStatus.setText(status.equals("PRESENT") ? "Có mặt" : "Vắng mặt");
        holder.tvStatus.setTextColor(status.equals("PRESENT") ? 
            holder.itemView.getContext().getColor(R.color.green) : 
            holder.itemView.getContext().getColor(R.color.red));
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTime, tvStatus;

        ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
} 