package hr.fer.zemris.java.hw06.commands;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents charsets command.
 * @author renat
 */
public class CharsetsShellCommand implements ShellCommand{
	
	/**
	 * Command name.
	 */
	private static final String COMMAND_NAME = "charsets";
	
	/**
	 * Command description.
	 */
	private static final List<String> COMMAND_DESCRIPTION;
	
	static {
		COMMAND_DESCRIPTION = Collections.unmodifiableList( List.of(
				"----- charsets -----",
				"Ne prihvaca niti jedan argument.",
				"Ispisuje sve podrzane charsetove."));
	}
	
	/**
	 * Ne prihvaca niti jedan argument.
	 * Ispisuje sve podrzane charsetove.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, Charset> charsets = Charset.availableCharsets();
		
		if( charsets != null) {
			for( Charset c : charsets.values()) {
				env.writeln( c.displayName());
			}
		}else {
			env.writeln( "No available charsets.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return COMMAND_DESCRIPTION;
	}

}
