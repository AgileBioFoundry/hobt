package org.abf.hobt.dao.memory;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dao.IRepository;

/**
 * In-memory implementation of persistent repository
 *
 * @author Hector Plahar
 */
public abstract class InMemoryRepository<T extends IDataModel> implements IRepository<T> {
}
