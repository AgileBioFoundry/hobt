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

    @ManyToOne()
    @JoinColumn(name = "attribute_id")
    private OrganismAttributeModel attribute;

    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public OrganismAttributeModel getAttribute() {
        return attribute;
    }

    public void setAttribute(OrganismAttributeModel attribute) {
        this.attribute = attribute;
    }

    @Override
    public CustomAttributeOption toDataTransferObject() {
        CustomAttributeOption option = new CustomAttributeOption();
        option.setValue(label);
        option.setId(id);
        return option;
    }
}
