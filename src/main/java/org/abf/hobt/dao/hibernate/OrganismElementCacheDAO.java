package org.abf.hobt.dao.hibernate;

import org.abf.hobt.cache.ElementCacheType;
import org.abf.hobt.dao.model.OrganismElementCacheModel;
import org.abf.hobt.dao.model.OrganismModel;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class OrganismElementCacheDAO extends HibernateRepository<OrganismElementCacheModel> {

    @Override
    public OrganismElementCacheModel get(long id) {
        return super.retrieve(OrganismElementCacheModel.class, id);
    }

    public Optional<OrganismElementCacheModel> getByType(OrganismModel model, ElementCacheType type) {
        CriteriaBuilder builder = getBuilder();
        CriteriaQuery<OrganismElementCacheModel> criteriaQuery = builder.createQuery(OrganismElementCacheModel.class);
        Root<OrganismElementCacheModel> root = criteriaQuery.from(OrganismElementCacheModel.class);
        criteriaQuery.where(builder.equal(root.get("type"), type), builder.equal(root.get("organism"), model));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }
}
