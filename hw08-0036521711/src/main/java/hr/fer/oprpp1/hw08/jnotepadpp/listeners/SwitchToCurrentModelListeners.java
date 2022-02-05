package hr.fer.oprpp1.hw08.jnotepadpp.listeners;

import java.util.LinkedList;
import java.util.List;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * Adds subscribed SinleDocumentListeners to active SingleDocumentModel's listeners.
 * @author renat
 */
public class SwitchToCurrentModelListeners {
	/**
	 * A list of all listeners
	 */
	private List<SingleDocumentListener> documentlisteners = new LinkedList<>();	
	/**
	 * Subscribes a SingleDocumentListener to this class.
	 * @param SingleDocumentListener that we want to subscribe 
	 */
	public void subscribeDocument( SingleDocumentListener listener) {
		documentlisteners.add(listener);
	}
	/**
	 * Unsubscribes a SingleDocumentListener to this class.
	 * @param SingleDocumentListener that we want to unsubscribe 
	 */
	public void unsubscribeDocument( SingleDocumentListener listener) {
		documentlisteners.remove(listener);
	}
	/**
	 * Removes all subscribed SingleDocumentListeners from a previous model and adds them to a new one.
	 */
	private MultipleDocumentListener documentChangedListener = new MultipleDocumentListener() {
		@Override
		public void documentRemoved(SingleDocumentModel model) {}
		
		@Override
		public void documentAdded(SingleDocumentModel model) {}
		
		/**
		 * Removes all subscribed SingleDocumentListeners from a previous model and adds them to a new one.
		 */
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			// setup document listeners
			if( previousModel != null) {
				documentlisteners.forEach( (l)->{
					previousModel.removeSingleDocumentListener( l);
					
				});
			}
			if( currentModel != null) {
				documentlisteners.forEach( (l)->{
					currentModel.addSingleDocumentListener( l);
				});
			}
			
			documentlisteners.forEach( (l)->{
				l.documentFilePathUpdated(currentModel);
			});
		}
	};
	
	public MultipleDocumentListener getDocumentChangedListener() {
		return documentChangedListener;
	}
}
