package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.OrganismAttributeOptionModel;

public class OrganismAttributeOptionDAO extends HibernateRepository<OrganismAttributeOptionModel> {
    @Override
    public OrganismAttributeOptionModel get(long id) {
        return super.retrieve(OrganismAttributeOptionModel.class, id);
    }
}
