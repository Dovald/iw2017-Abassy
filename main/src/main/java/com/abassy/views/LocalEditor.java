package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.services.LocalService;
import com.abassy.tables.Local;
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
public class LocalEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final LocalService service;

	private Local local;
	
	private boolean bandera;
	
	Binder<Local> binder = new Binder<>(Local.class);

	Label titulo = new Label("Local");
	TextField direccion = new TextField("Dirección");
	TextField ciudad = new TextField("Ciudad");

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	@Autowired
	public LocalEditor(LocalService service) {
		
		this.service = service;
		
		addComponents(titulo, direccion, ciudad, actions);

		binder.forField(direccion)
		.asRequired("No puede quedar en blanco")
		.bind(Local::getDireccion, Local::setDireccion);
		
		binder.forField(ciudad)
		.asRequired("No puede quedar en blanco")
		.bind(Local::getCiudad, Local::setCiudad);

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
				Local loc = service.findByDireccionIgnoreCaseAndCiudadIgnoreCase(direccion.getValue(), ciudad.getValue());
				if(loc != null) {
					if(loc.getId() != local.getId()) bandera = false;
				}
				if(bandera) {
					service.save(local);
				}
				else Notification.show("El local ya existe en ese lugar", Notification.TYPE_WARNING_MESSAGE);
			}
			else Notification.show("Revise los campos del formulario", Notification.TYPE_WARNING_MESSAGE);
		});
		delete.addClickListener(e -> service.delete(local));
		cancel.addClickListener(e -> editLocal(local));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editLocal(Local c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			local = service.findOne(c.getId());
		}
		else {
			local = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(local);

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
