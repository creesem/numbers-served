package com.canopy.numbers.served.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "numbers-served")
@EntityScan(basePackages = { "com.canopy.application.data", "com.canopy.numbers.served.application.data" })
//@ComponentScan(basePackages = { "com.canopy.application", "com.canopy.numbers.served" })
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {
	private static final long serialVersionUID = -319468755450296587L;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	/*
	 * @Bean SqlDataSourceScriptDatabaseInitializer
	 * dataSourceScriptDatabaseInitializer(DataSource dataSource,
	 * SqlInitializationProperties properties, SamplePersonRepository repository) {
	 * // This bean ensures the database is only initialized when empty return new
	 * SqlDataSourceScriptDatabaseInitializer(dataSource, properties) {
	 * 
	 * @Override public boolean initializeDatabase() { if (repository.count() == 0L)
	 * { return super.initializeDatabase(); } return false; } }; }
	 */

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
