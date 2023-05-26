package com.example.commutecast;

public class ToDoModel extends TaskId {
    private String id;
    private String task;
    private String date;
    private String location;
    private int status;


    public String getTask() {
        return task;
    }

    public int getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
