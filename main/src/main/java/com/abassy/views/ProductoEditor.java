package com.abassy.views;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("deprecation")
@SpringComponent
@UIScope
public class ProductoEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;


	private Producto product;
	
	private boolean bandera;

	Label title = new Label("Producto");
	ComboBox<FamiliaProducto> familia_producto = new ComboBox<>("Familia Producto");
	TextField nombre = new TextField("Nombre");
	TextField precio = new TextField("Precio");
	TwinColSelect<Producto> select = new TwinColSelect<>("Componentes:");

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Producto> binder = new Binder<>(Producto.class);
	
	@Autowired
	public ProductoEditor(ProductoService service, FamiliaProductoService serviceFP) {

		addComponents(title, familia_producto, nombre, precio, select, actions);
		
		select.setWidth("500px");

		binder.forField(familia_producto)
		  .asRequired("No puede quedar en blanco")
		  .bind(Producto::getFamiliaProducto, Producto::setFamiliaProducto);
		
		binder.forField(nombre)
		  .asRequired("No puede quedar en blanco")
		  .bind(Producto::getNombre, Producto::setNombre);
		
		binder.forField(select)
		  .bind(Producto::getProductos, Producto::setProductos);
		
		binder.forField(precio)
		  .asRequired("No puede quedar en blanco")
		  .withValidator(str -> str != null && Float.parseFloat(str) >= 0.0f, "Número introducido Inválido")
		  .withNullRepresentation(Float.toString(0.0f))
		  .withConverter(
		    new StringToFloatConverter("Debes introducir un número") {
				private static final long serialVersionUID = 1L;
				protected java.text.NumberFormat getFormat(Locale locale) {
		        NumberFormat format = super.getFormat(Locale.forLanguageTag("US"));
		        format.setParseIntegerOnly(false);
		        return format;
				};
		    })
		  .bind(Producto::getPrecio, Producto::setPrecio);		

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
				List<Producto> prod = new ArrayList<Producto>(service.findByNombreStartsWithIgnoreCase(nombre.getValue()));
				for(int i = 0; i < prod.size(); i++) {
					if(prod.get(i).getNombre().equals(nombre.getValue()))
						if(prod.get(i).getId() != product.getId()) bandera = false;
				}
				if(bandera) {
					service.save(product);
				}
				else Notification.show("El producto ya existe", Notification.TYPE_WARNING_MESSAGE);
			}
			else Notification.show("Revise los campos del formulario", Notification.TYPE_WARNING_MESSAGE);
		});
		delete.addClickListener(e -> service.delete(product));
		cancel.addClickListener(e -> editProducto(product,service));
		
		familia_producto.setItems(serviceFP.findAll());
		select.setItems(service.findAll());
	
		
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editProducto(Producto c,ProductoService service) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			product = service.findOne(c.getId());
			select.setItems(product.getProductos());

		}
		else {
			product = c;
		}
		cancel.setVisible(persisted);
		
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
