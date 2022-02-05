package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Represents communication interface between user and application.
 * @author renat
 */
public interface Environment extends AutoCloseable{
	/**
	 * Reads next line from input stream.
	 * @return next line 
	 * @throws ShellIOException if reading fails
	 */
	String readLine() throws ShellIOException;

	/**
	 * Flushes desired text to output stream. 
	 * @param text to be flushed in output stream
	 * @throws ShellIOException if writing fails
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Same as using write( text + "\n"). 
	 * @param text to be flushed in output stream
	 * @throws ShellIOException if writing fails
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * @return A map which contains all supported commands. 
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * @return Character that is used as a MULTILINE symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets desired character as MULTILINE symbol
	 * @param symbol new MULTILINE symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * @return Character that is used as a PROMPT symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets desired character as PROMPT symbol
	 * @param symbol new PROMPT symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * @return Character that is used as a MORELINES symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets desired character as MORELINES symbol
	 * @param symbol new MORELINES symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
