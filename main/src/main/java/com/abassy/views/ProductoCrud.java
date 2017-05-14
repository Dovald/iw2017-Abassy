/*package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.tables.*;

import com.vaadin.server.FontAwesome;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

@SpringUI
@Theme("valo")
public class ProductoCrud extends UI {
	
	private static final long serialVersionUID = 1L;
	
	//private final UserRepository repo; //hay que cambiarlo
	//private final UserEditor editor; ////hay que cambiarlo
	final Grid<Producto> grid;
	final TextField filter;
	private final Button addNewBtn;

	
	@Autowired
	//public CierreCajaCrud(UserRepository hay que cambiarlo repo, UserEditor hay que cambiarlo editor) {
	public ProductoCrud(){	
		//this.repo = repo;
		//this.editor = editor;
		this.grid = new Grid<>(Producto.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Producto", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		//VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid);
		setContent(mainLayout);

		grid.setWidth(1465, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "familiaProducto", "nombre", "precio", "tipo", "imagen", "productoCompuestosForIdProducto", "lineaPedidos", "productoCompuestosForIdProducto");

		//filter.setPlaceholder("Filter by last name");

		
		
	}

	private void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}
	
	
}*/
