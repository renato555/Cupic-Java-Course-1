package hr.fer.oprpp1.custom.collections;

/**
 * Interface that tests elements.
 * @author renat
 *
 */
public interface Tester {
	/**
	 * Tests elements.
	 * @param obj object that we want to test
	 * @return true if obj is accepted, false otherwise
	 */
	boolean test( Object obj);
}
