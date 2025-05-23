package org.abf.hobt.dao.hibernate;

import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.dao.DbType;
import org.abf.hobt.dao.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.nio.file.Path;
import java.util.Properties;

/**
 * Singleton helper class to initialize SessionFactory
 * and obtain sessions and create transactions
 *
 * @author Hector Plahar
 */
public class HibernateConfiguration {

    // thread safe global object that is instantiated once
    private static SessionFactory sessionFactory;
    public static final String H2DB_NAME = "hobt-h2db";

    private HibernateConfiguration() {
    }

    public static boolean isInitialized() {
        return sessionFactory != null && sessionFactory.isOpen();
    }

    /**
     * Configure the database using the (optional) connection properties
     *
     * @param dbType        database type
     * @param properties    optional connection properties. Required only if
     *                      DbType is <code>POSTGRESQL</code>
     * @param dataDirectory path to the data directory optional for in memory database
     */
    public static synchronized void initialize(DbType dbType, Properties properties, Path dataDirectory) {
        if (sessionFactory != null) {
            Logger.info("Database already configured. Close/reset to re-configure");
            return;
        }

        Logger.info("Initializing session factory for type " + dbType.name());
        Configuration configuration = new Configuration();

        switch (dbType) {
            case H2DB:
            default:
                configureH2Db(configuration, dataDirectory.toString());
                break;

            case MEMORY:
                configureInMemoryDb(configuration);
                break;

            case POSTGRESQL:
                configurePostgresDb(configuration, properties, dataDirectory.toString());
                break;
        }

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        configuration.configure();
        addAnnotatedClasses(configuration);
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    private static void configurePostgresDb(Configuration configuration, Properties properties, String dbPath) {
        // load (additional) base configuration
        configuration.configure();
        String url = properties.getProperty("connectionUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String dbName = properties.getProperty("dbName");

        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://" + url + "/" + dbName);
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.username", username);
        configuration.setProperty("hibernate.connection.password", password);
        configuration.setProperty("hibernate.search.default.indexBase", dbPath + "/data/lucene-data");
    }

    private static void configureH2Db(Configuration configuration, String dbPath) {
        configuration.configure();
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:" + dbPath + "/db/" + H2DB_NAME);
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.search.default.indexBase", dbPath + "/data/lucene-data");
    }

    private static void configureInMemoryDb(Configuration configuration) {
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.current_session_context_class",
            "org.hibernate.context.internal.ThreadLocalSessionContext");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.search.default.directory_provider",
            "org.hibernate.search.store.impl.RAMDirectoryProvider");
    }

    private static void addAnnotatedClasses(Configuration configuration) {
        configuration.addAnnotatedClass(AccountModel.class);
        configuration.addAnnotatedClass(GroupModel.class);
        configuration.addAnnotatedClass(OrganismModel.class);
        configuration.addAnnotatedClass(TierModel.class);
        configuration.addAnnotatedClass(ConfigurationModel.class);
        configuration.addAnnotatedClass(CriteriaModel.class);
        configuration.addAnnotatedClass(OrganismCriteriaStatusModel.class);
        configuration.addAnnotatedClass(RoleModel.class);
        configuration.addAnnotatedClass(UserRoleModel.class);
        configuration.addAnnotatedClass(TierRuleModel.class);
        configuration.addAnnotatedClass(PermissionModel.class);
        configuration.addAnnotatedClass(PublicationModel.class);
        configuration.addAnnotatedClass(TierStatusModel.class);
        configuration.addAnnotatedClass(OrganismAttributeModel.class);
        configuration.addAnnotatedClass(OrganismAttributeOptionModel.class);
        configuration.addAnnotatedClass(OrganismAttributeValueModel.class);
        configuration.addAnnotatedClass(OrganismElementCacheModel.class);
    }

    /**
     * @return session bound to context.
     */
    protected static Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    public static void beginTransaction() {
        if (sessionFactory == null)
            return;

        if (!sessionFactory.getCurrentSession().getTransaction().isActive())
            sessionFactory.getCurrentSession().beginTransaction();
    }

    public static void commitTransaction() {
        if (sessionFactory == null)
            return;

        if (sessionFactory.getCurrentSession().getTransaction().isActive())
            sessionFactory.getCurrentSession().getTransaction().commit();
    }

    public static void rollbackTransaction() {
        if (sessionFactory == null)
            return;

        if (sessionFactory.getCurrentSession().getTransaction().isActive())
            sessionFactory.getCurrentSession().getTransaction().rollback();
    }

    /**
     * Initialize a in-memory mock database for testing.
     */
    public static void initializeMock() {
        initialize(DbType.MEMORY, null, null);
    }

    /**
     * Closes the sessionFactory. This is meant to be used on application exit
     */
    public static void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            Logger.info("Closing session factory");
            sessionFactory.getCurrentSession().close();
            sessionFactory.close();
        }
    }
}
