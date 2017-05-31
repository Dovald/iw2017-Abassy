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
public class ClienteEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final ClienteRepository repository;

	private Cliente Cliente;

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Local");
	TextField nombre = new TextField("Nombre");
	TextField apellidos = new TextField("Apellidos");
	TextField direccion = new TextField("Dirección");
	TextField telefono = new TextField("Teléfono");

	/* Action buttons */
	Button save = new Button("Save", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancel", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Delete", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Cliente> binder = new Binder<>(Cliente.class);

	@Autowired
	public ClienteEditor(ClienteRepository repository) {
		this.repository = repository;

		addComponents(nombre, apellidos, direccion, telefono, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(Cliente));
		delete.addClickListener(e -> repository.delete(Cliente));
		cancel.addClickListener(e -> editCliente(Cliente));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editCliente(Cliente c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			Cliente = repository.findOne(c.getId());
		}
		else {
			Cliente = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(Cliente);

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
