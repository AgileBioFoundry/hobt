package org.abf.hobt.common.access;

import org.abf.hobt.account.Accounts;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.IDataModel;
import org.abf.hobt.dao.IRepository;
import org.abf.hobt.dao.model.AccountModel;
import org.abf.hobt.dao.model.RoleModel;
import org.abf.hobt.dto.Account;
import org.abf.hobt.role.Roles;

/**
 * Used in instances where access permissions are to be enforced.
 * <p>
 * Currently, the only rule is that a user must belong to the same
 * group as the owner of the object being accessed, in order to be able to
 * read it.
 *
 * @author Hector Plahar
 */
public abstract class Authorization<T extends IDataModel> {

    protected final IRepository<T> repository;

    public Authorization(IRepository<T> repository) {
        this.repository = repository;
    }

    protected AccountModel getAccount(String userId) {
        AccountModel account = DAOFactory.getAccountDAO().getByUserId(userId.toLowerCase());
        if (account == null)
            throw new IllegalArgumentException("Could not retrieve account information for user " + userId);
        return account;
    }

    protected Account getOwner(T object) {
        return null;
    }

    protected T getObjectById(long id) {
        return repository.get(id);
    }

    public boolean isAdmin(String userId) {
        AccountModel account = getAccount(userId);
        if (account == null)
            return false;

        if (Accounts.DEFAULT_ADMIN_USERID.equalsIgnoreCase(account.getUserId()))
            return true;

        for (RoleModel role : account.getRoles()) {
            if (Roles.ADMIN_ROLE_NAME.equals(role.getLabel())) {
                return true;
            }
        }

        return false;
    }

    public boolean canRead(String userId, T object) {
        return (isOwner(userId, object) || isAdmin(userId));
    }

    protected boolean isOwner(String userId, T object) {
        Account owner = getOwner(object);
        return owner == null || userId.equalsIgnoreCase(owner.getUserId());
    }

    public void expectRead(String userId, T object) throws AuthorizationException {
        if (!canRead(userId, object))
            throw new AuthorizationException(userId + " does not have the required read privileges");
    }

    /**
     * Should either be an administrator or the owner of the object to be able to write
     *
     * @param userId unique user identifier
     * @param object object write ownership is being checked against
     * @return true is user is an admin or the owner of the object
     */
    public boolean canWrite(String userId, T object) {
        if (isAdmin(userId))
            return true;

        Account owner = getOwner(object);
        return owner == null || userId.equals(owner.getUserId());
    }

    public void expectWrite(String userId, T object) throws AuthorizationException {
        if (!canWrite(userId, object))
            throw new AuthorizationException(userId);
    }

    public void expectAdmin(String userId) throws AuthorizationException {
        if (!isAdmin(userId))
            throw new AuthorizationException(userId + " attempting to access admin restricted action");
    }
}
