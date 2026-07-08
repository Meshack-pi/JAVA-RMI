package com.cat2.task4;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int id;
    private final String name;
    private final String course;
    private final int score;
    private final String email;

    public Student(int id, String name, String course, int score, String email) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.score = score;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public int getScore() {
        return score;
    }

    public String getEmail() {
        return email;
    }
}
