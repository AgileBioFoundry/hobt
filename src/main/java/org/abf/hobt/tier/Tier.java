package org.abf.hobt.tier;

import org.abf.hobt.service.ice.IDataTransferObject;

public class Tier implements IDataTransferObject {

    private long id;
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
