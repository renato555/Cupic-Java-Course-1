package hr.fer.zemris.java.hw06.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents copy command.
 * @author renat
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String COMMAND_NAME = "copy";

	/**
	 * Command description.
	 */
	private static final List<String> COMMAND_DESCRIPTION;

	static {
		COMMAND_DESCRIPTION = Collections.unmodifiableList(List.of(
				"----- copy -----.",
				"Ocekuje dva parametra koji predstavljaju path na datoteke.",
				"Kopira sadrzaj prve datoteke u sadrzaj druge datoteke."));
	}

	/**
	 * Ocekuje dva parametra koji predstavljaju path na datoteke.
	 * Kopira sadrzaj prve datoteke u sadrzaj druge datoteke.
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
		if (arg.size() != 2) {
			env.writeln("krivi broj argumenata");
			return ShellStatus.CONTINUE;
		}

		Path firstPath = Path.of(arg.get(0));
		if (Files.isDirectory(firstPath)) {
			env.writeln("dozvoljene je samo kopirati dokumente");
			return ShellStatus.CONTINUE;
		}
		Path secondPath = Path.of(arg.get(1));
		if (Files.isDirectory(secondPath)) {
			secondPath = Path.of(secondPath.toString(), firstPath.getFileName().toString());
		}

		if (Files.exists(secondPath)) {
			env.write("prepisati postojeci file? ");
			do {
				env.writeln("da\\ne");
				env.write(Character.toString(env.getPromptSymbol()));
				String input = env.readLine();
				if (input.equals("da")) {
					break;
				} else if (input.equals("ne")) {
					env.writeln("prekid naredbe...");
					return ShellStatus.CONTINUE;
				}
			} while (true);
		}

		try (InputStream inputFile = new BufferedInputStream(Files.newInputStream(firstPath));
				OutputStream outputFile = new BufferedOutputStream(Files.newOutputStream(secondPath));) {

			byte[] input = new byte[4096];
			while (true) {
				int r = inputFile.read(input);
				if (r < 1) {
					break;
				} else {
					outputFile.write( input, 0, r);
				}
			}
			env.writeln( "uspjesno izvreno");
		} catch (IOException e) {
			env.writeln("nije moguce otvoriti neku od datoteka");
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
