package hr.fer.oprpp1.hw04.db;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that parses a query.
 * @author renat
 */
public class QueryParser {
	/**
	 * Input query string.
	 */
	private String queryString;
	/**
	 * List of expressions.
	 */
	private List<ConditionalExpression> expressions;
	
	private boolean withStatistics = false;
	
	/**
	 * Constructor
	 * @param queryString input query
	 */
	public QueryParser( String queryString) {
		this.queryString = queryString;
		expressions = new LinkedList<>();
		
		parse();
	}
	
	/**
	 * Parses Query.
	 * desired query format: field operator string.
	 * @throws IllegalStateException query is not in a desired format
	 */
	private void parse() {
		Lexer lexer = new Lexer( queryString);
		Token currToken= lexer.nextToken();
		while( currToken.getType() != TokenType.EOF) {
			// trazena struktura je field operator string
			IFieldValueGetter fg = getFieldGetter( currToken);
			
			currToken = lexer.nextToken();
			IComparisonOperator co = getComparisonOperator( currToken);
			
			currToken = lexer.nextToken();
			if( currToken.getType() != TokenType.STRING) throw new IllegalStateException( "ocekivan je string");
			String stringLiteral = (String) currToken.getValue();
			
			ConditionalExpression exp = new ConditionalExpression( fg, stringLiteral, co);
			expressions.add( exp);
			
			currToken = lexer.nextToken();
			// preskoci 'and' ako ga ima
			if( currToken.getType() == TokenType.LOGICAL_OPERATOR && currToken.getValue().equals("and")) {
				currToken = lexer.nextToken();
			}
			
			if( currToken.getType() == TokenType.STATISTICS && currToken.getValue().equals( "with-statistics")) {
				withStatistics = true;
				currToken = lexer.nextToken();
				if( currToken.getType() != TokenType.EOF) throw new IllegalArgumentException( "with-statistics moze doci samo na kraj");
			}
		}
	};
	
	/**
	 * @param token
	 * @return IFieldValueGetter depending on token.value
	 * @throws IllegalStateException if token.type != TokenType.FIELD
	 */
	private IFieldValueGetter getFieldGetter( Token token) {
		if( token.getType() != TokenType.FIELD) throw new IllegalStateException( "ocekivan je field");
		
		String tokenValue = (String) token.getValue();
		if( tokenValue.equals( "jmbag")) {
			return FieldValueGetters.JMBAG;
		}else if( tokenValue.equals( "firstName")) {
			return FieldValueGetters.FIRST_NAME;
		}else if( tokenValue.equals( "lastName")) {
			return FieldValueGetters.LAST_NAME;
		}else{
			throw new IllegalStateException( "nepoznato ime fielda");
		}
	}
	
	/**
	 * @param token
	 * @return IComparisonOperator depending on token.value
	 * @throws IllegalStateException if token.type != TokenType.OPERATOR
	 */
	private IComparisonOperator getComparisonOperator( Token token) {
		if( token.getType() != TokenType.OPERATOR) throw new IllegalStateException( "ocekivan je operator");
		
		String tokenValue = (String) token.getValue();
		if( tokenValue.equals( ">")) {
			return ComparisonOperators.GREATER;
		}else if( tokenValue.equals( "<")) {
			return ComparisonOperators.LESS;
		}else if( tokenValue.equals( ">=")) {
			return ComparisonOperators.GREATER_OR_EQUALS;
		}else if( tokenValue.equals( "<=")) {
			return ComparisonOperators.LESS_OR_EQUALS;
		}else if( tokenValue.equals( "=")) {
			return ComparisonOperators.EQUALS;
		}else if( tokenValue.equals( "!=")) {
			return ComparisonOperators.NOT_EQUALS;
		}else if( tokenValue.equals( "like")) {
			return ComparisonOperators.LIKE;
		}else {
			throw new IllegalStateException( "nepoznat operator");
		}
	}
	
	/**
	 * @return if query is a direct query, false otherwise
	 */
	public boolean isDirectQuery() {
		if( expressions.size() != 1) return false;
		
		ConditionalExpression expression = expressions.get(0);
		return  expression.isDirectExpression();
	}
	
	/**
	 * @return jmbag literal from query
	 * @throws IllegalStateException query is not a direct query
	 */
	public String getQueriedJMBAG() {
		if( !isDirectQuery()) throw new IllegalStateException( "query nije direktan");
		
		ConditionalExpression expression = expressions.get(0);
		return expression.getStringLiteral();
	}
	
	/**
	 * @return a list of all conditionalExpressions parsed from query
	 */
	public List<ConditionalExpression> getQuery(){
		return expressions;
	}
	
	public boolean getWithStatistics() {
		return withStatistics;
	}
}
