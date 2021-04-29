package org.abf.hobt.dao;

import org.abf.hobt.dao.hibernate.AccountDAO;
import org.abf.hobt.dao.hibernate.OrganismDAO;

/**
 * Instantiates various data access objects as needed. To be replaced by DI
 *
 * @author Hector Plahar
 */
public class DAOFactory {

    private static AccountDAO accountDAO;
    private static OrganismDAO organismDAO;

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

}
