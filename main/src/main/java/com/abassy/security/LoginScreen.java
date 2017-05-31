package com.abassy.security;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class LoginScreen extends VerticalLayout {
	
	private static final long serialVersionUID = 1L;

	public LoginScreen(LoginCallback callback) {
        setMargin(true);
        setSpacing(true);
        
        Component loginForm = buildLoginForm(callback);
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
    }
	
	private Component buildLoginForm(LoginCallback callback) {
		VerticalLayout loginF = new VerticalLayout();
		Label welcome = new Label("Bienvenidos a Abassy");
        //welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_LARGE);
        welcome.addStyleName(ValoTheme.LABEL_BOLD);

        TextField username = new TextField("Usuario");
        username.setIcon(VaadinIcons.HANDS_UP);

        PasswordField password = new PasswordField("Contraseña");
        password.setIcon(VaadinIcons.LOCK);

        Button login = new Button("Entrar", evt -> {
            String pword = password.getValue();
            password.setValue("");
            if (!callback.login(username.getValue(), pword)) {
                Notification.show("Fallo en autenticación");
                username.focus();
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.setIcon(VaadinIcons.CHECK_CIRCLE);
        
        loginF.addComponents(welcome, username, password, login);
        //setComponentAlignment(welcome, Alignment.MIDDLE_CENTER);
        loginF.setComponentAlignment(welcome, Alignment.MIDDLE_CENTER);
        loginF.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
        loginF.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        loginF.setComponentAlignment(login, Alignment.MIDDLE_CENTER);
        
        return loginF;
    }

    @FunctionalInterface
    public interface LoginCallback {

        boolean login(String username, String password);
    }

	
}
