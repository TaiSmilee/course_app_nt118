package com.example.nt118.UI.Deadline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.R;

import java.util.List;

public class DeadlineAdapter extends RecyclerView.Adapter<DeadlineAdapter.ViewHolder> {

    private Context context;
    private List<DeadlineItem> deadlines;

    public DeadlineAdapter(Context context, List<DeadlineItem> deadlines) {
        this.context = context;
        this.deadlines = deadlines;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubject, tvName, tvDescription, tvDeadline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tv_subject);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvDeadline = itemView.findViewById(R.id.tv_deadline);
        }
    }

    @NonNull
    @Override
    public DeadlineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_deadline, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeadlineAdapter.ViewHolder holder, int position) {
        DeadlineItem item = deadlines.get(position);
        holder.tvSubject.setText(item.subject);
        holder.tvName.setText(item.name);
        holder.tvDescription.setText(item.description);
        holder.tvDeadline.setText(item.deadline);
    }

    @Override
    public int getItemCount() {
        return deadlines.size();
    }
}
