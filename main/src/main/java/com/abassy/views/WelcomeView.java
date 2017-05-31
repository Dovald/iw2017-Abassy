package com.abassy.views;

import javax.annotation.PostConstruct;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author ruizrube
 *
 */
@SpringView(name = WelcomeView.VIEW_NAME)
public class WelcomeView extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "";

	@PostConstruct
	void init() {
		addComponent(new Label("¡Bienvenidos a Abassy!"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
