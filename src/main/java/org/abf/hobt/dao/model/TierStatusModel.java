package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.TierStatus;

import javax.persistence.*;

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

    @Override
    public TierStatus toDataTransferObject() {
        TierStatus status = new TierStatus();
        status.setTierId(this.tier.getId());
        status.setHostId(this.organism.getId());
        status.setComplete(this.complete);
        return status;
    }
}
