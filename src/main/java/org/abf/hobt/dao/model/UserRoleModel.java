package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.service.ice.IDataTransferObject;

import javax.persistence.*;
import java.util.Date;

/**
 * User to Role relationship table
 */
@Entity
@Table(name = "UserRole")
public class UserRoleModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_role_id")
    @SequenceGenerator(name = "user_role_id", sequenceName = "user_role_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private AccountModel user;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, updatable = false)
    private RoleModel role;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "assigned")
    private Date assigned;

    public long getId() {
        return id;
    }

    public AccountModel getUser() {
        return user;
    }

    public void setUser(AccountModel user) {
        this.user = user;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public Date getAssigned() {
        return assigned;
    }

    public void setAssigned(Date assigned) {
        this.assigned = assigned;
    }

    @Override
    public IDataTransferObject toDataTransferObject() {
        return null;
    }
}
