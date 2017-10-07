package com.example.mijin.hue.LoginTab.Tab1;

import java.util.Date;

/**
 * Created by mijin on 2017-10-02.
 */

public class ProjectViewItem {

    private String projectName;
    private Date createdTime;
    private String participatedID;

    public String getProjectName() {
        return projectName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getParticipatedID() {
        return participatedID;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setParticipatedID(String participatedID) {
        this.participatedID = participatedID;
    }

}
