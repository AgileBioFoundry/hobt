package org.abf.hobt.account;

import org.abf.hobt.common.access.AuthorizationException;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.AccountDAO;
import org.abf.hobt.dao.hibernate.RoleDAO;
import org.abf.hobt.dao.model.AccountModel;
import org.abf.hobt.dao.model.PermissionModel;
import org.abf.hobt.dao.model.RoleModel;
import org.abf.hobt.dao.model.UserRoleModel;
import org.abf.hobt.dto.Permission;

import java.util.ArrayList;
import java.util.List;

public class AccountRoles {

    private final AccountDAO accountDAO;
    private final RoleDAO roleDAO;
    private final String userId;
    private final AccountAuthorization accountAuthorization;

    public AccountRoles(String userId) {
        this.accountDAO = DAOFactory.getAccountDAO();
        this.roleDAO = DAOFactory.getRoleDAO();
        this.userId = userId;
        this.accountAuthorization = new AccountAuthorization();
    }

    public void addRole(long userId, long roleId) {
        // todo : check permission to ensure it works

        AccountModel accountModel = this.accountDAO.get(userId);
        if (accountModel == null)
            throw new IllegalArgumentException("Cannot retrieve account with id : " + userId);

        RoleModel roleModel = this.roleDAO.get(roleId);
        if (roleModel == null)
            throw new IllegalArgumentException("Cannot retrieve role with id : " + roleId);

        // todo: check if role already exists for user
        accountModel.getRoles().add(roleModel);
        this.accountDAO.update(accountModel);
    }

    public List<Permission> getPermissions(long userId) {
        AccountModel accountModel = this.accountDAO.get(userId);
        if (accountModel == null)
            throw new IllegalArgumentException("Cannot retrieve account with id : " + userId);

        if (!this.userId.equalsIgnoreCase(accountModel.getUserId()) && !this.accountAuthorization.isAdmin(this.userId))
            throw new AuthorizationException("No permissions to retrieve user permissions");

        List<Permission> permissions = new ArrayList<>();
        List<UserRoleModel> results = DAOFactory.getUserRoleDAO().getByUser(accountModel.getUserId());
        for (UserRoleModel userRoleModel : results) {
            if (userRoleModel.getRole() == null)
                continue;

            List<PermissionModel> permissionModels = userRoleModel.getRole().getPermissions();
            if (permissionModels == null || permissionModels.isEmpty())
                continue;

            for (PermissionModel permissionModel : permissionModels) {
                permissions.add(permissionModel.toDataTransferObject());
            }
        }

        return permissions;
    }
}
