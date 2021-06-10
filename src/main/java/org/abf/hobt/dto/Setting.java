package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * @author Hector Plahar
 */
public class Setting implements IDataTransferObject {

    private String key;
    private String value;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }
}
