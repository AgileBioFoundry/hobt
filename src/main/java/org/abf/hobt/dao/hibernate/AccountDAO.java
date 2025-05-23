package org.abf.hobt.dao.hibernate;

import jakarta.persistence.criteria.*;
import org.abf.hobt.account.Accounts;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DataAccessException;
import org.abf.hobt.dao.model.AccountModel;
import org.abf.hobt.dao.model.GroupModel;
import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Data Accessor object for managing {@link AccountModel} objects
 *
 * @author Hector Plahar
 */
public class AccountDAO extends HibernateRepository<AccountModel> {

    /**
     * Retrieves account using a user id
     *
     * @param userId unique user id
     * @return account obtaining using the user id, or null if not found
     * @throws IllegalArgumentException if the referenced user id is null
     * @ on error processing the resulting database query
     */
    public AccountModel getByUserId(String userId) {
        try {
            if (userId == null)
                throw new IllegalArgumentException("Cannot retrieve account with null user id");

            CriteriaQuery<AccountModel> criteriaQuery = getBuilder().createQuery(AccountModel.class);
            Root<AccountModel> from = criteriaQuery.from(AccountModel.class);
            criteriaQuery.select(from).where(getBuilder().equal(getBuilder().lower(from.get("userId")), userId.toLowerCase()));
            return currentSession().createQuery(criteriaQuery).uniqueResult();
        } catch (HibernateException he) {
            throw new DataAccessException(he);
        }
    }

    public List<AccountModel> retrieveAccounts(int start, int limit, boolean asc, String property, String filter) {
        CriteriaBuilder cb = getBuilder();
        CriteriaQuery<AccountModel> criteriaQuery = getBuilder().createQuery(AccountModel.class);
        Root<AccountModel> root = criteriaQuery.from(AccountModel.class);
        setWhereQuery(root, criteriaQuery, filter);
        criteriaQuery.orderBy(asc ? cb.asc(root.get(property)) : cb.desc(root.get(property)));
        return currentSession().createQuery(criteriaQuery).setFirstResult(start).setMaxResults(limit).getResultList();
    }

    public long getAccountTotal(String filter) {
        CriteriaQuery<Long> criteriaQuery = getBuilder().createQuery(Long.class);
        Root<AccountModel> root = criteriaQuery.from(AccountModel.class);
        criteriaQuery.select(getBuilder().countDistinct(root));
        setWhereQuery(root, criteriaQuery, filter);
        return currentSession().createQuery(criteriaQuery).uniqueResult();
    }

    private void setWhereQuery(Root<AccountModel> root, CriteriaQuery<?> criteriaQuery, String filter) {
        Predicate p1 = getBuilder().notEqual(root.get("userId"), Accounts.DEFAULT_ADMIN_USERID.toLowerCase());
        if (!StringUtils.isBlank(filter)) {
            filter = filter.toUpperCase();
            Predicate or1 = getBuilder().like(getBuilder().upper(root.get("firstName")), "%" + filter + "%");
            Predicate or2 = getBuilder().like(getBuilder().upper(root.get("lastName")), "%" + filter + "%");
            criteriaQuery.where(getBuilder().and(p1, getBuilder().or(or1, or2)));
        } else {
            criteriaQuery.where(getBuilder().and(p1));
        }
    }

    /**
     * Retrieves accounts whose firstName, lastName, or email fields match the specified
     * token up to the specified limit.
     *
     * @param token filter for the account fields
     * @param limit maximum number of matching accounts to return
     * @return list of matching accounts
     */
    public List<AccountModel> getMatchingAccounts(String token, int limit) {
        try {
            CriteriaQuery<AccountModel> query = getBuilder().createQuery(AccountModel.class);
            Root<AccountModel> from = query.from(AccountModel.class);
            String[] tokens = token.split("\\s+");
            createQuery(from, query, null, tokens);
            return currentSession().createQuery(query).setMaxResults(limit).list();
        } catch (HibernateException e) {
            Logger.error(e);
            throw new DataAccessException(e);
        }
    }

    public List<AccountModel> getGroupMembers(GroupModel groupModel) {
        try {
            CriteriaQuery<AccountModel> query = getBuilder().createQuery(AccountModel.class);
            Root<AccountModel> from = query.from(AccountModel.class);
            Join<AccountModel, GroupModel> groups = from.join("groups");
            query.where(getBuilder().equal(groups, groupModel));
            return currentSession().createQuery(query).list();
        } catch (HibernateException e) {
            Logger.error(e);
            throw new DataAccessException(e);
        }
    }

    public List<AccountModel> getMatchingGroupMembers(Set<GroupModel> matchingGroupModels, String token, int limit) {
        try {
            CriteriaQuery<AccountModel> query = getBuilder().createQuery(AccountModel.class);
            Root<AccountModel> from = query.from(AccountModel.class);
            Join<AccountModel, GroupModel> groups = from.join("groups");

            String[] tokens = token.split("\\s+");
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(groups.in(matchingGroupModels));
            createQuery(from, query, predicates, tokens);
            return currentSession().createQuery(query).setMaxResults(limit).list();
        } catch (HibernateException e) {
            Logger.error(e);
            throw new DataAccessException(e);
        }
    }

    private void createQuery(Root<AccountModel> from, CriteriaQuery<AccountModel> query, List<Predicate> predicates, String[] tokens) {
        if (predicates == null)
            predicates = new ArrayList<>();

        for (String tok : tokens) {
            tok = tok.toLowerCase();
            predicates.add(
                getBuilder().or(
                    getBuilder().like(getBuilder().lower(from.get("firstName")), "%" + tok + "%"),
                    getBuilder().like(getBuilder().lower(from.get("lastName")), "%" + tok + "%"),
                    getBuilder().like(getBuilder().lower(from.get("email")), "%" + tok + "%"))
            );
        }
        query.where(predicates.toArray(new Predicate[0])).distinct(true);
    }

    // remove
    public void removeGroup(GroupModel groupModel, AccountModel accountModel) {
        accountModel.getGroups().remove(groupModel);
        update(accountModel);
    }

    @Override
    public AccountModel get(long id) {
        return super.retrieve(AccountModel.class, id);
    }
}
