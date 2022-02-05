package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Groups document characters into tokens.
 * @author renat
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
		state = LexerState.TEXT_MODE;
	}
	/**
	 * Groups characters into a token depending on lexer state.
	 * In TAG_MODE state:
	 * VARIABLE - starts by letter and after follows zero or more letters, digits or underscores
	 * CONSTANT_INTEGER - integer
	 * CONSTANT_DOUBLE - double
	 * STRING - regular words
	 * FUNCTION - starts with @ after which follows a letter and after that can follow zero or more letters digits or underscores
	 * OPERATOR - +, -, *, / or ^
	 * TAG_NAME - FOR, = or END
	 * Returns TokenType EOF when the end of a document is reached.
	 * Outside tag we can escape '{' and '\' and inside strings inside a tag we can escape '\', '"', 'n', 'r' and 't'
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
		
		if( state == LexerState.TEXT_MODE) {
			// TEXT_MODE - samo ispisuj rijeci
			
			if( jelPocetakTaga()) {
				// pocinje tag
				token = new Token( TokenType.CHANGE_STATE, '$');
				currentIndex += 1; // $ ostaje u bufferu kako bi znali da je sljeda rijec tag name
			}else{
				// samo je neka rijec
				String nextTokenValue = "";
				while( currentIndex < data.length && !jelPraznina( data[currentIndex]) && !jelPocetakTaga()) {
					char c = data[currentIndex];
					if( c == '\\') {
						// escape znakovi
						if( currentIndex+1 < data.length && ( data[currentIndex+1] == '{' || data[currentIndex+1] == '\\')) {
							nextTokenValue += data[currentIndex+1];
							currentIndex += 2;
						}else {
							throw new LexerException( "izvan taga se mogu escapeat samo znak { i \\");
						}
					}else {
						nextTokenValue += c;
						++currentIndex;
					}
				}
				token = new Token( TokenType.STRING, nextTokenValue);
			}
			
		}else {
			// TAG_MODE - grupiraj prema pravilima unutar taga
			if( jelKrajTaga()) {
				// kraj taga
				currentIndex += 2;
				token = new Token( TokenType.CHANGE_STATE, '#');
			}else if( data[currentIndex] == '$') {
				// ovo je tag name = ili FOR ili END
				++currentIndex;
				zanemariPraznine();
				if( currentIndex >= data.length) throw new LexerException( "nodostaje tag name uz $");
				
				if( data[currentIndex] == '=') {
					token = new Token( TokenType.TAG_NAME, "=");
					++currentIndex;
				}else if( currentIndex+2 < data.length){
					String temp = ""+data[currentIndex] + data[currentIndex+1]+data[currentIndex+2];
					if( temp.toUpperCase().equals( "FOR") || temp.toUpperCase().equals( "END")) {
						token = new Token( TokenType.TAG_NAME, temp.toUpperCase());
						currentIndex += 3;
					}else {
						throw new LexerException( "pogresan tag name");
					}
				}else {
					throw new LexerException( "pogresan tag name");
				}
			}else if( Character.isLetter( data[currentIndex])) {
				// ovo je varijabla
				String nextTokenValue = "";
				while( currentIndex < data.length && !jelPraznina( data[currentIndex]) && jelSlovoBrojIliPovlaka( data[currentIndex]) ) {
					nextTokenValue += data[currentIndex];
					currentIndex++;
				}
				token = new Token( TokenType.VARIABLE, nextTokenValue);
			}else if( data[currentIndex] == '@') {
				// ovo je ime funkcije
				++currentIndex;
				String nextTokenValue = "";
				if( !Character.isLetter( data[ currentIndex])) throw new LexerException( "ime funkcije mora poceti sa slovom");
				while( currentIndex < data.length && !jelPraznina( data[currentIndex]) && jelSlovoBrojIliPovlaka( data[currentIndex]) ) {
					nextTokenValue += data[currentIndex];
					currentIndex++;
				}
				if( nextTokenValue.length() == 0) throw new LexerException( "pogresano ime funkcije");
				token = new Token( TokenType.FUNCTION, nextTokenValue);
			}else if( data[currentIndex] == '"') {
				// ovo je string
				++currentIndex;
				String nextTokenValue = "";
				while( currentIndex < data.length && data[currentIndex] != '\"') {
					char c = data[currentIndex];
					if( c == '\\' ) {
						// escape znakovi
						if( currentIndex+1 < data.length) {
							char next = data[currentIndex+1]; 
							if( next == '\\') {
								nextTokenValue += '\\';
							}else if( next == '\"'){
								nextTokenValue += '\"';
							}else if( next == 'n') {
								nextTokenValue += '\n';
							}else if( next == 'r') {
								nextTokenValue += '\r';
							}else if( next == 't') {
								nextTokenValue += '\t';
							}else {
								throw new LexerException( "nedozvoljeno koristenje znaka \\"); 
							}
							currentIndex += 2;
						}else {
							throw new LexerException( "nedozvoljeno koristenje znaka \\");
						}
					}else {
						nextTokenValue += c;
						currentIndex += 1;
					}
				}
				++currentIndex; //trash "
				token = new Token( TokenType.STRING, nextTokenValue);
			}else if( Character.isDigit( data[currentIndex]) || (data[currentIndex] == '-' && currentIndex+1<data.length && Character.isDigit( data[currentIndex+1]))) {
				// ovo je broj
				char c = data[currentIndex];
				boolean isNegative = false;
				if( c == '-') {
					isNegative = true;
					++currentIndex;
				}
				
				String num = "";
				while( currentIndex < data.length && Character.isDigit( data[currentIndex])) {
					num += data[currentIndex];
					++currentIndex;
				}
				if( data[currentIndex] == '.') {
					// double
					num += data[currentIndex++];
					while( currentIndex < data.length && Character.isDigit( data[currentIndex])) {
						num += data[currentIndex];
						++currentIndex;
					}
					try {
						double number = Double.parseDouble( num);
						if( isNegative) number *= -1;
						token = new Token( TokenType.CONSTANT_DOUBLE, number);
					}catch( Exception e) {
						throw new LexerException( "broj se ne moze pretvorit u double");
					}
				}else {
					// integer
					try {
						int number = Integer.parseInt( num);
						if( isNegative) number *= -1;
						token = new Token( TokenType.CONSTANT_INTEGER, number);
					}catch( Exception e) {
						throw new LexerException( "broj se ne moze pretvorit u integer");
					}
				}
			}else if( jelOperator()) {
				// ovo je operator
				String operator = "" + data[currentIndex++];
				token = new Token( TokenType.OPERATOR, operator);
			}else {
				// ovo je smeće
				throw new LexerException( "ne prepoznaje sljedeci znak");
			}
		}
		
		return getToken();
	}
	
	/**
	 * Skips blanks.
	 */
	private void zanemariPraznine() {
		while( currentIndex < data.length && jelPraznina( data[currentIndex])) {
			++currentIndex;
		}
	}
	/**
	 * @return true if currentIndex is at a start of a tag, false otherwise
	 */
	private boolean jelPocetakTaga() {
		return data[currentIndex] == '{' && currentIndex+1 < data.length && data[currentIndex+1] == '$';
	}
	/**
	 * @return true if currentIndex is at an end of a tag, false otherwise
	 */
	private boolean jelKrajTaga() {
		return data[currentIndex] == '$' && currentIndex+1 < data.length && data[currentIndex+1] == '}'; 
	}
	/**
	 * @param c char to be examined 
	 * @return true if a parameter is a blank, false otherwise
	 */
	private boolean jelPraznina( char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}
	/**
	 * @param c char to be examined 
	 * @return true if a parameter is a letter or a digit or a underscode, false otherwise
	 */
	private boolean jelSlovoBrojIliPovlaka( char c) {
		return Character.isLetter( c) || Character.isDigit( c) || c == '_';
	}
	/**
	 * @return true if a char at position currentIndex is an operator
	 */
	private boolean jelOperator() {
		char c = data[currentIndex];
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
	}
	
	public Token getToken() { 
		return token;
	}

	/**
	 * Sets current lexer state.
	 * @param state next lexer state
	 * @throws NullPointerException if state equals null
	 */
	public void setState( LexerState state) {
		if( state == null) throw new NullPointerException( "state ne moze biti null");
		
		this.state = state;
	}
}
