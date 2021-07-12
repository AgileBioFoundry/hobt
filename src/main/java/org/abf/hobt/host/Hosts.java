package org.abf.hobt.host;

import org.abf.hobt.common.ResultData;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.CriteriaDAO;
import org.abf.hobt.dao.hibernate.OrganismCriteriaDAO;
import org.abf.hobt.dao.hibernate.OrganismDAO;
import org.abf.hobt.dao.model.CriteriaModel;
import org.abf.hobt.dao.model.OrganismCriteriaModel;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.dao.model.TierModel;
import org.abf.hobt.dto.Criteria;
import org.abf.hobt.dto.Organism;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * TODO: rename to Organisms?
 *
 * @author Hector Plahar
 */
public class Hosts {

    private final OrganismDAO dao;

    public Hosts() {
        this.dao = DAOFactory.getOrganismDAO();
    }

    public Organism retrieve(long id) {
        OrganismModel model = this.dao.get(id);
        if (model == null)
            return null;
        return model.toDataTransferObject();
    }

    public Organism create(Organism organism) {
        if (StringUtils.isBlank(organism.getName()) || StringUtils.isBlank(organism.getPhylum()))
            throw new IllegalArgumentException("Cannot create organism without name or phylum");

        OrganismModel model = new OrganismModel();
        model.setCreationTime(new Date());
        model.setName(organism.getName());
        model.setPhylum(organism.getPhylum());

        TierModel tier = DAOFactory.getTierDAO().get(organism.getTier().getId());
        if (tier == null)
            throw new IllegalArgumentException("Cannot retrieve tier for organism creation");

        model.setTier(tier);

        model = this.dao.create(model);
        if (model != null)
            return model.toDataTransferObject();

        return null;
    }

    public ResultData<Organism> retrieveList(int offset, int limit, String sort, boolean asc) {
        ResultData<Organism> result = new ResultData<>();
        List<OrganismModel> organisms = this.dao.retrieveOrganisms(offset, limit, asc, sort);
        long count = this.dao.getAvailableOrganismCount();
        result.setAvailable(count);

        for (OrganismModel model : organisms)
            result.getRequested().add(model.toDataTransferObject());

        return result;
    }

    /**
     * Update criteria for specified organism
     *
     * @param organismId
     * @param criteriaId
     * @param percentComplete
     */
    public void updateOrganismCriteriaStatus(long organismId, long criteriaId, int percentComplete) {
        if (percentComplete < 0 || percentComplete > 100)
            throw new IllegalArgumentException("Percent complete status must be in the range [0-100]. Received status of " + percentComplete);

        OrganismModel organismModel = this.dao.get(organismId);
        if (organismModel == null)
            throw new IllegalArgumentException("Cannot locate organism with id: " + organismId);

        CriteriaDAO criteriaDAO = DAOFactory.getCriteriaDAO();
        CriteriaModel criteriaModel = criteriaDAO.get(criteriaId);
        if (criteriaModel == null)
            throw new IllegalArgumentException("Invalid criteria id " + criteriaId);

        OrganismCriteriaDAO organismCriteriaDAO = DAOFactory.getOrganismCriteriaDAO();
        Optional<OrganismCriteriaModel> optional = organismCriteriaDAO.getByOrganismAndCriteria(organismId, criteriaId);
        if (optional.isPresent()) {
            // update
            OrganismCriteriaModel model = optional.get();
            model.setPercentageComplete(percentComplete);
            model.setUpdated(new Date());
            organismCriteriaDAO.update(model);
        } else {
            // create new
            OrganismCriteriaModel model = new OrganismCriteriaModel();
            model.setCreated(new Date());
            model.setOrganism(organismModel);
            model.setPercentageComplete(percentComplete);
            model.setCriteria(criteriaModel);
            organismCriteriaDAO.create(model);
        }
    }

    public List<Criteria> retrieveCriteria(long organismId) {
        OrganismCriteriaDAO organismCriteriaDAO = DAOFactory.getOrganismCriteriaDAO();
        List<OrganismCriteriaModel> list = organismCriteriaDAO.getByOrganism(organismId);
        List<Criteria> results = new ArrayList<>();
        for (OrganismCriteriaModel model : list)
            results.add(model.toDataTransferObject());
        return results;
    }
}
