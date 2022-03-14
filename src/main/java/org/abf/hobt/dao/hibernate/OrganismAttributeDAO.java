package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.OrganismAttributeModel;

public class OrganismAttributeDAO extends HibernateRepository<OrganismAttributeModel> {

    @Override
    public OrganismAttributeModel get(long id) {
        return super.retrieve(OrganismAttributeModel.class, id);
    }
}
