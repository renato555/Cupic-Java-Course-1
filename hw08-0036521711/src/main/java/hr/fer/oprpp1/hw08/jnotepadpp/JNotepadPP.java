package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.JToolBar;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CopyTextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CreateBlankDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CutTextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ExitAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.InvertCaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.LowercaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.OpenExistingDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.PasteTextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SetLanguageAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SortAscendingAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SortDescendingAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.StatisticalInfoAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.UniqueAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.UppercaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.components.StatusBar;
import hr.fer.oprpp1.hw08.jnotepadpp.listeners.DocumentExistsEnabledListener;
import hr.fer.oprpp1.hw08.jnotepadpp.listeners.SelectedEnableActionListener;
import hr.fer.oprpp1.hw08.jnotepadpp.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.listeners.SwitchToCurrentModelListeners;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * File editor with graphical user interface.
 * @author renat
 *
 */
public class JNotepadPP extends JFrame {
	/**
	 * Map of translated words.
	 */
	private FormLocalizationProvider flp;
	/**
	 * Closes a document.
	 */
	private Action closeDocumentAction;
	/**
	 * Copies selected text.
	 */
	private Action copyTextAction;
	/**
	 * Crates a blank document.
	 */
	private Action createBlankDocumentAction;
	/**
	 * Cuts selected text.
	 */
	private Action cutTextAction;
	/**
	 * Closes the application.
	 */
	private Action exitAction;
	/**
	 * Opens a new document.
	 */
	private Action openExistingDocumentAction;
	/**
	 * Pastes text.
	 */
	private Action pasteTextAction;
	/**
	 * Saves document to a new location.
	 */
	private Action saveAsAction;
	/**
	 * Saves document.
	 */
	private Action saveAction;
	/**
	 * Shows document information.
	 */
	private Action statisficalInfoAction;
	/**
	 * Sets language to Croatian.
	 */
	private Action languageHr;
	/**
	 * Sets language to English.
	 */
	private Action languageEn;
	/**
	 * Sets language to German.
	 */
	private Action languageDe;
	/**
	 * Transforms selected letter to upper case.
	 */
	private Action uppercaseAction;
	/**
	 * Transforms selected letter to lower case.
	 */
	private Action lowercaseAction;
	/**
	 * Inverts select letters.
	 */
	private Action invertCaseAction;
	/**
	 * Sorts selected lines in ascending order.
	 */
	private Action sortAscendingAction;
	/**
	 * Sorts selected lines in descending order.
	 */
	private Action sortDescendingAction;
	/**
	 * Removes duplicate lines from selected lines.
	 */
	private Action uniqueAction;
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel mdm;
	/**
	 * Panel with document text area and status bar.
	 */
	private JPanel contentPanel;
	/**
	 * Component which shows document information.
	 */
	private StatusBar statusBar;
	/**
	 * Adds subscribed document listeners to a new document and removes them from the previous model.
	 */
	private SwitchToCurrentModelListeners switchListeners;
	/**
	 * Changes title when the active document is changed.
	 */
	private SingleDocumentListener changeTitleListener = new SingleDocumentListener() {
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
		}
		/**
		 * Changes title when the active document is changed.
		 */
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			String title;
			if( model == null) {
				title = "";
			}else {
				Path filePath = model.getFilePath();
				if( filePath != null) {
					title = filePath.toString();
				}else {
					title = LocalizationProvider.getInstance().getString( "unnamed");
				}				
			}
			
