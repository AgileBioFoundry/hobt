package org.abf.hobt.dao.model;

import jakarta.persistence.*;
import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.CustomAttributeOption;

/**
 * Multi choice option value for custom attribute
 */
@Entity
@Table(name = "OrganismAttributeOption")
@SequenceGenerator(name = "organism_attribute_option_id", sequenceName = "organism_attribute_option_id_seq",
    allocationSize = 1)
public class OrganismAttributeOptionModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_attribute_option_id")
    private long id;

    @Column(name = "label")
    private String label;

    public long getId() {
        return id;
    }

    @Override
    public CustomAttributeOption toDataTransferObject() {
        return null;
    }
}
