package org.abf.hobt;

import org.abf.hobt.account.Accounts;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.config.ConfigurationValue;
import org.abf.hobt.config.Settings;
import org.abf.hobt.dao.DbType;
import org.abf.hobt.dao.hibernate.HibernateConfiguration;
import org.abf.hobt.dto.Setting;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Properties;

/**
 * Application wide controller for handling tasks that affect the entire application
 *
 * @author Hector Plahar
 */
public class ApplicationInitializer {

    private static final String SERVER_PROPERTY_NAME = "hobt-server.properties";

    /**
     * Responsible for initializing the system and checking for the existence of needed
     * data (such as settings) and creating as needed
     *
     * @return a valid data directory path or null if none could be detected
     */
    public static Path configure() {
        try {
            // get or initialize data directory
            Path dataDirectory = initializeDataDirectory();
            if (dataDirectory == null || !Files.exists(dataDirectory))
                return null;

            // check if there is a ice-server.properties in the config directory inside the data directory
            // and use it to connect to the database if so
            if (Files.exists(Paths.get(dataDirectory.toString(), "config", SERVER_PROPERTY_NAME))) {
                loadServerProperties(dataDirectory);
            } else {
                // todo : do not initialize hibernate unless "hobt-server.properties is available
                // todo : this will force a redirect on the ui and let user create/select a supported database

                // unless there is already a db directory (check if using for built-in database)
                // since using the built in doesn't require a config file
                Path dbFolder = Paths.get(dataDirectory.toString(), "db");
                if (Files.exists(dbFolder) && Files.isDirectory(dbFolder)) {
                    // check if there is a database with the name hobt-h2db
                    Iterator<Path> files = Files.list(dbFolder).iterator();
                    if (files.hasNext()) {
                        Path file = files.next();
                        String fileName = file.getFileName().toString();
                        if (fileName.endsWith(".db") && fileName.startsWith(HibernateConfiguration.H2DB_NAME)) {
                            HibernateConfiguration.initialize(DbType.H2DB, null, dataDirectory);
                            return dataDirectory;
                        } else {
                            // db folder located but no database
                            return null;
                        }
                    }
                } else if (Files.isWritable(dataDirectory)) {
                    // create the db directory for the file database
                    Files.createDirectory(dbFolder);
                    // todo : create database config file template
                    // todo : create ldap-config.properties.template
                    HibernateConfiguration.initialize(DbType.H2DB, null, dataDirectory); // this creates a directory "/db"
                }
            }

            return dataDirectory;
        } catch (Exception e) {
            Logger.error(e);
            throw new RuntimeException(e);
        }
    }

    // this should not create a home directory
    private static Path initializeDataDirectory() {
        // check environ variable
        String propertyHome = System.getenv("HOBT_DATA_HOME");
        Path hobtDataHome;

        if (StringUtils.isBlank(propertyHome)) {
            // check system property (-D in startup script)
            propertyHome = System.getProperty("HOBT_DATA_HOME");

            // still nothing, check home directory
            if (StringUtils.isBlank(propertyHome)) {
                String userHome = System.getProperty("user.home");
                hobtDataHome = Paths.get(userHome, ".HOBTData");
                if (!Files.exists(hobtDataHome)) {
                    // create home directory
                    try {
                        if (Files.isWritable(Paths.get(userHome)))
                            return Files.createDirectory(hobtDataHome);
                        else
                            return null;
                    } catch (IOException e) {
                        Logger.error(e);
                        return null;
                    }
                }
            } else {
                hobtDataHome = Paths.get(propertyHome);
            }
        } else {
            hobtDataHome = Paths.get(propertyHome);
        }

        Logger.info("Using HOBT data directory: " + hobtDataHome);
        return hobtDataHome;
    }

    // initialize the database using the database configuration
    private static void loadServerProperties(Path dataDirectory) throws IOException {
        Path serverPropertiesPath = Paths.get(dataDirectory.toString(), "config", SERVER_PROPERTY_NAME);
        Properties properties = new Properties();
        properties.load(new FileInputStream(serverPropertiesPath.toFile()));

        // get type of data base
        String dbTypeString = properties.getProperty("connectionType");
        DbType type = DbType.valueOf(dbTypeString.toUpperCase());

        // get type of database etc
        HibernateConfiguration.initialize(type, properties, dataDirectory);
    }

    /**
     * initializes application
     */
    public static void start(Path dataDirectory) {

        // start the task runner
//        TaskRunner.getInstance();

        // create default admin account (or update password if already created and log to the console)
        new Accounts().createDefaultAdminAccount();

        // initialize settings
        new Settings().initialize();

        // check data directory
        checkDataDirectory(dataDirectory);
    }

    private static void checkDataDirectory(Path dataDirectory) {
        Settings settings = new Settings();
        String value = settings.getValue(ConfigurationValue.DATA_DIR);
        if (!StringUtils.isBlank(value))
            return;

        Setting setting = new Setting();
        setting.setValue(dataDirectory.toString());
        setting.setKey(ConfigurationValue.DATA_DIR.name());
        settings.update(setting);
    }
}
