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

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    private List<ClassModel> classList;
    private Context context;

    public ClassAdapter(List<ClassModel> scheduleList, Context context) {
        this.classList = scheduleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassModel item = classList.get(position);
        holder.startTime.setText(item.getStartTime());
        holder.endTime.setText(item.getEndTime());
        holder.subjectName.setText(item.getSubjectName());
        holder.room.setText("Room: " + item.getRoom());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CourseDetailActivity.class);

            // 👉 Gửi dữ liệu qua Intent (ví dụ: tên môn học)
            intent.putExtra("subjectName", item.getSubjectName());
            intent.putExtra("startTime", item.getStartTime());
            intent.putExtra("endTime", item.getEndTime());
            intent.putExtra("room", item.getRoom());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public interface ClassItemClickListener {
        void onClassItemClick(ClassModel classModel);
    }

    static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView startTime, endTime, subjectName, room;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.tvClassTime);
            endTime = itemView.findViewById(R.id.tvClassTime);
            subjectName = itemView.findViewById(R.id.tvClassName);
            room = itemView.findViewById(R.id.tvClassTime);
        }
    }
}
