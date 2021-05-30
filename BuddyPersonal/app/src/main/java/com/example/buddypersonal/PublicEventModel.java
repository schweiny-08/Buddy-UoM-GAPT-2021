package com.example.buddypersonal;

public class PublicEventModel {

    private int puEventId, userId, siteId;
    private String title, startTime, startDate, endTime, endDate, location, description;

    //Overloaded constructors
    public PublicEventModel() {
        this.puEventId = -1;
        this.userId = -1;
        this.siteId = -1;
        this.title = "Title";
        this.startTime = "8:00";
        this.startDate = "28/06/2021";
        this.endTime = "9:00";
        this.endDate = "28/06/2021";
        this.location = "Zoom";
        this.description = "Demonstration of app";
    }

    public PublicEventModel(int puid, int aid, int sid, String titl, String startTajm, String startDejt, String endTajm, String endDejt, String loca, String desc) {
        this.puEventId = puid;
        this.userId = aid;
        this.siteId = sid;
        this.title = titl;
        this.startTime = startTajm;
        this.startDate = startDejt;
        this.endTime = endTajm;
        this.endDate = endDejt;
        this.location = loca;
        this.description = desc;
    }

    //Getters and setters for the private event criteria
    public int getPuEventId() {
        return puEventId;
    }

    public void setPuEventId(int puEventId) {
        this.puEventId = puEventId;
    }

    public int getUsedId() {
        return userId;
    }

    public void setUsedId(int usedId) {
        this.userId = usedId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}