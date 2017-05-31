package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.abassy.tables.*;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

//@SuppressWarnings("serial")
@SpringView(name=LineaPedidoCrud.VIEW_NAME)
@Theme("valo")
public class LineaPedidoCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME="LineaPedido";
	private static final long serialVersionUID = 1L;
	
	private final LineaPedidoRepository repo; // Debe ser un servicio
	private final LineaPedidoEditor editor;
	final Grid<LineaPedido> grid;
	final TextField filter;
	private final Button addNewBtn;

	
	@Autowired
	public LineaPedidoCrud(LineaPedidoRepository repo, LineaPedidoEditor editor) {
	//public LineaPedidoCrud(){	
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(LineaPedido.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Línea de Pedido", VaadinIcons.PLUS_CIRCLE);
	}

	@PostConstruct
	protected void init( ) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		//VerticalLayout mainLayout = new VerticalLayout(actions, grid);
		addComponent(mainLayout);

		grid.setWidth(1465, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "pedido", "producto", "cantidad");

		filter.setPlaceholder("Filtrar");
		
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
		
		//editor.setWidth("100%");
		
		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editLineaPedido(e.getValue());
		});

		//filter.setPlaceholder("Filter by last name");
		addNewBtn.addClickListener(e -> editor.editLineaPedido(new LineaPedido()));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		
		listCustomers(null);

		
		
	}

	private void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		/*else {
			grid.setItems(repo.findByPedido(filterText));
		}*/
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
}

