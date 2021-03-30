package org.abf.hobt.account;

import org.abf.hobt.common.exception.ServiceException;
import org.abf.hobt.common.exception.UtilityException;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.common.util.PasswordUtil;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.AccountDAO;
import org.abf.hobt.dao.model.AccountModel;

import java.util.Date;

/**
 * @author Hector Plahar
 */
public class Accounts {

    private final AccountDAO dao;
    public static final String DEFAULT_ADMIN_USERID = "Administrator";
    private static final long MIN_PASSWORD_RESET_PERIOD = 1000 * 60 * 60 * 3;  // 3 hour in milliseconds

    public Accounts() {
        dao = DAOFactory.getAccountDAO();
    }

    public void createDefaultAdminAccount() throws ServiceException {
        AccountModel account = dao.getByUserId(DEFAULT_ADMIN_USERID);
        if (account != null) {
            Logger.info("Resetting default admin account");
            String newPassword = PasswordUtil.generateRandomToken(64);
            try {
                account.setPassword(PasswordUtil.encryptPassword(newPassword, account.getSalt()));
            } catch (UtilityException e) {
                throw new ServiceException("Exception encrypting password", e);
            }
            account.setLastUpdateTime(account.getCreationTime());
            dao.update(account);

            Logger.info("NEW HOBT ADMIN PASSWORD");
            Logger.info("************************");
            Logger.info(newPassword);
            Logger.info("************************");
            return;
        }

        String adminUserId = DEFAULT_ADMIN_USERID.toLowerCase();
        Logger.info("Creating Administrator Account");
        account = new AccountModel();
//        account.getRoles().add(AccountRole.ADMINISTRATOR);
        account.setCreationTime(new Date(System.currentTimeMillis()));
        account.setLastUpdateTime(account.getCreationTime());
        account.setFirstName("Administrator");
        account.setLastName("");
        account.setUserId(adminUserId);
        account.setDisabled(false);
        account.setEmail("administrator");
        account.setSalt(PasswordUtil.generateSalt());
        account.setUsingTempPassword(true); // to force the user to update the admin password on login

        try {
            account.setPassword(PasswordUtil.encryptPassword(adminUserId, account.getSalt()));
        } catch (UtilityException ue) {
            throw new ServiceException("Exception encrypting password", ue);
        }

        dao.create(account);
    }
}
