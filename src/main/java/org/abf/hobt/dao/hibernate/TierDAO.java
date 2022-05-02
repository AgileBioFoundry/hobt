package org.abf.hobt.dao.hibernate;

import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.dao.DataAccessException;
import org.abf.hobt.dao.model.TierModel;
import org.hibernate.HibernateException;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class TierDAO extends HibernateRepository<TierModel> {

    @Override
    public TierModel get(long id) {
        return super.retrieve(TierModel.class, id);
    }

    public Optional<TierModel> getByName(String name) {
        CriteriaQuery<TierModel> criteriaQuery = getBuilder().createQuery(TierModel.class);
        Root<TierModel> from = criteriaQuery.from(TierModel.class);
        criteriaQuery.where(getBuilder().equal(getBuilder().lower(from.get("name")), name.trim().toLowerCase()));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }

    public List<TierModel> list() {
        return super.list(TierModel.class, "id", true, 0, Integer.MAX_VALUE);
    }

    /**
     * Retrieve the tier model record after the model at specified id
     *
     * @param id tier model id
     * @return next tier model id or null if none found
     */
    public TierModel getAfter(long id) {
        try {
            CriteriaQuery<TierModel> query = getBuilder().createQuery(TierModel.class);
            Root<TierModel> from = query.from(TierModel.class);
            query.orderBy(getBuilder().asc(from.get("id")));
            query.where(getBuilder().greaterThan(from.get("id"), id));
            List<TierModel> result = currentSession().createQuery(query).setMaxResults(1).list();
            if (result != null && !result.isEmpty())
                return result.get(0);
            return null;
        } catch (HibernateException e) {
            Logger.error(e);
            throw new DataAccessException(e);
        }
    }
}
