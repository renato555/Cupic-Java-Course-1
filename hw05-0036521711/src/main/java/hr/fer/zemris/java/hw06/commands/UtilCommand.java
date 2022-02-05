package hr.fer.zemris.java.hw06.commands;

import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for shell commands.
 * @author renat
 */
public class UtilCommand {

	/**
	 * Splits arguments into a list
	 * @param arguments concatenated arguments
	 * @return list containing separated arguments
	 */
	public static List<String> getArgumentList(String arguments) {
		List<String> result = new LinkedList<>();
		char[] data = arguments.toCharArray();
		int i = 0;
		i = removeWhiteSpaces(data, i);

		StringBuilder builder = new StringBuilder();
		while (i < data.length) {
			builder.setLength( 0);
			
			if (data[i] == '\"') {
				// navodnici
				++i; // trash "
				while (i < data.length && data[i] != '\"') {
					if (data[i] == '\\') {
						if (i + 1 < data.length && data[i + 1] == '\"') {
							builder.append('\"');
							i += 2;
						} else if (i + 1 < data.length && data[i + 1] == '\\') {
							builder.append('\\');
							i += 2;
						} else {
							builder.append(data[i]);
							i++;
						}
					} else {
						builder.append(data[i]);
						++i;
					}
				}
				if( i >= data.length) {
					throw new IllegalArgumentException("potrebno je zatvoriti navodnike"); // nije dozvoljeno "C:\fi le 
				}
				++i; // trash "
				if (i < data.length && data[i] != ' ') {
					throw new IllegalArgumentException("nedozvoljen parametar"); // nije dozvoljeno "C:\fi le ".txt
				}
			} else {
				while (i < data.length && !jelPraznina(data[i])) {
					builder.append(data[i]);
					i++;
				}
			}

			i = removeWhiteSpaces( data, i);
			result.add( builder.toString());
		}

		return result;
	}

	/**
	 * Moves index past white spaces.
	 * @param data char array
	 * @param currentIndex current index in data
	 * @return new index
	 */
	private static int removeWhiteSpaces(char[] data, int currentIndex) {
		while (currentIndex < data.length && jelPraznina(data[currentIndex])) {
			++currentIndex;
		}
		return currentIndex;
	}

	/**
	 * @param c char for we wish to know if it is a whitespace 
	 * @return true if c is a whitespace, false otherwise
	 */
	private static boolean jelPraznina(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}
}
