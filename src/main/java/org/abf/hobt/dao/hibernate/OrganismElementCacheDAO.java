package org.abf.hobt.dao.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.abf.hobt.cache.ElementCacheType;
import org.abf.hobt.dao.model.OrganismElementCacheModel;
import org.abf.hobt.dao.model.OrganismModel;

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
