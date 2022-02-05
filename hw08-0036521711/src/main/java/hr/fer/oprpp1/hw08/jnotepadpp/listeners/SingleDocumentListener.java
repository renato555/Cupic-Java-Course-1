package hr.fer.oprpp1.hw08.jnotepadpp.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * Single Document Listener.
 * @author renat
 *
 */
public interface SingleDocumentListener {
	/**
	 * Called when a document is modified.
	 * @param model modified document model 
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	/**
	 * Called when a document's path is changed.
	 * @param model which got his path changed.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
