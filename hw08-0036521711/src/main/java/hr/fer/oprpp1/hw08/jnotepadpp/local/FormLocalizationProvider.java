package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * Localization provider bridge extension.
 * Automatically connects the bridge when the window is activated and disconnects it when the window is closed.
 * @author renat
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{
	/**
	 * Constructor.
	 * Automatically connects the bridge and disconnects it when the window is closed.
	 * @param localizaionProvider localisation provider 
	 * @param frame connects the bridge when the frame is activated and disables it when it gets closed 
	 */
	public FormLocalizationProvider(ILocalizationProvider localizaionProvider, JFrame frame) {
		super(localizaionProvider);

		frame.addWindowListener( new WindowAdapter() {
			/**
			 * Connect the bridge.
			 */
			public void windowActivated( WindowEvent e) {
				connect();
			}
			/**
			 * Disconnect the bridge.
			 */
			public void windowClosed( WindowEvent e) {
				disconnect();
			}
		});
	}

	
}
