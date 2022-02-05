package hr.fer.zemris.java.hw06.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Represents tree command.
 * @author renat
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String COMMAND_NAME = "tree";
	
	/**
	 * Command description.
	 */
	private static final List<String> COMMAND_DESCRIPTION;
	
	static {
		COMMAND_DESCRIPTION = Collections.unmodifiableList( List.of(
				"----- tree -----",
				"Prima jedan argument koji je path na neki direktorij.",
				"Ispisuje strukturu navedenog direktorija."));
	}
	
	/**
	 * Prima jedan argument koji je path na neki direktorij.
	 * Ispisuje strukturu navedenog direktorija.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments) {
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
		
		Path path = Path.of( arg.get( 0));
		MyVisitor visitor = new MyVisitor( env);
		try {
			Files.walkFileTree( path, visitor);
		} catch (IOException e) {
			env.writeln( "nije moguce setati danim direktorijem");
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

	private static class MyVisitor extends SimpleFileVisitor<Path>{
		private int level;
		private Environment env;
		
		private MyVisitor( Environment env) {
			this.env = env;
			level = 0;
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln( "  ".repeat( level) + dir.getFileName().toString());
			++level;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			--level;
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln( "  ".repeat( level) + file.getFileName().toString());
			return FileVisitResult.CONTINUE;
		}

		
	}
}
