package com.canopy.numbers.served.application.views.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = LoginView.PATH)
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends HorizontalLayout implements BeforeEnterObserver {
	private static final long serialVersionUID = -5906989043207068746L;
	public static final String PATH = "login"; // Changed from "/login" to "login"

	private final LoginForm login = new LoginForm();

	public LoginView() {
		UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> {
			System.out.println(details.isTouchDevice());
			if (details.isTouchDevice() && !details.isIPad()) {
				configureMobileView();
			} else {
				configureDesktopTabletView();
			}
		});
	}

	public void configureDesktopTabletView() {
		addClassName("desktop-login-view");
		setSizeFull();

		add(new DesktopLoginView());
	}

	public void configureMobileView() {
		addClassName("mobile-login-view");
		setSizeFull();

		add(new MobileLoginView());
	}

	private void onLogin(LoginEvent event) {
		// Clear the Vaadin session state
		VaadinSession.getCurrent().getSession().invalidate();
		VaadinSession.getCurrent().close();

		// Redirect to the home page or desired view
		UI.getCurrent().navigate("/"); // Replace "home" with your target route
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
			login.setError(true);
		}
	}
}
