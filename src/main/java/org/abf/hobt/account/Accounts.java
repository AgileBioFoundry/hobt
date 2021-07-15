package org.abf.hobt.account;

import org.abf.hobt.account.authentication.AuthenticationException;
import org.abf.hobt.common.ResultData;
import org.abf.hobt.common.exception.ServiceException;
import org.abf.hobt.common.exception.UtilityException;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.common.util.PasswordUtil;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.AccountDAO;
import org.abf.hobt.dao.model.AccountModel;
import org.abf.hobt.dto.Account;
import org.abf.hobt.notification.Email;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Hector Plahar
 */
public class Accounts {

    private final AccountDAO dao;
    public static final String DEFAULT_ADMIN_USERID = "Administrator";
    private static final long MIN_PASSWORD_RESET_PERIOD = 1000 * 60 * 60 * 3;  // 3 hour in milliseconds
    private final AccountAuthorization authorization;

    public Accounts() {
        dao = DAOFactory.getAccountDAO();
        this.authorization = new AccountAuthorization();
    }

    public void createDefaultAdminAccount() throws ServiceException {
        AccountModel account = dao.getByUserId(DEFAULT_ADMIN_USERID);
        if (account != null) {
            Logger.info("Resetting default admin account");
            String newPassword = PasswordUtil.generateRandomToken(32);
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

    public Account create(Account account, boolean createPassword) {

        if (account == null || account.getUserId() == null || account.getUserId().trim()
            .isEmpty())
            throw new ServiceException("User id is required to create an account");

        if (StringUtils.isBlank(account.getFirstName()) || StringUtils.isBlank(account.getLastName()))
            throw new ServiceException("First and Last names are required to create an account");

        if (StringUtils.isBlank(account.getEmail()))
            throw new ServiceException("A valid email address is required to create an account");

        AccountModel accountModel = dao.getByUserId(account.getUserId());
        if (accountModel != null)
            throw new IllegalArgumentException("User with id " + account.getUserId() + " already exists");

        accountModel = new AccountModel();
        accountModel.setCreationTime(new Date());
        accountModel.setLastUpdateTime(accountModel.getCreationTime());
        accountModel.setFirstName(account.getFirstName());
        accountModel.setLastName(account.getLastName());
        accountModel.setUserId(account.getUserId());
        accountModel.setEmail(account.getEmail());

        String password = null;

        if (createPassword) {
            accountModel.setSalt(PasswordUtil.generateSalt());
            accountModel.setDescription(account.getDescription());
            password = account.getPassword();

            if (StringUtils.isBlank(password)) {
                // generate a temporary password if user doesn't specify password
                password = PasswordUtil.generateTemporaryPassword();
            }

            try {
                account.setPassword(PasswordUtil.encryptPassword(password, accountModel.getSalt()));
            } catch (UtilityException ue) {
                throw new ServiceException("Exception encrypting password", ue);
            }
        }

        Account newAccount = dao.create(accountModel).toDataTransferObject();
        if (!createPassword)
            return newAccount;

        // send email to new user
        sendAccountEmail(newAccount, password);
        return newAccount;
    }

    private void sendAccountEmail(Account newAccount, String password) {
        String subject = "Account created successfully";

        Email email = new Email();
        String stringBuilder = "Dear " +
            newAccount.getFirstName() +
            ", " +
            "\n\nThank you for creating a Host Onboarding Tool" +
            " account. \nPlease use the following credentials to access " +
            "the site " +
            "at https://hobt.agilebiofoundry.org" +
            ". " +
            "\n\nUser Id: " +
            newAccount.getUserId() +
            "\nPassword: " +
            password +
            "\n\nPlease remember to change your password once you login.\n\n";
        email.send(newAccount.getEmail(), null, subject, stringBuilder);
    }

    public boolean update(String userId, long id, Account transfer) {
        AccountModel account = dao.get(id);
        if (!account.getUserId().equalsIgnoreCase(userId))
            authorization.expectAdmin(userId);

        account.setFirstName(transfer.getFirstName());
        account.setLastName(transfer.getLastName());
        account.setEmail(transfer.getEmail());
        account.setLastUpdateTime(new Date());
        return dao.update(account) != null;
    }

    /**
     * Updates the password for the specified user
     *
     * @param userId      unique identifier for user whose password is being changed
     * @param password    existing user password
     * @param newPassword new user password
     * @throws ServiceException        on exception updating the password
     * @throws AuthenticationException on exception authenticating with the existing password
     */
    public Account updatePassword(String userId, String password, String newPassword) throws ServiceException,
        AuthenticationException {
        Authenticator authenticator = new Authenticator();
        Account accountTransfer = authenticator.authenticate(userId, password);
        if (accountTransfer == null)
            throw new AuthenticationException("Could not validate existing password for user " + userId);

        try {
            AccountModel account = dao.getByUserId(userId);
            if (StringUtils.isBlank(account.getSalt())) {
                account.setSalt(PasswordUtil.generateSalt());
            }
            account.setPassword(PasswordUtil.encryptPassword(newPassword, account.getSalt()));
            account.setLastUpdateTime(new Date());
            account.setPasswordUpdatedTime(new Date());
            account.setUsingTempPassword(false);
            dao.update(account);
//            accountTransfer.setUsingTemporaryPassword(false);
            accountTransfer.setLastUpdateTime(account.getLastUpdateTime().getTime());
            return accountTransfer;
        } catch (UtilityException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Resets the password for the user specified in the parameter. The exception is
     * members of the JBEI group who are not permitted to change their passwords.
     * <p>
     * <p>
     * If a new password is specified, the system authenticates (using existing password information)
     * and does not send an email, otherwise, it generates a new password and sends it via email.
     *
     * @param userId   unique identifier for the user
     * @param transfer whether to send notification to the user after the password reset
     * @throws IllegalArgumentException if the user id is not associated with any registered user
     * @throws ServiceException         on exception resetting the user password
     */
    public Account resetPassword(String userId, Account transfer) throws ServiceException {
        AccountModel account = dao.getByUserId(userId);
        if (account == null)
            throw new IllegalArgumentException("User id \"" + userId + "\" is invalid");

        if (account.getDisabled() || userId.equalsIgnoreCase(DEFAULT_ADMIN_USERID)) {
            Logger.error("Cannot reset password for account " + account.getUserId());
            String errorMsg = "Cannot reset the password for this account";
            throw new ServiceException(errorMsg);
        }


        // check how long since last update request; it must be greater than the min password reset period
        if (account.getPasswordUpdatedTime() != null && !account.getUsingTempPassword()) {
            // now - updatedTime >= MIN_PASSWORD_RESET_PERIOD;
            long updatedTime = account.getPasswordUpdatedTime().getTime();
            if (new Date(updatedTime + MIN_PASSWORD_RESET_PERIOD).after(new Date())) {
                String errMsg = "Too many password reset attempts in the past few hours";
                Logger.error(errMsg);
                throw new ServiceException(errMsg);
            }
        }

        // todo : session id has to be valid in this instance
        if (transfer != null && !StringUtils.isBlank(transfer.getPassword())) {
            try {
                return updatePassword(userId, transfer.getPassword(), transfer.getPassword());
            } catch (AuthenticationException e) {
                throw new ServiceException(e);
            }
        }

        // generate a new random password and send
        String tempPassword = PasswordUtil.generateTemporaryPassword();

        try {
            account.setPassword(PasswordUtil.encryptPassword(tempPassword, account.getSalt()));
        } catch (UtilityException ue) {
            throw new ServiceException("Exception encrypting password", ue);
        }

        account.setUsingTempPassword(true);
        account.setLastUpdateTime(new Date());
        account.setPasswordUpdatedTime(new Date());
        account = dao.update(account);

        String subject = "Your Host Onboarding Tool password was reset";
        String name = account.getFirstName();
        if (StringUtils.isBlank(name)) {
            name = account.getLastName();
            if (StringUtils.isBlank(name))
                name = account.getUserId();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy 'at' HH:mm aaa, z");

        StringBuilder builder = new StringBuilder();
        builder.append("Dear ").append(name).append(",\n\n")
            .append("The password for your ").append("HObT")
            .append(" account (").append(userId).append(") was reset on ")
            .append(dateFormat.format(new Date())).append(". Your new temporary password is\n\n")
            .append(tempPassword).append("\n\n")
            .append("Please use it to login and change your password");

        builder.append(" at ").append("https://hobt.agilebiofoundry.org");
        builder.append(".\n\nThank you.");

        Email email = new Email();
        email.send(account.getEmail(), null, subject, builder.toString());
        return account.toDataTransferObject();
    }

    public AccountModel retrieveAccountByUserId(String userId) {
        if (StringUtils.isBlank(userId))
            return null;
        return dao.getByUserId(userId.toLowerCase());
    }

    public boolean isAdministrator(String userId) {
        AccountModel account = dao.getByUserId(userId);
        return false;
//        return account != null && account.getRoles() != null && account.getRoles().contains(AccountRole.ADMINISTRATOR);
    }

    public Account get(String userId, String uid) {
        AccountModel account;

        try {
            account = dao.get(Long.decode(uid));
        } catch (NumberFormatException e) {
            // actual user id
            account = dao.getByUserId(uid);
        }

        if (account == null)
            return null;

        if (!account.getUserId().equalsIgnoreCase(userId))
            authorization.expectAdmin(userId);

        Account transfer = account.toDataTransferObject();
//        transfer.setAllowedToChangePassword(canChangePassword(userId));
        return transfer;
    }

    public Account getAccount(String userId) {
        AccountModel account = dao.getByUserId(userId.toLowerCase());
        if (account == null)
            return null;
        Account transfer = account.toDataTransferObject();
//        transfer.setAllowedToChangePassword(canChangePassword(userId));
        return transfer;
    }

    public boolean accountExists(String userId) {
        AccountModel account = retrieveAccountByUserId(userId);
        return account != null;
    }

    public ResultData<Account> retrieveAccounts(String userId, int start, int limit, boolean asc, String prop,
                                                String filter) {
        new AccountAuthorization().expectAdmin(userId);
        List<AccountModel> accounts = dao.retrieveAccounts(start, limit, asc, prop, filter);
        if (accounts == null)
            return new ResultData<>();

        ResultData<Account> data = new ResultData<>();

        for (AccountModel account : accounts) {
            Account transfer = account.toDataTransferObject();
            data.getRequested().add(transfer);
        }
        data.setAvailable(dao.getAccountTotal(filter));
        return data;
    }
}
