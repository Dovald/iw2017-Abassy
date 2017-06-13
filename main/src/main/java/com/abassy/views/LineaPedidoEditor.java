package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.services.LineaPedidoService;
import com.abassy.services.PedidoService;
import com.abassy.services.ProductoService;
import com.abassy.tables.LineaPedido;
import com.abassy.tables.Pedido;
import com.abassy.tables.Producto;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("deprecation")
@SpringComponent
@UIScope
public class LineaPedidoEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final LineaPedidoService service;

	private LineaPedido lineapedido;
	
	private Pedido pedido;
	
	private boolean bandera;

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Línea de Pedido");
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
		
		cantidad.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
				21, 22, 23, 24, 25, 26, 27, 28, 29, 30);
		producto.setItems(serviceprod.findAll());
		producto.setWidth(450, Unit.PIXELS);
		
		addComponents(titulo, producto, cantidad, actions);

		binder.forField(producto)
		.asRequired("No puede quedar en blanco")
		.bind(LineaPedido::getProducto, LineaPedido::setProducto);
		
		binder.forField(cantidad)
		.asRequired("No puede quedar en blanco")
		.bind(LineaPedido::getCantidad, LineaPedido::setCantidad);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {
			if(binder.isValid())
			{
				bandera = true;
				LineaPedido linPed = service.findByPedidoAndProducto(pedido, producto.getValue());
				if(linPed != null) {
					if(linPed.getId() != lineapedido.getId()) bandera = false;
				}
				if(bandera) {
					service.save(lineapedido, pedido);
				}
				else Notification.show("El producto ya se encuentra en una línea de pedido", Notification.TYPE_WARNING_MESSAGE);
			}
			else Notification.show("Revise los campos del formulario", Notification.TYPE_WARNING_MESSAGE);
		});
		delete.addClickListener(e -> service.delete(lineapedido, pedido));
		cancel.addClickListener(e -> editLineaPedido(lineapedido, pedido));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editLineaPedido(LineaPedido c, Pedido p) {
		if (c == null || p == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			lineapedido = service.findOne(c.getId());
		}
		else {
			lineapedido = c;
		}
		
		pedido = p;
		
		cancel.setVisible(persisted);

		if(persisted){
			producto.setSelectedItem(c.getProducto());
			cantidad.setSelectedItem(c.getCantidad());
		}
		
		binder.setBean(lineapedido);

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
