package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.services.MesaService;
import com.abassy.tables.Mesa;
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

@SpringView(name = MesaCrud.VIEW_NAME)
public class MesaCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME = "MesaCrud";
	private static final long serialVersionUID = 1L;
	
	private final MesaService service;
	private final MesaEditor editor;
	final Grid<Mesa> grid;
	final TextField filter;
	private final Button addNewBtn;
	
	@Autowired
	public MesaCrud(MesaService service, MesaEditor editor)
	{
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Mesa.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Mesa", VaadinIcons.PLUS_CIRCLE);
	}

	@PostConstruct
	protected void init() 
	{
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		actions.setSizeFull();
		grid.setSizeFull();
		mainLayout.setSizeFull();
		Responsive.makeResponsive(mainLayout);
		addComponent(mainLayout);

		grid.setColumns("id", "numero", "zona", "local");
		grid.getColumn("numero").setCaption("Número");

		filter.setPlaceholder("Filtrar Número");
		
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
		
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editMesa(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editMesa(new Mesa()));

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
			grid.setItems(service.findByNumero(Integer.parseInt(filterText)));
		}
	}
}