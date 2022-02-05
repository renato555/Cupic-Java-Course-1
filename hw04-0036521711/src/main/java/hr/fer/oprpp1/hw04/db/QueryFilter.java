package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class which filters StudentRecords.
 * @author renat
 */
public class QueryFilter implements IFilter{
	/**
	 * A reference to a list of expressions.
	 */
	private List<ConditionalExpression> expressions;

	/**
	 * Constructor.
	 * @param expressions
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	/**
	 * @return true if record satisfies all expressions, false otherwise
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for( ConditionalExpression exp : expressions) {
			
			boolean result = exp.apply( record);
			if( !result) return false;
		}
		return true;
	}
}
