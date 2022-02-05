package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.utilities.IconLoader;
/**
 * Represents a collection of single document models.
 * @author renat
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	/**
	 * A list of current opened document.
	 */
	private List<SingleDocumentModel> documentModels;
	/**
	 * Currently active document.
	 */
	private SingleDocumentModel currDocumentModel;
	/**
	 * Listeners
	 */
	private List<MultipleDocumentListener> listeners;
	/**
	 * Modified icon name.
	 */
	private static final String modifiedIconName = "redDiskette";
	/**
	 * Unmodified icon name.
	 */
	private static final String unmodifiedIconName = "greenDiskette";
	/**
	 * Updates unnamed document's title to current language.
	 */
	private ILocalizationListener updateUnnamedDocuments = new ILocalizationListener() {
		/**
		 * Updates unnamed document's title to current language.
		 */
		@Override
		public void localizationChanged() {
			int i = 0;
			for( SingleDocumentModel sdm : documentModels) {
				if( sdm.getFilePath() == null) {
					setTitleAt( i, LocalizationProvider.getInstance().getString( "unnamed"));
				}
				++i;
			}
		}
	};
	/**
	 * Updates currently active document reference.
	 */
	private ChangeListener changeListener = new ChangeListener() {
		/**
		 * Updates currently active document reference.
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			int indexNew = getSelectedIndex();
			if( indexNew != -1) {
				SingleDocumentModel newModel = documentModels.get(indexNew);
				
				SingleDocumentModel previousModel = setCurrentModel( newModel);
				fireCurrentDocumentChanged(previousModel, newModel);				
			}else {
				SingleDocumentModel previousModel = setCurrentModel( null);
				fireCurrentDocumentChanged(previousModel, null);
			}
		}
	};
	/**
	 * Keeps track of displayed icon and title.
	 */
	private SingleDocumentListener sdl = new SingleDocumentListener() {
		/**
		 * Changed icon.
		 */
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index = getIndexOfDocument( model);
			String iconName = model.isModified() ? modifiedIconName : unmodifiedIconName;
			Icon icon = IconLoader.loadImageIcon( iconName);
			setIconAt( index, icon);
		}
		/**
		 * Change title.
		 */
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			Path filePath = model.getFilePath();
			String tip, title;
			if( filePath == null) {
				tip = title = LocalizationProvider.getInstance().getString( "unnamed");
			}else {
				tip = filePath.toString();
				title = filePath.getFileName().toString();
			}
			int index = getIndexOfDocument( model);
			setToolTipTextAt( index, tip);
			setTitleAt( index, title);
		}
	};
	/**
	 * Constructor.
	 * Initialises attributes.
	 * @param flp
	 */
	public DefaultMultipleDocumentModel( ILocalizationProvider flp) {
		documentModels = new LinkedList<>();
		listeners = new LinkedList<>();
		
		addChangeListener( changeListener);
		flp.addLocalizationListener(updateUnnamedDocuments);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documentModels.iterator();
	}

	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel model = createModel(null, null);
		SingleDocumentModel prevModel = setCurrentModel( model);
		insertModel( model);
		switchDocument( prevModel, model);
		return model;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currDocumentModel;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		SingleDocumentModel model = findForPath(path); 
		if( findForPath(path) != null) {
			// switch to existing document
			SingleDocumentModel prevModel = setCurrentModel( model);
			switchDocument( prevModel, model);
			return model;
		}
		
		// create new model and switch
		try {
			Objects.requireNonNull( path);
			String textContent = Files.readString(path);
			model = createModel(path, textContent);
			SingleDocumentModel prevModel = setCurrentModel( model);
			insertModel( model);
			switchDocument( prevModel, model);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Path modelPath = model.getFilePath();
		Path saveTo = newPath != null ? newPath : modelPath;
		Objects.requireNonNull( saveTo, "nije definiran path za spremanje");
		
		for( var sdm : documentModels) {
			if( !sdm.equals(model) && saveTo.equals( sdm.getFilePath())) {
				throw new IllegalArgumentException( "File je vec otvoren u drugom prozoru");
			}
		}
		
		String textContent = model.getTextComponent().getText();
		try( var writer = Files.newBufferedWriter( saveTo)){
			writer.write(textContent);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		if( !saveTo.equals(modelPath)) {
			model.setFilePath(saveTo);
		}
		model.setModified( false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int oldIndex = getIndexOfDocument(model);
		SingleDocumentModel prevModel = model;
		documentModels.remove( prevModel);
		removeTabAt( oldIndex);
		fireDocumentRemoved( prevModel);
		
		// nakon ovog ce se aktivirat changeListener
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documentModels.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documentModels.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		Objects.requireNonNull(path, "path ne smije biti null");

		SingleDocumentModel model;
		var iterator = iterator();
		while (iterator.hasNext()) {
			model = iterator.next();
			if (path.equals(model.getFilePath()))
				return model;
		}

		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		return documentModels.indexOf(doc);
	}
	/**
	 * Creates a new document model.
	 * @param path document's path
	 * @param text document's content
	 * @return
	 */
	private SingleDocumentModel createModel(Path path, String text) {
		SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
		model.addSingleDocumentListener( sdl);
		
		return model;
	}
	/**
	 * Sets currently active document reference
	 * @param model new document model
	 * @return previous document model
	 */
	private SingleDocumentModel setCurrentModel( SingleDocumentModel model) {
		SingleDocumentModel previousModel = currDocumentModel;
		currDocumentModel = model;
		return previousModel;
	}
	/**
	 * Inserts a model into collection.
	 * @param model new document model.
	 */
	private void insertModel( SingleDocumentModel model) {
		documentModels.add(model);
		Path filePath = model.getFilePath();
		String title;
		String tip;
		if( filePath == null) {
			title = LocalizationProvider.getInstance().getString( "unnamed");
			tip = title;
		}else{
			title = filePath.getFileName().toString();
			tip = filePath.toString(); 
		}
		Icon icon = IconLoader.loadImageIcon( unmodifiedIconName);
		addTab( title, icon, new JScrollPane( model.getTextComponent()), tip);
		
		fireDocumentAdded(model);
	}
	/**
	 * Switches to a new document model.
	 * @param previousModel previous document model.
	 * @param newModel new document model.
	 */
	private void switchDocument( SingleDocumentModel previousModel, SingleDocumentModel newModel) {
		setSelectedIndex( getIndexOfDocument( newModel));
		fireCurrentDocumentChanged(previousModel, newModel);
	}
	/**
	 * Calls all listeners.
	 */
	private void fireCurrentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		listeners.forEach((l) -> l.currentDocumentChanged(previousModel, currentModel));
	}
	/**
	 * Calls all listeners.
	 */
	private void fireDocumentAdded(SingleDocumentModel model) {
		listeners.forEach((l) -> l.documentAdded(model));
	}
	/**
	 * Calls all listeners.
	 */
	private void fireDocumentRemoved(SingleDocumentModel model) {
		listeners.forEach((l) -> l.documentRemoved(model));
	}
}
