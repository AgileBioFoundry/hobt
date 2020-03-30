package org.abf.hobt.service.ice.entry;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * User created fields for entry
 *
 * @author Hector Plahar
 */
public class CustomField implements IDataTransferObject {

    private String name;
    private String value;

    public CustomField() {
    }

    public CustomField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
