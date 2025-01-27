package com.canopy.numbers.served.application.views.login;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class BaseLoginView extends VerticalLayout {

	private static final long serialVersionUID = -5906989043207068746L;

	protected final LoginForm login = new LoginForm();
	protected final Image logoHeader = new Image("images/logo.png", "facetsLogo");
	protected final Button microsoftButton = new Button("SSO",
			new com.vaadin.flow.component.icon.Icon(VaadinIcon.OFFICE));
	protected final VerticalLayout loginLayout = new VerticalLayout();

	public BaseLoginView() {
		setupLoginLayout();
		addClassName(getViewClassName());
		setSizeFull();

		login.setAction("login");
		logoHeader.addClassName("loginLogo");
		setupLogoHeader();
		setupMicrosoftButton();

		addContentToLayout();
		add(loginLayout);
	}

	private void setupLoginLayout() {
		loginLayout.setAlignItems(FlexComponent.Alignment.CENTER);
		loginLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		loginLayout.setWidth("100%");
	}

	protected void setupLogoHeader() {
		logoHeader.setMaxWidth("25%");
		loginLayout.add(logoHeader, login);
	}

	protected void setupMicrosoftButton() {
		microsoftButton.addClassName("sso-button");
		microsoftButton.setMaxWidth("100%");
	}

	protected void addContentToLayout() {
		loginLayout.add(login);
		Span orLoginText = new Span("Or login with");
		orLoginText.addClassName("orLoginText");
		orLoginText.getStyle().set("margin", "10px 0");
		loginLayout.add(orLoginText, microsoftButton);
	}

	protected abstract String getViewClassName();

	protected abstract void customizeLoginLayout();

	public void setLoginListner(ComponentEventListener<LoginEvent> action) {
		login.addLoginListener(action);
	}
}
