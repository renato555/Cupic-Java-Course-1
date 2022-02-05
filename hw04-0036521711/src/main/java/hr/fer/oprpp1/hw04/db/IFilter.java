package hr.fer.oprpp1.hw04.db;

/**
 * Interface that filters records.
 * @author renat
 */
public interface IFilter {
	/**
	 * @param record to be examined
	 * @return true if record is accepted, false otherwise
	 */
	public boolean accepts( StudentRecord record);
}
