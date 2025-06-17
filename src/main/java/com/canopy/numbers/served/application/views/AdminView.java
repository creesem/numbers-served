package com.canopy.numbers.served.application.views;

import com.canopy.shared.service.UserService;
import com.canopy.shared.views.UserGridView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Admin | Check-In")
@Route(value = "admin", layout = MainLayout.class)
@RolesAllowed("ADMIN") // This ensures only users with ADMIN role can access
public class AdminView extends VerticalLayout {
	private static final long serialVersionUID = -9541960244814188L;

	public AdminView(UserService userService) {
		UserGridView gridView = new UserGridView(userService);
		add(gridView);
	}

}
