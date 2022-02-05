package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * A class that contains grouped characters.
 * @author renat
 *
 */
public class Token {
	/**
	 * Type of token.
	 */
	private TokenType type;
	/**
	 * Current value of a token.
	 */
	private Object value;
	
	/**
	 * Default constructor.
	 * @param type type of a token
	 * @param value value of a token
	 * @throws NullPointerException if type equals null 
	 */
	public Token(TokenType type, Object value) {
		if( type == null) throw new NullPointerException( "type ne moze biti null");
		this.type = type;
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	public TokenType getType() {
		return type;
	}
}
