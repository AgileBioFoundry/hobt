package org.abf.hobt.common;

import org.abf.hobt.service.ice.IDataTransferObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Wrapper around requested list of <code>IDataTransferObject</code>s to include
 * total available. The requested list is typically less than the amount available
 *
 * @author Hector Plahar
 */
public class ResultData<T extends IDataTransferObject> implements IDataTransferObject {

    private List<T> requested;
    private long available;

    public ResultData() {
        this.requested = new LinkedList<>();
    }

    public List<T> getRequested() {
        return requested;
    }

    public void setRequested(List<T> requested) {
        if (requested == null)
            throw new NullPointerException();
        this.requested = new LinkedList<>(requested);
    }

    public boolean add(T datum) {
        return this.requested.add(datum);
    }

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }
}
