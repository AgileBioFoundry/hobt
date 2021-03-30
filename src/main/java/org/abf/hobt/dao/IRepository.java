package org.abf.hobt.dao;

/**
 * Interface for the persistent layer
 *
 * @author Hector Plahar
 */
public interface IRepository<T extends IDataModel> {

    T get(long id);

    T create(T model);

    T update(T object);

    void delete(T t);
}
