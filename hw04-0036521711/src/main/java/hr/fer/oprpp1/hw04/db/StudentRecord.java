package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * A class that represents a student record.
 * @author renat
 */
public class StudentRecord {
	/**
	 * Student's jmbag.
	 */
	private String jmbag;
	/**
	 * Student's firstName.
	 */
	private String firstName;
	/**
	 * Student's lastName.
	 */
	private String lastName;
	/**
	 * Student's final grade.
	 */
	private int finalGrade;
	
	/**
	 * Constructor. Initialises all attributes-
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}
	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
	@Override
	public String toString() {
		return jmbag + " " + lastName + " " + firstName + " " + finalGrade;
	}
	
	public String getJmbag() {
		return jmbag;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public int getFinalGrade() {
		return finalGrade;
	}
}
