package org.abf.hobt.role;

import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.RoleDAO;
import org.abf.hobt.dao.model.AccountModel;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.dao.model.PermissionModel;
import org.abf.hobt.dao.model.RoleModel;
import org.abf.hobt.dto.Account;
import org.abf.hobt.dto.Permission;
import org.abf.hobt.dto.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Roles {

    private final RoleDAO dao;

    public Roles() {
        this.dao = DAOFactory.getRoleDAO();
    }

    public Role create(Role role) {
        if (StringUtils.isBlank(role.getDescription()) || StringUtils.isBlank(role.getLabel()))
            throw new IllegalArgumentException("Label and description are required to create new roles");

        Optional<RoleModel> optional = dao.getByLabel(role.getLabel());
        if (optional.isPresent())
            throw new IllegalArgumentException("Role label is not unique");

        RoleModel model = new RoleModel();
        model.setLabel(role.getLabel());
        model.setDescription(role.getDescription());
        return this.dao.create(model).toDataTransferObject();
    }

    public Role retrieve(long roleId) {
        RoleModel model = this.dao.get(roleId);
        if (model != null)
            return model.toDataTransferObject();
        return null;
    }

    public List<Account> getRoleMembers(long roleId) {
        List<Account> result = new ArrayList<>();
        RoleModel model = this.dao.get(roleId);
        if (model != null && model.getMembers() != null) {
            for (AccountModel member : model.getMembers()) {
                result.add(member.toDataTransferObject());
            }
        }

        return result;
    }

    public Role retrieveByLabel(String label) {
        Optional<RoleModel> optional = this.dao.getByLabel(label);
        return optional.map(RoleModel::toDataTransferObject).orElse(null);
    }

    public List<Role> list(int offset, int limit, String sort, boolean asc) {
        List<RoleModel> roles = this.dao.retrieve(offset, limit);
        List<Role> results = new ArrayList<>(roles.size());
        for (RoleModel roleModel : roles) {
            Role role = roleModel.toDataTransferObject();

            // get permissions
            List<PermissionModel> list = roleModel.getPermissions();
            for (PermissionModel permissionModel : list) {
                Permission permission = permissionModel.toDataTransferObject();
                switch (permission.getResource().toLowerCase()) {
                    case "hosts":
                        String display = hostSubResourceDisplay(permission.getSubResource());
                        permission.setSubResourceDisplay(display);
                        break;
                }
                role.getPermissions().add(permission);
            }

            // get members

            results.add(role);
        }
        return results;
    }

    private String hostSubResourceDisplay(String subResource) {
        if (StringUtils.isBlank(subResource))
            return "";

        try {
            long subResourceId = Long.decode(subResource);
            OrganismModel model = DAOFactory.getOrganismDAO().get(subResourceId);
            if (model != null)
                return model.getName();
        } catch (NumberFormatException numberFormatException) {
            Logger.error("Cannot convert host sub-resource to long. Value is " + subResource);
        }
        return "";
    }
}
