package org.abf.hobt.cache;

import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.OrganismElementCacheDAO;
import org.abf.hobt.dao.model.OrganismElementCacheModel;
import org.abf.hobt.dao.model.OrganismModel;

import java.util.Optional;

public class ElementCaches {

    private final OrganismElementCacheDAO dao;
    private final OrganismModel organismModel;

    public ElementCaches(long organismId) {
        this.dao = DAOFactory.getOrganismElementCacheDAO();
        this.organismModel = DAOFactory.getOrganismDAO().get(organismId);
        if (this.organismModel == null)
            throw new IllegalArgumentException("Invalid organism id: " + organismId);
    }

    public void updateStatistics(ElementCacheType type, long count) {
        Optional<OrganismElementCacheModel> optional = dao.getByType(organismModel, type);
        OrganismElementCacheModel model;
        if (optional.isPresent()) {
            model = optional.get();
            if (model.getCount() == count)
                return;
            model.setCount(count);
            dao.update(model);
        } else {
            model = new OrganismElementCacheModel();
            model.setOrganism(organismModel);
            model.setType(type);
            model.setCount(count);
            dao.create(model);
        }
    }

}
