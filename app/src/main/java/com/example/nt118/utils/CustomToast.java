package com.example.nt118.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.nt118.R;

public class CustomToast {
    private static final int TOAST_DURATION = 6000; // 2 seconds
    private static View currentToastView;
    private static Animation slideDownAnimation;
    private static Animation slideUpAnimation;

    public static void show(Activity activity, String message, boolean isSuccess) {
        // Remove current toast if exists
        if (currentToastView != null) {
            ViewGroup parent = (ViewGroup) currentToastView.getParent();
            if (parent != null) {
                parent.removeView(currentToastView);
            }
        }

        // Inflate custom toast layout
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);

        // Set message
        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        // Add toast to root view
        ViewGroup root = activity.findViewById(android.R.id.content);
        root.addView(layout);

        // Load animations
        slideDownAnimation = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
        slideUpAnimation = AnimationUtils.loadAnimation(activity, R.anim.slide_up);

        // Start slide down animation
        layout.startAnimation(slideDownAnimation);

        // Store current toast view
        currentToastView = layout;

        // Remove toast after duration
        layout.postDelayed(() -> {
            if (currentToastView == layout) {
                layout.startAnimation(slideUpAnimation);
                slideUpAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ViewGroup parent = (ViewGroup) layout.getParent();
                        if (parent != null) {
                            parent.removeView(layout);
                        }
                        if (currentToastView == layout) {
                            currentToastView = null;
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
        }, TOAST_DURATION);
    }
} 