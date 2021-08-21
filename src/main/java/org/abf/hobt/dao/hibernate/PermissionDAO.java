package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.PermissionModel;

public class PermissionDAO extends HibernateRepository<PermissionModel> {

    @Override
    public PermissionModel get(long id) {
        return super.retrieve(PermissionModel.class, id);
    }
}
