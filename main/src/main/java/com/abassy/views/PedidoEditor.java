package com.abassy.views;

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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class PedidoEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final PedidoRepository repository;

	private Pedido Pedido;

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Pedido");
	TextField mesa = new TextField("Mesa");
	TextField usuario = new TextField("Usuario");
	TextField importe = new TextField("Importe");
	TextField fecha = new TextField("Fecha");
	
	/* Action buttons */
	Button save = new Button("Save", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancel", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Delete", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Pedido> binder = new Binder<>(Pedido.class);

	@Autowired
	public PedidoEditor(PedidoRepository repository) {
		this.repository = repository;

		addComponents(mesa, importe, fecha, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(Pedido));
		delete.addClickListener(e -> repository.delete(Pedido));
		cancel.addClickListener(e -> editPedido(Pedido));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editPedido(Pedido c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			Pedido = repository.findOne(c.getId());
		}
		else {
			Pedido = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(Pedido);

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
