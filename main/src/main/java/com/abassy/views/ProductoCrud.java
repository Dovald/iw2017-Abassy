package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.services.ProductoService;
import com.abassy.tables.Local;
import com.abassy.tables.Producto;
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
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		addComponent(mainLayout);

		grid.setWidth(1000, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "familiaProducto", "nombre", "precio");

		filter.setPlaceholder("Filtrar Nombre");
		
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
		
		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editProducto(e.getValue());
		});

		//filter.setPlaceholder("Filter by last name");
		addNewBtn.addClickListener(e -> editor.editProducto(new Producto()));

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
