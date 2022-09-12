package org.abf.hobt.config;

/**
 * Pre-defined system configuration values
 *
 * @author Hector Plahar
 */
public enum ConfigurationValue {

    DIVA_TEAM_GROUP_ID,
    DATA_DIR,
    SCRIPTS_DIR,          // scripts directory location for j5
    BLAST_CMD_PATH,
    REGISTRY,
    REGISTRY_API_TOKEN,
    JBEI_GROUP_ID,       // id of group to add users to if they authenticate with ldap (imply they are jbei)
    ADMIN_EMAIL,
    SMTP_HOST,
    PROJECT_NAME,
    URI_PREFIX,
    SEND_EMAIL_ON_ERRORS,
    SEND_EMAIL_NOTIFICATION,
    DESIGN_COMPLETE_THRESHOLD,
    REGISTRATION_ALLOWED,
    J5_RPC_URL,          // j5 installation is not local therefore use the remote installation,
    BY_PASS_DIVA_REVIEW, // this should get changed to BYPASS_DIVA_TEAM_REVIEW at some point, to better agree with DesignState
    ENABLE_IP_QUESTIONS, // enable IP questions when submitting for DIVA review
    ENABLE_BIO_SECURITY_QUESTION, // bio security questions for DIVA team on design approval

    BLISS,               // BLiSS UI instance url and API Token
    BLISS_API_TOKEN,

    BOOST_API_TOKEN,
    BOOST_URL,

    ENABLE_ACCOUNT_VETTING,  // requires that new registered users have to be vetted before they can use the account
    ENABLE_DEFAULT_DIVA_TEAM, // allows the diva team field of projects to be empty (if set to true)
    RESTRICT_PROJECT_CREATION, // permit only users with principal investigator privileges to create projects
    AUTHENTICATION_METHOD,

    PROTOCOL_IO_API_TOKEN
}
