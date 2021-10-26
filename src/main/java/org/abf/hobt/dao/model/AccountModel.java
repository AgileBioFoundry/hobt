package org.abf.hobt.dao.model;

import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dto.Account;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Hector Plahar
 */
@Entity
@Table(name = "ACCOUNT")
public class AccountModel implements IDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "account_id")
    @SequenceGenerator(name = "account_id", sequenceName = "account_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "email")
    private String email;

    @Column(name = "hashed_password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "description")
    private String description;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date creationTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date lastUpdateTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "last_login")
    private Date lastLoginTime;

    @Column(name = "is_temp_password")
    private Boolean usingTempPassword;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_group", joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "group_id"))
    private final Set<GroupModel> groups = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_role", joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private final Set<RoleModel> roles = new LinkedHashSet<>();

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "password_updated")
    private Date passwordUpdatedTime;

    public AccountModel() {
    }

    public long getId() {
        return this.id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Set<GroupModel> getGroups() {
        return groups;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Boolean getUsingTempPassword() {
        return usingTempPassword;
    }

    public void setUsingTempPassword(Boolean usingTempPassword) {
        this.usingTempPassword = usingTempPassword;
    }

    public boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getPasswordUpdatedTime() {
        return passwordUpdatedTime;
    }

    public void setPasswordUpdatedTime(Date passwordUpdatedTime) {
        this.passwordUpdatedTime = passwordUpdatedTime;
    }

    @Override
    public String toString() {
        return userId;
    }

    @Override
    public Account toDataTransferObject() {
        Account transfer = new Account();
        transfer.setId(id);
        transfer.setFirstName(firstName);
        transfer.setLastName(lastName);
        transfer.setEmail(email);
        transfer.setUserId(userId);
        transfer.setDescription(description);
        if (this.disabled != null)
            transfer.setDisabled(this.disabled);
        return transfer;
    }
}
