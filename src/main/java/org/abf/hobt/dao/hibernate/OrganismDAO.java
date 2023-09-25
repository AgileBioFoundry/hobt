package org.abf.hobt.dao.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.abf.hobt.dao.model.OrganismModel;

import java.util.List;

/**
 * DAO for managing {@link OrganismModel} objects
 *
 * @author Hector Plahar
 */
public class OrganismDAO extends HibernateRepository<OrganismModel> {

    @Override
    public OrganismModel get(long id) {
        return super.retrieve(OrganismModel.class, id);
    }

    public List<OrganismModel> retrieveOrganisms(int start, int limit, boolean asc, String property) {
        CriteriaBuilder cb = getBuilder();
        CriteriaQuery<OrganismModel> criteriaQuery = getBuilder().createQuery(OrganismModel.class);
        Root<OrganismModel> root = criteriaQuery.from(OrganismModel.class);
        criteriaQuery.orderBy(asc ? cb.asc(root.get(property)) : cb.desc(root.get(property)));
        return currentSession().createQuery(criteriaQuery).setFirstResult(start).setMaxResults(limit).getResultList();
    }

    public long getAvailableOrganismCount() {
        CriteriaQuery<Long> criteriaQuery = getBuilder().createQuery(Long.class);
        Root<OrganismModel> root = criteriaQuery.from(OrganismModel.class);
        criteriaQuery.select(getBuilder().countDistinct(root));
        return currentSession().createQuery(criteriaQuery).uniqueResult();
    }
}
