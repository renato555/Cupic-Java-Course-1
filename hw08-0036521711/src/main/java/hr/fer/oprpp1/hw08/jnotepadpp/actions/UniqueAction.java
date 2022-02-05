package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utilities.IconLoader;

/**
 * Action which deletes duplicate lines from selected lines.
 * @author renat
 */
public class UniqueAction extends LocalizableAction{/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "Unique";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "Unique_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "control Q";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_Q;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "unique";
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param mdm multiple document model
	 * @param lp localization provider
	 */
	public UniqueAction(MultipleDocumentModel mdm, ILocalizationProvider lp) {
		super( TRANSLATION_KEY, DESCRIPTION_KEY, lp);
		this.mdm = mdm;

		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}
	/**
	 * Deletes duplicate lines from selected lines.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JTextComponent text = mdm.getCurrentDocument().getTextComponent();
			
			int offsetStart = Math.min(text.getCaret().getDot(), text.getCaret().getMark());
			int offsetEnd = Math.max(text.getCaret().getDot(), text.getCaret().getMark());
			
			Document doc = text.getDocument();
			Element root = doc.getDefaultRootElement();
			int startRowIndex = root.getElementIndex( offsetStart);
			int endRowIndex = root.getElementIndex(offsetEnd);
			int startRowOffset = root.getElement(startRowIndex).getStartOffset();
		
			Set<String> selectedLines = getSelectedLines(doc, startRowIndex, endRowIndex);
			insertLines( doc, selectedLines, startRowOffset);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	/**
	 * Fills a list with selected lines.
	 * @param doc doc that contains selected text.
	 * @param startRowIndex index of the first selected line
	 * @param endRowIndex index of the last selected line
	 * @return a list filled with lines that were selected 
	 * @throws BadLocationException if remove text fails
	 */
	private Set<String> getSelectedLines( Document doc, int startRowIndex, int endRowIndex) throws BadLocationException{
		Set<String> result = new LinkedHashSet<>();
		Element root = doc.getDefaultRootElement();
		for( int i = 0, n = endRowIndex - startRowIndex; i <= n; ++i) {
			Element elem = root.getElement( startRowIndex);
			int start = elem.getStartOffset();
			int end = elem.getEndOffset();
			String line = doc.getText(start, end-start); // iz nekog razloga vraca \n na zadnju liniju u dokumenu iako ga nema
			result.add( line);
			if( startRowIndex != root.getElementCount()-1) {
				doc.remove( start, line.length());				
			}else {
				doc.remove( start, line.length() - 1);	
			}
		}
		
		return result;
	}
	/**
	 * Prints remaining lines.
	 * @param doc document onto which we print lines
	 * @param lines sorted lines
	 * @param startOffset determines where to start inserting
	 * @throws BadLocationException if inserting fails
	 */
	private void insertLines( Document doc, Set<String> lines, int startOffset) throws BadLocationException{
		for( String l : lines) {
			doc.insertString( startOffset, l, null);
			startOffset += l.length();
		}
	}
}
