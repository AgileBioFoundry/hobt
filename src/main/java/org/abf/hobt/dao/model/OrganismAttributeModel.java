package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.AttributeType;
import org.abf.hobt.service.ice.IDataTransferObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "OrganismAttribute")
@SequenceGenerator(name = "organism_attribute_id", sequenceName = "organism_attribute_id_seq", allocationSize = 1)
public class OrganismAttributeModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_attribute_id")
    private long id;

    // unique across same entry types for non-disabled fields
    @Column(name = "label")
    private String label;

    // type of field (e.g. multi choice etc)
    @Enumerated(EnumType.STRING)
    private AttributeType type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrganismModel> organisms = new ArrayList<>();

    @Column(name = "required")
    private Boolean required = Boolean.FALSE;

    @Column(name = "disabled")
    private Boolean disabled = Boolean.FALSE;

    @Column(name = "all_organisms")
    private Boolean allOrganisms = Boolean.FALSE;

    @Override
    public IDataTransferObject toDataTransferObject() {
        return null;
    }
}
