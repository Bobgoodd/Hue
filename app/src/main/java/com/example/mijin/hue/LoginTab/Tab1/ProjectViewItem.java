package com.example.mijin.hue.LoginTab.Tab1;

/**
 * Created by mijin on 2017-10-02.
 */

public class ProjectViewItem {

    private int projectid;
    private String projectName;
    private String createdTime;
    private String participatedID;
    private String start;
    private String end;

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getParticipatedID() {
        return participatedID;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setParticipatedID(String participatedID) {
        this.participatedID = participatedID;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
