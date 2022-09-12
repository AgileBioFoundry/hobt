package org.abf.hobt.host;

import org.abf.hobt.cache.ElementCacheType;
import org.abf.hobt.common.ResultData;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.CriteriaDAO;
import org.abf.hobt.dao.hibernate.OrganismCriteriaDAO;
import org.abf.hobt.dao.hibernate.OrganismDAO;
import org.abf.hobt.dao.model.*;
import org.abf.hobt.dto.HostStatistics;
import org.abf.hobt.dto.Organism;
import org.abf.hobt.dto.OrganismCriteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Hector Plahar
 */
public class Organisms {

    private final OrganismDAO dao;

    public Organisms() {
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

        if (organism.getTier() != null) {
            TierModel tier = DAOFactory.getTierDAO().get(organism.getTier().getId());
            if (tier == null)
                throw new IllegalArgumentException("Cannot retrieve tier for organism creation");

            model.setTier(tier);
        }

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
     * Update criteria for specified organism. Values are in 25% increments or -1 to indicate not applicable
     *
     * @param organismId      unique identifier for organism
     * @param criteriaId      unique identifier for criteria
     * @param percentComplete current status of organism
     */
    public void updateOrganismCriteriaStatus(long organismId, long criteriaId, int percentComplete) {
        // status must either be "-1", or between 0 and 100 inclusive
        if ((percentComplete < 0 && percentComplete != -1) || percentComplete > 100)
            throw new IllegalArgumentException("Percent complete status must be in the range [0-100] or -1. Received status of " + percentComplete);

        OrganismModel organismModel = this.dao.get(organismId);
        if (organismModel == null)
            throw new IllegalArgumentException("Cannot locate organism with id: " + organismId);

        CriteriaDAO criteriaDAO = DAOFactory.getCriteriaDAO();
        CriteriaModel criteriaModel = criteriaDAO.get(criteriaId);
        if (criteriaModel == null)
            throw new IllegalArgumentException("Invalid criteria id " + criteriaId);

        OrganismCriteriaDAO organismCriteriaDAO = DAOFactory.getOrganismCriteriaDAO();
        Optional<OrganismCriteriaStatusModel> optional = organismCriteriaDAO.getByOrganismAndCriteria(organismId, criteriaId);
        if (optional.isPresent()) {
            // update
            OrganismCriteriaStatusModel model = optional.get();
            model.setPercentageComplete(percentComplete);
            model.setUpdated(new Date());
            organismCriteriaDAO.update(model);
        } else {
            // create new
            OrganismCriteriaStatusModel model = new OrganismCriteriaStatusModel();
            model.setCreated(new Date());
            model.setOrganism(organismModel);
            model.setPercentageComplete(percentComplete);
            model.setCriteria(criteriaModel);
            organismCriteriaDAO.create(model);
        }
    }

    public List<OrganismCriteria> retrieveCriteria(long organismId) {
        OrganismModel organismModel = this.dao.get(organismId);

        List<OrganismCriteria> results = new ArrayList<>();
        for (OrganismCriteriaStatusModel model : organismModel.getOrganismCriterias())
            results.add(model.toDataTransferObject());
        return results;
    }

    public HostStatistics getStatistics(long organismId) {
        OrganismModel organismModel = DAOFactory.getOrganismDAO().get(organismId);
        if (organismModel == null)
            throw new IllegalArgumentException("Cannot find organism with id " + organismId);

        long count = DAOFactory.getPublicationDAO().listByOrganismCount(organismModel, null);
        HostStatistics statistics = new HostStatistics();
        statistics.setPublicationCount(count);
        statistics.setExperimentCount(getCacheCount(organismModel, ElementCacheType.EXPERIMENT));
        statistics.setPartCount(getCacheCount(organismModel, ElementCacheType.PART));
        statistics.setStrainCount(getCacheCount(organismModel, ElementCacheType.STRAIN));
        statistics.setProtocolCount(getCacheCount(organismModel, ElementCacheType.PROTOCOL));
        return statistics;
    }

    private long getCacheCount(OrganismModel organismModel, ElementCacheType type) {
        Optional<OrganismElementCacheModel> optional = DAOFactory.getOrganismElementCacheDAO().getByType(organismModel, type);
        if (optional.isEmpty())
            return 0;
        return optional.get().getCount();

    }
}
