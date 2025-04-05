package com.example.nt118;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.nt118.DeadlineItem;

public class DeadlineActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DeadlineAdapter adapter;
    List<DeadlineItem> deadlineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadline);

        recyclerView = findViewById(R.id.recycler_deadlines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        deadlineList = new ArrayList<>();
        deadlineList.add(new DeadlineItem(
                "NT118",
                "Nộp báo cáo tuần 3",
                "06/04/2025 23:59",
                "Còn chờ",                // trạng thái
                "Chưa nộp",               // tình trạng nộp bài
                "https://course.uit.edu.vn" // URL
        ));
        deadlineList.add(new DeadlineItem(
                "TH101",                    // Môn học
                "Làm quiz chương 5",       // Tên công việc
                "08/04/2025 21:00",        // Hạn chót
                "Còn chờ",                 // Trạng thái
                "Chưa nộp",                // Tình trạng nộp
                "https://course.uit.edu.vn" // Link (nếu có)
                ));
        adapter = new DeadlineAdapter(this, deadlineList);
        recyclerView.setAdapter(adapter);
    }
    //RecyclerView recyclerView = findViewById(R.id.recycler_deadlines);
}
