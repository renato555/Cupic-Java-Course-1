package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JComponent;

import hr.fer.oprpp1.hw08.jnotepadpp.listeners.MultipleDocumentListener;

/**
 * Represents a collection of single document models.
 * 
 * @author renat
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Returns a component which displays this model.
	 * 
	 * @return component which displays this model.
	 */
	JComponent getVisualComponent();

	/**
	 * Creates a blank document.
	 * 
	 * @return created document.
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns currently active document.
	 * 
	 * @return currently active document.
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads a new document.
	 * 
	 * @param path path of the desired document
	 * @return loaded document
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves passed in document.
	 * 
	 * @param model   model that we want to save.
	 * @param newPath path where we want to save this model, if null uses model's
	 *                path
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes passed in document.
	 * 
	 * @param model model which we want to close.
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds a listener to this model.
	 * 
	 * @param l listener to be added.
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes a listener from this model.
	 * 
	 * @param l listener to be removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * @return number of opened documents.
	 */
	int getNumberOfDocuments();

	/**
	 * @param index index of a document
	 * @return document at index.
	 */
	SingleDocumentModel getDocument(int index);

	/**
	 * Returns a document with passed in path.
	 * 
	 * @param path
	 * @return document with desired path, null if no such model exists
	 */
	SingleDocumentModel findForPath(Path path); // null, if no such model exists

	/**
	 * Returns an index of passed in document model.
	 * 
	 * @param doc
	 * @return index of the passed in document, -1 if not present
	 */
	int getIndexOfDocument(SingleDocumentModel doc);
}
