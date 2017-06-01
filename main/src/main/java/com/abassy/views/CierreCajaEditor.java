package com.abassy.views;

import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.security.SecurityUtils;
import com.abassy.services.CierreCajaService;
import com.abassy.tables.CierreCaja;
import com.abassy.tables.Local;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToFloatConverter;
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

@SpringComponent
@UIScope
public class CierreCajaEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final CierreCajaService service;
	
	//private final LocalService localRepository;
	
	private CierreCaja cierre;

	private Local local = SecurityUtils.getUserLogin().getLocal();
	
	Binder<CierreCaja> binder = new Binder<>(CierreCaja.class);

	/* Fields to edit properties in Local entity */
	Label titulo = new Label("Cerrar Caja");
	Label imp = new Label();
	TextField importe_real = new TextField("Importe Real");

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancelar", VaadinIcons.CLOSE_SMALL);
	//Button delete = new Button("Eliminar", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel);

	@Autowired
	public CierreCajaEditor(CierreCajaService service) {
		
		this.service = service;
		
		//this.localRepository = repository;
		
		final Float impor = service.cerrarcaja(local);
		
		imp.setValue("Importe: " + Float.toString(impor));
		
		//buscamos zonas
		/*Collection<Zona> zonas = (Collection<Zona>) repositoryzona.findAll();
		ArrayList<String> zonaList = new ArrayList<String>();
		for(Zona z: zonas){
			System.out.println(z.getNombre());
			zonaList.add(z.getNombre());
		}
		zona = new NativeSelect<>("Selecciona zona:", zonaList);
		zona.setEmptySelectionAllowed(false);*/
		
		addComponents(titulo, imp, importe_real, actions);
		
		// bind using naming convention
		//binder.bindInstanceFields(this);
		
		binder.forField(importe_real)
		  .withValidator(str -> str != null && Float.parseFloat(str) >= 0.0f, "Número introducido Inválido")
		  .withNullRepresentation(Float.toString(0.0f))
		  .withConverter(
		    new StringToFloatConverter("Debes introducir un número") {
				private static final long serialVersionUID = 1L;
				protected java.text.NumberFormat getFormat(Locale locale) {
		        NumberFormat format = super.getFormat(locale);
		        format.setParseIntegerOnly(false);
		        format.setGroupingUsed(false);
		        return format;
				};
		    })
		  .bind(CierreCaja::getImporte_real, CierreCaja::setImporte_real);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {
			
			service.cerrarcajafinal(local, impor, cierre);
		});
		//Float.parseFloat(importe_real.getValue())));
		//delete.addClickListener(e -> repository.delete(Local));
		cancel.addClickListener(e -> editCaja(cierre));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editCaja(CierreCaja c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			cierre = service.findOne(c.getId());
		} else{
			cierre = c;
		}
		
		cancel.setVisible(persisted);

		binder.setBean(cierre);

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
		//delete.addClickListener(e -> h.onChange());
	}

}
