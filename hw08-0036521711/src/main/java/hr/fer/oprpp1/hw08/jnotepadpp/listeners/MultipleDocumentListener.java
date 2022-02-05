package hr.fer.oprpp1.hw08.jnotepadpp.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * Multiple Document Listener.
 * @author renat
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Called when the active document is changed.
	 * @param previousModel previous active document
	 * @param currentModel new active document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	/**
	 * Called when a new model is added.
	 * @param model added document model.
	 */
	void documentAdded(SingleDocumentModel model);
	/**
	 * Called when a document model is removed.
	 * @param model removed document model.
	 */
	void documentRemoved(SingleDocumentModel model);
}
