package org.abf.hobt.role;

import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.PermissionDAO;
import org.abf.hobt.dao.model.PermissionModel;
import org.abf.hobt.dao.model.RoleModel;
import org.abf.hobt.dto.Permission;

public class RolePermissions {

    private final long roleId;
    private final PermissionDAO dao;

    public RolePermissions(long roleId) {
        this.roleId = roleId;
        this.dao = DAOFactory.getPermissionDAO();
    }

    public Permission add(Permission permission) {
        RoleModel roleModel = DAOFactory.getRoleDAO().get(this.roleId);
        if (roleModel == null)
            throw new IllegalArgumentException("Cannot create permission for invalid role: " + this.roleId);

        if (StringUtils.isBlank(permission.getResource()))
            throw new IllegalArgumentException("Permission requires resource");

        PermissionModel permissionModel = new PermissionModel();
        permissionModel.setWrite(permission.isWrite());
        permissionModel.setRole(roleModel);
        permissionModel.setResource(permission.getResource());
        permissionModel.setSubResource(permission.getSubResource());
        return this.dao.create(permissionModel).toDataTransferObject();
    }
}
