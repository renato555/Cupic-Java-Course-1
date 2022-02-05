package hr.fer.zemris.java.hw06.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents symbol command.
 * @author renat
 */
public class SymbolShellCommand implements ShellCommand{

	/**
	 * Command name.
	 */
	private static final String COMMAND_NAME = "symbol";
	
	/**
	 * Command description.
	 */
	private static final List<String> COMMAND_DESCRIPTION;
	
	static {
		COMMAND_DESCRIPTION = Collections.unmodifiableList( List.of(
				"----- symbol -----",
				"Moze dobiti jedan ili dva argumenta.",
				"Ako primi jedan argument onda ispisuje koji znak se koristi za navedeni simbol.",
				"Ako primi dva argumenta onda postavlja drugi argument kao znak za simbol prvog argumenta."));
	}
	
	private static final String[] symbolNames = new String[] { "PROMPT", "MORELINES", "MULTILINE"};
	
	/**
	 * Moze dobiti jedan ili dva argumenta.
	 * Ako primi jedan argument onda ispisuje koji znak se koristi za navedeni simbol.
	 * Ako primi dva argumenta onda postavlja drugi argument kao znak za simbol prvog argumenta.
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
		
		if( n == 2) {
			String firstParam = arg.get( 0);
			String secondParam = arg.get( 1);
			if( secondParam.length() != 1) {
				env.writeln( "zamjenski simbol mora biti character ");
				return ShellStatus.CONTINUE;
			}
			char c = secondParam.charAt( 0);
			if( firstParam.equals( symbolNames[0])) {
				char prev = env.getPromptSymbol();
				env.setPromptSymbol( c);
				env.writeln( "Symbol for PROMT changed from \'" + prev + "\' to \'" + c + "\'");
				
			}else if( firstParam.equals( symbolNames[1])) {
				char prev = env.getMorelinesSymbol();
				env.setMorelinesSymbol( c);
				env.writeln( "Symbol for MORELINES changed from \'" + prev + "\' to \'" + c + "\'");
			
			}else if( firstParam.equals( symbolNames[2])) {
				char prev = env.getMultilineSymbol();
				env.setMultilineSymbol( c);
				env.writeln( "Symbol for MULTILINE changed from \'" + prev + "\' to \'" + c + "\'");
				
			}else {
				env.writeln( "unesen je nepoznat simbol : " + firstParam);
				return ShellStatus.CONTINUE;
			}
		}else{
			String firstParam = arg.get( 0);
			if( firstParam.equals( symbolNames[0])) {
				env.writeln( "Symbol for PROMT is \'" + env.getPromptSymbol() + "\'");
			}else if( firstParam.equals( symbolNames[1])) {
				env.writeln( "Symbol for MORELINES is \'" + env.getMorelinesSymbol() + "\'");	
			}else if( firstParam.equals( symbolNames[2])) {
				env.writeln( "Symbol for MULTILINE is \'" + env.getMultilineSymbol() + "\'");	
			}else {
				env.writeln( "unesen je nepoznat simbol : " + firstParam);
				return ShellStatus.CONTINUE;
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
