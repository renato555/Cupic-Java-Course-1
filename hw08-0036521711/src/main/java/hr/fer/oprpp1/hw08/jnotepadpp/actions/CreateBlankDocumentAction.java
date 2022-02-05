package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utilities.IconLoader;
/**
 * Action which creates a blank document.
 * @author renat
 *
 */
public class CreateBlankDocumentAction extends LocalizableAction {
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "New";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "New_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "control N";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_N;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "createNewFile";
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param mdm multiple document model
	 * @param lp localization provider
	 */
	public CreateBlankDocumentAction(MultipleDocumentModel mdm, ILocalizationProvider lp) {
		super( TRANSLATION_KEY, DESCRIPTION_KEY, lp);
		this.mdm = mdm;

		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}
	/**
	 * Creates a blank document.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		mdm.createNewDocument();
	}
}