			title += " - JNotepad++";
			setTitle( title);
		}
	};
	/**
	 * Updates titles of unnamed documents when the language is changed.
	 */
	private ILocalizationListener updateUnnamedTitle = new ILocalizationListener() {
		/**
		 * Updates titles of unnamed documents when the language is changed.
		 */
		@Override
		public void localizationChanged() {
			SingleDocumentModel sdm = mdm.getCurrentDocument();
			if( sdm != null && sdm.getFilePath() == null) {
				String title = LocalizationProvider.getInstance().getString( "unnamed") + " - JNotepad++";
				setTitle( title);
			}
		}
	};
	/**
	 * Constructor.
	 * Initialises all attributes.
	 * Builds GUI.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle( "JNotepad++");
		setLocation(0, 0);
		setSize(900, 600);

		flp = new FormLocalizationProvider( LocalizationProvider.getInstance(), this);
		flp.addLocalizationListener(updateUnnamedTitle);
		
		mdm = new DefaultMultipleDocumentModel( flp);
		
		switchListeners = new SwitchToCurrentModelListeners();
		switchListeners.subscribeDocument(changeTitleListener);		
		mdm.addMultipleDocumentListener( switchListeners.getDocumentChangedListener());
		
		initGUI();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
			public void windowClosed( WindowEvent e) {
				statusBar.getClock().stop();
			}
		});
	}
	/**
	 * Initialises GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		contentPanel = new JPanel();
		contentPanel.setLayout( new BorderLayout());
		contentPanel.add(mdm.getVisualComponent(), BorderLayout.CENTER);

		cp.add( contentPanel, BorderLayout.CENTER);
		
		createStatusBar();
		createActions();
		createMenus();
		createToolbars();
	}

	/**
	 * Initialises all actions.
	 */
	private void createActions() {
		copyTextAction = new CopyTextAction(mdm, flp);
		createBlankDocumentAction = new CreateBlankDocumentAction(mdm, flp);
		cutTextAction = new CutTextAction(mdm, flp);
		openExistingDocumentAction = new OpenExistingDocumentAction(mdm, flp);
		pasteTextAction = new PasteTextAction(mdm, flp);
		saveAsAction = new SaveAsDocumentAction(mdm, flp);
		saveAction = new SaveDocumentAction(mdm, flp);
		exitAction = new ExitAction(this, mdm, flp);
		statisficalInfoAction = new StatisticalInfoAction(mdm, flp);
		closeDocumentAction = new CloseDocumentAction(mdm, flp);
		
		languageHr = new SetLanguageAction( "Hrvatski");
		languageEn = new SetLanguageAction( "English");
		languageDe = new SetLanguageAction( "Deutsch");
		
		uppercaseAction = new UppercaseAction( mdm, flp);
		lowercaseAction = new LowercaseAction( mdm, flp);
		invertCaseAction = new InvertCaseAction( mdm, flp);
		
		sortAscendingAction = new SortAscendingAction(mdm, flp);
		sortDescendingAction = new SortDescendingAction(mdm, flp);
		uniqueAction = new UniqueAction(mdm, flp);
		
		setupEnabledListeners();
	}
	/**
	 * Adds actions to proper listeners.
	 */
	private void setupEnabledListeners() {
		// svi gumbi koji su omoguceni kada je otvoren barem jedan dokument
		DocumentExistsEnabledListener deel = new DocumentExistsEnabledListener();
		closeDocumentAction.setEnabled(false);
		pasteTextAction.setEnabled(false); 
		saveAsAction.setEnabled(false); 
		statisficalInfoAction.setEnabled(false); 
		deel.subscribe( closeDocumentAction);
		deel.subscribe( pasteTextAction);
		deel.subscribe( saveAsAction);
		deel.subscribe( statisficalInfoAction);
		mdm.addMultipleDocumentListener( deel.getMultipleDocumentListener());
		
		// svi gumbi koji su omoguceni kada je neso oznaceno
		SelectedEnableActionListener seal = new SelectedEnableActionListener();		
		copyTextAction.setEnabled( false);
		cutTextAction.setEnabled(false); 
		invertCaseAction.setEnabled(false); 
		lowercaseAction.setEnabled(false);
		uppercaseAction.setEnabled(false);
		sortAscendingAction.setEnabled(false);
		sortDescendingAction.setEnabled(false);
		uniqueAction.setEnabled(false);
		seal.subscribe( copyTextAction);
		seal.subscribe( cutTextAction);
		seal.subscribe( invertCaseAction);
		seal.subscribe( lowercaseAction);
		seal.subscribe( uppercaseAction);
		seal.subscribe( sortAscendingAction);
		seal.subscribe( sortDescendingAction);
		seal.subscribe( uniqueAction);
		mdm.addMultipleDocumentListener( seal.getMultipleDocumentListener());
		
		// postavlja listenere na trenutno prikazan dokument
		// save je omogucen samo kada postoji path
		saveAction.setEnabled(false);
		switchListeners.subscribeDocument( new SaveDocumentAction.EnabledIfPathPresentListener( saveAction));
	}
	/**
	 * Creates application's menu.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new LJMenu("File", flp);
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(createBlankDocumentAction));
		fileMenu.add(new JMenuItem(openExistingDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new LJMenu("Edit", flp);
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(copyTextAction));
		editMenu.add(new JMenuItem(cutTextAction));
		editMenu.add(new JMenuItem(pasteTextAction));

		JMenu toolsMenu = new LJMenu("Tools", flp);
		menuBar.add(toolsMenu);
		toolsMenu.add(new JMenuItem(statisficalInfoAction));
		
		JMenu changeCase = new LJMenu( "Change_case", flp); 
		toolsMenu.add( changeCase);
		changeCase.add( uppercaseAction);
		changeCase.add( lowercaseAction);
		changeCase.add( invertCaseAction);
		
		JMenu sort = new LJMenu( "Sort", flp);
		toolsMenu.add( sort);
		sort.add( sortAscendingAction);
		sort.add( sortDescendingAction);
		
		toolsMenu.add( uniqueAction);
		
		JMenu languagesMenu = new LJMenu( "Languages", flp);
		menuBar.add( languagesMenu);
		languagesMenu.add( languageHr);
		languagesMenu.add( languageEn);
		languagesMenu.add( languageDe);
		setJMenuBar(menuBar);
	}
	/**
	 * Creates application's tool bar.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);

		toolBar.add(createBlankDocumentAction);
		toolBar.add(openExistingDocumentAction);
		toolBar.add(closeDocumentAction);
		toolBar.addSeparator();
		
		toolBar.add(saveAsAction);
		toolBar.add(saveAction);
		toolBar.addSeparator();
		
		toolBar.add(copyTextAction);
		toolBar.add(cutTextAction);
		toolBar.add(pasteTextAction);
		toolBar.addSeparator();
		
		toolBar.add(statisficalInfoAction);
		toolBar.addSeparator();
		
		toolBar.add( uppercaseAction);
		toolBar.add( lowercaseAction);
		toolBar.add( invertCaseAction);
		toolBar.addSeparator();
		
		toolBar.add( sortAscendingAction);
		toolBar.add( sortDescendingAction);
		toolBar.add( uniqueAction);
		toolBar.addSeparator();
		
		
		toolBar.add(exitAction);
		
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	/**
	 * Creates application's status bar.
	 */
	private void createStatusBar( ) {
		statusBar = new StatusBar( flp);
		mdm.addMultipleDocumentListener( statusBar.getMultipleDocumentListener());
		contentPanel.add( statusBar, BorderLayout.PAGE_END);
	}
	/**
	 * Main function. Starts up the application.
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}
