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
 * Action which shows statistical information about the current document.
 * @author renat
 *
 */
public class StatisticalInfoAction extends LocalizableAction {/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Key at which this action's name is stored.
	 */
	private static final String TRANSLATION_KEY = "Stats";
	/**
	 * Key at which this action's description is stored.
	 */
	private static final String DESCRIPTION_KEY = "Stats_desc";
	/**
	 * Keyboard shortcut.
	 */
	private static final String ACCELERATOR_KEY_VALUE = "control I";
	/**
	 * Displayed shortcut.
	 */
	private static final int MNEMONIC_KEY_VALUE = KeyEvent.VK_I;
	/**
	 * This action's icon name.
	 */
	private static final String ICON_NAME = "statistics";
	/**
	 * Constructor.
	 * Initialises all values.
	 * @param mdm multiple document model
	 * @param lp localization provider
	 */
	public StatisticalInfoAction(MultipleDocumentModel mdm, ILocalizationProvider lp) {
		super( TRANSLATION_KEY, DESCRIPTION_KEY, lp);
		this.mdm = mdm;

		// values
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACCELERATOR_KEY_VALUE));
		putValue(MNEMONIC_KEY, MNEMONIC_KEY_VALUE);
		putValue(SMALL_ICON, IconLoader.loadImageIcon(ICON_NAME));
	}

	/**
	 * Shows statistical information about the current document
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel sdm = mdm.getCurrentDocument();
		String text = sdm.getTextComponent().getText();
		int n = text.length();
		int ukupnoPraznoLen = 0;
		int brojLinija = 0;
		for (int i = 0; i < n; ++i) {
			char c = text.charAt(i);
			if (c == '\n') {
				ukupnoPraznoLen++;
				brojLinija++;
			}
			if (c == ' ' || c == '\t') {
				ukupnoPraznoLen++;
			}
		}
		
		LocalizationProvider lp = LocalizationProvider.getInstance();
		String statInfoMsg = lp.getString( "Stat_msg"); 
		String statInfoFilled = String.format(statInfoMsg, n, (n-ukupnoPraznoLen), brojLinija);
		String title = lp.getString( "Information");
		JOptionPane.showMessageDialog(null, statInfoFilled, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
