package org.abf.hobt.account;

import org.abf.hobt.account.authentication.*;
import org.abf.hobt.account.authentication.ldap.LdapAuthentication;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.config.ConfigurationValue;
import org.abf.hobt.config.Settings;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.AccountDAO;
import org.abf.hobt.dao.model.AccountModel;
import org.abf.hobt.dto.Account;

import java.util.Date;

/**
 * Responsible for handling all authentication related actions
 *
 * @author Hector Plahar
 */
public class Authenticator {

    private final AccountDAO dao;

    public Authenticator() {
        dao = DAOFactory.getAccountDAO();
    }

    public Account authenticate(String userName, String password) throws AuthenticationException {
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password))
            throw new IllegalArgumentException("Invalid credentials");

        userName = userName.toLowerCase().trim();
        IAuthentication authentication = getAuthentication();

        if (!authentication.authenticates(userName, password))
            return null;

        String sid = SessionHandler.createNewSessionForUser(userName);
        AccountModel account = dao.getByUserId(userName);
        account.setLastLoginTime(new Date());
        dao.update(account);

        Account transfer = account.toDataTransferObject();
        transfer.setSessionId(sid);
        transfer.setAdmin(new AccountAuthorization().isAdmin(transfer.getUserId()));
        return transfer;
    }

    private IAuthentication getAuthentication() {
        try {
            String clazz = new Settings().getValue(ConfigurationValue.AUTHENTICATION_METHOD);
            if (StringUtils.isBlank(clazz))
                return new LocalAuthentication();

            switch (AuthType.valueOf(clazz.toUpperCase())) {
                case LDAP:
                    return new LdapAuthentication();

                case OPEN:
                    return new UserIdOnlyAuthentication();

                case DEFAULT:
                default:
                    return new LocalAuthentication();
            }
        } catch (Exception e) {
            Logger.error("Exception loading authentication class: ", e);
            Logger.error("Using default authentication");
            return new LocalAuthentication();
        }
    }
}
