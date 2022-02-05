package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LexerTest {

	@Test
	void nullUKonstruktoru() {
		assertThrows( NullPointerException.class, ()->{ new Lexer(null);}, "null pointer u konstruktoru");
	}

	@Test
	void parvilnoVracaString() {
		String test = "Ovo je string";
		Lexer lexer = new Lexer( test);
		assertEquals( TokenType.STRING, lexer.nextToken().getType(), "krivi tokenType izvan taga");
	}
	
	@Test
	void pravilnoEscape() {
		String test = "\\\\ ovo je escape";
		Lexer lexer = new Lexer( test);
		assertEquals( "\\", lexer.nextToken().getValue(), "krivi tokenType izvan taga");
	}
	
	@Test
	void kriviEscape() {
		String test = "\\n nedozvoljeni escape";
		assertThrows( LexerException.class, ()-> {(new Lexer(test)).nextToken();}, "nedozvoljeni escape");
	}
	
	
	@Test
	void prepoznajePocetakTAga() {
		String test = "{$= \" pocetak taga\"";
		Lexer lexer = new Lexer( test);
		
		Token token = lexer.nextToken();
		assertEquals( TokenType.CHANGE_STATE, token.getType(), "ne prepoznaje pocetak taga");

		lexer.setState( LexerState.TAG_MODE);
		token = lexer.nextToken();
		assertEquals( TokenType.TAG_NAME, token.getType(), "ne prepoznaje ime taga");
		
	}
}
