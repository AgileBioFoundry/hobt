package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * Options for a custom attribute type "MULTI_CHOICE"
 *
 * @author Hector Plahar
 */
public class CustomAttributeOption implements IDataTransferObject {
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
