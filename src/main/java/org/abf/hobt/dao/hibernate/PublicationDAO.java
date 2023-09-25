package org.abf.hobt.dao.hibernate;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.dao.DataAccessException;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.dao.model.PublicationModel;
import org.hibernate.HibernateException;

import java.util.List;

public class PublicationDAO extends HibernateRepository<PublicationModel> {

    @Override
    public PublicationModel get(long id) {
        return super.retrieve(PublicationModel.class, id);
    }

    public List<PublicationModel> list(String sort, boolean asc, int start, int limit) {
        return super.list(PublicationModel.class, sort, asc, start, limit);
    }

    public long listCount() {
        return super.listCount(PublicationModel.class);
    }

    public List<PublicationModel> listByOrganism(OrganismModel organism, String sort, boolean asc, int start, int limit,
                                                 Boolean privilegedOnly) {
        try {
            CriteriaQuery<PublicationModel> query = getBuilder().createQuery(PublicationModel.class);
            Root<PublicationModel> from = query.from(PublicationModel.class);
            Join<PublicationModel, OrganismModel> organisms = from.join("organisms");
            if (privilegedOnly == null) {
                query.where(getBuilder().equal(organisms.get("id"), organism.getId()));
            } else {
                query.where(getBuilder().equal(organisms.get("id"), organism.getId()),
                    getBuilder().equal(from.get("privileged"), privilegedOnly));
            }
            query.orderBy(asc ? getBuilder().asc(from.get(sort)) : getBuilder().desc(from.get(sort)));
            return currentSession().createQuery(query).setMaxResults(limit).setFirstResult(start).list();
        } catch (HibernateException e) {
            Logger.error(e);
            throw new DataAccessException(e);
        }
    }

    public long listByOrganismCount(OrganismModel organism, Boolean privilegedOnly) {
        try {
            CriteriaQuery<Long> query = getBuilder().createQuery(Long.class);
            Root<PublicationModel> from = query.from(PublicationModel.class);
            Join<PublicationModel, OrganismModel> organisms = from.join("organisms");

            query.select(getBuilder().countDistinct(from.get("id")));
            // filter if privileged value is set
            if (privilegedOnly == null) {
                query.where(getBuilder().equal(organisms.get("id"), organism.getId()));
            } else {
                query.where(getBuilder().equal(organisms.get("id"), organism.getId()),
                    getBuilder().equal(from.get("privileged"), privilegedOnly));
            }
            return currentSession().createQuery(query).uniqueResult();
        } catch (Exception e) {
            Logger.error(e);
            throw new DataAccessException(e);
        }
    }
}
