package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.Role;

import javax.persistence.*;

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

    // todo @OneToMany List<Permission> permissions (one role can have many permissions)

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

    @Override
    public Role toDataTransferObject() {
        Role role = new Role();
        role.setId(this.id);
        role.setLabel(this.label);
        role.setDescription(this.description);
        return role;
    }
}
