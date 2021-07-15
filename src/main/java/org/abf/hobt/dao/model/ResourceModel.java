package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.service.ice.IDataTransferObject;

import javax.persistence.*;

/**
 * Table for resources/securables/parts of HObT that can be secured and accessed only with authorization
 * Contains a unique key for each resource
 */
@Entity
@Table(name = "Resource")
public class ResourceModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "securable_role_id")
    @SequenceGenerator(name = "securable_role_id", sequenceName = "securable_role_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "label", length = 150, nullable = false, unique = true)
    private String label;

    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public IDataTransferObject toDataTransferObject() {
        return null;
    }
}
