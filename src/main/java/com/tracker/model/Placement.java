package com.tracker.model;

public class Placement {
    private int id;
    private int userId;
    private int aptitudeScore;
    private int codingScore;
    private int interviewScore;

    public Placement() {}

    public Placement(int id, int userId, int aptitudeScore, int codingScore, int interviewScore) {
        this.id = id;
        this.userId = userId;
        this.aptitudeScore = aptitudeScore;
        this.codingScore = codingScore;
        this.interviewScore = interviewScore;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getAptitudeScore() { return aptitudeScore; }
    public void setAptitudeScore(int aptitudeScore) { this.aptitudeScore = aptitudeScore; }

    public int getCodingScore() { return codingScore; }
    public void setCodingScore(int codingScore) { this.codingScore = codingScore; }

    public int getInterviewScore() { return interviewScore; }
    public void setInterviewScore(int interviewScore) { this.interviewScore = interviewScore; }
}
