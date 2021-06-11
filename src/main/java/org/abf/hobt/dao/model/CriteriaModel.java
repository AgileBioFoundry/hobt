package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.service.ice.IDataTransferObject;

import javax.persistence.*;

@Entity
@Table(name = "CRITERIA")
public class CriteriaModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "criteria_id")
    @SequenceGenerator(name = "criteria_id", sequenceName = "criteria_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id")
    private TierModel tier;

    @Override
    public IDataTransferObject toDataTransferObject() {
        return null;
    }
}
