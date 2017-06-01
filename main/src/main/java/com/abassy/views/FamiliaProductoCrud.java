package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.tables.FamiliaProducto;
import com.abassy.tables.FamiliaProductoRepository;
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

@SpringView(name = FamiliaProductoCrud.VIEW_NAME)
public class FamiliaProductoCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME = "FamiliaProducto";
	private static final long serialVersionUID = 1L;
	
	private final FamiliaProductoRepository service; //hay que cambiarlo
	private final FamiliaProductoEditor editor; ////hay que cambiarlo
	final Grid<FamiliaProducto> grid;
	final TextField filter;
	private final Button addNewBtn;

	
	@Autowired
	public FamiliaProductoCrud(FamiliaProductoRepository service, FamiliaProductoEditor editor){	
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(FamiliaProducto.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Familia de Producto", VaadinIcons.PLUS_CIRCLE);
	}

	@PostConstruct
	protected void init() {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		addComponent(mainLayout);

		grid.setWidth(1000, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "nombre");

		filter.setPlaceholder("Filtrar Nombre");
		
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
		
		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editFamiliaProducto(e.getValue());
		});

		//filter.setPlaceholder("Filter by last name");
		addNewBtn.addClickListener(e -> editor.editFamiliaProducto(new FamiliaProducto()));

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
