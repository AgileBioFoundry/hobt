package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.TierStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Maintains information about the tier information for host organism
 *
 * @author Hector Plahar
 */
@Entity
@Table(name = "TierStatus")
public class TierStatusModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tier_status_id")
    @SequenceGenerator(name = "tier_status_id", sequenceName = "tier_status_id_seq", allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "tier_id", nullable = false, updatable = false)
    private TierModel tier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organism_id")
    private OrganismModel organism;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date creationTime;

    @Column(name = "complete")
    private boolean complete;

    public long getId() {
        return id;
    }

    public TierModel getTier() {
        return tier;
    }

    public void setTier(TierModel tier) {
        this.tier = tier;
    }

    public OrganismModel getOrganism() {
        return organism;
    }

    public void setOrganism(OrganismModel organism) {
        this.organism = organism;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public TierStatus toDataTransferObject() {
        TierStatus status = new TierStatus();
        status.setTierId(this.tier.getId());
        status.setHostId(this.organism.getId());
        status.setComplete(this.complete);
        if (this.creationTime != null)
            status.setCreated(this.creationTime.getTime());
        return status;
    }
}
