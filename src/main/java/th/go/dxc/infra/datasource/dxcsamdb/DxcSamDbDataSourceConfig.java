package th.go.dxc.infra.datasource.dxcsamdb;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableJpaRepositories(
		entityManagerFactoryRef = DxcSamDbDataSourceConfig.DATASOURCE_BEAN_NAME + "EntityManagerFactory", 
		transactionManagerRef = DxcSamDbDataSourceConfig.DATASOURCE_BEAN_NAME + "TransactionManager"
)
public class DxcSamDbDataSourceConfig {
	// Configuration Required
	public final static String DATASOURCE_BEAN_NAME = "dxcSamDb";
	public final static String DATASOURCE_PROPERTIES_NAME = "dxc-sam-db";
	// Derived values
	public final static String DATASOURCE_PROPERTIES = "infra.datasource." + DATASOURCE_PROPERTIES_NAME;

	@Primary
	@Bean(name = DATASOURCE_BEAN_NAME + "DataSourceProperties")
	@ConfigurationProperties(DATASOURCE_PROPERTIES + ".jdbc")
	public DataSourceProperties dataSourceProperties() {
		log.info("Init {}DataSourceProperties", DATASOURCE_BEAN_NAME);
		return new DataSourceProperties();
	}

	@Primary
	@Bean(name = DATASOURCE_BEAN_NAME + "HikariDataSource")
	@ConfigurationProperties(DATASOURCE_PROPERTIES + ".hikari")
	public HikariDataSource hikariDataSource() {
		log.info("Init {}HikariDataSource", DATASOURCE_BEAN_NAME);
		return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Primary
	@Bean(name = DATASOURCE_BEAN_NAME + "JpaProperties")
	@ConfigurationProperties(DATASOURCE_PROPERTIES + ".jpa")
	public JpaProperties jpaProperties() {
		log.info("Init {}JpaProperties", DATASOURCE_BEAN_NAME);
		return new JpaProperties();
	}

	@Primary
	@Bean(name = DATASOURCE_BEAN_NAME + "EntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier(DATASOURCE_BEAN_NAME + "HikariDataSource") HikariDataSource dataSource,
			@Qualifier(DATASOURCE_BEAN_NAME + "JpaProperties") JpaProperties jpaProperties) {
		log.info("Init {}EntityManagerFactory", DATASOURCE_BEAN_NAME);
		if (dataSource != null) {
			log.info("{}HikariDataSource.getJdbcUrl = {}", DATASOURCE_BEAN_NAME, dataSource.getJdbcUrl());
			log.info("{}HikariDataSource.getMinimumIdle = {}", DATASOURCE_BEAN_NAME, dataSource.getMinimumIdle());
			log.info("{}HikariDataSource.getMaximumPoolSize = {}", DATASOURCE_BEAN_NAME,
					dataSource.getMaximumPoolSize());
		} else {
			log.error("Null {} Hikari Data Source", DATASOURCE_BEAN_NAME);
		}
		log.info("{}JPA Properties: {}", DATASOURCE_BEAN_NAME,
				(jpaProperties == null ? null : jpaProperties.getProperties()));
		LocalContainerEntityManagerFactoryBean emf = builder.dataSource(dataSource)
				.packages(DxcSamDbDataSourceConfig.class.getPackageName())
				.persistenceUnit(DATASOURCE_BEAN_NAME + "PersistenceUnit").properties(jpaProperties.getProperties())
				.build();
		return emf;
	}

	@Primary
	@Bean(name = DATASOURCE_BEAN_NAME + "TransactionManager")
	public PlatformTransactionManager transactionManager(
			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers,
			@Qualifier(DATASOURCE_BEAN_NAME + "EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		log.info("Init {}TransactionManager", DATASOURCE_BEAN_NAME);
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
		transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
		return transactionManager;
	}

	@Primary
	@Bean(name = DATASOURCE_BEAN_NAME + "JpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter(
			@Qualifier(DATASOURCE_BEAN_NAME + "JpaProperties") JpaProperties jpaProperties) {
		log.info("Init {}JpaVendorAdapter", DATASOURCE_BEAN_NAME);
		AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(jpaProperties.isShowSql());
		if (jpaProperties.getDatabase() != null) {
			adapter.setDatabase(jpaProperties.getDatabase());
		}
		if (jpaProperties.getDatabasePlatform() != null) {
			adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
		}
		adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
		return adapter;
	}
}
