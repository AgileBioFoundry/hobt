package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.Criteria;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OrganismCriteria")
public class OrganismCriteriaModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_criteria_id")
    @SequenceGenerator(name = "organism_criteria_id", sequenceName = "organism_criteria_id_seq", allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "organism_id", nullable = false, updatable = false)
    private OrganismModel organism;

    @OneToOne
    @JoinColumn(name = "criteria_id", nullable = false, updatable = false)
    private CriteriaModel criteria;

    @Column(name = "percent_complete")
    private int percentageComplete;             // aka status

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;

    public long getId() {
        return id;
    }

    public int getPercentageComplete() {
        return percentageComplete;
    }

    public void setPercentageComplete(int percentageComplete) {
        this.percentageComplete = percentageComplete;
    }

    public OrganismModel getOrganism() {
        return organism;
    }

    public void setOrganism(OrganismModel organism) {
        this.organism = organism;
    }

    public CriteriaModel getCriteria() {
        return criteria;
    }

    public void setCriteria(CriteriaModel criteria) {
        this.criteria = criteria;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public Criteria toDataTransferObject() {
        Criteria criteria = new Criteria();
        criteria.setId(this.criteria.getId());
        criteria.setStatus(this.percentageComplete);
        criteria.setDescription(this.getCriteria().getDescription());
        criteria.setLabel(this.getCriteria().getLabel());
        return criteria;
    }
}
