package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.service.ice.IDataTransferObject;

import javax.persistence.*;

@Entity
@Table(name = "CRITERIA")
public class CriteriaModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "criteria_id")
    @SequenceGenerator(name = "organism_id", sequenceName = "organism_id_seq", allocationSize = 1)
    private long id;

    @Override
    public IDataTransferObject toDataTransferObject() {
        return null;
    }
}
