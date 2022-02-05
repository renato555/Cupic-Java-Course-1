package hr.fer.oprpp1.hw08.jnotepadpp.listeners;

import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * Keeps count of documents opened and enables/disabled subscribed actions.
 * @author renat
 */
public class DocumentExistsEnabledListener{
	/**
	 * A list of all listeners
	 */
	private List<Action> listeners = new LinkedList<>();
	/**
	 * Number of documents opened.
	 */
	private int documentsOpened = 0;

	/**
	 * Keeps count of documents opened and enables/disabled actions.
	 */
	private MultipleDocumentListener mdl = new MultipleDocumentListener() {
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		}
		
		/**
		 * Enables all actions.
		 */
		@Override
		public void documentAdded(SingleDocumentModel model) {
			documentsOpened++;
			setEnabledAll( true);
		}
		
		/**
		 * Disables all actions if there are no documents opened.
		 */
		@Override
		public void documentRemoved(SingleDocumentModel model) {
			documentsOpened--;
			if( documentsOpened < 1) {
				setEnabledAll( false);				
			}
		}		
	};
	
	public MultipleDocumentListener getMultipleDocumentListener() {
		return mdl;
	}
	
	/**
	 * Sets action's enable state to state.
	 * @param state desired state.
	 */
	private void setEnabledAll( boolean state) {
		listeners.forEach( (a)->{
			boolean currState = a.isEnabled();
			if( currState != state) {
				a.setEnabled( state);				
			}
		});
	}
	
	/**
	 * Subscribes an action to this class.
	 * @param action that we want to subscribe 
	 */
	public void subscribe( Action action) {
		listeners.add(action);
	}
	/**
	 * Unsubscribes an action from this class.
	 * @param action that we want to unsubscribe 
	 */
	public void unsubscribe( Action action) {
		listeners.add(action);
	}
}
