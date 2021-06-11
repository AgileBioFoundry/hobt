package org.abf.hobt.tier;

import org.abf.hobt.service.ice.IDataTransferObject;

public class Tier implements IDataTransferObject {

    private long id;
    private int index;
    private String label;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
