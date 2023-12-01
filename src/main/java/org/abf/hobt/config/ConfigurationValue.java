package org.abf.hobt.config;

/**
 * Pre-defined system configuration values
 *
 * @author Hector Plahar
 */
public enum ConfigurationValue {

    DATA_DIR,
    SCRIPTS_DIR,          // scripts directory location for j5
    BLAST_CMD_PATH,
    ADMIN_EMAIL,
    FROM_EMAIL,
    SMTP_HOST,
    PROJECT_NAME,
    URI_PREFIX,
    SEND_EMAIL_ON_ERRORS,
    SEND_EMAIL_NOTIFICATION,
    REGISTRATION_ALLOWED,
    ENABLE_ACCOUNT_VETTING,  // requires that new registered users have to be vetted before they can use the account
    RESTRICT_PROJECT_CREATION, // permit only users with principal investigator privileges to create projects
    AUTHENTICATION_METHOD,

    PROTOCOL_IO_API_TOKEN
}
