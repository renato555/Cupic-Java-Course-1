package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

class SmartScriptParserTest {
	
	//primjeri od 1-9 --------------------------------------
	@Test
	public void primjer1() {
		String input = readExample( 1, "extra");
		SmartScriptParser parser = new SmartScriptParser( input);
		assertEquals( 1, parser.getDocumentNode().numberOfChildren());
	}
	@Test
	public void primjer2() {
		String input = readExample( 2, "extra");
		SmartScriptParser parser = new SmartScriptParser( input);
		assertEquals( 1, parser.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void primjer3() {
		String input = readExample( 3, "extra");
		SmartScriptParser parser = new SmartScriptParser( input);
		assertEquals( 1, parser.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void primjer4() {
		String input = readExample( 4, "extra");
		assertThrows( SmartScriptParserException.class, ()->{ new SmartScriptParser( input);});
	}
	
	@Test
	public void primjer5() {
		String input = readExample( 5, "extra");
		assertThrows( SmartScriptParserException.class, ()->{ new SmartScriptParser( input);});
	}
	
	@Test
	public void primjer6() {
		String input = readExample( 6, "extra");
		SmartScriptParser parser = new SmartScriptParser( input);
		assertEquals( 1, parser.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void primjer7() {
		String input = readExample( 7, "extra");
		assertThrows( SmartScriptParserException.class, ()->{ new SmartScriptParser( input);});
	}
	
	@Test
	public void primjer8() {
		String input = readExample( 8, "extra");
		SmartScriptParser parser = new SmartScriptParser( input);
		assertEquals( 1, parser.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void primjer9() {
		String input = readExample( 9, "extra");
		assertThrows( SmartScriptParserException.class, ()->{ new SmartScriptParser( input);});
	}
	
	//moji primjeri -----------------------------------------
	@Test
	public void primjer10() {
		String input = readExample( 10, "examples");
		// sve je ok u inputu
		trebajuBitIsti( input);
	}

	@Test
	public void testPrimjer11() {
		String input = readExample( 11, "examples");
		// sve je ok u inputu
		trebajuBitIsti( input);
	}
	
	@Test
	public void primjer12() {
		String input = readExample( 12, "examples");
		// sve je ok u inputu
		trebajuBitIsti( input);
	}
	
	@Test
	public void primjer13() {
		String input = readExample( 13, "examples");
		// kriva for petlja 
		assertThrows( SmartScriptParserException.class, ()->{ new SmartScriptParser( input);});
	}
	
	@Test
	public void primjer14() {
		String input = readExample( 14, "examples");
		// sve je ok u inputu
		trebajuBitIsti( input);
	}
	
	private void trebajuBitIsti( String docBody) {
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); 
		if( !same) {
			fail( "testovi nisu isti");
		}
	}
	
	private String readExample(int n, String directory) {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(directory + "/primjer"+n+".txt")) {
			if(is==null) throw new RuntimeException("Datoteka " + directory + "/primjer"+n+".txt je nedostupna.");
			byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		}catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}
}
