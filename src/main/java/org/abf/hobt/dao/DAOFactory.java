package org.abf.hobt.dao;

import org.abf.hobt.dao.hibernate.AccountDAO;
import org.abf.hobt.dao.hibernate.ConfigurationDAO;
import org.abf.hobt.dao.hibernate.OrganismDAO;
import org.abf.hobt.dao.hibernate.TierDAO;

/**
 * Instantiates various data access objects as needed. To be replaced by DI
 *
 * @author Hector Plahar
 */
public class DAOFactory {

    private static AccountDAO accountDAO;
    private static OrganismDAO organismDAO;
    private static TierDAO tierDAO;
    private static ConfigurationDAO configurationDAO;

    public static AccountDAO getAccountDAO() {
        if (accountDAO == null)
            accountDAO = new AccountDAO();
        return accountDAO;
    }

    public static OrganismDAO getOrganismDAO() {
        if (organismDAO == null)
            organismDAO = new OrganismDAO();
        return organismDAO;
    }

    public static TierDAO getTierDAO() {
        if (tierDAO == null)
            tierDAO = new TierDAO();
        return tierDAO;
    }

    public static ConfigurationDAO getConfigurationDAO() {
        if (configurationDAO == null)
            configurationDAO = new ConfigurationDAO();
        return configurationDAO;
    }
}
