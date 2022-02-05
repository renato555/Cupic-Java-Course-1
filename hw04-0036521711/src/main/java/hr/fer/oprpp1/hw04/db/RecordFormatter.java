package hr.fer.oprpp1.hw04.db;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/**
 * Utility class for output formating.
 * @author renat
 */
public class RecordFormatter {
	
	/**
	 * @param list
	 * @return formated output
	 */
	public static String format( List<StudentRecord> list, boolean withStatistics) {
		int recordSize = list.size();
		
		int jmbagLen = 10;
		OptionalInt lastnameLen = list.stream()
									.mapToInt( s -> s.getLastName().length())
									.max();
		OptionalInt firstnameLen = list.stream()
				.mapToInt( s -> s.getFirstName().length())
				.max();
		
		StringBuilder builder = new StringBuilder();
		if( recordSize > 0) {
			builder.append( "+" + "=".repeat( jmbagLen+2));
			builder.append( "+" + "=".repeat( lastnameLen.getAsInt()+2));
			builder.append( "+" + "=".repeat( firstnameLen.getAsInt()+2));
			builder.append( "+===+\n");
			
			int lnLen = lastnameLen.getAsInt();
			int fnLen = firstnameLen.getAsInt();
			for( StudentRecord r : list) {
				String jmbag = r.getJmbag();
				String ln = r.getLastName() + " ".repeat( lnLen - r.getLastName().length());
				String fn = r.getFirstName() + " ".repeat( fnLen - r.getFirstName().length());
				
				builder.append( String.format( "| %s | %s | %s | %s |%n", jmbag, ln, fn, r.getFinalGrade()));
			}
			builder.append( "+" + "=".repeat( jmbagLen+2));
			builder.append( "+" + "=".repeat( lastnameLen.getAsInt()+2));
			builder.append( "+" + "=".repeat( firstnameLen.getAsInt()+2));
			builder.append( "+===+\n");
		}
		
		builder.append( "Records selected: " + recordSize + "\n");
		
		if( withStatistics) {
			OptionalDouble prosjek = list.stream()
						.mapToInt( StudentRecord::getFinalGrade)
						.average();
			
			builder.append( "prosjek studenata je: " + (prosjek.isPresent() ? prosjek.getAsDouble(): "nema studenata") + "\n");
			
			Map<Integer, Integer> mapaOcjena = list.stream()
						.collect( Collectors.groupingBy(StudentRecord::getFinalGrade,  Collectors.summingInt((a)->1)));
			
			for( int i = 1; i <= 5; ++i) {
				if( mapaOcjena.containsKey( i)) {
					builder.append( i + " ima: " + mapaOcjena.get( i) + "\n");
				}else {
					builder.append( i + " ima: 0\n");
				}
				
			}
		}
		return builder.toString();
	}
}
