package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

public class ProtocolImage implements IDataTransferObject {

    private String source;
    private String placeholder;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
