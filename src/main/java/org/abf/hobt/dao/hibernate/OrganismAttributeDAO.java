package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.OrganismAttributeModel;
import org.abf.hobt.dao.model.OrganismModel;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class OrganismAttributeDAO extends HibernateRepository<OrganismAttributeModel> {

    @Override
    public OrganismAttributeModel get(long id) {
        return super.retrieve(OrganismAttributeModel.class, id);
    }

    public List<OrganismAttributeModel> pageAttributes(int start, int limit, boolean asc, String property) {
        CriteriaQuery<OrganismAttributeModel> criteriaQuery = getBuilder().createQuery(OrganismAttributeModel.class);
        Root<OrganismAttributeModel> root = criteriaQuery.from(OrganismAttributeModel.class);
        criteriaQuery.orderBy(asc ? getBuilder().asc(root.get(property)) : getBuilder().desc(root.get(property)));
        return currentSession().createQuery(criteriaQuery).setFirstResult(start).setMaxResults(limit).getResultList();
    }

    public long getAvailableOrganismAttributeCount() {
        CriteriaQuery<Long> criteriaQuery = getBuilder().createQuery(Long.class);
        Root<OrganismAttributeModel> root = criteriaQuery.from(OrganismAttributeModel.class);
        criteriaQuery.select(getBuilder().countDistinct(root));
        return currentSession().createQuery(criteriaQuery).uniqueResult();
    }

    public List<OrganismAttributeModel> getAttributesForHost(long hostId) {
        CriteriaQuery<OrganismAttributeModel> criteriaQuery = getBuilder().createQuery(OrganismAttributeModel.class);
        Root<OrganismAttributeModel> root = criteriaQuery.from(OrganismAttributeModel.class);
        Join<OrganismAttributeModel, OrganismModel> join = root.join("organisms");
        criteriaQuery.where(getBuilder().or(
            getBuilder().isTrue(root.get("allOrganisms")))
        );
        return currentSession().createQuery(criteriaQuery).list();
    }
}
