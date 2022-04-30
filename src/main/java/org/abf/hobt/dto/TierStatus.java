package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

public class TierStatus implements IDataTransferObject {

    private long id;
    private long tierId;
    private boolean complete;
    private long hostId;

    public long getTierId() {
        return tierId;
    }

    public void setTierId(long tierId) {
        this.tierId = tierId;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
