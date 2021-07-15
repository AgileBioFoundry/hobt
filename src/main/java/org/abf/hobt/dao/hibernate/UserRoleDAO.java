package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.UserRoleModel;

public class UserRoleDAO extends HibernateRepository<UserRoleModel> {

    @Override
    public UserRoleModel get(long id) {
        return super.retrieve(UserRoleModel.class, id);
    }
}
