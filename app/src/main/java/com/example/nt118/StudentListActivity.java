package com.example.nt118;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.Deadline.DeadlineActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("DS Sinh Viên");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_deadline) {
                Intent intent = new Intent(this, DeadlineActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

// Lắng nghe khi nhấn vào icon back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở activity khác tại đây (ví dụ mở MainActivity)
                Intent intent = new Intent(StudentListActivity.this, CourseDetailActivity.class); // thay MainActivity bằng activity bạn muốn mở
                startActivity(intent);

                // Nếu muốn đóng activity hiện tại
                finish();
            }
        });

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