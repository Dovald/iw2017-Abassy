package com.abassy.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.security.SecurityUtils;
import com.abassy.services.ClienteService;
import com.abassy.services.LineaPedidoService;
import com.abassy.services.MesaService;
import com.abassy.services.PedidoService;
import com.abassy.services.ZonaService;
import com.abassy.tables.Cliente;
import com.abassy.tables.Local;
import com.abassy.tables.Mesa;
import com.abassy.tables.Pedido;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("deprecation")
@SpringComponent
@UIScope
public class PedidoEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final PedidoService service;

	private Pedido pedido;
	
	private Local local = SecurityUtils.getUserLogin().getLocal();
	
	// Bandera para comprobar si el pedido se encuentra ya en la base de datos.
	private boolean existePed;

	/* Fields to edit properties in User entity */
	Label titulo = new Label("Pedido");
	ComboBox<String> tipos = new ComboBox<>("Tipo");
	ComboBox<Zona> zonas = new ComboBox<>("Zona");
	ComboBox<Mesa> mesas = new ComboBox<>("Mesa");
	ComboBox<Cliente> telcliente = new ComboBox<>("Teléfono Cliente");
	
	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	Button close = new Button("Cerrar Pedido", VaadinIcons.INBOX);
	CssLayout actions = new CssLayout(save, cancel, delete, close);

	Binder<Pedido> binder = new Binder<>(Pedido.class);
	
	@Autowired
	public PedidoEditor(PedidoService service, ClienteService serviceCli, MesaService serviceMesa,
			ZonaService serviceZona, LineaPedidoService serviceLP) {
		this.service = service;
		
		tipos.setEmptySelectionAllowed(true);
		tipos.setItems("En local", "Llevar/Domicilio");
		
		addComponents(titulo, tipos, zonas, mesas, telcliente, actions);

		binder.forField(zonas).bind(Pedido::getZona, Pedido::setZona);
		
		binder.forField(mesas).bind(Pedido::getMesa, Pedido::setMesa);
		
		binder.forField(telcliente).bind(Pedido::getCliente, Pedido::setCliente);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		save.addClickListener(e -> {
			if(existePed) pedido.setLineaPedidos(serviceLP.findByPedido(pedido));
			if(tipos.getValue() != null)
			{
				if(tipos.getValue().equals("En local")) {
					if(zonas.getValue() != null && mesas.getValue() != null) {
						pedido.setCliente(null);
						service.save(local, pedido, false);
					}
					else Notification.show("Debes seleccionar una zona/mesa", Notification.TYPE_WARNING_MESSAGE);
				}
				else {
					if(telcliente.getValue() != null) {
						pedido.setMesa(null);
						pedido.setZona(null);
						service.save(local, pedido, false);
					}
					else Notification.show("Debes seleccionar un teléfono", Notification.TYPE_WARNING_MESSAGE);
				}
			}
			else Notification.show("Debes seleccionar un tipo de pedido", Notification.TYPE_WARNING_MESSAGE);
		});
		delete.addClickListener(e -> service.delete(local, pedido));
		cancel.addClickListener(e -> editPedido(pedido));
		close.addClickListener(e -> {
			pedido.setLineaPedidos(serviceLP.findByPedido(pedido));
			if(tipos.getValue() != null)
			{
				if(tipos.getValue().equals("En local")) {
					if(zonas.getValue() != null && mesas.getValue() != null) {
						pedido.setCliente(null);
						service.save(local, pedido, true);
					}
					else Notification.show("Debes seleccionar una zona/mesa", Notification.TYPE_WARNING_MESSAGE);
				}
				else {
					if(telcliente.getValue() != null) {
						pedido.setMesa(null);
						pedido.setZona(null);
						service.save(local, pedido, true);
					}
					else Notification.show("Debes seleccionar un teléfono", Notification.TYPE_WARNING_MESSAGE);
				}
			}
			else Notification.show("Debes seleccionar un tipo de pedido", Notification.TYPE_WARNING_MESSAGE);
		});
		
		zonas.setVisible(false);
		mesas.setVisible(false);
		telcliente.setVisible(false);
		
		zonas.setEmptySelectionAllowed(true);
		telcliente.setEmptySelectionAllowed(true);
		tipos.addValueChangeListener(e -> {
			if(e.getValue() != null){
				if(e.getValue().equals("En local")){
					zonas.setItems(serviceZona.findByLocal(local));
					zonas.setVisible(true);
					mesas.setVisible(true);
					telcliente.setVisible(false);
				}
				else{
					telcliente.setItems(serviceCli.findAll());
					telcliente.setVisible(true);
					zonas.setVisible(false);
					mesas.setVisible(false);
				}
			}
			else {
				telcliente.setVisible(false);
				mesas.setVisible(false);
				zonas.setVisible(false);
			}
		});
		
		mesas.setEmptySelectionAllowed(true);
		zonas.addValueChangeListener(e -> {
			mesas.setItems(serviceMesa.findByZona(e.getValue()));
			//mesas.setVisible(true);
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
			if(c.getCerrado()){
				setVisible(false);
				return;
			}
			// Find fresh entity for editing
			pedido = service.findOne(c.getId());
			existePed = true;
		}
		else {
			tipos.setValue(null);
			mesas.setValue(null);
			zonas.setValue(null);
			telcliente.setValue(null);
			pedido = c;
			existePed = false;
		}
		
		cancel.setVisible(persisted);
		close.setVisible(persisted);
		
		if(existePed){
			if(pedido.getMesa() != null){
				zonas.setSelectedItem(pedido.getMesa().getZona());
				mesas.setSelectedItem(pedido.getMesa());
				tipos.setSelectedItem("En local");
			}else {
				telcliente.setSelectedItem(pedido.getCliente());
				tipos.setSelectedItem("Llevar/Domicilio");
			}
		}
		
		binder.setBean(pedido);
		
		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save, delete or close is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
		close.addClickListener(e -> h.onChange());
	}

}
