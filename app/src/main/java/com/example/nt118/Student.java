package com.example.nt118;

public class Student {
    private String name;
    private String id;
    private String avatar;

    public Student(String name, String id, String avatar) {
        this.name = name;
        this.id = id;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }
}

