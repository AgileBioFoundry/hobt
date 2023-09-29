package org.abf.hobt.dao.hibernate;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.abf.hobt.dao.model.OrganismAttributeModel;
import org.abf.hobt.dao.model.OrganismAttributeValueModel;
import org.abf.hobt.dao.model.OrganismModel;

import java.util.List;
import java.util.Optional;

public class OrganismAttributeValueDAO extends HibernateRepository<OrganismAttributeValueModel> {

    @Override
    public OrganismAttributeValueModel get(long id) {
        return super.retrieve(OrganismAttributeValueModel.class, id);
    }

    public Optional<OrganismAttributeValueModel> getByAttributeAndHost(OrganismModel organism, OrganismAttributeModel attribute) {
        CriteriaQuery<OrganismAttributeValueModel> criteriaQuery = getBuilder().createQuery(OrganismAttributeValueModel.class);
        Root<OrganismAttributeValueModel> root = criteriaQuery.from(OrganismAttributeValueModel.class);
        criteriaQuery.where(getBuilder().equal(root.get("organism"), organism), getBuilder().equal(root.get("organismAttribute"), attribute));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }

    public List<OrganismAttributeValueModel> getByHost(OrganismModel organism) {
        CriteriaQuery<OrganismAttributeValueModel> criteriaQuery = getBuilder().createQuery(OrganismAttributeValueModel.class);
        Root<OrganismAttributeValueModel> root = criteriaQuery.from(OrganismAttributeValueModel.class);
        criteriaQuery.where(getBuilder().equal(root.get("organism"), organism));
        return currentSession().createQuery(criteriaQuery).getResultList();
    }

    public long getCountByHost(OrganismModel organismModel) {
        CriteriaQuery<Long> criteriaQuery = getBuilder().createQuery(Long.class);
        Root<OrganismAttributeValueModel> root = criteriaQuery.from(OrganismAttributeValueModel.class);
        criteriaQuery.where(getBuilder().equal(root.get("organism"), organismModel));
        criteriaQuery.select(getBuilder().countDistinct(root.get("id")));
        return currentSession().createQuery(criteriaQuery).getSingleResult();
    }
}
