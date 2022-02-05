package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * JMenu which changes displayed text depending on currently set language.
 * 
 * @author renat
 *
 */
public class LJMenu extends JMenu {
	/**
	 * Constructor.
	 * 
	 * @param key key under which translated name for this JMenu is stored
	 * @param lp  localisation provider
	 */
	public LJMenu(String key, ILocalizationProvider lp) {

		lp.addLocalizationListener(() -> {
			setTranslation(key, lp);
		});

		setTranslation(key, lp);
	}

	/**
	 * Sets displayed text.
	 * 
	 * @param key key under which translation for this JMenu is stored
	 * @param lp  localisation provider
	 */
	private void setTranslation(String key, ILocalizationProvider lp) {
		String translation = lp.getString(key);
		setText(translation);
	}
}
