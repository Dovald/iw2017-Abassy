package com.abassy.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.security.SecurityUtils;
import com.abassy.services.LineaPedidoService;
import com.abassy.services.PedidoService;
import com.abassy.tables.LineaPedido;
import com.abassy.tables.Local;
import com.abassy.tables.Pedido;
import com.abassy.tables.Usuario;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
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
	
	private final PedidoService service;
	private final LineaPedidoService serviceLP;
	private final PedidoEditor editor;
	private final LineaPedidoEditor editorLP;
	public final Grid<Pedido> grid;
	public final Grid<LineaPedido> gridLP;
	private final Button addNewBtn;
	private final Button addNewLine;
	
	private Pedido pedidoSeleccionado;
	
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
		actions.setSizeFull();
		actionsLP.setSizeFull();
		grid.setSizeFull();
		gridLP.setSizeFull();
		mainLayout.setSizeFull();
		Responsive.makeResponsive(mainLayout);
		addComponent(mainLayout);

		grid.setColumns("id", "zona", "mesa", "cliente", "usuario", "importe", "fecha", "cerrado");
		grid.sort("fecha", SortDirection.DESCENDING);
	
		gridLP.setColumns("id", "producto", "cantidad");

		gridLP.addColumn(lineaPedido -> {
			return lineaPedido.getProducto().getPrecio() * lineaPedido.getCantidad();
		}).setCaption("Precio");
		
		actionsLP.setVisible(false);
		gridLP.setVisible(false);
		
		if(SecurityUtils.getUserLogin().getTipo().equals("GERENTE")) addNewBtn.setVisible(false);

		grid.asSingleSelect().addValueChangeListener(e -> {
			if(e.getValue() != null){
				pedidoSeleccionado = e.getValue();
				if(!SecurityUtils.getUserLogin().getTipo().equals("GERENTE")) {
					editor.editPedido(pedidoSeleccionado);
					if(pedidoSeleccionado.getCerrado()) actionsLP.setVisible(false);
					else actionsLP.setVisible(true);
					gridLP.setVisible(true);
					listarLineasPedidos();
				} else {
					gridLP.setVisible(true);
					listarLineasPedidos();
				}
			}
			
		});
		
		gridLP.asSingleSelect().addValueChangeListener(e -> {
			if(!SecurityUtils.getUserLogin().getTipo().equals("GERENTE"))
				if(!pedidoSeleccionado.getCerrado())
					editorLP.editLineaPedido(e.getValue(), pedidoSeleccionado);
		});

		// Boton de Pedido
		addNewBtn.addClickListener(e -> {
			editorLP.setVisible(false);
			actionsLP.setVisible(false);
			gridLP.setVisible(false);
			editor.editPedido(new Pedido());
		});
		
		// Boton de LineaPedido
		addNewLine.addClickListener(e -> {
			editorLP.editLineaPedido(new LineaPedido(), pedidoSeleccionado);
		});
		
		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			gridLP.setVisible(false);
			actionsLP.setVisible(false);
			editorLP.setVisible(false);
			listarPedidos(SecurityUtils.getUserLogin());
		});
		
		// Listen changes made by the editor, refresh data from backend
		editorLP.setChangeHandler(() -> {
			editorLP.setVisible(false);
			listarLineasPedidos();
		});
	
		listarPedidos(SecurityUtils.getUserLogin());

	}
	
	public void listarPedidos(Usuario u) {
		if (u != null){
			if(u.getLocal() != null){
				Local loc = u.getLocal();
				grid.setItems(service.findByLocal(loc));
			} else grid.setItems(service.findAll());
		}
	}
	
	
	public void listarLineasPedidos() {
		if(serviceLP != null)
			if(pedidoSeleccionado != null)
			{
				Long id = pedidoSeleccionado.getId();
				Pedido ped = service.findOne(id);
				gridLP.setItems(serviceLP.findByPedido(ped));
			}		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
