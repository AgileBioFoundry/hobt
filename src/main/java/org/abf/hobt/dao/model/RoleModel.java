package org.abf.hobt.dao.model;

import jakarta.persistence.*;
import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Role")
public class RoleModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role_id")
    @SequenceGenerator(name = "role_id", sequenceName = "role_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "label", length = 150, nullable = false, unique = true)
    private String label;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<AccountModel> members = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "role", orphanRemoval = true)
    private final List<PermissionModel> permissions = new ArrayList<>();

    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PermissionModel> getPermissions() {
        return permissions;
    }

    public Set<AccountModel> getMembers() {
        return members;
    }

    @Override
    public Role toDataTransferObject() {
        Role role = new Role();
        role.setId(this.id);
        role.setLabel(this.label);
        role.setDescription(this.description);
        return role;
    }
}
