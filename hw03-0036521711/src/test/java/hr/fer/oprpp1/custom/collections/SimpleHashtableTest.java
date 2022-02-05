package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

class SimpleHashtableTest {

	@Test
	void capacityLessThan1() {
		assertThrows(IllegalArgumentException.class, () -> {
			new SimpleHashtable<>(-5);
		});
	}

	@Test
	void putRadi() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();

		try {
			d.put("jedan", 1);
		} catch (Exception e) {
			fail("put baca iznimku");
		}
	}

	@Test
	void visePutZaRedom() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();

		try {
			for (int i = 0; i < 100; ++i) {
				d.put(Integer.valueOf(i).toString(), i);
			}
		} catch (Exception e) {
			fail("put ne radi ako se zove vise puta");
		}
	}

	@Test
	void putGaziVrijednost() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();
		Integer returned1 = d.put("jedan", 1);
		Integer returned2 = d.put("jedan", 2);

		assertNull(returned1, "put ne vraca null kada se upisuje nova vrijednost");
		assertEquals(1, returned2, "put ne vraca vrijednost koju je prepisao");
		assertEquals(2, d.get("jedan"), "put ne vraca vrijednost koju je prepisao");
	}

	@Test
	void radiLiGet() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();
		d.put("jedan", 1);
		d.put("dva", 2);
		d.put("tri", 3);

		assertEquals(2, d.get("dva"), "get ne vraca dobru vrijednost");
	}

	@Test
	void getNakonPrepisivanja() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();
		d.put("jedan", 1);
		d.put("dva", 2);
		d.put("tri", 3);

		d.put("dva", 5);
		assertEquals(5, d.get("dva"), "get ne vraca dobru vrijednost");
	}

	@Test
	void putBacaException() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> {
			d.put(null, null);
		}, "put ne baca exception kada je kljuc null");
	}

	@Test
	void removeBrise() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();
		d.put("jedan", 1);
		d.put("dva", 2);

		assertEquals(2, d.remove("dva"), "remove ne brise vrijednosti");
		assertNull(d.get("dva"), "remove nije pobriso");
		assertEquals(1, d.size(), "remove nije pobriso");
	}

	@Test
	void removePaPut() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();
		d.put("jedan", 1);
		d.put("dva", 2);

		d.remove("jedan");
		assertNull(d.put("jedan", 3), "put nije izbacio null iako kljuc nije postojao");
		assertEquals(3, d.get("jedan"), "put nije dobro ubacio vrijednost nakon remove");
	}

	@Test
	void removeNestoStoNema() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();
		d.put("jedan", 1);
		d.put("dva", 2);

		assertNull(d.remove("tri"), "remove nije izbacio null iako nema kljuc");
	}

	@Test
	void clearPaGet() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();
		d.put("jedan", 1);
		d.put("dva", 2);
		d.put("tri", 3);

		d.clear();
		assertNull(d.get("dva"), "clear nije pobriso vrijednosti");
	}

	@Test
	void isEmptyNaknoBrisanja() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>();
		d.put("jedan", 1);
		d.put("dva", 2);

		d.clear();
		assertEquals(true, d.isEmpty(), "clear nije pobriso sve");
	}

	@Test
	void containsKeyRadi() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(32);
		d.put("jedan", 1);
		d.put("dva", 2);

		assertEquals(true, d.containsKey("jedan"), "containsKey ne vraca true za kljuc koji ima");
		assertEquals(false, d.containsKey("fasdfs"), "containsKey ne vraca false za kljuc koji nema");
	}

	@Test
	void containsValueRadi() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(32);
		d.put("jedan", 1);
		d.put("dva", 2);
		d.put("tri", 3);

		assertEquals(true, d.containsValue(2), "containsKey ne vraca true za kljuc koji ima");
		assertEquals(true, d.containsValue(1), "containsKey ne vraca true za kljuc koji ima");
		assertEquals(false, d.containsValue(564), "containsKey ne vraca false za kljuc koji nema");
	}

	@Test
	void toArrayRadi() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(32);
		d.put("jedan", 1);
		d.put("dva", 2);
		d.put("tri", 3);

		assertEquals(3, d.toArray().length, "nije dobra duljina nakon toArray()");
	}

	@Test
	void mijesanjeSvega() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(1);
		for (int i = 0; i < 50; ++i) {
			d.put(Integer.valueOf(i).toString(), i);
		}

		assertEquals(49, d.remove("49"), "remove nije uklonio dobro");
		assertEquals(17, d.put("17", 21), "put nije dobro prepiso");
		assertEquals(21, d.get("17"), "put nije dobro prepiso");
		assertNull(d.get("49"), "remove nije dobro izbacio");

		assertEquals(49, d.size(), "size nije dobro podesavan");
		d.clear();
		for (int i = 0; i < 50; ++i) {
			d.put(Integer.valueOf(i).toString(), i);
		}
		assertEquals(50, d.size(), "nije dobra velicina nakon brisanja pa punjenja");
	}

	@Test
	void iteratorSveBrise() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(2);
		d.put("jedan", 1);
		d.put("dva", 2);
		d.put("tri", 3);
		
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = d.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		
		assertEquals(0, d.size());
	}
	
	@Test
	void iteratorDvaputZaRedomRemove() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(2);
		d.put("jedan", 1);
		d.put("dva", 2);
		d.put("tri", 3);
		
		assertThrows( IllegalStateException.class, ()->{
			Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = d.iterator();
			iter.next();
			iter.remove();
			iter.remove();
		}, "ne baca se iznimka kada iterator za redom poziva remove");
	}
	
	@Test
	void iteratorIzbacujeJedanElement() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(2);
		d.put("jedan", 1);
		d.put("dva", 2);
		d.put("tri", 3);
		
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = d.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("jedan")) {
				iter.remove();
			}
		}
		
		assertNull( d.get("jedan"), "get vraca vrijednost koju je iterator trebo pobrisat");
	}
	
	@Test
	void putNakonBrisanjaIteratorom() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(2);
		d.put("jedan", 1);
		d.put("dva", 2);
		d.put("tri", 3);
		
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = d.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("jedan")) {
				iter.remove();
			}
		}
		
		d.put( "cetiri", 4);
		assertEquals( 3, d.size(), "kriva velicina nakon ubacivanja nakon sto iterator brise element");
	}
	
	@Test
	void modificationException() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(2);
		d.put("jedan", 1);
		d.put("dva", 2);
		d.put("tri", 3);
		
		assertThrows( ConcurrentModificationException.class, ()->{
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = d.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("jedan")) {
				d.remove( "jedan");
			}
		}
		}, "uspjelo se pobrisat mapom dok se iterira po njoj");
	}
	
	@Test
	void removeBriseUListi() {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(16);
		d.put("AaAa", 1);
		d.put("BBBB", 2);
		d.put("AaBB", 3);
		d.put("BBAa", 4);
		
		assertEquals( 2, d.remove( "BBBB"), "remove ne brise kada je objekt u listi");
		assertEquals( 4, d.remove( "BBAa"), "remove ne brise kada je objekt u listi");
		assertEquals( 1, d.get( "AaAa" ), "remove ne brise dobro kada je objekt u listi");
		assertEquals( 3, d.get( "AaBB" ), "remove ne brise dobro kada je objekt u listi");
	}
}
