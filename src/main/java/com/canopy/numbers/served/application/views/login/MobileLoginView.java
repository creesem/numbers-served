package com.canopy.numbers.served.application.views.login;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "mobile-login")
@PageTitle("Login - Mobile")
@AnonymousAllowed
public class MobileLoginView extends BaseLoginView {

	private static final long serialVersionUID = -5906989043207068746L;

	@Override
	protected String getViewClassName() {
		return "mobile-login-view";
	}

	@Override
	protected void customizeLoginLayout() {
		loginLayout.setWidth("100%");
	}
}
