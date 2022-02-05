package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

import hr.fer.oprpp1.hw08.jnotepadpp.listeners.SingleDocumentListener;

/**
 * Represent an opened document.
 * 
 * @author renat
 *
 */
public interface SingleDocumentModel {
	/**
	 * Returns document's text area.
	 * 
	 * @return text area
	 */
	JTextArea getTextComponent();

	/**
	 * Returns this document's path.
	 * 
	 * @return document's path
	 */
	Path getFilePath();

	/**
	 * Sets this document's path.
	 * 
	 * @param path new path
	 */
	void setFilePath(Path path);

	/**
	 * @return true if the document has been modified, false otherwises
	 */
	boolean isModified();

	/**
	 * Sets this document's modified state
	 * 
	 * @param modified new modified state
	 */
	void setModified(boolean modified);

	/**
	 * Add a new listener to this document
	 * 
	 * @param l new listener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes a listener from this document.
	 * 
	 * @param l
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
