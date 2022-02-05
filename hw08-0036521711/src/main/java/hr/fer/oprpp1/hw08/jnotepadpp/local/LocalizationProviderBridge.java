package hr.fer.oprpp1.hw08.jnotepadpp.local;
/**
 * Localisation provider bridge.
 * Handles listeners that have references to GUI components. 
 * @author renat
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * A map of all translated words.
	 */
	private ILocalizationProvider localizationProvider;
	/**
	 * Listens on language changes.
	 */
	private ILocalizationListener localizationListener;
	/**
	 * true if the bridge is connected, false otherwise.
	 */
	private boolean connected;
	/**
	 * Currently set language.
	 */
	private String currentLanguage = "English";
	/**
	 * Constructor.
	 * @param localizaionProvider a map of all translated words.
	 */
	public LocalizationProviderBridge( ILocalizationProvider localizaionProvider) {
		this.localizationProvider = localizaionProvider;
		
		localizationListener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				currentLanguage = localizationProvider.getLanguage();
				fire();
			}
		};
	}
	/**
	 * Disconnects the bridge.
	 */
	public void disconnect() {
		if( connected) {
			localizationProvider.removeLocalizationListener(localizationListener);
			connected = false;
		}
	}
	/**
	 * Connects the bridge.
	 */
	public void connect() {
		if( !connected) {
			if( !localizationProvider.getLanguage().equals(currentLanguage)) {
				currentLanguage = localizationProvider.getLanguage();
				fire();
			}
			
			localizationProvider.addLocalizationListener(localizationListener);
			connected = true;
		}
	}
	
	@Override
	public String getString(String key) {
		return localizationProvider.getString(key);
	}

	@Override
	public String getLanguage() {
		return currentLanguage;
	}
}
