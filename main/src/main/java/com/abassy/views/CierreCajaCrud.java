package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.services.CierreCajaService;
import com.abassy.tables.CierreCaja;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = CierreCajaCrud.VIEW_NAME)
public class CierreCajaCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME = "CierreCaja";
	private static final long serialVersionUID = 1L;
	
	private final CierreCajaService service;
	private final CierreCajaEditor editor;
	final Grid<CierreCaja> grid;
	private final Button addNewBtn;
	
	@Autowired
	public CierreCajaCrud(CierreCajaService service, CierreCajaEditor editor){	
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(CierreCaja.class);
		this.addNewBtn = new Button("Cerrar la caja", VaadinIcons.ARCHIVE);
	}

	@PostConstruct
	protected void init() {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		addComponent(mainLayout);

		grid.setWidth(1000, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "local", "importe", "importe_real", "fecha");
		
		addNewBtn.addClickListener(e -> editor.editCaja(new CierreCaja()));
		
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			grid.setItems(service.findAll());
		});
		
		grid.setItems(service.findAll());
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
}
