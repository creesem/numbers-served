package com.canopy.numbers.served.application.views;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import org.vaadin.lineawesome.LineAwesomeIcon;

import com.canopy.application.data.User;
import com.canopy.numbers.served.application.security.AuthenticatedUser;
import com.canopy.numbers.served.application.views.reports.FormsView;
import com.canopy.numbers.served.application.views.reports.NumbersServedStudentUploadView;
import com.canopy.numbers.served.application.views.reports.ReportsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {
	private static final long serialVersionUID = 995836277857772709L;

	private H1 viewTitle;

	private AuthenticatedUser authenticatedUser;
	private AccessAnnotationChecker accessChecker;

	public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
		this.authenticatedUser = authenticatedUser;
		this.accessChecker = accessChecker;

		setPrimarySection(Section.DRAWER);
		addDrawerContent();
		addHeaderContent();
	}

	private void addHeaderContent() {
		DrawerToggle toggle = new DrawerToggle();
		toggle.setAriaLabel("Menu toggle");

		viewTitle = new H1();
		viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

		addToNavbar(true, toggle, viewTitle);
	}

	private void addDrawerContent() {
		Span appName = new Span("FACETS");
		appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
		Header header = new Header(appName);

		Scroller scroller = new Scroller(createNavigation());

		addToDrawer(header, scroller, createFooter());
	}

	private SideNav createNavigation() {
		SideNav nav = new SideNav();

		List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
		menuEntries.forEach(entry -> {
			if (entry.icon() != null) {
				nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
			} else {
				nav.addItem(new SideNavItem(entry.title(), entry.path()));
			}

		});

		nav.addItem(new SideNavItem("Forms", FormsView.class, LineAwesomeIcon.HAND_PAPER.create()));
		nav.addItem(new SideNavItem("Reports", ReportsView.class, LineAwesomeIcon.CHART_PIE_SOLID.create()));
		nav.addItem(new SideNavItem("Upload Students", NumbersServedStudentUploadView.class,
				LineAwesomeIcon.UPLOAD_SOLID.create()));

		// Add link to the Upload View

		return nav;
	}

	private Footer createFooter() {
		Footer layout = new Footer();

		Optional<User> maybeUser = authenticatedUser.get();
		if (maybeUser.isPresent()) {
			User user = maybeUser.get();

			Avatar avatar = new Avatar(user.getName());
			StreamResource resource = new StreamResource("profile-pic",
					() -> new ByteArrayInputStream(user.getProfilePicture()));
			avatar.setImageResource(resource);
			avatar.setThemeName("xsmall");
			avatar.getElement().setAttribute("tabindex", "-1");

			MenuBar userMenu = new MenuBar();
			userMenu.setThemeName("tertiary-inline contrast");

			MenuItem userName = userMenu.addItem("");
			Div div = new Div();
			div.add(avatar);
			div.add(user.getName());
			div.add(new Icon("lumo", "dropdown"));
			div.getElement().getStyle().set("display", "flex");
			div.getElement().getStyle().set("align-items", "center");
			div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
			userName.add(div);
			userName.getSubMenu().addItem("Sign out", e -> {
				authenticatedUser.logout();
			});

			layout.add(userMenu);
		} else {
			Anchor loginLink = new Anchor("login", "Sign in");
			layout.add(loginLink);
		}

		return layout;
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		viewTitle.setText(getCurrentPageTitle());
	}

	private String getCurrentPageTitle() {
		return MenuConfiguration.getPageHeader(getContent()).orElse("");
	}
}
