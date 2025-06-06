package com.example.nt118.api.models.grades;

import com.google.gson.annotations.SerializedName;

public class Lecturer {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("department")
    private String department;

    @SerializedName("position")
    private String position;

    @SerializedName("avatarUrl")
    private String avatarUrl;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
} 