package hr.fer.oprpp1.hw08.jnotepadpp.listeners;

import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * Enables subscribed buttons if text is selected on current textArea, disables otherwise.
 * @author renat
 */
public class SelectedEnableActionListener {
	/**
	 * A list of all listeners
	 */
	private List<Action> listeners = new LinkedList<>();
	/**
	 * Subscribes an action to this class.
	 * @param action that we want to subscribe 
	 */
	public void subscribe( Action action) {
		listeners.add( action);
	}
	/**
	 * Unsubscribes an action from this class.
	 * @param action that we want to unsubscribe 
	 */
	public void unsubscribe( Action action) {
		listeners.remove( action);
	}

	/**
	 * Listener on active JTextArea.
	 * Enables subscribed actions if text is selected.
	 */
	private CaretListener cl = new CaretListener() {
		/**
		 * Enables subscribed actions if text is selected.
		 */
		@Override
		public void caretUpdate(CaretEvent e) {
			refreshAll( (JTextArea) e.getSource());
		}		
	};
	
	/**
	 * Enables subscribed actions if text is selected.
	 */
	private void refreshAll( JTextArea area) {
		String selectedText = area.getSelectedText();
		if( selectedText != null && selectedText.length() > 0) {
			setEnabledAll( true);
		}else{
			setEnabledAll(false);
		}
	}
	
	/**
	 * Sets action's enable state to state.
	 * @param state desired state.
	 */
	private void setEnabledAll( boolean state) {
		listeners.forEach( (a)->{
			a.setEnabled( state);				
		});
	}
	
	/**
	 * Removes caret listener from an old TextArea and adds it to a new one.
	 */
	private MultipleDocumentListener mdl = new MultipleDocumentListener() {
		@Override
		public void documentRemoved(SingleDocumentModel model) {	
		}
		@Override
		public void documentAdded(SingleDocumentModel model) {
		}
		/**
		 * Removes caret listener from an old TextArea and adds it to a new one.
		 */
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if( previousModel != null) {
				JTextArea area = previousModel.getTextComponent();
				area.removeCaretListener( cl);
			}
			if( currentModel != null) {
				JTextArea area = currentModel.getTextComponent();
				area.addCaretListener( cl);
				refreshAll( area);
			}else {
				setEnabledAll(false);
			}
		}
	};
	
	public MultipleDocumentListener getMultipleDocumentListener() {
		return mdl;
	}
}
