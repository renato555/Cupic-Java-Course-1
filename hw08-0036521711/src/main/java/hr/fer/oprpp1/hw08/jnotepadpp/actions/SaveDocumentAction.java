package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utilities.IconLoader;

/**
 * Action which saves current document.
 * 
 * @author renat
 *
 */
public class SaveDocumentAction extends LocalizableAction {
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "Save";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "Save_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "control S";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_S;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "save";

	/**
	 * Enables action if the active document has a save path.
	 * 
	 * @author renat
	 */
	public static class EnabledIfPathPresentListener implements SingleDocumentListener {
		private Action action;

		public EnabledIfPathPresentListener(Action action) {
			this.action = action;
		}

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
		}

		/**
		 * Enables action if the active document has a save path.
		 */
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			if (model == null || model.getFilePath() == null) {
				action.setEnabled(false);
			} else {
				action.setEnabled(true);
			}
		}

	};

	/**
	 * Constructor. Initialises all values.
	 * 
	 * @param mdm multiple document model
	 * @param lp  localization provider
	 */
	public SaveDocumentAction(MultipleDocumentModel mdm, ILocalizationProvider lp) {
		super(TRANSLATION_KEY, DESCRIPTION_KEY, lp);
		this.mdm = mdm;

		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}

	/**
	 * Saves current document.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		saveDocument(mdm.getCurrentDocument(), mdm);
	}

	/**
	 * Saves desired document
	 * @param sdm document that we want to save
	 * @param mdm multiple document model
	 */
	public static void saveDocument(SingleDocumentModel sdm, MultipleDocumentModel mdm) {
		mdm.saveDocument(sdm, null);

		LocalizationProvider lp = LocalizationProvider.getInstance();
		String message = lp.getString("File_saved");
		String title = lp.getString("Information");
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
