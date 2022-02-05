package hr.fer.oprpp1.hw08.jnotepadpp.components;

import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Document;
import javax.swing.text.Element;

import hr.fer.oprpp1.hw08.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * A component that displays information about current document.
 * @author renat
 */
public class StatusBar extends JComponent{
	/**
	 * Displays lenght of a document.
	 */
	private JLabel length = new JLabel();
	/**
	 * Contains a word 'length' in a currently selected language.
	 */
	private String lengthText;
	/**
	 * Length of a document.
	 */
	private int lengthInt;
	/**
	 * Displays current line, column and selected text.
	 */
	private JLabel carretInfo = new JLabel();
	/**
	 * Contains a word 'line' in a currently selected language.
	 */
	private String lnText;
	/**
	 * Current line.
	 */
	private int ln;
	/**
	 * Contains a word 'column' in a currently selected language.
	 */
	private String colText;
	/**
	 * Current column.
	 */
	private int col;
	/**
	 * Contains a word 'selected' in a currently selected language.
	 */
	private String selText;
	/**
	 * Length of a selected text.
	 */
	private int sel;
	/**
	 * A component which displays time.
	 */
	private Clock clock = new Clock( SwingConstants.RIGHT);
	/**
	 * Constructor.
	 * Initialises all attributes and adds its listener to LocalizationProvider.
	 * @param flp localization provider
	 */
	public StatusBar( ILocalizationProvider flp) {
		setLayout( new GridLayout(1,3));
		setBorder( BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		
		add( length, "1,1");
		add( carretInfo, "1,2");
		add( clock, "1,3");
		
		flp.addLocalizationListener( ()->{
			updateStatusBarWords( flp);
			updateGUI();
		});
		
		updateStatusBarWords( flp);
	}
	/**
	 * Updates the translation of displayed words.
	 * @param flp localizationProvider
	 */
	private void updateStatusBarWords( ILocalizationProvider flp) {
		lengthText = flp.getString( "length");
		lnText = flp.getString( "Ln");
		colText = flp.getString( "Col");
		selText = flp.getString( "Sel");
	}
	/**
	 * Refreshes displayed information.
	 */
	private void updateGUI() {
		length.setText( lengthText + ": " + lengthInt);
		carretInfo.setText( String.format("%s: %d %s: %d %s: %d", lnText, ln+1, colText, col+1, selText, sel));
	}
	/**
	 * Clears displayed information.
	 */
	private void clearGUI() {
		length.setText( "");
		carretInfo.setText( "");
	}
	/**
	 * Caret listener which updates currently displayed information.
	 */
	private CaretListener caretListener = new CaretListener() {
		@Override
		public void caretUpdate(CaretEvent e) {
			updateStatusBarValues( (JTextArea) e.getSource());
			updateGUI();
		}
	};
	/**
	 * Updates information about the textArea
	 * @param area current textArea
	 */
	private void updateStatusBarValues( JTextArea area) {
		Document doc = area.getDocument();
		// length
		lengthInt = doc.getLength();
		length.setText( "length: " + lengthInt);
		
		// carretInfo
		int pos = area.getCaretPosition();
		Element root = doc.getDefaultRootElement();
		// ln
		ln = root.getElementIndex( pos);
		// col
		col = pos - root.getElement( ln).getStartOffset();
		// Sel
		sel = Math.abs( area.getSelectionStart() - area.getSelectionEnd());
	}
	/**
	 * Removes the caret listener from the previous model and adds it to a new one.
	 * Updates GUI.
	 */
	private MultipleDocumentListener mdl = new MultipleDocumentListener() {
		/**
		 * Removes the caret listener from the previous model and adds it to a new one.
		 * Updates GUI.
		 */
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if( previousModel != null) {
				previousModel.getTextComponent().removeCaretListener( caretListener);
			}
			if( currentModel != null) {
				currentModel.getTextComponent().addCaretListener(caretListener);
				updateStatusBarValues( currentModel.getTextComponent());
				updateGUI();
			}else {
				clearGUI();
			}
		}

		@Override
		public void documentAdded(SingleDocumentModel model) {
		}

		@Override
		public void documentRemoved(SingleDocumentModel model) {
		}
		
	};
	
	public MultipleDocumentListener getMultipleDocumentListener() {
		return mdl;
	}
	
	public Clock getClock() {
		return clock;
	}
	
}	
