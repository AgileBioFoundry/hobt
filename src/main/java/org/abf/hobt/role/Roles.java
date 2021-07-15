package org.abf.hobt.role;

import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.RoleDAO;
import org.abf.hobt.dao.model.RoleModel;
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

    public Role retrieveByLabel(String label) {
        Optional<RoleModel> optional = this.dao.getByLabel(label);
        return optional.map(RoleModel::toDataTransferObject).orElse(null);
    }

    public List<Role> list(int offset, int limit, String sort, boolean asc) {
        return new ArrayList<>();
    }
}
