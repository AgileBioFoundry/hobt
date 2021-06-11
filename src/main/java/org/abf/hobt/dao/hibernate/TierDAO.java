package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.TierModel;

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
}
