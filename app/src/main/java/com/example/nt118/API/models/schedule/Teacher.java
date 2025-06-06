package com.example.nt118.api.models.schedule;

import com.google.gson.annotations.SerializedName;

public class Teacher {
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
} 