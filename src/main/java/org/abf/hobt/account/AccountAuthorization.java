package org.abf.hobt.account;

import org.abf.hobt.common.access.Authorization;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.model.AccountModel;

/**
 * @author Hector Plahar
 */
public class AccountAuthorization extends Authorization<AccountModel> {

    public AccountAuthorization() {
        super(DAOFactory.getAccountDAO());
    }

//    public boolean isUserHasSpecifiedRole(String userId, AccountRole role) {
//        AccountDAO dao = (AccountDAO) this.repository;
//        AccountModel account = dao.getByUserId(userId);
//        if (account == null)
//            throw new NullPointerException("No account for specified id " + userId);
//
//        if (role == null)
//            throw new NullPointerException("Cannot check against null role");
//
//        return account.getRoles().contains(role);
//    }

}
