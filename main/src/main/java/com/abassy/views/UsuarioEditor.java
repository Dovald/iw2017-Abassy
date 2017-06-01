package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.tables.*;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToFloatConverter;
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
import com.abassy.security.SecurityUtils;
//import com.abassy.security.SecurityUtils;
import com.abassy.services.*;

@SpringComponent
@UIScope
public class UsuarioEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final LocalService serviceLocal;
	
	private final UsuarioService service;

	private Usuario Usuario;

	Binder<Usuario> binder = new Binder<>(Usuario.class);
	
	Label titulo = new Label("Usuario");
	TextField nombre = new TextField("Nombre");
	TextField apellidos = new TextField("Apellidos");
	TextField username = new TextField("Username");
	TextField password = new TextField("Password");
	ComboBox<Integer> tipo = new ComboBox<>("Autoridad");
	ComboBox<Local> local = new ComboBox<>("Local");
	/* Action buttons */
	Button save = new Button("Save", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancel", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Delete", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);
	

	@Autowired
	public UsuarioEditor(UsuarioService service, LocalService serviceLocal) {
		this.service = service;
		this.serviceLocal = serviceLocal;
		
		tipo.setItems(0, 1, 2);
		local.setItems(serviceLocal.findAll());

		addComponents(nombre, apellidos, username, password, tipo, local, actions);

		/*binder.forField(importe_real)
		  .withConverter(
		    new StringToFloatConverter("Debes introducir un número"))
		  .bind(CierreCaja::getImporte_real, CierreCaja::setImporte_real);*/
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
		
		/*tipo.addSelectionListener(e -> {
			if(tipo.getValue().equals("GERENTE")){
				Usuario.setTipo(0);
			}
			else{ 
				if(tipo.getValue().equals("ENCARGADO")){
					Usuario.setTipo(1);
				}
				else{
					Usuario.setTipo(2);
				}
			}
		});*/
		
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