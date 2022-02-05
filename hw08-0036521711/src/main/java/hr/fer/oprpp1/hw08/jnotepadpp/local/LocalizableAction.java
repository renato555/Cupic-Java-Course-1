package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;
/**
 * Action which changes displayed text depending on currently set language.
 * 
 * @author renat
 *
 */
public abstract class LocalizableAction extends AbstractAction {
	/**
	 * Constructor.
	 * @param nameKey key under which translated name for this Action is stored
	 * @param descriptionKey key under which translated description for this Action is stored
	 * @param lp localisation provider
	 */
	public LocalizableAction( String nameKey, String descriptionKey, ILocalizationProvider lp) {
		
		lp.addLocalizationListener( ()->{
			setTranslation(nameKey, descriptionKey, lp);
		});
		
		setTranslation(nameKey, descriptionKey, lp);
	}
	/**
	 * Sets displayed text and description.
	 * @param nameKey key under which translated name for this Action is stored
	 * @param descriptionKey key under which translated description for this Action is stored
	 * @param lp localisation provider
	 */
	private void setTranslation( String nameKey, String descriptionKey, ILocalizationProvider lp) {
		String nameTranslation = lp.getString( nameKey);
		String descTranslation = lp.getString( descriptionKey);
		putValue( NAME, nameTranslation);
		putValue( SHORT_DESCRIPTION, descTranslation);
	}
}
