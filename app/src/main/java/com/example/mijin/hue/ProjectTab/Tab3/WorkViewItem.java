package com.example.mijin.hue.ProjectTab.Tab3;

import java.util.Date;

/**
 * Created by mijin on 2017-10-03.
 */

public class WorkViewItem {
    private String workName;
    private Date dueTime;
    private int d_day;
    private String participationId;
    private int progress;

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public int getD_day() {
        return d_day;
    }

    public void setD_day(int d_day) {
        this.d_day = d_day;
    }

    public String getParticipationId() {
        return participationId;
    }

    public void setParticipationId(String participationId) {
        this.participationId = participationId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
