package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
/**
 * Action which sets the language.
 * @author renat
 *
 */
public class SetLanguageAction extends AbstractAction {
	/**
	 * Language which this action sets.
	 */
	private String language;
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param language language which this action sets.
	 */
	public SetLanguageAction( String language) {
		this.language = language;
		
		// values
		putValue( NAME, language);
	}
	/**
	 * Sets the language.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(language);
		
		LocalizationProvider lp = LocalizationProvider.getInstance();
		String message = lp.getString( "Language_changed");
		String title = lp.getString( "Information");
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

}
