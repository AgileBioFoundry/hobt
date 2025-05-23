package org.abf.hobt.service.ice.entry;

import org.abf.hobt.service.ice.IDataTransferObject;

import java.util.ArrayList;

/**
 * Arabidopsis seed plant type
 *
 * @author Hector Plahar
 */
public enum PlantType implements IDataTransferObject {

    EMS("EMS"),
    OVER_EXPRESSION("Over Expression"),
    RNAI("RNAi"),
    REPORTER("Reporter"),
    T_DNA("T-DNA"),
    OTHER("Other"),
    NULL("");

    private String display;

    PlantType() {
    }

    PlantType(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return this.display;
    }

    public static ArrayList<String> getDisplayList() {
        ArrayList<String> list = new ArrayList<String>();
        for (PlantType option : PlantType.values()) {
            list.add(option.display);
        }
        return list;
    }

    public static PlantType fromString(String value) {
        for (PlantType option : PlantType.values()) {
            if (value.equalsIgnoreCase(option.toString()) || value.equalsIgnoreCase(option.name()))
                return option;
        }
        return null;
    }
}

