package com.example.nt118;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;
import java.util.List;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchBar;
    private List<Student> studentList;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_list);

        recyclerView = findViewById(R.id.recyclerView);
        searchBar = findViewById(R.id.search_bar);

        studentList = new ArrayList<>();
        studentList.add(new Student("Nguyễn Minh D", "22522944", "ND"));
        studentList.add(new Student("Nguyễn Văn A", "21523241", "NA"));
        studentList.add(new Student("Trần Thị B", "21334567", "TB"));
        studentList.add(new Student("Lê Văn C", "22234589", "LC"));

        adapter = new StudentAdapter(this, studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}