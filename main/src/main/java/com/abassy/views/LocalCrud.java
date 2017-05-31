package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.tables.*;

import java.util.Collection;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.icons.VaadinIcons;
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
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.spring.annotation.*;

@SpringView(name=LocalCrud.VIEW_NAME)
public class LocalCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME="LocalCrud";
	private static final long serialVersionUID = 1L;
	
	private final LocalRepository repo;
	private final LocalEditor editor;
	final Grid<Local> grid;
	final TextField filter;
	private final Button addNewBtn;
	//private final Button boton;
	
	@Autowired
	public LocalCrud(LocalRepository repo, LocalEditor editor){
		this.repo = repo;
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
		//setContent(mainLayout);
		addComponent(mainLayout);
		grid.setWidth(1000, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "direccion", "ciudad");
		
		filter.setPlaceholder("Filtrar Dirección");
		
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
		
		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editLocal(e.getValue());
		});

		//filter.setPlaceholder("Filter by last name");
		addNewBtn.addClickListener(e -> editor.editLocal(new Local("", "")));

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
			grid.setItems((Collection<Local>) repo.findAll());
		}
		else {
			grid.setItems(repo.findByDireccionStartsWithIgnoreCase(filterText));
		}
	}
	
	
}

