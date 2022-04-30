package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.Organism;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organism", orphanRemoval = true)
    private final Set<OrganismCriteriaStatusModel> organismCriterias = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organism", orphanRemoval = true)
    private final Set<TierStatusModel> tierStatus = new LinkedHashSet<>();

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

    public Set<OrganismCriteriaStatusModel> getOrganismCriterias() {
        return organismCriterias;
    }

    public Set<TierStatusModel> getTierStatus() {
        return tierStatus;
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
        if (this.tier != null)
            organism.setTier(this.tier.toDataTransferObject());
        return organism;
    }
}
