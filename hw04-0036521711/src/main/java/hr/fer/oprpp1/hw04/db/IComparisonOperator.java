package hr.fer.oprpp1.hw04.db;

/**
 * Interface for comparing two strings.
 * @author renat
 */
public interface IComparisonOperator {
	/**
	 * @param value1
	 * @param value2
	 * @returntrue if value1 and values2 satisfy some expression.
	 */
	public boolean satisfied( String value1, String value2);
}
