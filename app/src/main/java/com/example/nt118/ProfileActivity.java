package com.example.nt118;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile); // Đảm bảo XML của bạn được load đúng

        // Ánh xạ các nút
        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnEdit = findViewById(R.id.btnEdit);
        Button btnPersonalDetail = findViewById(R.id.btnPersonalDetail);
        Button btnFriendsList = findViewById(R.id.btnFriendsList);

        // Gán sự kiện click
        btnBack.setOnClickListener(view -> Toast.makeText(this, "Back clicked", Toast.LENGTH_SHORT).show());
        btnEdit.setOnClickListener(view -> Toast.makeText(this, "Edit clicked", Toast.LENGTH_SHORT).show());
        btnPersonalDetail.setOnClickListener(view -> Toast.makeText(this, "Personal Detail clicked", Toast.LENGTH_SHORT).show());
        btnFriendsList.setOnClickListener(view -> Toast.makeText(this, "Friends List clicked", Toast.LENGTH_SHORT).show());
    }
}
