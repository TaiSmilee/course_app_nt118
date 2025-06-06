package com.example.nt118.UI.Attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.Model.Attendance.AttendanceResponse;
import com.example.nt118.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {
    private List<AttendanceResponse.Session> sessions;
    private SimpleDateFormat dateFormat;

    public SessionAdapter(List<AttendanceResponse.Session> sessions) {
        this.sessions = sessions;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_session, parent, false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        AttendanceResponse.Session session = sessions.get(position);
        holder.bind(session);
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    static class SessionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvStatus;
        private TextView tvNotes;

        public SessionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvNotes = itemView.findViewById(R.id.tvNotes);
        }

        public void bind(AttendanceResponse.Session session) {
            tvDate.setText(session.getDate());
            
            // Set status text and color
            String status = session.getStatus();
            tvStatus.setText(status);
            
            int statusColor;
            switch (status.toLowerCase()) {
                case "present":
                    statusColor = itemView.getContext().getResources().getColor(R.color.green);
                    break;
                case "absent":
                    statusColor = itemView.getContext().getResources().getColor(R.color.red);
                    break;
                case "late":
                    statusColor = itemView.getContext().getResources().getColor(R.color.orange);
                    break;
                default:
                    statusColor = itemView.getContext().getResources().getColor(R.color.gray_attendance);
            }
            tvStatus.setTextColor(statusColor);

            // Set notes if available
            if (session.getNotes() != null && !session.getNotes().isEmpty()) {
                tvNotes.setVisibility(View.VISIBLE);
                tvNotes.setText(session.getNotes());
            } else {
                tvNotes.setVisibility(View.GONE);
            }
        }
    }
} 