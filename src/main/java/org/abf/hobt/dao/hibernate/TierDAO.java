package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.TierModel;

public class TierDAO extends HibernateRepository<TierModel> {

    @Override
    public TierModel get(long id) {
        return super.retrieve(TierModel.class, id);
    }
}
