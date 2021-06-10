package org.abf.hobt.config;

import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.DataAccessException;
import org.abf.hobt.dao.hibernate.ConfigurationDAO;
import org.abf.hobt.dao.hibernate.HibernateConfiguration;
import org.abf.hobt.dao.model.ConfigurationModel;
import org.abf.hobt.dto.Setting;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hector Plahar
 */
public class Settings {

    private static final String UI_CONFIG_DIR = "asset";
    private final ConfigurationDAO dao;

    public Settings() {
        this.dao = DAOFactory.getConfigurationDAO();
    }

    public boolean hasDataDirectory() {
        // session valid?
        return HibernateConfiguration.isInitialized();
    }

    /**
     * Checks to ensure that all define {@link ConfigurationValue}s are entered in the settings database
     * Creates those that have not been entered with the default values
     */
    public void initialize() {
        for (ConfigurationValue value : ConfigurationValue.values()) {
            if (dao.get(value.name()) != null)
                continue;

            try {
                ConfigurationModel configuration = new ConfigurationModel();
                configuration.setKey(value.name());
                configuration.setValue("");
                dao.create(configuration);
            } catch (DataAccessException de) {
                Logger.error(de);
                return;
            }
        }
    }

    /**
     * Creates a new system setting. This is mainly intended to be used to created a fixed
     * set of configuration params on system initialization
     *
     * @param setting wrapper around the key/value pair of the system to create
     * @return created setting or null if setting already exists
     */
    public Setting create(Setting setting) {
        if (setting == null)
            throw new IllegalArgumentException("Cannot create null setting");

        ConfigurationModel configuration = dao.get(setting.getKey());
        if (configuration != null && configuration.getValue().equalsIgnoreCase(setting.getValue()))
            return configuration.toDataTransferObject();

        configuration = new ConfigurationModel();
        configuration.setKey(setting.getKey());
        configuration.setValue(setting.getValue());
        return dao.create(configuration).toDataTransferObject();
    }

    /**
     * Updates an existing setting in the database. For settings that specify file and directory
     * locations
     *
     * @param setting key/value pair of the setting to update. The key of a setting is unique
     * @return updated setting with the new value (if successful) or null if setting cannot be found
     */
    public Setting update(Setting setting) {
        ConfigurationModel configuration = dao.get(setting.getKey());
        if (configuration == null)
            return create(setting);

        configuration.setValue(setting.getValue());
        return dao.update(configuration).toDataTransferObject();
    }

    /**
     * retrieves the value associated with the setting key in the parameter
     *
     * @param key unique setting display identifier which is one of {@link ConfigurationValue}
     * @return value or empty string if one is not found. This method never returns null
     */
    public String getValue(ConfigurationValue key) {
        ConfigurationModel configuration = dao.get(key.name());
        if (configuration == null)
            return "";
        return configuration.getValue();
    }

    /**
     * @return all the configuration key/value pairs in the database
     */
    public List<Setting> getAll() {
        List<ConfigurationModel> results = dao.getAll();
        if (results == null)
            return null;

        ArrayList<Setting> settings = new ArrayList<>();
        for (ConfigurationModel configuration : results) {
            try {
                ConfigurationValue.valueOf(configuration.getKey());
            } catch (IllegalArgumentException e) {
                Logger.info("Skipping configuration key " + configuration.getKey() + " with value "
                    + configuration.getValue());
                continue;
            }
            settings.add(configuration.toDataTransferObject());
        }

        return settings;
    }

    public long getLong(ConfigurationValue key) {
        ConfigurationModel configuration = dao.get(key.name());
        if (configuration == null || StringUtils.isBlank(configuration.getValue()))
            return -1;

        try {
            return Long.decode(configuration.getValue().trim());
        } catch (Exception e) {
            Logger.error("Exception converting config value " + configuration.getValue() + " to a long value");
            return -1;
        }
    }

    public boolean getBoolean(ConfigurationValue key) {
        ConfigurationModel configuration = dao.get(key.name());
        if (configuration == null || StringUtils.isBlank(configuration.getValue()))
            return false;

        try {
            String value = configuration.getValue().trim();
            return value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
        } catch (Exception e) {
            Logger.error("Exception converting config value '" + configuration.getValue() + "' to a boolean value");
            return false;
        }
    }

    public Setting get(ConfigurationValue key) {
        ConfigurationModel configuration = dao.get(key.name());
        if (configuration != null)
            return configuration.toDataTransferObject();
        return new Setting();
    }

    public SiteSettings getSiteSettings() {
        SiteSettings settings = new SiteSettings();
        String dataDirectory = dao.get(ConfigurationValue.DATA_DIR.name()).getValue();
        final String LOGO_NAME = "logo.png";
        final String LOGIN_MESSAGE_FILENAME = "institution.html";
        final String FOOTER_FILENAME = "footer.html";

        settings.setHasLogo(Files.exists(Paths.get(dataDirectory, UI_CONFIG_DIR, LOGO_NAME)));
        settings.setHasLoginMessage(Files.exists(Paths.get(dataDirectory, UI_CONFIG_DIR, LOGIN_MESSAGE_FILENAME)));
        settings.setHasFooter(Files.exists(Paths.get(dataDirectory, UI_CONFIG_DIR, FOOTER_FILENAME)));
        settings.setAssetName(UI_CONFIG_DIR);

        return settings;
    }

    public SiteSettings getInitialValues() {

        SiteSettings siteSettings = new SiteSettings();

        // get the data directory home
        String propertyHome = System.getenv("DIVA_DATA_HOME");
        Path iceHome;

        if (StringUtils.isBlank(propertyHome)) {
            // check system property (-D in startup script)
            propertyHome = System.getProperty("DIVA_DATA_HOME");

            // still nothing, check home directory
            if (StringUtils.isBlank(propertyHome)) {
                String userHome = System.getProperty("user.home");
                iceHome = Paths.get(userHome, ".DIVAData");
            } else {
                iceHome = Paths.get(propertyHome);
            }
        } else {
            iceHome = Paths.get(propertyHome);
        }
        siteSettings.setDataDirectory(iceHome.toString());

        // get the

        return siteSettings;
    }
}


