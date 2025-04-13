//package com.example.nt118;
//
//import android.app.Activity;
//import android.content.Intent;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.example.nt118.R;
//import com.example.nt118.MainActivity;
//import com.example.nt118.Deadline.DeadlineActivity;
//import com.example.nt118.StudentListActivity;
//import com.example.nt118.ProfileActivity;
//
//public class BottomNavHelper {
//    public static void setupBottomNav(Activity activity, BottomNavigationView bottomNav) {
//        bottomNav.setOnItemSelectedListener(item -> {
//            Intent intent = null;
//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    intent = new Intent(activity, MainActivity.class);
//                    break;
//                case R.id.nav_deadline:
//                    intent = new Intent(activity, DeadlineActivity.class);
//                    break;
//                case R.id.nav_examDate:
//                    intent = new Intent(activity, StudentListActivity.class);
//                    break;
//                case R.id.nav_profile:
//                    if (!(activity instanceof ProfileActivity)) {
//                        intent = new Intent(activity, ProfileActivity.class);
//                    }
//                    break;
//            }
//
//            if (intent != null) {
//                activity.startActivity(intent);
//                activity.overridePendingTransition(0, 0);
//                return true;
//            }
//            return false;
//        });
//    }
//}
