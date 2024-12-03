package com.example.to_do_list_app;

public class dataModelClass {

    private String title;
    private boolean completed;
    private String day;
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public dataModelClass(String title, boolean completed, String day, String detail) {
        this.title = title;
        this.completed = completed;
        this.day = day;
        this.detail=detail;
    }

    public dataModelClass() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
