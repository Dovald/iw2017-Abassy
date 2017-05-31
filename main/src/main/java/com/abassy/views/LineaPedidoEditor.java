package com.abassy.views;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.tables.*;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

//@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class LineaPedidoEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final LineaPedidoRepository repository;
	
	private final PedidoRepository repositorypedido;
	
	private final ProductoRepository repositoryproducto;

	private LineaPedido LineaPedido;

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Línea de Pedido");
	
	NativeSelect<String> pedido;
	NativeSelect<String> producto;
	TextField cantidad = new TextField("Cantidad");
	//TextField hola = new TextField("Hola");

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<LineaPedido> binder = new Binder<>(LineaPedido.class);

	@Autowired
	public LineaPedidoEditor(LineaPedidoRepository repository, PedidoRepository repositoryped,
			ProductoRepository repositoryprod) {
		this.repository = repository;
		this.repositorypedido = repositoryped;
		this.repositoryproducto = repositoryprod;
		
		Collection<Pedido> pedidos = (Collection<Pedido>) repositorypedido.findAll();
		ArrayList<String> pedidoList = new ArrayList<String>();
		for(Pedido p: pedidos){
			System.out.println(p.getId());
			pedidoList.add(p.getId().toString());
		}
		pedido = new NativeSelect<>("Selecciona pedido:", pedidoList);
		pedido.setEmptySelectionAllowed(false);
		
		Collection<Producto> productos = (Collection<Producto>) repositoryproducto.findAll();
		ArrayList<String> productoList = new ArrayList<String>();
		for(Producto p: productos){
			System.out.println(p.getNombre());
			productoList.add(p.getNombre());
		}
		producto = new NativeSelect<>("Selecciona producto:", productoList);
		producto.setEmptySelectionAllowed(false);
		
		addComponents(pedido, producto, cantidad, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(LineaPedido));
		delete.addClickListener(e -> repository.delete(LineaPedido));
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
			LineaPedido = repository.findOne(c.getId());
		}
		else {
			LineaPedido = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(LineaPedido);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		//firstName.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
