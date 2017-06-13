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
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("deprecation")
@SpringComponent
@UIScope
public class CierreCajaEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final CierreCajaService service;
	
	private CierreCaja cierre;

	private Local local = SecurityUtils.getUserLogin().getLocal();
	
	Binder<CierreCaja> binder = new Binder<>(CierreCaja.class);

	Label titulo = new Label("Cerrar Caja");
	Label imp = new Label();
	TextField importe_real = new TextField("Importe Real");

	/* Action buttons */
	Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
	CssLayout actions = new CssLayout(save);

	@Autowired
	public CierreCajaEditor(CierreCajaService service) {
		
		this.service = service;
		
		addComponents(titulo, imp, importe_real, actions);
	
		binder.forField(importe_real)
		  .asRequired("Debe introducir el importe real")
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
		  .bind(CierreCaja::getImporte_real, CierreCaja::setImporte_real);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {
			if(binder.isValid()) service.cerrarcajafinal(local, service.cerrarcaja(local), cierre);
			else Notification.show("Debe introducir el importe real", Notification.TYPE_WARNING_MESSAGE);
		});
	
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
		
		imp.setValue("Importe: " + Float.toString(service.cerrarcaja(local)));

		binder.setBean(cierre);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
	}

}
