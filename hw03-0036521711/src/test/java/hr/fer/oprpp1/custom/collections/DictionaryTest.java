package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void putRadi() {
		Dictionary<String, Integer> d = new Dictionary<>();
		
		try {
			d.put( "jedan", 1);
		}catch( Exception e) {
			fail( "put baca iznimku");
		}
	}
	
	@Test
	void visePutZaRedom() {
		Dictionary<String, Integer> d = new Dictionary<>();
		
		try {
			for( int i = 0; i< 10; ++i) {
				d.put( Integer.valueOf(i).toString(), i);
			}
		}catch( Exception e) {
			fail( "put ne radi ako se zove vise puta");
		}
	}
	
	@Test
	void putGaziVrijednost() {
		Dictionary<String, Integer> d = new Dictionary<>();
		Integer returned1 = d.put( "jedan", 1);
		Integer returned2 = d.put( "jedan", 2);
		
		assertNull( returned1, "put ne vraca null kada se upisuje nova vrijednost");
		assertEquals( 1, returned2, "put ne vraca vrijednost koju je prepisao");
		assertEquals( 2, d.get("jedan"), "put ne vraca vrijednost koju je prepisao");
	}
	
	@Test
	void radiLiGet() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put( "jedan", 1);
		d.put( "dva", 2);
		d.put( "tri", 3);
		
		assertEquals( 2, d.get("dva"), "get ne vraca dobru vrijednost");
	}
	
	@Test
	void getNakonPrepisivanja() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put( "jedan", 1);
		d.put( "dva", 2);
		d.put( "tri", 3);
		
		d.put( "dva", 5);
		assertEquals( 5, d.get("dva"), "get ne vraca dobru vrijednost");
	}
	
	@Test
	void putBacaException() {
		Dictionary<String, Integer> d = new Dictionary<>();
		assertThrows( NullPointerException.class, ()->{ d.put( null, null);}, "put ne baca exception kada je kljuc null");
	}
	
	@Test
	void removeBrise() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put( "jedan", 1);
		d.put( "dva", 2);
		
		assertEquals( 2, d.remove("dva"), "remove ne brise vrijednosti");
		assertNull( d.get( "dva"), "remove nije pobriso");
		assertEquals( 1, d.size(), "remove nije pobriso");
	}
	
	@Test
	void removePaPut() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put( "jedan", 1);
		d.put( "dva", 2);
		
		d.remove( "jedan");
		assertNull(d.put( "jedan", 3), "put nije izbacio null iako kljuc nije postojao");
		assertEquals( 3, d.get("jedan"), "put nije dobro ubacio vrijednost nakon remove");
	}
	
	@Test
	void removeNestoStoNema() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put( "jedan", 1);
		d.put( "dva", 2);
		
		assertNull( d.remove( "tri"), "remove nije izbacio null iako nema kljuc");
	}
	
	@Test
	void clearPaGet() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put( "jedan", 1);
		d.put( "dva", 2);
		d.put( "tri", 3);
		
		d.clear();
		assertNull( d.get("dva"), "clear nije pobriso vrijednosti");
	}
	
	@Test
	void isEmptyNaknoBrisanja(){
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put( "jedan", 1);
		d.put( "dva", 2);
		
		d.clear();
		assertEquals( true, d.isEmpty(), "clear nije pobriso sve");
	}
	
}
