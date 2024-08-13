package com.example.realtimedb_firebase;

import androidx.annotation.NonNull;

public class User {
    private int id;
    private String name;

    private Job job;
    public User() {
    }


    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(int id, String name, Job job) {
        this.id = id;
        this.name = name;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // chu y phai get set them ca object Job
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", job=" + job +
                '}';
    }
}
