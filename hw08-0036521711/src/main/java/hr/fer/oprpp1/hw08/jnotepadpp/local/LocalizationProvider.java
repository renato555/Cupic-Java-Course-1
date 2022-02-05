package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ILocalizationProvider implementation.
 * @author renat
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{
	/**
	 * Currently set language.
	 */
	private String language;
	/**
	 * Map of all translated words.
	 */
	private ResourceBundle bundle;
	/**
	 * Singleton instance. 
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	/**
	 * Constructor.
	 * Initialises all attributes.
	 */
	private LocalizationProvider() {
		language = "English";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
	}
	/**
	 * @return instance of this class
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	/**
	 * Sets a new language and notifies listeners.
	 * @param language new language.
	 */
	public void setLanguage( String language) {
		if( !this.language.equals(language)) {
			this.language = language;
			Locale locale = Locale.forLanguageTag(language);
			bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
			fire();
		}
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getLanguage() {
		return language;
	}
}
