package org.abf.hobt.dao.model;

import jakarta.persistence.*;
import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.Permission;

@Entity
@Table(name = "Permission")
public class PermissionModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "permission_id")
    @SequenceGenerator(name = "permission_id", sequenceName = "permission_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel role;

    @Column(name = "isWrite")
    private boolean isWrite;

    @Column(name = "resource")
    private String resource;

    @Column(name = "subResource")
    private String subResource;

    public long getId() {
        return id;
    }

    public boolean isWrite() {
        return isWrite;
    }

    public void setWrite(boolean write) {
        isWrite = write;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getSubResource() {
        return subResource;
    }

    public void setSubResource(String subResource) {
        this.subResource = subResource;
    }

    @Override
    public Permission toDataTransferObject() {
        Permission permission = new Permission();
        permission.setId(this.id);
        permission.setResource(this.resource);
        permission.setWrite(isWrite);
        permission.setRead(!isWrite);
        permission.setSubResource(this.subResource);
        return permission;
    }
}
