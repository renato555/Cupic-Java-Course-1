package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	@Test
	void likeTest() {
		
		assertEquals( true, ComparisonOperators.LIKE.satisfied( "ABC", "A*"), "ne radi like");
		assertEquals( true, ComparisonOperators.LIKE.satisfied( "ABC", "*C"), "ne radi like");
		assertEquals( true, ComparisonOperators.LIKE.satisfied( "ABC", "ABC"), "ne radi like");
		assertEquals( false, ComparisonOperators.LIKE.satisfied( "ABC", "AB"), "ne radi like");
		
		assertEquals( true, ComparisonOperators.LIKE.satisfied( "ABCCBA", "AB*BA"), "ne radi like");
		assertEquals( true, ComparisonOperators.LIKE.satisfied( "ABCCBA", "ABC*CBA"), "ne radi like");
		assertEquals( false, ComparisonOperators.LIKE.satisfied( "ABCCBA", "ABCD*CBA"), "ne radi like");
		assertEquals( false, ComparisonOperators.LIKE.satisfied( "AAA", "AA*AA"), "ne radi like");
		assertEquals( true, ComparisonOperators.LIKE.satisfied( "AAA", "*"), "ne radi like");
		
		assertEquals( false, ComparisonOperators.LIKE.satisfied( "Zagreb", "Aba*"), "ne radi like");
		assertEquals( true, ComparisonOperators.LIKE.satisfied( "AAAA", "AA*AA"), "ne radi like");
	}
	
	@Test
	void greaterTest() {
		assertEquals( true, ComparisonOperators.GREATER.satisfied( "3", "2"), "ne radi greater");
		assertEquals( false, ComparisonOperators.GREATER.satisfied( "2", "2"), "ne radi greater");
		assertEquals( false, ComparisonOperators.GREATER.satisfied( "2", "3"), "ne radi greater");
	}

	@Test
	void greaterOrEqualTest() {
		assertEquals( true, ComparisonOperators.GREATER_OR_EQUALS.satisfied( "3", "2"), "ne radi greater or equals");
		assertEquals( true, ComparisonOperators.GREATER_OR_EQUALS.satisfied( "2", "2"), "ne radi greater or equals");
		assertEquals( false, ComparisonOperators.GREATER_OR_EQUALS.satisfied( "2", "3"), "ne radi greater or equals");
	}
	
	@Test
	void lessTest() {
		assertEquals( false, ComparisonOperators.LESS.satisfied( "3", "2"), "ne radi less");
		assertEquals( false, ComparisonOperators.LESS.satisfied( "2", "2"), "ne radi less");
		assertEquals( true, ComparisonOperators.LESS.satisfied( "2", "3"), "ne radi less");
		assertEquals( true, ComparisonOperators.LESS.satisfied( "Ana", "Jasna"), "ne radi less");
	}
	
	@Test
	void lessOrEqualTest() {
		assertEquals( false, ComparisonOperators.LESS_OR_EQUALS.satisfied( "3", "2"), "ne radi less or equals");
		assertEquals( true, ComparisonOperators.LESS_OR_EQUALS.satisfied( "2", "2"), "ne radi less or equals");
		assertEquals( true, ComparisonOperators.LESS_OR_EQUALS.satisfied( "2", "3"), "ne radi less or equals");
	}
	
	@Test
	void equals() {
		assertEquals( false, ComparisonOperators.EQUALS.satisfied( "3", "2"), "ne radi equals");
		assertEquals( true, ComparisonOperators.EQUALS.satisfied( "2", "2"), "ne radi equals");
	}
	
	@Test
	void notEquals() {
		assertEquals( true, ComparisonOperators.NOT_EQUALS.satisfied( "3", "2"), "ne radi not equals");
		assertEquals( false, ComparisonOperators.NOT_EQUALS.satisfied( "2", "2"), "ne radi not equals");
	}
}
