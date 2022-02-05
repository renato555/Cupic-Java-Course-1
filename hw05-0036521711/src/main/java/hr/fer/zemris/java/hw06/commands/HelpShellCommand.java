package hr.fer.zemris.java.hw06.commands;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents help command.
 * @author renat
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String COMMAND_NAME = "help";
	
	/**
	 * Command description.
	 */
	private static final List<String> COMMAND_DESCRIPTION;
	
	static {
		COMMAND_DESCRIPTION = Collections.unmodifiableList( List.of(
				"----- help -----",
				"Ocekuje nula ili jedan parametar koji je ime naredbe.",
				"Ako nema parametara onda se ispisuju imena svih podrzanih naredbi.",
				"Ako je predan parametar onda se ispisuje opis navedene naredbe."));
	}
	
	/**
	 * Ocekuje nula ili jedan parametar koji je ime naredbe.
	 * Ako nema parametara onda se ispisuju imena svih podrzanih naredbi.
	 * Ako je predan parametar onda se ispisuje opis navedene naredbe.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> arg;
		// validacija
		try {
			arg = UtilCommand.getArgumentList(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("argument je krvio napisan");
			return ShellStatus.CONTINUE;
		}
		int n = arg.size();
		if ( n < 0 || n > 1) {
			env.writeln("krivi broj argumenata");
			return ShellStatus.CONTINUE;
		}
		
		if( n == 0) {
			Set<String> commands = env.commands().keySet();
			for( String command : commands) {
				env.writeln( command);
			}
		}else {
			ShellCommand command = env.commands().get( arg.get(0));
			if( command == null) {
				env.writeln( "ne postoji navedena naredba");
			}else {
				List<String> desc = command.getCommandDescription();
				for( String d : desc) {
					env.writeln( d);
				}
			}
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
