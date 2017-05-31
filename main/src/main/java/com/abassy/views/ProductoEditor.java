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

	private final LocalRepository repository;

	/**
	 * The currently edited Local
	 */
	private Local local;

	/* Fields to edit properties in User entity */
	TextField direccion = new TextField("Dirección");
	TextField ciudad = new TextField("Ciudad");

	/* Action buttons */
	Button save = new Button("Save", VaadinIcons.CHECK_CIRCLE);
	Button cancel = new Button("Cancel", VaadinIcons.CLOSE_SMALL);
	Button delete = new Button("Delete", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Local> binder = new Binder<>(Local.class);

	@Autowired
	public ProductoEditor(LocalRepository repository) {
		this.repository = repository;

		addComponents(direccion, ciudad, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(local));
		delete.addClickListener(e -> repository.delete(local));
		cancel.addClickListener(e -> editLocal(local));
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
			local = repository.findOne(c.getId());
		}
		else {
			local = c;
		}
		cancel.setVisible(persisted);

		// Bind User properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(local);

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
