package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

public class Criteria implements IDataTransferObject {

    private long id;
    private String label;
    private String description;
    private int status;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
