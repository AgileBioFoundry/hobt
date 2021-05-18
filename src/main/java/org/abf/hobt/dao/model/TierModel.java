package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.tier.Tier;

import javax.persistence.*;

@Entity
@Table(name = "TIER")
public class TierModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tier_id")
    @SequenceGenerator(name = "tier_id", sequenceName = "tier_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Tier toDataTransferObject() {
        Tier tier = new Tier();
        tier.setId(this.id);
        tier.setValue(this.name);
        return tier;
    }
}
