package org.abf.hobt.dao.model;

import jakarta.persistence.*;
import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.tier.Tier;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TIER")
public class TierModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tier_id")
    @SequenceGenerator(name = "tier_id", sequenceName = "tier_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name", length = 125, nullable = false)
    private String name;

    @Column(name = "index")
    private int index;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tier", orphanRemoval = true)
    private final Set<CriteriaModel> criteria = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tier", orphanRemoval = true)
    private final Set<TierRuleModel> rules = new HashSet<>();

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

    public Set<TierRuleModel> getRules() {
        return this.rules;
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
