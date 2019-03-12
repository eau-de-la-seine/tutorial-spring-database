package fr.ekinci.demojpa.configuration;

import fr.ekinci.demojpa.model.ProductEntity;
import fr.ekinci.demojpa.repository.ProductRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
// Activate transactions
@EnableTransactionManagement
// Analyze packages containing the Entity classes and Reposity interfaces/classes
@EnableJpaRepositories(basePackageClasses = {
	ProductEntity.class,
	ProductRepository.class
})
public class DatabaseConfiguration {
	private static final String JPA_PACKAGES_TO_SCAN = "fr.ekinci.demojpa.model";
	public static final String JPA_PERSISTANCE_UNIT_NAME = "demo-jpa-unit";

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean("jdbcTemplate")
	@Profile("jdbcTemplate")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	@Profile("jpa")
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(
			DataSource dataSource
	) {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setPackagesToScan(JPA_PACKAGES_TO_SCAN);
		emfb.setPersistenceUnitName(JPA_PERSISTANCE_UNIT_NAME);
		emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return emfb;
	}
}
