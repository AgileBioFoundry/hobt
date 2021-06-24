package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.CriteriaModel;

import java.util.List;

public class CriteriaDAO extends HibernateRepository<CriteriaModel> {

    @Override
    public CriteriaModel get(long id) {
        return super.retrieve(CriteriaModel.class, id);
    }

    public List<CriteriaModel> criteriaList() {
        return super.list(CriteriaModel.class, "id", false, 0, Integer.MAX_VALUE);
    }
}
