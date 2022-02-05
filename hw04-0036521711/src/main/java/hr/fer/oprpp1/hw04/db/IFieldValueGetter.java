package hr.fer.oprpp1.hw04.db;

/**
 * Interface that fetches some attribute from record.
 * @author renat
 *
 */
public interface IFieldValueGetter {
	/**
	 * @param record
	 * @return record's field value.
	 */
	public String get( StudentRecord record);
}
