package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.tier.Tier;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TIER")
public class TierModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tier_id")
    @SequenceGenerator(name = "tier_id", sequenceName = "tier_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "index")
    private int index;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tier", orphanRemoval = true)
    private Set<CriteriaModel> criteria = new HashSet<>();

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<CriteriaModel> getCriteria() {
        return this.criteria;
    }

    @Override
    public Tier toDataTransferObject() {
        Tier tier = new Tier();
        tier.setId(this.id);
        tier.setLabel(this.name);
        tier.setIndex(this.index);
        return tier;
    }
}
