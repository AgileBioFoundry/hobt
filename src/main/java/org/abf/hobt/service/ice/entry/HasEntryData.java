package org.abf.hobt.service.ice.entry;

import org.abf.hobt.service.ice.IDataTransferObject;

public abstract class HasEntryData implements IDataTransferObject {

    private PartData entryInfo;

    public HasEntryData() {
    }

    public void setEntryInfo(PartData view) {
        this.entryInfo = view;
    }

    public PartData getEntryInfo() {
        return this.entryInfo;
    }
}
