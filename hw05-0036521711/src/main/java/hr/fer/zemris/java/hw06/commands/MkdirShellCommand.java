package hr.fer.zemris.java.hw06.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents mkdir command.
 * @author renat
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String COMMAND_NAME = "mkdir";
	
	/**
	 * Command description.
	 */
	private static final List<String> COMMAND_DESCRIPTION;
	
	static {
		COMMAND_DESCRIPTION = Collections.unmodifiableList( List.of(
				"----- mkdir -----",
				"Prihvaca jedan argument koji je path na direktorij kojeg treba stvorit.",
				"Stvara navedeni direktorij."));
	}
	
	/**
	 * Prihvaca jedan argument koji je path na direktorij kojeg treba stvorit.
	 * Stvara navedeni direktorij.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> arg;
		// validacija
		try {
			arg = UtilCommand.getArgumentList( arguments);
		}catch(IllegalArgumentException e) {
			env.writeln( "argument je krvio napisan");
			return ShellStatus.CONTINUE;
		}
		if( arg.size() != 1) {
			env.writeln( "krivi broj argumenata");
			return ShellStatus.CONTINUE;
		}
		
		Path dir = Path.of( arg.get( 0));
		try {
			Files.createDirectories( dir);
		} catch (IOException e) {
			env.writeln( "nije moguce stvoriti direktorij");
			return ShellStatus.CONTINUE;
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
