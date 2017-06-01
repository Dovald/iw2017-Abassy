package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.security.VaadinSessionSecurityContextHolderStrategy;
import com.abassy.services.LineaPedidoService;
import com.abassy.services.PedidoService;
import com.abassy.tables.LineaPedido;
import com.abassy.tables.Local;
import com.abassy.tables.Pedido;
import com.abassy.tables.Usuario;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = PedidoCrud.VIEW_NAME)
public class PedidoCrud extends VerticalLayout implements View {
	public static final String VIEW_NAME = "Pedido";
	private static final long serialVersionUID = 1L;
	
	private static PedidoService service;
	private static LineaPedidoService serviceLP;
	private static PedidoEditor editor;
	private static LineaPedidoEditor editorLP;
	public static Grid<Pedido> grid;
	public static Grid<LineaPedido> gridLP;
	private final Button addNewBtn;
	private final Button addNewLine;

	
	@Autowired
	public PedidoCrud(PedidoService service, PedidoEditor editor, LineaPedidoService serviceLP, LineaPedidoEditor editorLP){	
		this.service = service;
		this.editor = editor;
		this.serviceLP = serviceLP;
		this.editorLP = editorLP;
		this.grid = new Grid<>(Pedido.class);
		this.gridLP = new Grid<>(LineaPedido.class);
		this.addNewBtn = new Button("Añadir Pedido", VaadinIcons.PLUS_CIRCLE);
		this.addNewLine = new Button("Añadir Línea de Pedido", VaadinIcons.PLUS_CIRCLE);
	}

	@PostConstruct
	protected void init() {
		HorizontalLayout actions = new HorizontalLayout(addNewBtn);
		HorizontalLayout actionsLP = new HorizontalLayout(addNewLine);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor, actionsLP, gridLP, editorLP);
		addComponent(mainLayout);

		grid.setWidth(1000, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "cliente", "local", "mesa", "usuario", "importe", "fecha");
		grid.sort("fecha", SortDirection.DESCENDING);
		gridLP.setWidth(1000, Unit.PIXELS);
		gridLP.setHeight(500, Unit.PIXELS);
		gridLP.setColumns("id", "producto", "cantidad");
		
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editPedido(e.getValue());
			gridLP.setVisible(true);
			actionsLP.setVisible(true);
			listarLineasPedidos();
		});
		
		gridLP.asSingleSelect().addValueChangeListener(e -> {
			
			editorLP.editLineaPedido(e.getValue());
		});

		// Boton de Pedido
		addNewBtn.addClickListener(e -> {
			
			editor.editPedido(new Pedido());
		});
		
		// Boton de LineaPedido
		addNewLine.addClickListener(e -> {
			editorLP.editLineaPedido(new LineaPedido());
		});

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
		});
		
		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
		});

		
		//listCustomers(null);

	}
	
	public static void listarPedidos(Usuario u) {
		if (u != null){
			Local loc = u.getLocal();
			grid.setItems(service.findByLocal(loc));
		}else
			grid.setItems(service.findAll());
	}
	
	
	public static void listarLineasPedidos() {
		if(serviceLP != null)
			if((Long)VaadinSessionSecurityContextHolderStrategy.getSession().getAttribute("pedido_id") != null)
			{
				Long id = (Long)VaadinSessionSecurityContextHolderStrategy.getSession().getAttribute("pedido_id");
				Pedido ped = service.findOne(id);
				gridLP.setItems(serviceLP.findByPedido(ped));
			}		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
