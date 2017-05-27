package com.abassy.views;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.abassy.tables.*;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class LocalEditor extends VerticalLayout {

	private static final long serialVersionUID = -3086938115277081533L;

	private final LocalRepository repository;
	
	private final ZonaRepository repositoryzona;

	private Local Local;

	/* Fields to edit properties in Local entity */
	Label titulo = new Label("Local");
	TextField direccion = new TextField("Dirección");
	TextField ciudad = new TextField("Ciudad");
	NativeSelect<String> zona;

	/* Action buttons */
	Button save = new Button("Save", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancel", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Delete", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Local> binder = new Binder<>(Local.class);

	@Autowired
	public LocalEditor(LocalRepository repository, ZonaRepository repositoryzona) {
		this.repository = repository;
		this.repositoryzona=repositoryzona;
		
		//buscamos zonas
		Collection<Zona> zonas = (Collection<Zona>) repositoryzona.findAll();
		ArrayList<String> zonaList = new ArrayList<String>();
		for(Zona z: zonas){
			System.out.println(z.getNombre());
			zonaList.add(z.getNombre());
		}
		zona = new NativeSelect<>("Selecciona zona:", zonaList);
		zona.setEmptySelectionAllowed(false);
		
		addComponents(direccion, ciudad, zona, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(Local));
		delete.addClickListener(e -> repository.delete(Local));
		cancel.addClickListener(e -> editLocal(Local));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editLocal(Local c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			Local = repository.findOne(c.getId());
		}
		else {
			Local = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(Local);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		direccion.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
