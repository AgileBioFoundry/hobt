package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.Criteria;

import javax.persistence.*;

@Entity
@Table(name = "CRITERIA")
public class CriteriaModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "criteria_id")
    @SequenceGenerator(name = "criteria_id", sequenceName = "criteria_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "label", length = 125, nullable = false)
    private String label;

    @Column(name = "description", length = 1024)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id")
    private TierModel tier;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TierModel getTier() {
        return tier;
    }

    public void setTier(TierModel tier) {
        this.tier = tier;
    }

    @Override
    public Criteria toDataTransferObject() {
        Criteria criteria = new Criteria();
        criteria.setLabel(this.label);
        criteria.setDescription(this.description);
        return criteria;
    }
}
