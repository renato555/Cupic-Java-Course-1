package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.listeners.SingleDocumentListener;

/**
 * Represent an opened document.
 * 
 * @author renat
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * Path of this document.
	 */
	private Path filePath;
	/**
	 * Text area of this document.
	 */
	private JTextArea textComponent;
	/**
	 * True is this document has been modified since it was created or saved, false
	 * otherwise.
	 */
	private boolean modified;
	/**
	 * Listeners,
	 */
	private List<SingleDocumentListener> listeners;
	/**
	 * Called when text area has been changed. Sets modified attribute to true.
	 */
	private DocumentListener documentListener = new DocumentListener() {
		@Override
		public void insertUpdate(DocumentEvent e) {
			modifyIfDifferent(true);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			modifyIfDifferent(true);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			modifyIfDifferent(true);
		}

		/**
		 * Sets modified attribute to new state.
		 * 
		 * @param newState new state
		 */
		private void modifyIfDifferent(boolean newState) {
			if (newState == isModified())
				return;

			setModified(newState);
		}
	};

	/**
	 * Constructor. Initialises all attributes.
	 * 
	 * @param filePath    path of this document
	 * @param textContent content of this document.
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.filePath = filePath;
		modified = false;

		textComponent = new JTextArea(textContent);
		textComponent.getDocument().addDocumentListener(documentListener);

		listeners = new LinkedList<>();
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path, "file path ne moze biti null");
		this.filePath = path;
		fireDocumentFilePathUpdated();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		fireDocumentModifyStatusUpdated();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * Calls all listeners.
	 */
	private void fireDocumentModifyStatusUpdated() {
		listeners.forEach((l) -> l.documentModifyStatusUpdated(this));
	}

	/**
	 * Calls all listeners.
	 */
	private void fireDocumentFilePathUpdated() {
		listeners.forEach((l) -> l.documentFilePathUpdated(this));
	}
}
