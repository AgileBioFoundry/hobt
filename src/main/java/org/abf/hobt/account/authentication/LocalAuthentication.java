package org.abf.hobt.account.authentication;

import org.abf.hobt.account.Accounts;
import org.abf.hobt.common.exception.UtilityException;
import org.abf.hobt.common.util.PasswordUtil;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.DataAccessException;
import org.abf.hobt.dao.model.AccountModel;

/**
 * Backend for authentication using the database. This is the default backend.
 *
 * @author Hector Plahar
 */
public class LocalAuthentication implements IAuthentication {

    public LocalAuthentication() {
    }

    @Override
    public boolean authenticates(String userId, String password) throws AuthenticationException {
        Accounts accounts = new Accounts();
        boolean accountExists = accounts.accountExists(userId);

        if (!accountExists)
            return false;

        boolean isValid = isValidPassword(userId, password);
        if (!isValid) {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                // ?
            }
        }
        return isValid;
    }

    private boolean isValidPassword(String username, String password) throws AuthenticationException {
        AccountModel account;
        try {
            account = DAOFactory.getAccountDAO().getByUserId(username);
        } catch (DataAccessException e) {
            throw new AuthenticationException("Exception retrieving account by id " + username, e);
        }

        if (account == null)
            throw new AuthenticationException("Cannot authenticate password for non-existent account " + username);

        String existingPassword = account.getPassword().trim();
        if (StringUtils.isBlank(account.getSalt()) || StringUtils.isBlank(existingPassword))
            return false;

        try {
            return existingPassword.equals(PasswordUtil.encryptPassword(password, account.getSalt()));
        } catch (UtilityException e) {
            throw new AuthenticationException("Exception encrypting user password", e);
        }
    }
}
