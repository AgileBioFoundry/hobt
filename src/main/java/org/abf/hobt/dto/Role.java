package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

import java.util.ArrayList;
import java.util.List;

public class Role implements IDataTransferObject {

    private long id;
    private String label;
    private String description;
    private final List<Permission> permissions = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
