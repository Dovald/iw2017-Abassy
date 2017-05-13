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
public class VaadinUI extends UI {
	
	private static final long serialVersionUID = 1L;
	
	private final UserRepository repo;
	private final UserEditor editor;
	final Grid<User> grid;
	final TextField filter;
	private final Button addNewBtn;

	
	@Autowired
	public VaadinUI(UserRepository repo, UserEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(User.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New customer", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		setContent(mainLayout);

		grid.setWidth(1465, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "firstName", "lastName");

		filter.setPlaceholder("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editUser(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editUser(new User("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}

	private void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}
}
*/