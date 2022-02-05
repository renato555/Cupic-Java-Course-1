package hr.fer.oprpp1.hw04.db;


/**
 * Contains attributs that implement IComparisonOperato
 * @author renat
 *
 */
public class ComparisonOperators {
	/**
	 * Compares two strings.
	 * returns a < b
	 */
	public static final IComparisonOperator LESS = (a, b) -> {
		int result = a.compareTo( b);
		return result < 0;
	};
	/**
	 * Compares two strings.
	 * returns a <= b
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (a, b) -> {
		int result = a.compareTo( b);
		return result <= 0;
	};
	/**
	 * Compares two strings.
	 * returns a > b
	 */
	public static final IComparisonOperator GREATER = (a, b) -> {
		int result = a.compareTo( b);
		return result > 0;
	};
	/**
	 * Compares two strings.
	 * returns a >= b
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (a, b) -> {
		int result = a.compareTo( b);
		return result >= 0;
	};
	/**
	 * Compares two strings.
	 * returns a == b
	 */
	public static final IComparisonOperator EQUALS = (a, b) -> {
		return a.equals( b);
	};
	/**
	 * Compares two strings.
	 * returns a != b
	 */
	public static final IComparisonOperator NOT_EQUALS = (a, b) -> {
		return !EQUALS.satisfied(a, b);
	};
	/**
	 * Compares two strings, where second string can have one wild card character *.
	 * returns a like b
	 */
	public static final IComparisonOperator LIKE = (a, b) -> {
		int numOfStars = b.length() - b.replace( "*", "").length();
		if( numOfStars > 1) throw new IllegalArgumentException( "like moze imati samo jednu *");
		
		if( numOfStars == 0) {
			return a.equals( b);
		}
		
		if( b.startsWith("*")) {
			String end = b.substring( 1);
			return a.endsWith( end);
		}else if( b.endsWith("*")) {
			String start = b.substring( 0, b.length() -1);
			return a.startsWith( start);
		}else {
			// zvjezda je negdje u sredini
			String[] bTokens = b.split( "\\*");
			boolean startsWith = a.startsWith( bTokens[0]);
			if( !startsWith) return false;
			
			// makni pocetak sa string tako da npr AAA like AA*AA ne bude lazno pozitivno
			String restOfA = a.substring( bTokens[0].length());
			return restOfA.endsWith( bTokens[1]);
		}
	};
}
