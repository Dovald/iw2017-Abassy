package com.abassy.views;

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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class FamiliaProductoEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final FamiliaProductoRepository repository;

	private FamiliaProducto FamiliaProducto;

	/* Fields to edit properties in FamiliaProducto entity */
	Label titulo = new Label("FamiliaProducto");
	TextField nombre = new TextField("Nombre");

	/* Action buttons */
	Button save = new Button("Save", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancel", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Delete", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<FamiliaProducto> binder = new Binder<>(FamiliaProducto.class);

	@Autowired
	public FamiliaProductoEditor(FamiliaProductoRepository repository) {
		this.repository = repository;

		addComponents(nombre, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(FamiliaProducto));
		delete.addClickListener(e -> repository.delete(FamiliaProducto));
		cancel.addClickListener(e -> editFamiliaProducto(FamiliaProducto));
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
			FamiliaProducto = repository.findOne(c.getId());
		}
		else {
			FamiliaProducto = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(FamiliaProducto);

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
