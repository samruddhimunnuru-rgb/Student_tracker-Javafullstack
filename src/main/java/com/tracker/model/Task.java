package com.tracker.model;

import java.util.Date;

public class Task {
    private int id;
    private int userId;
    private String subject;
    private String topic;
    private Date deadline;
    private String status;
    private String priority;

    public Task() {}

    public Task(int id, int userId, String subject, String topic, Date deadline, String status, String priority) {
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.topic = topic;
        this.deadline = deadline;
        this.status = status;
        this.priority = priority;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
}
