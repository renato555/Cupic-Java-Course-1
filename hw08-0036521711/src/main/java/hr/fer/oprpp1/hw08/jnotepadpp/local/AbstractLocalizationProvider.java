package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.LinkedList;
import java.util.List;
/**
 * Abstract representation of ILocalizationProvider.
 * Implements methods that deal with listeners.
 * @author renat
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/**
	 * Collection of listeners.
	 */
	private List<ILocalizationListener> listeners = new LinkedList<>();
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	/**
	 * Calls all listeners.
	 */
	protected void fire() {
		listeners.forEach( (l)->{
			l.localizationChanged();
		});
	}
}
