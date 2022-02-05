package hr.fer.oprpp1.hw08.jnotepadpp.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * JLabel which displays current time.
 * @author renat
 *
 */
public class Clock extends JLabel {
	/**
	 * Formats current time to string.
	 */
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd kk:mm:ss");
	/**
	 * Stops the clock if its set to true.
	 */
	private volatile boolean stopRequested;
	/**
	 * Constructor.
	 * Starts a new Thread which updates displayed time.
	 * @param alignment label alignment
	 */
	public Clock( int alignment) {
		this();
		setHorizontalAlignment( alignment);
	}
	/**
	 * Constructor.
	 * Starts a new Thread which updates displayed time.
	 */
	public Clock() {
		updateTime();
		Thread t = new Thread(() -> {
			while( true) {
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
				if( stopRequested) break;
				SwingUtilities.invokeLater(() -> {
					updateTime();
				});				
			}

		});
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Updates displayed time.
	 */
	private void updateTime() {
		setText( formatter.format( LocalDateTime.now()));
	}
	
	/**
	 * Stops the clock.
	 */
	public void stop() {
		stopRequested = true;
	}
}
