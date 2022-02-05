package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utilities.IconLoader;
/**
 * Action which saves current document to a new path.
 * @author renat
 *
 */
public class SaveAsDocumentAction extends LocalizableAction {
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "Save_As";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "Save_As_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "F12";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_F12;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "saveAs";
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param mdm multiple document model
	 * @param lp localization provider
	 */
	public SaveAsDocumentAction(MultipleDocumentModel mdm, ILocalizationProvider lp) {
		super( TRANSLATION_KEY, DESCRIPTION_KEY, lp);
		this.mdm = mdm;

		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}
	/**
	 * Saves current document to a new path.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		saveDocument( mdm.getCurrentDocument(), mdm);
	}
	/**
	 * Saves desired document to a new path.
	 * @param sdm document which we want to save
	 * @param mdm multiple document model
	 */
	public static void saveDocument( SingleDocumentModel sdm, MultipleDocumentModel mdm) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save document");
		LocalizationProvider lp = LocalizationProvider.getInstance();
		if (fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			String message = lp.getString("Save_aborted");
			String title = lp.getString( "Warning");
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
			return;
		}
		Path filePath = fc.getSelectedFile().toPath();

		if (Files.exists(filePath)) {
			String question = lp.getString("Overwrite_file_question");
			String title = lp.getString("Warning");
			Object[] options = { lp.getString("Yes"), lp.getString("No") };
			int result = JOptionPane.showOptionDialog(null, question, title, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, null);
			if (result != 0) {
				return;
			}
		}

		try {
			mdm.saveDocument( sdm, filePath);
		} catch (IllegalArgumentException exception) {
			String message = lp.getString("File_already_opened");
			String title = lp.getString( "Error");
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String message = lp.getString( "File_saved");
		String title = lp.getString( "Information");
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
