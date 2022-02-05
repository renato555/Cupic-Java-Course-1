package hr.fer.zemris.java.hw06.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents ls command.
 * @author renat
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String COMMAND_NAME = "ls";
	
	/**
	 * Command description.
	 */
	private static final List<String> COMMAND_DESCRIPTION;
	
	static {
		COMMAND_DESCRIPTION = Collections.unmodifiableList( List.of(
				"----- ls -----",
				"Prihvaca jedan argument koji je path na neki direktorij.",
				"Ispisuje metapodatke o datotekama i drugim direktorijima sadrzanim u tom direktoriju. (nije rekurzivno)"));
	}
	
	/**
	 * Prihvaca jedan argument koji je path na neki direktorij.
	 * Ispisuje metapodatke o datotekama i drugim direktorijima sadrzanim u tom direktoriju. (nije rekurzivno)
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
		
		String pathString = arg.get( 0);
		Path p = Path.of( pathString);
		
		try {
			Files.list( p).forEach(( path)->{
				char d = Files.isDirectory( path) ? 'd' : '-';
				char r = Files.isReadable( path) ? 'r' : '-';
				char w = Files.isWritable( path) ? 'w' : '-';
				char x = Files.isExecutable( path) ? 'x' : '-';
				String size;
				try {
					size = Long.toString( Files.size( path));
				} catch (IOException e) {
					size = "err";
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(
						path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
				);
				String formattedDateTime;
				try {
					BasicFileAttributes attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();
					formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				} catch (IOException e) {
					formattedDateTime = "err";
				}
				
				String fileName = path.getFileName().toString();
				
				String result = String.format( "%c%c%c%c %10s %19s %s", d, r, w, x, size, formattedDateTime, fileName);
				env.writeln( result);
			});
		} catch (IOException e) {
			env.writeln( "nije moguce otvoriti dani path");
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
