package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.services.UsuarioService;
import com.abassy.tables.Usuario;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = UsuarioCrud.VIEW_NAME)
public class UsuarioCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME="Usuario";
	private static final long serialVersionUID = 1L;
	
	private final UsuarioService service;
	private final UsuarioEditor editor;
	final Grid<Usuario> grid;
	final TextField filter;
	private final Button addNewBtn;

	
	@Autowired
	public UsuarioCrud(UsuarioService service, UsuarioEditor editor) {	
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Usuario.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Usuario", VaadinIcons.PLUS_CIRCLE);
	}

	@PostConstruct
	protected void init() {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		addComponent(mainLayout);

		grid.setWidth(1000, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "nombre", "apellidos", "username", "local");
		//grid.setColumns("id", "local", "tipo", "nombre", "apellidos", "password", "pedidos");

	
		filter.setPlaceholder("Filtrar Nombre");

		// Hook logic to components
		
		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editUsuario(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editUsuario(new Usuario("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	
	private void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(service.findAll());
		}
		else {
			grid.setItems(service.findByNombreStartsWithIgnoreCase(filterText));
		}
	}
	
	
}
