package org.abf.hobt.dao.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.abf.hobt.dao.model.UserRoleModel;
import org.abf.hobt.dto.Account;

import java.util.List;

public class UserRoleDAO extends HibernateRepository<UserRoleModel> {

    @Override
    public UserRoleModel get(long id) {
        return super.retrieve(UserRoleModel.class, id);
    }

    public List<UserRoleModel> getByUser(String userId) {
        CriteriaBuilder builder = getBuilder();
        CriteriaQuery<UserRoleModel> criteriaQuery = builder.createQuery(UserRoleModel.class);
        Root<UserRoleModel> root = criteriaQuery.from(UserRoleModel.class);
        Join<UserRoleModel, Account> accountJoin = root.join("user");
        criteriaQuery.where(builder.equal(accountJoin.get("userId"), userId));
        return currentSession().createQuery(criteriaQuery).list();
    }
}
