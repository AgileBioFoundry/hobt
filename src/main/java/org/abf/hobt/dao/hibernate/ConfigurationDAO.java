package org.abf.hobt.dao.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.abf.hobt.dao.model.ConfigurationModel;

import java.util.List;

/**
 * @author Hector Plahar
 */
public class ConfigurationDAO extends HibernateRepository<ConfigurationModel> {

    public ConfigurationModel get(long id) {
        return super.retrieve(ConfigurationModel.class, id);
    }

    /**
     * Retrieves configuration by key which are unique
     *
     * @param key unique display name identifier
     * @return Configuration with key specified in the parameter or null if not found
     */
    public ConfigurationModel get(String key) {
        CriteriaBuilder cb = getBuilder();
        CriteriaQuery<ConfigurationModel> criteria = cb.createQuery(ConfigurationModel.class);
        Root<ConfigurationModel> root = criteria.from(ConfigurationModel.class);
        criteria.select(root).where(cb.equal(root.get("key"), key));
        return currentSession().createQuery(criteria).uniqueResult();
    }

    public List<ConfigurationModel> getAll() {
        CriteriaBuilder cb = getBuilder();
        CriteriaQuery<ConfigurationModel> criteria = cb.createQuery(ConfigurationModel.class);
        Root<ConfigurationModel> root = criteria.from(ConfigurationModel.class);
        criteria.select(root);
        return currentSession().createQuery(criteria).getResultList();
    }
}
