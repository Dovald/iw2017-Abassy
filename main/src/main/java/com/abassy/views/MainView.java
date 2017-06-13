package com.abassy.views;
/*import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.*;

import com.abassy.views.WelcomeView;
//@SpringUI(path="/main2")
@SpringViewDisplay
public class MainView extends UI implements ViewDisplay{
	
	
	private Panel springViewDisplay;
	
	@Override
    public void attach() {
        super.attach();
        this.getUI().getNavigator().navigateTo("");
    }
	
	@Override
	protected void init(VaadinRequest vaadinRequest){
		System.out.println("hola");
		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		setContent(root);
		final CssLayout navigationBar = new CssLayout();
		
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("Inicio", ""));
		navigationBar.addComponent(createNavigationButton("Local", WelcomeView.VIEW_NAME));
		root.addComponent(navigationBar);
		
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);
	}
	
	private Button createNavigationButton(String caption, final String viewName){
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		
		return button;
	}
	
	@Override
	public void showView(View view){
		springViewDisplay.setContent((Component) view);
	}
}*/

import javax.annotation.PostConstruct;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author ruizrube
 *
 */
@SpringViewDisplay
public class MainView extends VerticalLayout implements ViewDisplay {

	private static final long serialVersionUID = 1L;
	
	private Panel springViewDisplay;
	
	@Override
    public void attach() {
        super.attach();
        this.getUI().getNavigator().navigateTo("");
    }
	
	@PostConstruct
	void init() {

		final VerticalLayout root = new VerticalLayout();
		Responsive.makeResponsive(root);
		root.setSizeFull();
		
		// Creamos la cabecera 
		Label tit = new Label("Abassy");
		tit.setStyleName("h2");
		root.addComponent(tit);
		root.setComponentAlignment(tit, Alignment.TOP_LEFT);
		
		Button logoutButton = new Button("Logout", event -> logout());
		//logoutButton.setStyleName(ValoTheme.BUTTON_LINK);
		logoutButton.setIcon(VaadinIcons.SIGN_OUT);
		root.addComponent(logoutButton);

		// Creamos la barra de navegación
		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("Welcome", WelcomeView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Local", LocalCrud.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Zona", ZonaCrud.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Usuarios", UsuarioCrud.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Clientes", ClienteCrud.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Productos", ProductoCrud.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Familia de Productos", FamiliaProductoCrud.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Mesa", MesaCrud.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Pedidos", PedidoCrud.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Cierre de Caja", CierreCajaCrud.VIEW_NAME));
		root.addComponent(navigationBar);

		// Creamos el panel
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 50.0f);

		addComponent(root);
		
	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		// If you didn't choose Java 8 when creating the project, convert this
		// to an anonymous listener class
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}
	
	
	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}

	
	private void logout() {
		getUI().getPage().reload();
		getSession().close();
	}
}
