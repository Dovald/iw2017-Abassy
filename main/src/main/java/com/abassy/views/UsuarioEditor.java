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

//import com.abassy.security.SecurityUtils;
import com.abassy.services.*;

@SpringComponent
@UIScope
public class UsuarioEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	//private final UsuarioRepository repository;
	
	private final UsuarioService service;

	private Usuario Usuario;

	Binder<Usuario> binder = new Binder<>(Usuario.class);
	
	Label titulo = new Label("Usuario");
	TextField nombre = new TextField("Nombre");
	TextField apellidos = new TextField("Apellidos");
	TextField username = new TextField("Username");
	TextField password = new TextField("Password");

	/* Action buttons */
	Button save = new Button("Save", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancel", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Delete", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	

	@Autowired
	public UsuarioEditor(UsuarioService service) {
		this.service = service;

		addComponents(nombre, apellidos, username, password, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> service.save(Usuario));
		delete.addClickListener(e -> service.delete(Usuario));
		cancel.addClickListener(e -> editUsuario(Usuario));
		setVisible(false);
		
		// Solo borra el admin
		//delete.setEnabled(SecurityUtils.hasRole("ADMIN"));
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editUsuario(Usuario c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			Usuario = service.findOne(c.getId());
		}
		else {
			Usuario = c;
		}
		cancel.setVisible(persisted);

		// Bind User properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(Usuario);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		//nombre.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}