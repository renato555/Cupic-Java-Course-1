package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Represent one shell command.
 * @author renat
 */
public interface ShellCommand {
	
	/**
	 * Runs this shell command.
	 * @param env interface with which this command communicates with the user
	 * @param arguments command arguments
	 * @return ShellStatus.CONTINUE
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	String getCommandName();
	List<String> getCommandDescription();
}
