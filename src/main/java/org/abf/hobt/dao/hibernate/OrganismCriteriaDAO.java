package org.abf.hobt.dao.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.abf.hobt.dao.model.CriteriaModel;
import org.abf.hobt.dao.model.OrganismCriteriaStatusModel;
import org.abf.hobt.dao.model.OrganismModel;

import java.util.List;
import java.util.Optional;

public class OrganismCriteriaDAO extends HibernateRepository<OrganismCriteriaStatusModel> {

    @Override
    public OrganismCriteriaStatusModel get(long id) {
        return super.retrieve(OrganismCriteriaStatusModel.class, id);
    }

    public List<OrganismCriteriaStatusModel> getByOrganism(long organismId) {
        CriteriaBuilder cb = getBuilder();
        CriteriaQuery<OrganismCriteriaStatusModel> criteriaQuery = getBuilder().createQuery(OrganismCriteriaStatusModel.class);
        Root<OrganismCriteriaStatusModel> root = criteriaQuery.from(OrganismCriteriaStatusModel.class);
        Join<OrganismCriteriaStatusModel, OrganismModel> organism = root.join("organism");
        criteriaQuery.where(cb.equal(organism.get("id"), organismId));
//        criteriaQuery.orderBy(asc ? cb.asc(root.get(property)) : cb.desc(root.get(property)));
        return currentSession().createQuery(criteriaQuery).getResultList();
    }

    public Optional<OrganismCriteriaStatusModel> getByOrganismAndCriteria(long organismId, long criteriaId) {
        CriteriaBuilder cb = getBuilder();
        CriteriaQuery<OrganismCriteriaStatusModel> criteriaQuery = getBuilder().createQuery(OrganismCriteriaStatusModel.class);
        Root<OrganismCriteriaStatusModel> root = criteriaQuery.from(OrganismCriteriaStatusModel.class);
        Join<OrganismCriteriaStatusModel, OrganismModel> organism = root.join("organism");
        Join<OrganismCriteriaStatusModel, CriteriaModel> criteria = root.join("criteria");
        criteriaQuery.where(getBuilder().and(cb.equal(organism.get("id"), organismId), cb.equal(criteria.get("id"), criteriaId)));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }

}
