package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.services.LocalService;
import com.abassy.services.UsuarioService;
import com.abassy.tables.Local;
import com.abassy.tables.Usuario;
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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("deprecation")
@SpringComponent
@UIScope
public class UsuarioEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	private final UsuarioService service;

	private Usuario usuario;
	
	private boolean bandera;

	Binder<Usuario> binder = new Binder<>(Usuario.class);
	
	Label titulo = new Label("Usuario");
	TextField nombre = new TextField("Nombre");
	TextField apellidos = new TextField("Apellidos");
	TextField username = new TextField("Username");
	PasswordField password = new PasswordField("Password");
	ComboBox<String> tipo = new ComboBox<>("Autoridad");
	ComboBox<Local> local = new ComboBox<>("Local");
	
	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	@Autowired
	public UsuarioEditor(UsuarioService service, LocalService serviceLocal) {
		this.service = service;
		
		tipo.setItems("GERENTE","ENCARGADO","CAMARERO");
		local.setEmptySelectionAllowed(true);
		local.setItems(serviceLocal.findAll());

		addComponents(nombre, apellidos, username, password, tipo, local, actions);

		binder.forField(nombre)
		.asRequired("No puede quedar en blanco")
		.bind(Usuario::getNombre, Usuario::setNombre);
		
		binder.forField(apellidos)
		.asRequired("No puede quedar en blanco")
		.bind(Usuario::getApellidos, Usuario::setApellidos);
		
		binder.forField(username)
		.asRequired("No puede quedar en blanco")
		.bind(Usuario::getUsername, Usuario::setUsername);
		
		binder.forField(password)
		.asRequired("No puede quedar en blanco")
		.bind(Usuario::getPassword, Usuario::setPassword);
		
		binder.forField(tipo)
		.asRequired("No puede quedar en blanco")
		.bind(Usuario::getTipo, Usuario::setTipo);
		
		binder.forField(local)
		//.asRequired("No puede quedar en blanco")
		.bind(Usuario::getLocal, Usuario::setLocal);

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
				Usuario user = service.findByUsername(username.getValue());
				if(user != null) {
					if(user.getId() != usuario.getId()) bandera = false;
				}
				if(bandera) {
					if(!usuario.getTipo().equals("GERENTE"))
						if(usuario.getLocal() != null) service.save(usuario);
						else Notification.show("Los ENCARGADOS y CAMAREROS necesitan un local", Notification.TYPE_WARNING_MESSAGE);
					else {
						usuario.setLocal(null);
						service.save(usuario);
					}
				}
				else Notification.show("El username ya está registrado", Notification.TYPE_WARNING_MESSAGE);
			}
			else Notification.show("Revise los campos del formulario", Notification.TYPE_WARNING_MESSAGE);
		});
		delete.addClickListener(e -> service.delete(usuario));
		cancel.addClickListener(e -> editUsuario(usuario));
		
		setVisible(false);
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
			usuario = service.findOne(c.getId());
		}
		else {
			usuario = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(usuario);

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