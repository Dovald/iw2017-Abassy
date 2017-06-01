package com.abassy.views;

import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.services.FamiliaProductoService;
import com.abassy.services.ProductoService;
import com.abassy.tables.FamiliaProducto;
import com.abassy.tables.Producto;
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

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX. See e.g. AbstractForm in Viritin
 * (https://vaadin.com/addon/viritin).
 */

@SpringComponent
@UIScope
public class ProductoEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final ProductoService service;
	
	private final FamiliaProductoService serviceFP;

	private Producto product;

	Label title = new Label("Nuevo Producto");
	ComboBox<FamiliaProducto> familia_producto = new ComboBox<>("Familia Producto");
	TextField nombre = new TextField("Nombre");
	TextField precio = new TextField("Precio");
	

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Producto> binder = new Binder<>(Producto.class);

	@Autowired
	public ProductoEditor(ProductoService service, FamiliaProductoService serviceFP) {
		this.service = service;
		this.serviceFP = serviceFP;

		addComponents(title, familia_producto, nombre, precio, actions);
		
		familia_producto.setItems(serviceFP.findAll());

		binder.forField(nombre).bind(Producto::getNombre, Producto::setNombre);
		binder.forField(precio)
		  .withValidator(str -> str != null && Float.parseFloat(str) >= 0.0f, "Número introducido Inválido")
		  .withNullRepresentation(Float.toString(0.0f))
		  .withConverter(
		    new StringToFloatConverter("Debes introducir un número") {
				private static final long serialVersionUID = 1L;
				protected java.text.NumberFormat getFormat(Locale locale) {
		        NumberFormat format = super.getFormat(locale);
		        format.setParseIntegerOnly(false);
		        return format;
				};
		    })
		  .bind(Producto::getPrecio, Producto::setPrecio);
		binder.forField(familia_producto).bind(Producto::getFamiliaProducto, Producto::setFamiliaProducto);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> service.save(product));
		delete.addClickListener(e -> service.delete(product));
		cancel.addClickListener(e -> editProducto(product));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editProducto(Producto c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			product = service.findOne(c.getId());
		}
		else {
			product = c;
		}
		cancel.setVisible(persisted);

		// Bind User properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(product);

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
