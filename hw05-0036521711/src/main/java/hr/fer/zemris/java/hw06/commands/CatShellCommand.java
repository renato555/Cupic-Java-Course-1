package hr.fer.zemris.java.hw06.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents cat command.
 * @author renat
 */
public class CatShellCommand implements ShellCommand {
	
	/**
	 * Command name.
	 */
	private static final String COMMAND_NAME = "cat";
	
	/**
	 * Command description.
	 */
	private static final List<String> COMMAND_DESCRIPTION;
	
	static {
		COMMAND_DESCRIPTION = Collections.unmodifiableList( List.of(
				"----- cat -----",
				"Prihvaca jedan ili dva argumenta.",
				"Prvi je argument path na datoteku, a drugi je željeni charset ( moze se izostavit, pa se koristi default).",
				"Ispisuje sadrzaj datoteke."));
	}
	/**
	 * Prihvaca jedan ili dva argumenta.
	 * Prvi je argument path na datoteku, a drugi je željeni charset ( moze se izostavit, pa se koristi default).
	 * Ispisuje sadrzaj datoteke.
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
		int n = arg.size();
		if( n < 1 || n > 2) {
			env.writeln( "unesen je krivi broj parametara");
			return ShellStatus.CONTINUE;
		}
		
		Charset charset;
		if( n == 1) {
			charset = Charset.defaultCharset();
		}else {
			try {
				charset = Charset.forName( arg.get( 1));				
			}catch( IllegalCharsetNameException e) {
				env.writeln( "unesen je nepoznat charset");
				return ShellStatus.CONTINUE;
			}
		}
		
		Path p = Path.of( arg.get( 0));
		try(BufferedReader reader = Files.newBufferedReader( p, charset);){
			String input = reader.readLine();
			while( input != null) {
				env.writeln( input);
				input = reader.readLine();
			}
		} catch (IOException e) {
			env.writeln( "nije moguce citati dani fajl");
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
