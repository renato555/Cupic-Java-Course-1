package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.KeyEvent;
import java.text.Collator;
import java.util.List;
import java.util.Locale;

import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utilities.IconLoader;
/**
 * Action which sorts selected lines in descending order.
 * @author renat
 *
 */
public class SortDescendingAction extends SortAction {
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "Descending";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "Descending_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "control H";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_H;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "descending";
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param mdm multiple document model
	 * @param lp localization provider
	 */
	public SortDescendingAction( MultipleDocumentModel mdm, ILocalizationProvider flp) {
		super( TRANSLATION_KEY, DESCRIPTION_KEY, mdm, flp);
		
		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}

	@Override
	protected void sort(List<String> selectedLines) {
		Locale locale = new Locale( LocalizationProvider.getInstance().getLanguage());
		selectedLines.sort( Collator.getInstance( locale).reversed());
	}

	
}