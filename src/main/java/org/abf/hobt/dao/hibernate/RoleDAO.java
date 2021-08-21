package org.abf.hobt.dao.hibernate;

import org.abf.hobt.dao.model.RoleModel;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class RoleDAO extends HibernateRepository<RoleModel> {

    @Override
    public RoleModel get(long id) {
        return super.retrieve(RoleModel.class, id);
    }

    /**
     * Retrieves a role by label. Role labels are unique across all roles
     *
     * @param label label used to retrieve rol
     * @return Optional container that contains the role if one is found with the associated label, or null otherwise
     */
    public Optional<RoleModel> getByLabel(String label) {
        CriteriaBuilder builder = getBuilder();
        CriteriaQuery<RoleModel> criteriaQuery = builder.createQuery(RoleModel.class);
        Root<RoleModel> root = criteriaQuery.from(RoleModel.class);
        criteriaQuery.where(builder.equal(root.get("label"), label));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }

    public List<RoleModel> retrieve(int offset, int limit) {
        CriteriaBuilder builder = getBuilder();
        CriteriaQuery<RoleModel> query = builder.createQuery(RoleModel.class);
        Root<RoleModel> from = query.from(RoleModel.class);
        query.orderBy(getBuilder().desc(from.get("id")));
        return currentSession().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
