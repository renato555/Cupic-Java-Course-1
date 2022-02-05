package hr.fer.oprpp1.hw04.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class that represents a database.
 * @author renat
 */
public class StudentDatabase {
	/**
	 * A list of all students in database.
	 */
	private List<StudentRecord> allStudentsList;
	/**
	 * A map of all students in database.
	 */
	private Map<String, StudentRecord> allStudentsMap;
	
	/**
	 * Constructor. Fills inner list and map with students.
	 * @param students
	 * @throws IllegalArgumentException row is in a wrong format
	 */
	public StudentDatabase( List<String> students) {
		allStudentsList = new LinkedList<>();
		allStudentsMap = new HashMap<>();
		
		for( String s : students) {
			String[] studentTokens = s.split( "\\s+");
			int length = studentTokens.length;
			if( length < 4) throw new IllegalArgumentException( "jedan redak u bazi nema sve atribute");
			
			// prva rijec je jmbag
			String jmbag = studentTokens[0];
			
			// prezime je sve do imena i ocjene
			String lastName = studentTokens[1];
			for( int i = 2; i < length-2; ++i) lastName += " " + studentTokens[i];
			
			// ime je predzadnja rijec
			String firstName = studentTokens[length-2];
			
			// ocjena je zadnja
			int grade;
			try {
				grade =  Integer.parseInt( studentTokens[length-1]);
				if( grade < 1 || grade > 5) throw new IllegalArgumentException( "ocjena je manja od 1 ili veca od 5");
			}catch( NumberFormatException e) {
				throw new IllegalArgumentException( "ocjena se ne moze parsirati u int");
			}
			
			// ne smiju bit dupli jmbagovi
			if( allStudentsMap.containsKey( jmbag)) throw new IllegalArgumentException( "baza sadrzi redove s istim jmbagovima");
			
			StudentRecord record = new StudentRecord( jmbag, firstName, lastName, grade);
			allStudentsMap.put( jmbag, record);
			allStudentsList.add( record);
		}
	}
	
	/**
	 * @param jmbag
	 * @return StudentRecord with desired jmbag, or null if it doesn't exist 
	 */
	public StudentRecord forJMBAG( String jmbag) {
		return allStudentsMap.get( jmbag);	
	}
	
	/**
	 * @param filter
	 * @return a list of students that satisfy passed in filter.
	 */
	public List<StudentRecord> filter( IFilter filter){
		return allStudentsList.stream()
				.filter( (s)-> filter.accepts(s))
				.collect( Collectors.toList());
	}
}
