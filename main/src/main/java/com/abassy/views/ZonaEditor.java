package com.abassy.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.services.LocalService;
import com.abassy.services.ZonaService;
import com.abassy.tables.Local;
import com.abassy.tables.Zona;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("deprecation")
@SpringComponent
@UIScope
public class ZonaEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final ZonaService service;

	private Zona zona;
	
	private boolean bandera;
	
	Binder<Zona> binder = new Binder<>(Zona.class);

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Zona");
	TextField nombre = new TextField("Nombre");
	ComboBox<Local> local = new ComboBox<>("Local");

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public ZonaEditor(ZonaService service, LocalService servicelocal) {
		this.service = service;

		addComponents(titulo, nombre, local, actions);

		binder.forField(nombre)
		.asRequired("No puede quedar en blanco")
		.bind(Zona::getNombre, Zona::setNombre);
		
		binder.forField(local)
		.asRequired("No puede quedar en blanco")
		.bind(Zona::getLocal, Zona::setLocal);

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
				List<Zona> zonas = new ArrayList<Zona>(service.findByLocal(local.getValue()));
				for(int i = 0; i < zonas.size(); i++){
					if(zonas.get(i).getNombre().equals(nombre.getValue()))
						if(zonas.get(i).getId() != zona.getId()) bandera = false;
				}
				if(bandera) {
					service.save(zona);
				}
				else Notification.show("La zona ya existe en ese local", Notification.TYPE_WARNING_MESSAGE);
			}
			else {
				Notification.show("Revise los campos del formulario", Notification.TYPE_WARNING_MESSAGE);
			}
		});
		delete.addClickListener(e -> service.delete(zona));
		cancel.addClickListener(e -> editZona(zona));
		
		local.setItems(servicelocal.findAll());
		
		setVisible(false);
		
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editZona(Zona c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			zona = service.findOne(c.getId());
		}
		else {
			zona = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(zona);

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
