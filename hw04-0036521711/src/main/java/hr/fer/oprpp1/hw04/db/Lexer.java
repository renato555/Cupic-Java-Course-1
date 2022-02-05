package hr.fer.oprpp1.hw04.db;

/**
 * Groups document characters into tokens.
 */
public class Lexer {
	/**
	 * Contains current document.
	 */
	private char[] data;
	/**
	 * Current token.
	 */
	private Token token;
	/**
	 * Current position in the document.
	 */
	private int currentIndex;

	/**
	 * Default constructor.
	 * @param text document that we want to process
	 * @throws NullPointerException if text == null
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Groups characters into tokens.
	 * In BASIC state:
	 * FIELD - jmbag, lastname, firstname
	 * STRING - anything inside quotation marks
	 * OPERATOR - >, <, >=, <=, =, !=, LIKE
	 * LOGICAL_OPERATOR AND
	 * Returns TokenType EOF when the end of a document is reached.
	 * @return next Token
	 * @throws LexerException if an exception happens
	 */
	public Token nextToken() {
		zanemariPraznine();

		// provjera jesmo li dosli do kraja niza
		if (currentIndex >= data.length) {
			if (token == null || token.getType() != TokenType.EOF) {
				token = new Token(TokenType.EOF, null);
				return getToken();
			} else {
				throw new IllegalStateException( "ne moze se generirati token jer je dosegnut kraj");
			}
		}
		
		char c = data[currentIndex];
		if( c == '\"') {
			// ovo je string
			++currentIndex;
			StringBuilder builder = new StringBuilder();
			int i, n;
			for( i = currentIndex, n = data.length; i < n && data[i] != '\"'; ++i) {
				builder.append( data[i]);
			}
			currentIndex = i; 
			if( currentIndex >= data.length) throw new IllegalStateException( "pogresno zadan string");
			
			++currentIndex; // izbaci zadnji "
			token = new Token( TokenType.STRING, builder.toString());
			
		}else if( jelOperator( c)) {
			// ovo je operator - LIKE
			if( currentIndex + 1 < data.length && data[currentIndex] == '>' && data[currentIndex+1] == '=') {
				currentIndex += 2;
				token = new Token( TokenType.OPERATOR, ">=");
			}else if( currentIndex + 1 < data.length && data[currentIndex] == '<' && data[currentIndex+1] == '=') {
				currentIndex += 2;
				token = new Token( TokenType.OPERATOR, "<=");
			}else if( currentIndex + 1 < data.length && data[currentIndex] == '!' && data[currentIndex+1] == '=') {
				currentIndex += 2;
				token = new Token( TokenType.OPERATOR, "!=");
			}else if( data[currentIndex] == '>') {
				++currentIndex;
				token = new Token( TokenType.OPERATOR, ">");
			}else if( data[currentIndex] == '<') {
				++currentIndex;
				token = new Token( TokenType.OPERATOR, "<");
			}else if( data[currentIndex] == '=') {
				++currentIndex;
				token = new Token( TokenType.OPERATOR, "=");
			}
			
		}else if( Character.isLetter( c)){
			// ovdje moze upast field ili and ili LIKE ili with-statistics
			StringBuilder builder = new StringBuilder();
			int i, n;
			for( i = currentIndex, n = data.length; i < n && (Character.isLetter( data[i]) || data[i] == '-'); ++i) {
				builder.append( data[i]);
			}
			currentIndex = i;
			String newTokenValue = builder.toString();
			String newTokenValueSmall = newTokenValue.toLowerCase();
			
			if( newTokenValueSmall.equals( "and")) {
				token = new Token( TokenType.LOGICAL_OPERATOR, "and");
			}else if( newTokenValueSmall.equals( "like")) {
				token = new Token( TokenType.OPERATOR, "like");
			}else if( newTokenValue.equals( "with-statistics")){
				token = new Token( TokenType.STATISTICS, newTokenValue);
			}else{
				token = new Token( TokenType.FIELD, newTokenValue);
			}
			
		}else {
			// ne znam sta je
			throw new IllegalStateException( "lexer ne prepoznaje sljedeci token");
		}
		
		return getToken();
	}
	
	/**
	 * @param c char in question
	 * @return true if operator, false otherwise
	 */
	private boolean jelOperator( char c) {
		return c == '>' || c == '<' || c == '=' || c == '!';
	}
	
	/**
	 * Skips blanks.
	 */
	private void zanemariPraznine() {
		while (currentIndex < data.length && (data[currentIndex] == ' ' || data[currentIndex] == '\t'
				|| data[currentIndex] == '\n' || data[currentIndex] == '\r')) {
			++currentIndex;
		}
	}
	
	/**
	 * @return current token value
	 */
	public Token getToken() { 
		return token;
	}
}
