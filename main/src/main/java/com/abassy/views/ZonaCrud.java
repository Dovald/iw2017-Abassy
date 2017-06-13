package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.services.ZonaService;
import com.abassy.tables.Zona;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringView(name=ZonaCrud.VIEW_NAME)
public class ZonaCrud extends VerticalLayout implements View{
	public static final String VIEW_NAME="ZonaCrud";
	private static final long serialVersionUID = 1L;
	
	private final ZonaService service;
	private final ZonaEditor editor;
	final Grid<Zona> grid;
	final TextField filter;
	private final Button addNewBtn;

	@Autowired
	public ZonaCrud(ZonaService service, ZonaEditor editor)
	{	
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Zona.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Zona", VaadinIcons.PLUS_CIRCLE);
	}

	@PostConstruct
	protected void init() {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		actions.setSizeFull();
		grid.setSizeFull();
		mainLayout.setSizeFull();
		Responsive.makeResponsive(mainLayout);
		addComponent(mainLayout);

		grid.setColumns("id", "local", "nombre");

		filter.setPlaceholder("Filtrar Zona");
		
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
		
		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editZona(e.getValue());
		});

		//filter.setPlaceholder("Filter by last name");
		addNewBtn.addClickListener(e -> editor.editZona(new Zona()));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

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