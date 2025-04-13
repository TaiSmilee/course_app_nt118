package com.example.nt118;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
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

        holder.tableLayout.removeViews(1, holder.tableLayout.getChildCount() - 1); // Clear old rows

        for (SubjectResult subject : semester.getSubjectResults()) {
            TableRow row = new TableRow(context);

            TextView name = new TextView(context);
            name.setText(subject.getSubjectName());

            TextView credits = new TextView(context);
            credits.setText(String.valueOf(subject.getCredits()));

            TextView mid = new TextView(context);
            mid.setText(String.valueOf(subject.getMidScore()));

            TextView fin = new TextView(context);
            fin.setText(String.valueOf(subject.getFinalScore()));

            row.addView(name);
            row.addView(credits);
            row.addView(mid);
            row.addView(fin);

            holder.tableLayout.addView(row);
        }
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
