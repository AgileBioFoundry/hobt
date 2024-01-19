package org.abf.hobt.dao;

import org.abf.hobt.dao.hibernate.*;

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
    private static CriteriaDAO criteriaDAO;
    private static OrganismCriteriaDAO organismCriteriaDAO;
    private static UserRoleDAO userRoleDAO;
    private static RoleDAO roleDAO;
    private static TierRuleDAO tierRuleDAO;
    private static PermissionDAO permissionDAO;
    private static PublicationDAO publicationDAO;
    private static TierStatusDAO tierStatusDAO;
    private static OrganismAttributeDAO organismAttributeDAO;
    private static OrganismElementCacheDAO organismElementCacheDAO;
    private static OrganismAttributeValueDAO organismAttributeValueDAO;
    private static OrganismAttributeOptionDAO organismAttributeOptionDAO;

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

    public static CriteriaDAO getCriteriaDAO() {
        if (criteriaDAO == null)
            criteriaDAO = new CriteriaDAO();
        return criteriaDAO;
    }

    public static OrganismCriteriaDAO getOrganismCriteriaDAO() {
        if (organismCriteriaDAO == null)
            organismCriteriaDAO = new OrganismCriteriaDAO();
        return organismCriteriaDAO;
    }

    public static RoleDAO getRoleDAO() {
        if (roleDAO == null)
            roleDAO = new RoleDAO();
        return roleDAO;
    }

    public static UserRoleDAO getUserRoleDAO() {
        if (userRoleDAO == null)
            userRoleDAO = new UserRoleDAO();
        return userRoleDAO;
    }

    public static TierRuleDAO getTierRuleDAO() {
        if (tierRuleDAO == null)
            tierRuleDAO = new TierRuleDAO();
        return tierRuleDAO;
    }

    public static PermissionDAO getPermissionDAO() {
        if (permissionDAO == null)
            permissionDAO = new PermissionDAO();
        return permissionDAO;
    }

    public static PublicationDAO getPublicationDAO() {
        if (publicationDAO == null)
            publicationDAO = new PublicationDAO();
        return publicationDAO;
    }

    public static TierStatusDAO getTierStatusDAO() {
        if (tierStatusDAO == null)
            tierStatusDAO = new TierStatusDAO();
        return tierStatusDAO;
    }

    public static OrganismAttributeDAO getOrganismAttributeDAO() {
        if (organismAttributeDAO == null)
            organismAttributeDAO = new OrganismAttributeDAO();
        return organismAttributeDAO;
    }

    public static OrganismElementCacheDAO getOrganismElementCacheDAO() {
        if (organismElementCacheDAO == null)
            organismElementCacheDAO = new OrganismElementCacheDAO();
        return organismElementCacheDAO;
    }

    public static OrganismAttributeValueDAO getOrganismAttributeValueDAO() {
        if (organismAttributeValueDAO == null)
            organismAttributeValueDAO = new OrganismAttributeValueDAO();
        return organismAttributeValueDAO;
    }

    public static OrganismAttributeOptionDAO getOrganismAttributeOptionDAO() {
        if (organismAttributeOptionDAO == null)
            organismAttributeOptionDAO = new OrganismAttributeOptionDAO();
        return organismAttributeOptionDAO;
    }
}
