package org.abf.hobt.servlet;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * @author Hector Plahar
 */
public class ErrorResult implements IDataTransferObject {

    private String errorMessage;

    public ErrorResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
