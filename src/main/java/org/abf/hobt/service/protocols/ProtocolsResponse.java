package org.abf.hobt.service.protocols;

import org.abf.hobt.dto.Protocol;
import org.abf.hobt.service.ice.IDataTransferObject;

import java.util.List;

/**
 * Response object for protocols.io api call
 */
public class ProtocolsResponse  implements IDataTransferObject {
    private List<Protocol> items;
    private int total;
    private int total_pages;
    private int status_code;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<Protocol> getItems() {
        return items;
    }
}
