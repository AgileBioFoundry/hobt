package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.CriteriaModel;
import org.abf.hobt.dao.model.OrganismCriteriaModel;
import org.abf.hobt.dao.model.OrganismModel;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class OrganismCriteriaDAO extends HibernateRepository<OrganismCriteriaModel> {

    @Override
    public OrganismCriteriaModel get(long id) {
        return super.retrieve(OrganismCriteriaModel.class, id);
    }

    public List<OrganismCriteriaModel> getByOrganism(long organismId) {
        CriteriaBuilder cb = getBuilder();
        CriteriaQuery<OrganismCriteriaModel> criteriaQuery = getBuilder().createQuery(OrganismCriteriaModel.class);
        Root<OrganismCriteriaModel> root = criteriaQuery.from(OrganismCriteriaModel.class);
        Join<OrganismCriteriaModel, OrganismModel> organism = root.join("organism");
        criteriaQuery.where(cb.equal(organism.get("id"), organismId));
//        criteriaQuery.orderBy(asc ? cb.asc(root.get(property)) : cb.desc(root.get(property)));
        return currentSession().createQuery(criteriaQuery).getResultList();
    }

    public Optional<OrganismCriteriaModel> getByOrganismAndCriteria(long organismId, long criteriaId) {
        CriteriaBuilder cb = getBuilder();
        CriteriaQuery<OrganismCriteriaModel> criteriaQuery = getBuilder().createQuery(OrganismCriteriaModel.class);
        Root<OrganismCriteriaModel> root = criteriaQuery.from(OrganismCriteriaModel.class);
        Join<OrganismCriteriaModel, OrganismModel> organism = root.join("organism");
        Join<OrganismCriteriaModel, CriteriaModel> criteria = root.join("criteria");
        criteriaQuery.where(getBuilder().and(cb.equal(organism.get("id"), organismId), cb.equal(criteria.get("id"), criteriaId)));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }

}
