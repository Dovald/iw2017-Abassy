package com.abassy.views;

import java.util.Collection;

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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class ZonaEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final ZonaService service;
	
	private final LocalService servicelocal;

	private Zona Zona;
	
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
		this.servicelocal = servicelocal;

		addComponents(nombre,local, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> service.save(Zona));
		delete.addClickListener(e -> service.delete(Zona));
		cancel.addClickListener(e -> editZona(Zona));
		setVisible(false);
		local.setItems(servicelocal.findAll());
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
			Zona = service.findOne(c.getId());
		}
		else {
			Zona = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(Zona);

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
