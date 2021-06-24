package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.Organism;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ORGANISM")
public class OrganismModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_id")
    @SequenceGenerator(name = "organism_id", sequenceName = "organism_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name", length = 125, nullable = false)
    private String name;

    @Column(name = "phylum", length = 125, nullable = false)
    private String phylum;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date creationTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date lastUpdateTime;

    @OneToOne
    @JoinColumn(name = "tier_id")
    private TierModel tier;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhylum() {
        return phylum;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setTier(TierModel tierModel) {
        this.tier = tierModel;
    }

    public TierModel getTier() {
        return this.tier;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public Organism toDataTransferObject() {
        Organism organism = new Organism();
        organism.setId(id);
        organism.setName(name);
        organism.setPhylum(phylum);
        organism.setCreated(creationTime.getTime());
        if (lastUpdateTime != null)
            organism.setUpdated(lastUpdateTime.getTime());
        if (tier != null)
            organism.setTier(tier.toDataTransferObject());
        return organism;
    }
}
