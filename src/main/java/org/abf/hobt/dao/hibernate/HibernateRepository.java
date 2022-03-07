package org.abf.hobt.dao.hibernate;

import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.dao.DataAccessException;
import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dao.IRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Hibernate Persistence repository implementation
 *
 * @author Hector Plahar
 */

public abstract class HibernateRepository<T extends IDataModel> implements IRepository<T> {

    /**
     * Obtain the current hibernate {@link Session}.
     *
     * @return {@link Session}
     */
    protected static Session currentSession() {
        return HibernateConfiguration.currentSession();
    }

    protected static CriteriaBuilder getBuilder() {
        return currentSession().getCriteriaBuilder();
    }

    /**
     * Deletes an {@link IDataModel} from the database.
     *
     * @param object model to delete
     * @throws DataAccessException on Hibernate Exception or invalid parameter
     */
    @Override
    public void delete(T object) {
        try {
            currentSession().delete(object);
        } catch (HibernateException e) {
            throw new DataAccessException("dbDelete failed!", e);
        }
    }

    /**
     * Updates an existing object in the database
     *
     * @param object Object to update
     * @return updated object
     * @throws DataAccessException on Hibernate Exception or invalid parameter
     */
    public T update(T object) {
        try {
            currentSession().update(object);
        } catch (HibernateException e) {
            throw new DataAccessException("dbUpdate failed!", e);
        }
        return object;
    }

    /**
     * Creates new object in the database
     *
     * @param model {@link IDataModel} object to create
     * @return Object created {@link IDataModel} object
     * @throws DataAccessException in the event of a problem saving or invalid parameter
     */
    public T create(T model) {
        try {
            currentSession().save(model);
        } catch (HibernateException e) {
            throw new DataAccessException("dbCreate failed!", e);
        }

        return model;
    }

    /**
     * Retrieve an {@link IDataModel} from the database by Class and database id.
     *
     * @param clazz class type for {@link IDataModel}
     * @param id    unique synthetic identifier for {@link IDataModel}
     * @return {@link IDataModel} obtained from the database.
     * @throws DataAccessException on Hibernate Exception
     */
    protected T retrieve(Class<T> clazz, long id) {
        try {
            return currentSession().get(clazz, id);
        } catch (HibernateException e) {
            Logger.error(e);
            throw new DataAccessException("Could not retrieve " + clazz.getSimpleName() + " with id \"" + id + "\"", e);
        }
    }

    protected List<T> list(Class<T> clazz, String sort, boolean asc, int start, int limit) {
        try {
            CriteriaQuery<T> query = getBuilder().createQuery(clazz);
            Root<T> from = query.from(clazz);
            query.orderBy(asc ? getBuilder().asc(from.get(sort)) : getBuilder().desc(from.get(sort)));
            return currentSession().createQuery(query).setMaxResults(limit).setFirstResult(start).list();
        } catch (HibernateException e) {
            Logger.error(e);
            throw new DataAccessException(e);
        }
    }

    protected long listCount(Class<T> clazz) {
        try {
            CriteriaQuery<Long> query = getBuilder().createQuery(Long.class);
            Root<T> from = query.from(clazz);
            query.select(getBuilder().countDistinct(from.get("id")));
            return currentSession().createQuery(query).uniqueResult();
        } catch (Exception e) {
            Logger.error(e);
            throw new DataAccessException(e);
        }
    }

    protected CriteriaQuery<T> getQuery(Class<T> tClass) {
        return getBuilder().createQuery(tClass);
    }
}
