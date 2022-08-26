package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

public class Permission implements IDataTransferObject {

    private long id;
    private String resource;
    private String subResource;
    private String subResourceDisplay;
    private boolean isRead;
    private boolean isWrite;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isWrite() {
        return isWrite;
    }

    public void setWrite(boolean write) {
        isWrite = write;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getSubResource() {
        return subResource;
    }

    public void setSubResource(String subResource) {
        this.subResource = subResource;
    }

    public String getSubResourceDisplay() {
        return subResourceDisplay;
    }

    public void setSubResourceDisplay(String subResourceDisplay) {
        this.subResourceDisplay = subResourceDisplay;
    }
}
