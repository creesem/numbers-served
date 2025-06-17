package com.canopy.numbers.served.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.canopy.numbers.served.deprecated.AuthenticatedUser;
import com.canopy.shared.data.User;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;

@Endpoint
@AnonymousAllowed
public class UserEndpoint {

	@Autowired
	private AuthenticatedUser authenticatedUser;

	public Optional<User> getAuthenticatedUser() {
		return authenticatedUser.get();
	}
}
