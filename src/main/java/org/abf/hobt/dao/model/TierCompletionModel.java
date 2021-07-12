package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.service.ice.IDataTransferObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TierCompletion")
public class TierCompletionModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tier_completion_id")
    @SequenceGenerator(name = "tier_completion_id", sequenceName = "tier_completion_id_seq", allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "organism_id", nullable = false, updatable = false)
    private OrganismModel organism;

    @OneToOne
    @JoinColumn(name = "tier_id", nullable = false, updatable = false)
    private TierModel tier;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date creationTime;

    @ManyToOne
    private AccountModel createdBy;

    public long getId() {
        return id;
    }

    public OrganismModel getOrganism() {
        return organism;
    }

    public void setOrganism(OrganismModel organism) {
        this.organism = organism;
    }

    public TierModel getTier() {
        return tier;
    }

    public void setTier(TierModel tier) {
        this.tier = tier;
    }

    @Override
    public IDataTransferObject toDataTransferObject() {
        return null;
    }
}
