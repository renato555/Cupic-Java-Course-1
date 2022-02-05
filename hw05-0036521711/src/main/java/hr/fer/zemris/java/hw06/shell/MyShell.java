package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.commands.TreeShellCommand;

/**
 * Main class.
 * Simulates a simplified command prompt.
 * @author renat
 */
public class MyShell {

	public static void main( String[] args) {
		
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		commands.put( "symbol", new SymbolShellCommand());
		commands.put( "charsets", new CharsetsShellCommand());
		commands.put( "cat", new CatShellCommand());
		commands.put( "ls", new LsShellCommand());
		commands.put( "tree", new TreeShellCommand());
		commands.put( "copy", new CopyShellCommand());
		commands.put( "mkdir", new MkdirShellCommand());
		commands.put( "hexdump", new HexdumpShellCommand());
		commands.put( "help", new HelpShellCommand());
		commands.put( "exit", new ExitShellCommand());
		
		
		try(Environment env = new EnvironmentImpl( System.in, System.out, Collections.unmodifiableSortedMap( commands));){
			ShellStatus status = ShellStatus.CONTINUE;
			env.writeln( "Welcome to MyShell v 1.0");
			StringBuilder builder = new StringBuilder();			
			do {
				builder.setLength( 0);
				char promptSymbol = env.getPromptSymbol();
				char multiLineSymbol = env.getMultilineSymbol();
				char moreLinesSymbol = env.getMorelinesSymbol();

				// unos naredbe
				env.write( Character.toString(promptSymbol) + " ");
				String input = env.readLine();
				while( input.endsWith( " "+ moreLinesSymbol)) {
					builder.append( input.substring(0, input.length() - 1));
					env.write( Character.toString( multiLineSymbol) + " ");
					input = env.readLine();
				}
				builder.append( input);
				String l = builder.toString().trim();
				
				int firstSpace = l.indexOf( ' ');
				String commandName;
				String arguments;
				if( firstSpace != -1) {
					// naredba ima parametre
					commandName = l.substring( 0, firstSpace);
					arguments = l.substring( firstSpace + 1);					
				}else {
					// naredba nema parametre
					commandName = l;
					arguments = "";
				}
				
				// izvrsi naredbu
				ShellCommand command = commands.get( commandName);
				if( command == null) {
					env.writeln( "nepoznata naredba");
					continue;
				}
				status = command.executeCommand( env, arguments);				
				
			}while( status != ShellStatus.TERMINATE);			
		} catch (ShellIOException e) {
			e.printStackTrace();
			// obradi gresku
		} catch( Exception e) {
			e.printStackTrace();
			// nije moguce stvoriti env
		}
	}
}
