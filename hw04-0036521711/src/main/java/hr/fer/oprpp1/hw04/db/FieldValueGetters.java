package hr.fer.oprpp1.hw04.db;

/**
 * Class that has attributes that implement IFieldValueGetter.
 * @author renat
 */
public class FieldValueGetters {
	/**
	 * Returns record's firstName.
	 */
	public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;
	/**
	 * Returns record's lastName.
	 */
	public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;
	/**
	 * Returns record's jmbag.
	 */
	public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
}
