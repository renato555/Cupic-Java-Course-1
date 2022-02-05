package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	@Test
	void firstName() {
		StudentRecord r = new StudentRecord( "0000000001", "Marin", "Akšamović", 2);
		
		assertEquals( "Marin", FieldValueGetters.FIRST_NAME.get( r), "first name ne radi");
	}

	@Test
	void lastName() {
		StudentRecord r = new StudentRecord( "0000000001", "Marin", "Akšamović", 2);
		
		assertEquals( "Akšamović", FieldValueGetters.LAST_NAME.get( r), "last name ne radi");
	}
	
	@Test
	void jmbag() {
		StudentRecord r = new StudentRecord( "0000000001", "Marin", "Akšamović", 2);
		
		assertEquals( "0000000001", FieldValueGetters.JMBAG.get( r), "jmbag ne radi");
	}
}
