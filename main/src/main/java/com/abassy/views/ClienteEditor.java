package com.abassy.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.services.ClienteService;
import com.abassy.tables.Cliente;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("deprecation")
@SpringComponent
@UIScope
public class ClienteEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final ClienteService service;

	private Cliente cliente;
	
	private boolean bandera;

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Cliente");
	TextField nombre = new TextField("Nombre");
	TextField direccion = new TextField("Dirección");
	TextField telefono = new TextField("Teléfono");

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Cliente> binder = new Binder<>(Cliente.class);

	@Autowired
	public ClienteEditor(ClienteService service) {
		this.service = service;

		addComponents(titulo, nombre, direccion, telefono, actions);

		binder.forField(nombre)
		.asRequired("No puede quedar en blanco")
		.bind(Cliente::getNombre, Cliente::setNombre);
		
		binder.forField(direccion)
		.asRequired("No puede quedar en blanco")
		.bind(Cliente::getDireccion, Cliente::setDireccion);
		
		binder.forField(telefono)
		.asRequired("No puede quedar en blanco")
		.bind(Cliente::getTelefono, Cliente::setTelefono);

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
				List<Cliente> clientes = new ArrayList<Cliente>(service.findByTelefonoStartsWithIgnoreCase(telefono.getValue()));
				for(int i = 0; i < clientes.size(); i++) {
					if(clientes.get(i).getTelefono().equals(telefono.getValue()))
					if(clientes.get(i).getId() != cliente.getId()) bandera = false;
				}
				if(bandera) {
					service.save(cliente);
				}
				else Notification.show("El teléfono ya está registrado", Notification.TYPE_WARNING_MESSAGE);
			}
			else Notification.show("Revise los campos del formulario", Notification.TYPE_WARNING_MESSAGE);
		});
		delete.addClickListener(e -> service.delete(cliente));
		cancel.addClickListener(e -> editCliente(cliente));
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
			cliente = service.findOne(c.getId());
		}
		else {
			cliente = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(cliente);

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
