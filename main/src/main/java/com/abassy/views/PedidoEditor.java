package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.tables.*;
import com.abassy.security.SecurityUtils;
import com.abassy.services.*;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class PedidoEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final PedidoService service;
	private final ClienteService serviceCli;
	private final MesaService serviceMesa;
	private final ZonaService serviceZona;

	private Pedido Pedido;
	
	private Cliente cliente;

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Nuevo Pedido");
	NativeSelect<String> tipos = new NativeSelect<>("Tipo");
	ComboBox<Zona> zonas = new ComboBox<>("Zona");
	ComboBox<Mesa> mesas = new ComboBox<>("Mesa");
	TextField telcliente = new TextField("Teléfono Cliente");
	
	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Pedido> binder = new Binder<>(Pedido.class);

	@Autowired
	public PedidoEditor(PedidoService service, ClienteService serviceCli, MesaService serviceMesa,
			ZonaService serviceZona) {
		this.service = service;
		this.serviceCli = serviceCli;
		this.serviceMesa = serviceMesa;
		this.serviceZona = serviceZona;
		
		tipos.setItems("En local", "Llevar/Domicilio");
		
		addComponents(titulo, tipos, zonas, mesas, telcliente, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> service.save(Pedido));
		delete.addClickListener(e -> service.delete(Pedido));
		cancel.addClickListener(e -> editPedido(Pedido));
		
		zonas.setVisible(false);
		mesas.setVisible(false);
		telcliente.setVisible(false);
		
		tipos.addSelectionListener(e -> {
			if(tipos.getValue().equals("En local")){
				zonas.setItems(SecurityUtils.getUserLogin().getLocal().getZonas());
				zonas.setVisible(true);
			}
			else{
				telcliente.setVisible(true);
			}
		});
		
		zonas.addSelectionListener(e -> {
			mesas.setItems(serviceMesa.findByZona(e.getValue()));
			mesas.setVisible(true);
		});
		
		telcliente.addValueChangeListener(e -> {
			if(serviceCli.findByTelefono(telcliente.getValue()) != null) {
				cliente = serviceCli.findByTelefono(telcliente.getValue());
				Pedido.setCliente(cliente);
			}
			
		});
		
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editPedido(Pedido c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			Pedido = service.findOne(c.getId());
		}
		else {
			Pedido = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(Pedido);
		
		if(c.getMesa() != null){
			zonas.setSelectedItem(c.getMesa().getZona());
			mesas.setSelectedItem(c.getMesa());
			tipos.setSelectedItem("En local");
		}else{
			telcliente.setValue(c.getCliente().getTelefono());
			tipos.setSelectedItem("Llevar/Domicilio");
		}
		
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
