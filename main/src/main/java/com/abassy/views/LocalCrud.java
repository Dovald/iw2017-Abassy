package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.services.LocalService;
import com.abassy.tables.Local;
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

@SpringView(name=LocalCrud.VIEW_NAME)
public class LocalCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME = "LocalCrud";
	private static final long serialVersionUID = 1L;
	
	private final LocalService service;
	private final LocalEditor editor;
	final Grid<Local> grid;
	final TextField filter;
	private final Button addNewBtn;
	
	@Autowired
	public LocalCrud(LocalService service, LocalEditor editor){
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Local.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Local", VaadinIcons.PLUS_CIRCLE);
	}

	//@Override
	@PostConstruct
	 public void init() {
		
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		actions.setSizeFull();
		grid.setSizeFull();
		mainLayout.setSizeFull();
		Responsive.makeResponsive(mainLayout);
		addComponent(mainLayout);

		grid.setColumns("id", "direccion", "ciudad");
		grid.getColumn("direccion").setCaption("Dirección");
		
		filter.setPlaceholder("Filtrar Dirección");
		
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
		
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editLocal(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editLocal(new Local()));

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
			grid.setItems(service.findByDireccionStartsWithIgnoreCase(filterText));
		}
	}
	
	
}

