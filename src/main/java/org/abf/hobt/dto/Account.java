package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

public class Account implements IDataTransferObject {

    private String userId;
    private String email;

    public String getUserId() {
        return this.userId;
    }
}
