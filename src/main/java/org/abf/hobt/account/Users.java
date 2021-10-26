package org.abf.hobt.account;

import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.AccountDAO;
import org.abf.hobt.dao.model.AccountModel;
import org.abf.hobt.dto.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hector Plahar
 */
public class Users {

    private final AccountDAO dao;
    private final AccountAuthorization authorization;

    public Users() {
        this.dao = DAOFactory.getAccountDAO();
        this.authorization = new AccountAuthorization();
    }

    public List<Account> filter(String token, int limit) {
//        if (authorization.isAdmin(this.userId)) {
            List<AccountModel> results = dao.getMatchingAccounts(token, limit);
            List<Account> accountTransfers = new ArrayList<>();
            for (AccountModel match : results) {
                Account info = new Account();
                info.setId(match.getId());
                info.setUserId(match.getUserId());
                info.setEmail(match.getEmail());
                info.setFirstName(match.getFirstName());
                info.setLastName(match.getLastName());
                accountTransfers.add(info);
            }
        return accountTransfers;
//        }

//        List<Account> result = new ArrayList<>();
//        AccountModel tokenAccount = dao.getByUserId(token);
//        if (tokenAccount != null) {
//            result.add(tokenAccount.toDataTransferObject());
//            return result;
//        }
//
//        // else non admin; filter by users that account is allowed to see
//        // this is typically based on group membership
//        AccountModel account = dao.getByUserId(userId);
////        Set<Group> groups = account.getGroups();
////        if (groups.isEmpty())
////            return result;
//
////        List<Account> matches = dao.getMatchingGroupMembers(groups, token, limit);
////        result.addAll(matches.stream().map(Account::toDataTransferObject).collect(Collectors.toList()));
//        return result;
    }
}
