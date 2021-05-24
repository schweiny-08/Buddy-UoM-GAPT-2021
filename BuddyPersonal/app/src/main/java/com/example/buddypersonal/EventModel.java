package com.example.buddypersonal;

public class EventModel {

    private int eventId, userId;
    private String title, startTime, startDate, endTime, endDate, notes, loc;

    public EventModel() {
        this.eventId = -1;
        this.userId = -1;
        this.title = "title";
        this.startTime = "startTajm";
        this.startDate = "startDejt";
        this.endTime = "endTajm";
        this.endDate = "endDejt";
        this.notes = "noti";
        this.loc = "loca";
    }

    public EventModel(int evid, int uid, String titl, String startTajm, String startDejt, String endTajm, String endDejt, String noti, String loca) {
        this.eventId = evid;
        this.userId = uid;
        this.title = titl;
        this.startTime = startTajm;
        this.startDate = startDejt;
        this.endTime = endTajm;
        this.endDate = endDejt;
        this.notes = noti;
        this.loc = loca;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
