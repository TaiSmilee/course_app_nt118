package com.example.nt118.Semester;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.R;

import java.util.List;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder> {

    private final List<Semester> semesterList;
    private final Context context;

    public SemesterAdapter(Context context, List<Semester> semesterList) {
        this.context = context;
        this.semesterList = semesterList;
    }

    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_semester, parent, false);
        return new SemesterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        Semester semester = semesterList.get(position);
        holder.semesterTitle.setText(semester.getSemesterTitle());

        // Xóa các dòng cũ (trừ header)
        holder.tableLayout.removeViews(1, holder.tableLayout.getChildCount() - 1);

        for (SubjectResult subject : semester.getSubjectResults()) {
            TableRow row = new TableRow(context);

            TextView tvCode = createCell(subject.getSubjectCode());
            TextView tvClass = createCell(subject.getClassCode());
            TextView tvCredits = createCell(String.valueOf(subject.getCredits()));
            TextView tvMidterm = createCell(String.valueOf(subject.getMidterm()));
            TextView tvPractice = createCell(String.valueOf(subject.getPractice()));
            TextView tvExam = createCell(String.valueOf(subject.getExam()));
            TextView tvFinal = createCell(String.valueOf(subject.getFinalExam()));
            TextView tvAverage = createCell(String.valueOf(subject.getAverage()));

            row.addView(tvCode);
            row.addView(tvClass);
            row.addView(tvCredits);
            row.addView(tvMidterm);
            row.addView(tvPractice);
            row.addView(tvExam);
            row.addView(tvFinal);
            row.addView(tvAverage);

            holder.tableLayout.addView(row);
        }
    }

    // Hàm tạo TextView cho từng ô
    private TextView createCell(String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setPadding(8, 8, 8, 8);
        tv.setTextSize(14);
        return tv;
    }


    @Override
    public int getItemCount() {
        return semesterList.size();
    }

    public static class SemesterViewHolder extends RecyclerView.ViewHolder {
        TextView semesterTitle;
        TableLayout tableLayout;

        public SemesterViewHolder(@NonNull View itemView) {
            super(itemView);
            semesterTitle = itemView.findViewById(R.id.semesterTitle);
            tableLayout = itemView.findViewById(R.id.tableLayoutSubjects);
        }
    }
}
