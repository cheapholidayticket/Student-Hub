package com.example.studentHub.Model_helper_classes;


public class Data {

    String title;
    String description;
    String campus;
    String salary;
    String ImageUrl;
    String id;
    String date;
    String DateTime;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public Data() {
    }

    public Data(String title, String description, String campus, String salary, String imageUrl, String id, String date, String dateTime) {
        this.title = title;
        this.description = description;
        this.campus = campus;
        this.salary = salary;
        ImageUrl = imageUrl;
        this.id = id;
        this.date = date;
        DateTime = dateTime;
    }
}