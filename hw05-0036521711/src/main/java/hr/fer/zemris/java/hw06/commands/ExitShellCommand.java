package hr.fer.zemris.java.hw06.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents exit command.
 * @author renat
 */
public class ExitShellCommand implements ShellCommand{
	
	/**
	 * Command name.
	 */
	private static final String COMMAND_NAME = "exit";
	
	/**
	 * Command description.
	 */
	private static final List<String> COMMAND_DESCRIPTION;
	
	static {
		COMMAND_DESCRIPTION = Collections.unmodifiableList( List.of(
				"----- exit -----",
				"Terminira aplikaciju."));
	}
	
	/**
	 * Terminira aplikaciju.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.writeln( "Goodbye!");
		return ShellStatus.TERMINATE;
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
