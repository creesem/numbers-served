package com.canopy.numbers.served.application.views.login;


import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "desktop-login")
@PageTitle("Login - Desktop")
@AnonymousAllowed
public class DesktopLoginView extends BaseLoginView {

	private static final long serialVersionUID = -5906989043207068746L;

	public DesktopLoginView() {
		super();
		customizeLoginLayout();
	}

	@Override
	protected String getViewClassName() {
		return "desktop-login-view";
	}

	@Override
	protected void customizeLoginLayout() {
		this.removeAll();
		loginLayout.setWidth("50%");

		VerticalLayout imgLayout = new VerticalLayout();
		imgLayout.setAlignItems(Alignment.CENTER);
		imgLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		imgLayout.setWidth("50%");

		Image heroImage = new Image("images/login_hero.png", "hero-img");
		heroImage.addClassName("hero-img");
		heroImage.setMaxWidth("155%");
		imgLayout.add(heroImage);

		HorizontalLayout rootLayout = new HorizontalLayout(imgLayout, loginLayout);
		rootLayout.setSizeFull();
		add(rootLayout);
	}
}
