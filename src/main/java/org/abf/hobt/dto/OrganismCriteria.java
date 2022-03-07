package org.abf.hobt.dto;

import org.abf.hobt.service.ice.IDataTransferObject;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link org.abf.hobt.dao.model.OrganismCriteriaModel}
 *
 * @author Hector Plahar
 */
public class OrganismCriteria implements IDataTransferObject {

    private final List<Organism> organism = new ArrayList<>();
    private final List<Criteria> criteria = new ArrayList<>();
    private int percentageComplete;             // aka status
    private long created;
    private long updated;

    public List<Organism> getOrganism() {
        return organism;
    }

    public List<Criteria> getCriteria() {
        return criteria;
    }

    public int getPercentageComplete() {
        return percentageComplete;
    }

    public void setPercentageComplete(int percentageComplete) {
        this.percentageComplete = percentageComplete;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }
}
