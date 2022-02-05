package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void hexToByteNormal() {
		assertArrayEquals( new byte[] {1, -82, 34}, Util.hextobyte( "01aE22"), "hextobyte ne radi dobro");
	}
	
	@Test
	void hexToByteThrowsError() {
		assertThrows( IllegalArgumentException.class, ()->{ Util.hextobyte( "01aE2");}, "hextobyte ne baca error za neparan input");
	}
	
	@Test
	void hexToByteThrowsError2() {
		assertThrows( IllegalArgumentException.class, ()->{ Util.hextobyte( "01aE2g");}, "hextobyte ne baca error za nepoznat character");
	}
	
	@Test
	void hexToByteEmptyArray() {
		assertArrayEquals( new byte[0], Util.hextobyte( ""), "hextobyte ne vraca prazan array za prazan string");
	}
	
	@Test
	void byteToHexNormal() {
		assertEquals( "01ae22", Util.bytetohex( new byte[] {1, -82, 34}), "bytetohex ne radi dobro");
	}
	
	@Test
	void byteToHexEmptyArray() {
		assertEquals( "", Util.bytetohex( new byte[0]), "bytetohex ne vraca prazan string za prazan array");
	}

}
