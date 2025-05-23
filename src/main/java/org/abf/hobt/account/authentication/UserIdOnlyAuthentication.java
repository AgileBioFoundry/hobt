package org.abf.hobt.account.authentication;

import org.abf.hobt.account.Accounts;

/**
 * Authentication for userId only. Does not validate password
 *
 * @author Hector Plahar
 */
public class UserIdOnlyAuthentication implements IAuthentication {

    @Override
    public boolean authenticates(String userId, String password) {
        Accounts accounts = new Accounts();
        if (accounts.accountExists(userId))
            return true;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // ?
        }
        return false;
    }
}
