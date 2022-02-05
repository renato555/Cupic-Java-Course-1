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
 * Action which inverts selected text.
 * @author renat
 *
 */
public class InvertCaseAction extends LocalizableAction {
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "Invert_case";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "Invert_case_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "control T";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_T;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "invertcase";
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param mdm multiple document model
	 * @param lp localization provider
	 */
	public InvertCaseAction( MultipleDocumentModel mdm, ILocalizationProvider flp) {
		super( TRANSLATION_KEY, DESCRIPTION_KEY, flp);
		this.mdm = mdm;
		
		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}
	/**
	 * Inverts selected text.
	 * Turns upper case letters into lower case letters.
	 * Turns lower cases letters into upper case letters.
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
			doc.insertString( offset, changeCase( content), null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * Inverts selected text.
	 * Turns upper case letters into lower case letters.
	 * Turns lower cases letters into upper case letters.
	 * @param content text which we want to invert
	 * @return inverted text
	 */
	private String changeCase( String content) {
		char[] characters = content.toCharArray();
		for( int i = 0; i < characters.length; ++i) {
			char c = characters[i];
			if( Character.isUpperCase( c)) {
				characters[i] = Character.toLowerCase(c);
			}else if( Character.isLowerCase( c)){
				characters[i] = Character.toUpperCase(c);
			}
		}
		
		return new String( characters);
	}
}
