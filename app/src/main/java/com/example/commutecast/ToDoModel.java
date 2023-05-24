package com.example.commutecast;

public class ToDoModel extends TaskId {
    private String task;
    private String alarm;
    private String location;
    private int status;


    public String getTask() {
        return task;
    }

    public int getStatus() {
        return status;
    }

    public String getAlarm() {
        return alarm;
    }

    public String getLocation() {
        return location;
    }
}
