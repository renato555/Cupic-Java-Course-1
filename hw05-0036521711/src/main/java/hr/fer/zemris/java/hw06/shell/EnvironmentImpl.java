package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.SortedMap;

/**
 * Environment implementation.
 * @author renat
 */
public class EnvironmentImpl implements Environment, AutoCloseable{

	/**
	 * Reads from inputStream.
	 */
	private final BufferedReader reader;

	/**
	 * Writes to outputStream.
	 */
	private final BufferedWriter writer;

	/**
	 * Unmodifiable map which contains all supported shellCommands.
	 */
	private final SortedMap<String, ShellCommand> commands;
	
	/**
	 * Default MULTILINE symbol.
	 */
	private Character MULTI_LINES_SYMBOL = '|';
	
	/**
	 * Default PROMPT symbol.
	 */
	private Character PROMPT_SYMBOL = '>';
	
	/**
	 * Default MORELINES symbol.
	 */
	private Character MORE_LINES_SYMBOL = '\\';
	
	/**
	 * @param is input stream from which this environment reads
	 * @param os output stream to which this environment writes
	 * @param commands Unmodifiable map which contains all supported shellCommands.
	 */
	public EnvironmentImpl( InputStream is, OutputStream os, SortedMap<String, ShellCommand> commands) {
		reader = new BufferedReader( new InputStreamReader( is));
		writer = new BufferedWriter( new OutputStreamWriter( os));
		this.commands = commands;
	}
	
	@Override
	public String readLine() throws ShellIOException {
		String input = null;
		try {
			input = reader.readLine();
		} catch (IOException e) {
			throw new ShellIOException( "ne moze se citat s ulaznog toka");
		}
		return input;
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			writer.write( text);
			writer.flush();
		} catch (IOException e) {
			throw new ShellIOException( "ne moze se pisati u izlazni tok");
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		write( text + "\n");
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTI_LINES_SYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTI_LINES_SYMBOL = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPT_SYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPT_SYMBOL = symbol;
		
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORE_LINES_SYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORE_LINES_SYMBOL = symbol;
	}

	@Override
	public void close() throws IOException {
		reader.close();
		writer.close();
	}
}
