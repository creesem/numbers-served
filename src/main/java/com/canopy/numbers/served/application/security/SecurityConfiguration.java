package com.canopy.numbers.served.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.canopy.application.service.SecurityServiceImpl;
import com.canopy.numbers.served.application.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration extends VaadinWebSecurity {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private SecurityServiceImpl securityServiceImpl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Allow access to Vaadin static resources

		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login", "/VAADIN/**", "/HEARTBEAT/**", "/UIDL/**", "/resources/**", "/public/**",
						"/error/**")
				.permitAll().requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png"))
				.permitAll());
		super.configure(http);

		// Configure the login view

		setLoginView(http, LoginView.class);

		http.headers().frameOptions().sameOrigin();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(authenticationProvider());
		return authenticationManagerBuilder.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(securityServiceImpl);
		provider.setPasswordEncoder(encoder);
		return provider;
	}
}
