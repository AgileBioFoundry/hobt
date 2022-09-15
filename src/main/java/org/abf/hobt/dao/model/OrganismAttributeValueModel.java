package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.OrganismAttribute;

import javax.persistence.*;

@Entity
@Table(name = "OrganismAttributeValue")
@SequenceGenerator(name = "organism_attribute_value_id", sequenceName = "organism_attribute_value_id_seq", allocationSize = 1)
public class OrganismAttributeValueModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_attribute_value_id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organism_id", nullable = false)
    private OrganismModel organism;

    @ManyToOne(optional = false)
    @JoinColumn(name = "custom_attribute_id", nullable = false)
    private OrganismAttributeModel organismAttribute;

    @Column(name = "\"value\"")
    private String value;

    public long getId() {
        return id;
    }

    public OrganismModel getOrganism() {
        return organism;
    }

    public void setOrganism(OrganismModel organism) {
        this.organism = organism;
    }

    public OrganismAttributeModel getOrganismAttribute() {
        return organismAttribute;
    }

    public void setOrganismAttribute(OrganismAttributeModel organismAttribute) {
        this.organismAttribute = organismAttribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public OrganismAttribute toDataTransferObject() {
        OrganismAttribute attribute = new OrganismAttribute();
        attribute.setId(this.id);
        attribute.setValue(this.value);
        attribute.setLabel(this.organismAttribute.getLabel());
        attribute.setRequired(this.organismAttribute.getRequired());
        return attribute;
    }
}
