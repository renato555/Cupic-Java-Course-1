package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.components.Clock;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utilities.IconLoader;
/**
 * Action which closes the application.
 * @author renat
 *
 */
public class ExitAction extends LocalizableAction {
	/**
	 * Application window.
	 */
	private JFrame window;
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "Exit";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "Exit_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "control E";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_E;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "exit";
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param window application window
	 * @param mdm multiple document model
	 * @param clock component which displays current time.
	 * @param lp localization provider
	 */
	public ExitAction(JFrame window, MultipleDocumentModel mdm, ILocalizationProvider lp) {
		super( TRANSLATION_KEY, DESCRIPTION_KEY, lp);
		this.window = window;
		this.mdm = mdm;

		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}

	/**
	 * Closes application.
	 * Asks user to save modified documents before exiting.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Iterator<SingleDocumentModel> iterator = mdm.iterator();
		while (iterator.hasNext()) {
			SingleDocumentModel sdm = iterator.next();
			if (sdm.isModified()) {
				// jel treba spremit?
				LocalizationProvider lp = LocalizationProvider.getInstance();
				String fileName = sdm.getFilePath() == null ? lp.getString("unnamed") : sdm.getFilePath().getFileName().toString();
				String question = lp.getString("Not_saved_question") + "\n" + fileName;
				String title = lp.getString("Warning");
				Object[] options = { lp.getString("Yes"), lp.getString("No"), lp.getString("Cancel") };
				int reply = JOptionPane.showOptionDialog(null, question, title,
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
				if (reply == 0) {
					if (sdm.getFilePath() != null) {
						SaveDocumentAction.saveDocument(sdm, mdm);
					} else {
						SaveAsDocumentAction.saveDocument(sdm, mdm);
					}
				} else if (reply == 2) {
					return;
				}
			}
		}
		
		window.dispose();
	}
}
