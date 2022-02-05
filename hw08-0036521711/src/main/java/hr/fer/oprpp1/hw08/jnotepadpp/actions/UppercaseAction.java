package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utilities.IconLoader;
/**
 * Action which transforms selected letters into upper case.
 * @author renat
 *
 */
public class UppercaseAction extends LocalizableAction {
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "Uppercase";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "Uppercase_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "control U";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_U;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "uppercase";
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param mdm multiple document model
	 * @param lp localization provider
	 */
	public UppercaseAction(MultipleDocumentModel mdm, ILocalizationProvider flp) {
		super(TRANSLATION_KEY, DESCRIPTION_KEY, flp);
		this.mdm = mdm;
		
		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}
	/**
	 * Transforms selected letters into upper case.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea textArea = mdm.getCurrentDocument().getTextComponent();
		Document doc = textArea.getDocument();
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());

		try {
			String content = textArea.getText(offset, len);
			doc.remove(offset, len);
			doc.insertString( offset, content.toUpperCase(), null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

}
