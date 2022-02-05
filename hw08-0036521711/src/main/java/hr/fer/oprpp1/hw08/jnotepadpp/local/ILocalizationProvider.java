package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Represents a map with all translated words.
 * @author renat
 *
 */
public interface ILocalizationProvider {
	/**
	 * Adds a new listeners.
	 * @param l new listener
	 */
	void addLocalizationListener( ILocalizationListener l);
	/**
	 * Removes a listener.
	 * @param l listener
	 */
	void removeLocalizationListener( ILocalizationListener l);
	/**
	 * @param key key under which the translated word is stored
	 * @return translated word under key
	 */
	String getString(String key);
	/**
	 * @return currently set language
	 */
	String getLanguage();
}
