package com.example.mijin.hue.ProjectTab.Tab2;

import java.util.Date;

/**
 * Created by mijin on 2017-10-03.
 */

public class DocumentViewItem {
    private String documentName;
    private Date createdTime;

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    private Date modifiedTime;
    private String participationId;

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }


    public String getParticipationId() {
        return participationId;
    }

    public void setParticipationId(String participationId) {
        this.participationId = participationId;
    }
}
