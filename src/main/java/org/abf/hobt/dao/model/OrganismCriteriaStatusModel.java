package org.abf.hobt.dao.model;

import jakarta.persistence.*;
import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.OrganismCriteria;

import java.util.Date;

@Entity
@Table(name = "OrganismCriteriaStatus")
public class OrganismCriteriaStatusModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_criteria_id")
    @SequenceGenerator(name = "organism_criteria_id", sequenceName = "organism_criteria_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organism_id")
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

    public void setCriteria(CriteriaModel criteria) {
        this.criteria = criteria;
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

    @Override
    public OrganismCriteria toDataTransferObject() {
        OrganismCriteria organismCriteria = new OrganismCriteria();
        if (this.created != null)
            organismCriteria.setCreated(this.created.getTime());
        organismCriteria.setPercentageComplete(this.percentageComplete);
        if (this.updated != null)
            organismCriteria.setUpdated(this.updated.getTime());
        organismCriteria.setCriteria(this.criteria.toDataTransferObject());
        return organismCriteria;
    }
}
