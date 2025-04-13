package com.example.nt118.Deadline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        TextView tvSubject, tvDescription, tvDeadline;
        Button btnView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tv_subject);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvDeadline = itemView.findViewById(R.id.tv_deadline);
            btnView = itemView.findViewById(R.id.btn_view);
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
        holder.tvDescription.setText(item.description);
        holder.tvDeadline.setText("📅 " + item.deadline);

        holder.btnView.setOnClickListener(v -> {
            if (item.url != null && !item.url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.url));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deadlines.size();
    }
}
