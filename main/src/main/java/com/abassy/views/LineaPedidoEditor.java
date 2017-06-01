package com.abassy.views;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.security.SecurityUtils;
import com.abassy.services.LineaPedidoService;
import com.abassy.services.PedidoService;
import com.abassy.services.ProductoService;
import com.abassy.tables.LineaPedido;
import com.abassy.tables.LineaPedidoRepository;
import com.abassy.tables.Pedido;
import com.abassy.tables.PedidoRepository;
import com.abassy.tables.Producto;
import com.abassy.tables.ProductoRepository;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class LineaPedidoEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final LineaPedidoService service;
	
	private final PedidoService servicepedido;
	
	private final ProductoService serviceproducto;

	private LineaPedido LineaPedido;

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Línea de Pedido");
	//NativeSelect<String> pedido;
	ComboBox<Producto> producto = new ComboBox<>("Producto");
	NativeSelect<Integer> cantidad = new NativeSelect<>("Cantidad");

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<LineaPedido> binder = new Binder<>(LineaPedido.class);

	@Autowired
	public LineaPedidoEditor(LineaPedidoService service, PedidoService serviceped,
			ProductoService serviceprod) {
		this.service = service;
		this.servicepedido = serviceped;
		this.serviceproducto = serviceprod;
		
		/*Collection<Pedido> pedidos = servicepedido.findAll();
		ArrayList<String> pedidoList = new ArrayList<String>();
		for(Pedido p: pedidos){
			System.out.println(p.getId());
			pedidoList.add(p.getId().toString());
		}
		pedido = new NativeSelect<>("Selecciona pedido:", pedidoList);
		pedido.setEmptySelectionAllowed(false);*/
		
		cantidad.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
		cantidad.setEmptySelectionAllowed(false);
		producto.setItems(serviceprod.findAll());
		producto.setEmptySelectionAllowed(false);
		/*Collection<Producto> productos = serviceproducto.findAll();
		ArrayList<String> productoList = new ArrayList<String>();
		for(Producto p: productos){
			System.out.println(p.getNombre());
			productoList.add(p.getNombre());
		}
		producto = new NativeSelect<>("Selecciona producto:", productoList);
		producto.setEmptySelectionAllowed(false);*/
		
		addComponents(producto, cantidad, actions);
		
		//SecurityUtils.getUserLogin().getLocal();

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> service.save(LineaPedido));
		delete.addClickListener(e -> service.delete(LineaPedido));
		cancel.addClickListener(e -> editLineaPedido(LineaPedido));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editLineaPedido(LineaPedido c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			LineaPedido = service.findOne(c.getId());
		}
		else {
			LineaPedido = c;
		}
		cancel.setVisible(persisted);

		if(persisted)
			producto.setSelectedItem(c.getProducto());
		
		binder.setBean(LineaPedido);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
