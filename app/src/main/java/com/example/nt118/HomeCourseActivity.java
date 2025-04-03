package com.example.nt118;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import com.example.nt118.databinding.ActivityHomeCourseBinding;

public class HomeCourseActivity extends AppCompatActivity {
    private ActivityHomeCourseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Kiểm tra và thiết lập ActionBar nếu có
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


}
