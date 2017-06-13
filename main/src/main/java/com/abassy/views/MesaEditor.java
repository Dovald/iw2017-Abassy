package com.abassy.views;

//import java.util.ArrayList;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.services.LocalService;
import com.abassy.services.MesaService;
import com.abassy.services.ZonaService;
import com.abassy.tables.Local;
import com.abassy.tables.Mesa;
import com.abassy.tables.Zona;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
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
public class MesaEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final MesaService service;

	private Mesa mesa;
	
	private boolean bandera;
	
	Binder<Mesa> binder = new Binder<>(Mesa.class);

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Mesa");
	TextField numero = new TextField("Nº Mesa");
	ComboBox<Local> local = new ComboBox<>("Local");
	ComboBox<Zona> zona = new ComboBox<>("Zona");

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public MesaEditor(MesaService service, ZonaService serviceZona, LocalService serviceLocal) {
		this.service = service;

		addComponents(titulo, numero, local, zona, actions);

		binder.forField(numero)
		  .asRequired("No puede quedar en blanco")
		  .withNullRepresentation("")
		  .withConverter(
		    new StringToIntegerConverter("Debes introducir un número"))
		  .bind(Mesa::getNumero, Mesa::setNumero);
		
		binder.forField(local)
		.asRequired("No puede quedar en blanco")
		.bind(Mesa::getLocal, Mesa::setLocal);
		
		binder.forField(zona)
		.asRequired("No puede quedar en blanco")
		.bind(Mesa::getZona, Mesa::setZona);
		
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
				Mesa mesas = service.findByNumeroAndZonaAndLocal(Integer.parseInt(numero.getValue()), zona.getValue(), local.getValue());
				if(mesas != null)
					if(mesas.getId() != mesa.getId()) bandera = false;
				if(bandera) {
					service.save(mesa);
				}
				else Notification.show("La mesa ya existe en esa zona", Notification.TYPE_WARNING_MESSAGE);
			}
			else {
				Notification.show("Revise los campos del formulario", Notification.TYPE_WARNING_MESSAGE);
			}
		});
		delete.addClickListener(e -> service.delete(mesa));
		cancel.addClickListener(e -> editMesa(mesa));
		
		local.setItems(serviceLocal.findAll());
		
		zona.setEmptySelectionAllowed(true);

		local.addValueChangeListener(e -> {
			zona.setItems(serviceZona.findByLocal(local.getValue()));
			zona.setVisible(true);
		});
		
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editMesa(Mesa c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			mesa = service.findOne(c.getId());
		}
		else {
			zona.setValue(null);
			mesa = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(mesa);

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
