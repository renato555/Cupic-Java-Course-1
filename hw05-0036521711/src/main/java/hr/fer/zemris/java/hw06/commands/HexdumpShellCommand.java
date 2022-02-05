package hr.fer.zemris.java.hw06.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents hexdump command.
 * @author renat
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String COMMAND_NAME = "hexdump";
	
	/**
	 * Command description.
	 */
	private static final List<String> COMMAND_DESCRIPTION;
	
	static {
		COMMAND_DESCRIPTION = Collections.unmodifiableList( List.of(
				"----- hexdump -----",
				"Ocekuje jedan parametar koji je path na neku datoteku.",
				"Ispisuje heksadski zapis bajtova."));
	}
	
	/**
	 * Ocekuje jedan parametar koji je path na neku datoteku.
	 * Ispisuje heksadski zapis bajtova.
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
		
		Path path = Path.of( arg.get(0));
		if( !Files.isRegularFile(path)) {
			env.writeln( "argument mora biti neka datoteka");
			return ShellStatus.CONTINUE;
		}
		
		try (InputStream inputFile = new BufferedInputStream( Files.newInputStream( path))){
			long count = 0;
			
			byte[] input = new byte[ 16];
			StringBuilder builder = new StringBuilder();
			StringBuilder builder2 = new StringBuilder();
			while( true) {
				builder.setLength( 0);
				builder2.setLength( 0);
				int r = inputFile.read( input);
				if( r < 1) {
					break;
				}else {
					builder.append( String.format( "%08X: ", count));
					for( int i = 0; i < 16; ++i) {
						if( i < r) {
							builder.append( String.format( "%2X", input[i]));
							if( input[i] < 32 || input[i] > 127) {
								builder2.append( '.');
							}else {
								builder2.append( (char) input[i]);
							}
						}else {
							builder.append( "  ");
						}
						
						if( i == 7) {
							builder.append( '|');
						}else{
							builder.append( ' ');
						}
					}
					
					builder.append( "| " + builder2.toString());
					env.writeln( builder.toString());
					count += r;
				}
			}
		} catch (IOException e) {
			env.writeln( "nije moguce otvoriti datoteku");
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
