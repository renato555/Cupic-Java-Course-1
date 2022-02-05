package hr.fer.oprpp1.hw04.db;

/**
 * Class that represents one query expression.
 * @author renat
 */
public class ConditionalExpression {
	/**
	 * A reference to a field getter.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * A reference to a string literal.
	 */
	private String stringLiteral;
	/**
	 * A reference to a comparison operator.
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor.
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}
	
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	/**
	 * Returnes true if the passed in param satisfies expression.
	 * @param record to be examined
	 * @return
	 */
	public boolean apply( StudentRecord record) {
		return comparisonOperator.satisfied( fieldGetter.get( record), stringLiteral);
	}
	
	/**
	 * @return true if this expression represents a direct query, false otherwise.
	 */
	public boolean isDirectExpression() {
		return fieldGetter == FieldValueGetters.JMBAG && 
				comparisonOperator == ComparisonOperators.EQUALS;
	}
}
