package hr.fer.oprpp1.hw05.crypto;

/**
 * Contains helper functions.
 * @author renat
 */
public class Util {

	/**
	 * Converts given string into byte array.
	 * @param keyText number in hexadecimal notation
	 * @return corresponding byte array.
	 * @throws IllegalArgumentException if keyText.length() != 0 or if keyText contains invalid characters
	 * @throws NullPointerException if keyText equals null
	 */
	public static byte[] hextobyte(String keyText) {
		int n = keyText.length();
		if( n % 2 != 0) throw new IllegalArgumentException( "key text ne moze biti neparne duljine");
		
		char[] keyTextChar = keyText.toCharArray();
		byte[] result = new byte[ n/2];
		if( n == 0) return result;
		
		for( int i = 0; i < n; i+=2) {
			byte first = hexDigitToLowerBytePart( keyTextChar[i]);
			byte second = hexDigitToLowerBytePart( keyTextChar[i+1]);
			
			result[i/2] = (byte) ((first << 4) | second);
		}
		
		return result;
	}
	/**
	 * @param c char that we want to convert
	 * @return corresponding byte value
	 * @throws IllegalArgumentException if c is not a hex character
	 */
	private static byte hexDigitToLowerBytePart( char c) {
		if( Character.isDigit( c)) return (byte) (c-'0');
		c = Character.toLowerCase( c);
		if( c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f') {
			return (byte) (c - 'a' + 10); 
		}
		
		throw new IllegalArgumentException( "nepoznata hex znamenka");
	}
	
	/**
	 * Converts given byte array into a string.
	 * @param byteArray byte array which we wish to convert
	 * @return corresponding number in hexadecimal notation
	 * @throws NullPointerException if byteArray equals null
	 */
	public static String bytetohex( byte[] byteArray) {
		if( byteArray == null) throw new NullPointerException( "byte array ne moze biti null");
		
		StringBuilder builder = new StringBuilder();
		for( byte b : byteArray) {
			char second = lowerBytePartToHexDigit( b);
			b = (byte) (b >> 4);
			char first = lowerBytePartToHexDigit( b);
			builder.append( first);
			builder.append( second);
		}
		return builder.toString();
	}

	/**
	 * Converts the lower part of argument into a hexadecimal character
	 * @param b byte which we wish to convert
	 * @return corresponding hexadecimal character
	 */
	private static char lowerBytePartToHexDigit( byte b) {
		b = (byte) (b & (byte) 0x0f);
		if( b <= 0x9) return (char) (b + '0');
		if( b == 0xa) return 'a';
		if( b == 0xb) return 'b';
		if( b == 0xc) return 'c';
		if( b == 0xd) return 'd';
		if( b == 0xe) return 'e';
		
		return 'f';
	}
}
