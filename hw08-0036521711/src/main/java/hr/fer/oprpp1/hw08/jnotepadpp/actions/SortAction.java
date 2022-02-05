package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

/**
 * Action which sorts selected lines.
 * @author renat
 *
 */
public abstract class SortAction extends LocalizableAction {
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * @param nameKey key at which this action's name is stored.
	 * @param descriptionKey key at which this action's description is stored.
	 * @param mdm multiple document model
	 * @param lp localization provider
	 */
	public SortAction(String nameKey, String descriptionKey, MultipleDocumentModel mdm, ILocalizationProvider lp) {
		super(nameKey, descriptionKey, lp);
		this.mdm = mdm;
	}
	/**
	 * Sorts selected lines.
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
			
			List<String> selectedLines = getSelectedLines(doc, startRowIndex, endRowIndex);
			sort( selectedLines);
			insertLines( doc, selectedLines, startRowOffset);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * Sorts selected lines
	 * @param selectedLines a list filled with lines that were selected
	 */
	abstract protected void sort( List<String> selectedLines);
	/**
	 * Fills a list with selected lines.
	 * @param doc doc that contains selected text.
	 * @param startRowIndex index of the first selected line
	 * @param endRowIndex index of the last selected line
	 * @return a list filled with lines that were selected 
	 * @throws BadLocationException if remove text fails
	 */
	private List<String> getSelectedLines( Document doc, int startRowIndex, int endRowIndex) throws BadLocationException{
		List<String> result = new LinkedList<>();
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
	 * Prints selected lines in sorted order.
	 * @param doc document onto which we print lines
	 * @param lines sorted lines
	 * @param startOffset determines where to start inserting
	 * @throws BadLocationException if inserting fails
	 */
	private void insertLines( Document doc, List<String> lines, int startOffset) throws BadLocationException{
		for( String l : lines) {
			doc.insertString( startOffset, l, null);
			startOffset += l.length();
		}
	}
}
