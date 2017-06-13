package com.abassy.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.tables.*;
import com.abassy.services.FamiliaProductoService;

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
public class FamiliaProductoEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final FamiliaProductoService service;

	private FamiliaProducto familiaProducto;
	
	private boolean bandera;

	Label titulo = new Label("Familia Producto");
	TextField nombre = new TextField("Nombre");

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<FamiliaProducto> binder = new Binder<>(FamiliaProducto.class);

	@Autowired
	public FamiliaProductoEditor(FamiliaProductoService service) {
		this.service = service;

		addComponents(titulo, nombre, actions);

		binder.forField(nombre)
		  .asRequired("No puede quedar en blanco")
		  .bind(FamiliaProducto::getNombre, FamiliaProducto::setNombre);

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
				List<FamiliaProducto> famProd = new ArrayList<FamiliaProducto>(service.findByNombreStartsWithIgnoreCase(nombre.getValue()));
				for(int i = 0; i < famProd.size(); i++) {
					if(famProd.get(i).getNombre().equals(nombre.getValue()))
					if(famProd.get(i).getId() != familiaProducto.getId()) bandera = false;
				}
				if(bandera) {
					service.save(familiaProducto);
				}
				else Notification.show("La familia de producto ya existe", Notification.TYPE_WARNING_MESSAGE);
			}
			else Notification.show("Revise los campos del formulario", Notification.TYPE_WARNING_MESSAGE);
		});
		delete.addClickListener(e -> service.delete(familiaProducto));
		cancel.addClickListener(e -> editFamiliaProducto(familiaProducto));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editFamiliaProducto(FamiliaProducto c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			familiaProducto = service.findOne(c.getId());
		}
		else {
			familiaProducto = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(familiaProducto);

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
