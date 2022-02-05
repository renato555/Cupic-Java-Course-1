package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utilities.IconLoader;
/**
 * Action which copies current selected text.
 * @author renat
 *
 */
public class CopyTextAction extends LocalizableAction {
	/**
	 * This class delegates work to DefaultEditorKit.CopyAction. 
	 */
	private static final DefaultEditorKit.CopyAction DEFAULT_COPY_ACTION = new DefaultEditorKit.CopyAction();
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "Copy";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "Copy_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "control C";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_C;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "copy";
	
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param mdm multiple document model
	 * @param lp localization provider
	 */
	public CopyTextAction(MultipleDocumentModel mdm, ILocalizationProvider lp) {
		super( TRANSLATION_KEY, DESCRIPTION_KEY, lp);
		this.mdm = mdm;

		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}

	/**
	 * Copies currently selected text into the system clipboard.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		DEFAULT_COPY_ACTION.actionPerformed(e);
	}

}
