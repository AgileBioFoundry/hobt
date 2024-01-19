package org.abf.hobt.dto;

import org.abf.hobt.attribute.AttributeType;
import org.abf.hobt.service.ice.IDataTransferObject;

import java.util.ArrayList;
import java.util.List;

public class OrganismAttribute implements IDataTransferObject {

    private long id;
    private String label;
    private AttributeType type;
    private boolean required;
    private boolean allOrganisms;
    private final List<Organism> hosts = new ArrayList<>();
    private final List<CustomAttributeOption> options = new ArrayList<>();
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<Organism> getHosts() {
        return hosts;
    }

    public boolean isAllOrganisms() {
        return allOrganisms;
    }

    public void setAllOrganisms(boolean allOrganisms) {
        this.allOrganisms = allOrganisms;
    }

    public List<CustomAttributeOption> getOptions() {
        return options;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
