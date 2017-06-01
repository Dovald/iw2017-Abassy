package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.services.ProductoService;
import com.abassy.tables.Producto;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = ProductoCrud.VIEW_NAME)
public class ProductoCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME = "Producto";
	private static final long serialVersionUID = 1L;
	
	private final ProductoService service;
	private final ProductoEditor editor;
	final Grid<Producto> grid;
	final TextField filter;
	private final Button addNewBtn;

	
	@Autowired
	//public CierreCajaCrud(UserRepository hay que cambiarlo repo, UserEditor hay que cambiarlo editor) {
	public ProductoCrud(ProductoService service, ProductoEditor editor){	
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Producto.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Producto", VaadinIcons.PLUS_CIRCLE);
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
		grid.setColumns("id", "familiaProducto", "nombre", "precio", "tipo", "productoCompuestosForIdProducto", "lineaPedidos", "productoCompuestosForIdProducto");

		//filter.setPlaceholder("Filter by last name");

		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	/*
	private void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}*/
	
	
}
