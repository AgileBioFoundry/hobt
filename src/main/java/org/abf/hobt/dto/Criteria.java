package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

public class Criteria implements IDataTransferObject {

    private String label;
    private String description;

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
}
