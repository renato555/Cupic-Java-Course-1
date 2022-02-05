package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryParserTest {

	@Test
	void primjer1() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals( true,  qp1.isDirectQuery(), "direct query ne radi"); // true
		assertEquals( "0123456789", qp1.getQueriedJMBAG()); // 0123456789"
		assertEquals( 1, qp1.getQuery().size()); // 1
	}

	@Test
	void primjer2() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals( false, qp2.isDirectQuery()); // false
		assertThrows( IllegalStateException.class, ()->qp2.getQueriedJMBAG());
		assertEquals( 2, qp2.getQuery().size()); // 2
	}
}
