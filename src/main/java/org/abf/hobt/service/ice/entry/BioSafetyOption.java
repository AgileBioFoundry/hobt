package org.abf.hobt.service.ice.entry;

import org.abf.hobt.service.ice.IDataTransferObject;

/**
 * BioSafety Option options
 *
 * @author Hector Plahar
 */
public enum BioSafetyOption implements IDataTransferObject {

    LEVEL_ONE("1"),
    LEVEL_TWO("2");

    private String value;

    BioSafetyOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
