package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.OrganismCriteria;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "OrganismCriteria")
public class OrganismCriteriaModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "organism_criteria_id")
    @SequenceGenerator(name = "organism_criteria_id", sequenceName = "organism_criteria_id_seq", allocationSize = 1)
    private long id;

    @OneToMany(fetch = FetchType.LAZY)
    private final List<OrganismModel> organism = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private final List<CriteriaModel> criteria = new ArrayList<>();

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

    public List<OrganismModel> getOrganisms() {
        return organism;
    }

    public List<CriteriaModel> getCriteria() {
        return criteria;
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
    public OrganismCriteria toDataTransferObject() {
        OrganismCriteria criteria = new OrganismCriteria();
        if (this.created != null)
            criteria.setCreated(this.created.getTime());
        criteria.setPercentageComplete(this.percentageComplete);
        if (this.updated != null)
            criteria.setUpdated(this.updated.getTime());

        for (CriteriaModel criteriaModel : this.criteria) {
            criteria.getCriteria().add(criteriaModel.toDataTransferObject());
        }
        return criteria;
    }
}
