package hr.fer.oprpp1.hw02.prob1;

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
	 * Current lexer state
	 */
	private LexerState state;
	
	/**
	 * Default constructor.
	 * @param text document that we want to process
	 * @throws NullPointerException if text == null
	 */
	public Lexer(String text) {
		if( text == null) throw new NullPointerException( "text ne moze biti null");
		
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	/**
	 * Groups characters into a token depending on lexer state.
	 * In BASIC state:
	 * WORD - consecutive letters ( we can escape numbers and '\' ).
	 * NUMBER - consecutive numbers.
	 * SYMBOL - everything else that is not a word, number or blank.
	 * In EXTENDED state everythings is a word.
	 * Returns TokenType EOF when the end of a document is reached.
	 * @return next Token
	 * @throws LexerException if an exception happens
	 */
	public Token nextToken() { 
		zanemariPraznine();
		
		// provjera jesmo li dosli do kraja niza
		if( currentIndex >= data.length) {
			if( token == null || token.getType() != TokenType.EOF) {
				token = new Token( TokenType.EOF, null);
				return getToken();
			}else {
				throw new LexerException( "ne moze se generirati token jer je dosegnut kraj");
			}
		}
		
		if( state == LexerState.BASIC) {			
			String nextTokenValue = "";
			if(  Character.isLetter( data[currentIndex]) || data[currentIndex] =='\\') {
				// sljedeci token je rijec
				while( currentIndex < data.length) {
					if( Character.isLetter( data[currentIndex])) {
						// normalno slovo
						nextTokenValue += data[currentIndex++];
					}else if( data[currentIndex] == '\\') {
						// escape znakovi
						if( currentIndex+1 < data.length && ( Character.isDigit( data[currentIndex+1]) || data[currentIndex+1]=='\\')) {
							nextTokenValue += data[currentIndex + 1];
							currentIndex += 2;
						}else {
							//throw err
							throw new LexerException( "dozvoljeno je samo escapeanje brojeva i znaka \\");
						}
					}else {
						break;
					}
				}
				token = new Token( TokenType.WORD, nextTokenValue);
				
			}else if( Character.isDigit( data[currentIndex])) {
				// sljedeci token je broj
				while( currentIndex < data.length && Character.isDigit( data[currentIndex])) {
					nextTokenValue += data[currentIndex++];
				}
				try {
					long digit = Long.parseLong( nextTokenValue);
					token = new Token( TokenType.NUMBER, digit);
				}catch( NumberFormatException e) {
					throw new LexerException( "broj je pre velik da bi se parsiro u Long");
				}
				
			}else {
				// sljedeci token je simbol
				token = new Token( TokenType.SYMBOL, data[currentIndex++]);
			}
		}else {
			//stanje extended
			if( data[currentIndex] == '#') {
				token = new Token( TokenType.SYMBOL, '#');
				++currentIndex;
			}else {
				String nextTokenValue = "";
				while( currentIndex < data.length && data[currentIndex] != ' ' && data[currentIndex] != '\t'
						&& data[currentIndex] != '\n' && data[currentIndex] != '\r'
						&& data[currentIndex] != '#') {
					nextTokenValue += data[currentIndex++];
				}
				token = new Token( TokenType.WORD, nextTokenValue);				
			}
		}
		
		return getToken();
	}
	/**
	 * Skips blanks.
	 */
	private void zanemariPraznine() {
		while( currentIndex < data.length && (data[currentIndex] == ' ' || data[currentIndex] == '\t'
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
	
	/**
	 * Sets lexer state.
	 * @param state next lexer state value
	 * @throws NullPointerException if state == null
	 */
	public void setState( LexerState state) {
		if( state == null) throw new NullPointerException( "state ne moze biti null");
		
		this.state = state;
	}
}
