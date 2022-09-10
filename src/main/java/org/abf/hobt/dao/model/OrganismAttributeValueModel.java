package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.CustomAttributeValue;

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

    @Override
    public CustomAttributeValue toDataTransferObject() {
        return null;
    }
}
