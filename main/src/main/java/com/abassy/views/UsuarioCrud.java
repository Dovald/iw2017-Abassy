package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.tables.*;

import java.util.Collection;

import javax.annotation.PostConstruct;



import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.UI;
import com.vaadin.annotations.Theme;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.spring.annotation.*;

@SpringView(name = UsuarioCrud.VIEW_NAME)
public class UsuarioCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME="Usuario";
	private static final long serialVersionUID = 1L;
	
	private final UsuarioRepository repo; //hay que cambiarlo
	private final UsuarioEditor editor; ////hay que cambiarlo
	final Grid<Usuario> grid;
	final TextField filter;
	private final Button addNewBtn;

	
	@Autowired
	//public UsuarioCrud(UsuarioRepository repo, UsuarioEditor editor) {
		public UsuarioCrud(UsuarioRepository repo, UsuarioEditor editor) {	
	//public UsuarioCrud(){	
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Usuario.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Usuario", FontAwesome.PLUS);
	}

	@PostConstruct
	protected void init() {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		//VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid);
		addComponent(mainLayout);

		grid.setWidth(1000, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		//grid.setColumns("id", "nombre", "apellidos");
		grid.setColumns("id", "local", "tipo", "nombre", "apellidos", "password", "pedidos");

		//filter.setPlaceholder("Filter by last name");

		
		filter.setPlaceholder("Filter by last name");

		// Hook logic to components
		/*
		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editUser(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editUser(new Usuario("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});*/

		// Initialize listing
		//listCustomers(null);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
/*
	
	private void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems((Collection<Usuario>) repo.findAll());
		}
		else {
			grid.setItems(repo.findByApellidosStartsWithIgnoreCase(filterText));
		}
	}
	*/
	
}
