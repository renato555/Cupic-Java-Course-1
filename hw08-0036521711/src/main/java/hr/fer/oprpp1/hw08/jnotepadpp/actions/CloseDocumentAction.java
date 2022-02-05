package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utilities.IconLoader;

/**
 * Action which closes closes currently active document.
 * @author renat
 *
 */
public class CloseDocumentAction extends LocalizableAction {
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "Close";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "Close_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "control W";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_W;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "closeDocument";
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param mdm multiple document model
	 * @param lp localization provider
	 */
	public CloseDocumentAction(MultipleDocumentModel mdm, ILocalizationProvider lp) {
		super(TRANSLATION_KEY, DESCRIPTION_KEY, lp);
		this.mdm = mdm;

		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}
	/**
	 * Closes currently active document.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel sdm = mdm.getCurrentDocument();
		if (sdm.isModified()) {
			LocalizationProvider lp = LocalizationProvider.getInstance();
			String question = lp.getString("Not_saved_question");
			String title = lp.getString("Warning");
			Object[] options = { lp.getString("Yes"), lp.getString("No") };
			int result = JOptionPane.showOptionDialog(null, question, title, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, null);
			if (result == 0) {
				if (sdm.getFilePath() != null) {
					SaveDocumentAction.saveDocument( sdm, mdm);
				} else {
					SaveAsDocumentAction.saveDocument( sdm, mdm);
				}
			}
		}

		mdm.closeDocument(sdm);
	}

}
