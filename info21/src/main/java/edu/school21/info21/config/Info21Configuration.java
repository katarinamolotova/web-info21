//package edu.school21.info21.config;
//
//import jakarta.annotation.Resource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//@ComponentScan("edu.school21.info21")
//@EnableWebMvc
//@EnableTransactionManagement
//@PropertySource("classpath:application.properties")
//public class Info21Configuration {
//
//    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
//    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
//    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
//    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
//
//    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
//    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
//    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
//
//    @Resource
//    private Environment env;
//
//    @Bean
//    public DataSource dataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
//        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
//        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
//        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
//
//        return dataSource;
//    }
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource());
//        sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
//        sessionFactoryBean.setHibernateProperties(hibProperties());
//        return sessionFactoryBean;
//    }
//
//    private Properties hibProperties() {
//        final Properties properties = new Properties();
//        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
//        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
//        return properties;
//    }
//
//    @Bean
//    public HibernateTransactionManager transactionManager() {
//        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory().getObject());
//        return transactionManager;
//    }
//}
