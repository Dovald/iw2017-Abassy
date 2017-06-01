package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.security.SecurityUtils;
import com.abassy.services.MesaService;
import com.abassy.services.ZonaService;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class MesaEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final MesaService service;
	
	private final ZonaService serviceZona;

	private Mesa mesa;
	
	Binder<Mesa> binder = new Binder<>(Mesa.class);

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Nueva Mesa");
	TextField numero = new TextField("Nº Mesa");
	ComboBox<Zona> zona = new ComboBox<>("Zona"); 

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);


	@Autowired
	public MesaEditor(MesaService service, ZonaService serviceZona) {
		this.service = service;
		this.serviceZona = serviceZona;

		addComponents(titulo, numero, zona, actions);

		binder.forField(numero)
		  .withNullRepresentation(Integer.toString(0))
		  .withConverter(
		    new StringToIntegerConverter("Debes introducir un número"))
		  .bind(Mesa::getNumero, Mesa::setNumero);
		
		binder.forField(zona).bind(Mesa::getZona, Mesa::setZona);
		
		// bind using naming convention
		//binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> service.save(mesa));
		delete.addClickListener(e -> service.delete(mesa));
		cancel.addClickListener(e -> editMesa(mesa));
		
		zona.setItems(serviceZona.findAll());
		
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
			mesa = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(mesa);

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
