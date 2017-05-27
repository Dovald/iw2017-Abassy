package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.tables.*;

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

@SpringView(name = PedidoCrud.VIEW_NAME)
public class PedidoCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME = "Pedido";
	private static final long serialVersionUID = 1L;
	
	//private final UserRepository repo; //hay que cambiarlo
	//private final UserEditor editor; ////hay que cambiarlo
	final Grid<Pedido> grid;
	final TextField filter;
	private final Button addNewBtn;

	
	@Autowired
	//public CierreCajaCrud(UserRepository hay que cambiarlo repo, UserEditor hay que cambiarlo editor) {
	public PedidoCrud(){	
		//this.repo = repo;
		//this.editor = editor;
		this.grid = new Grid<>(Pedido.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Pedido", FontAwesome.PLUS);
	}

	@PostConstruct
	protected void init() {
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		//VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid);
		addComponent(mainLayout);

		grid.setWidth(1000, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "cliente", "local", "mesa", "usuario", "importe", "fecha", "lineaPedidos");

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
