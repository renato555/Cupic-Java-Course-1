package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	@Test
	void prolazi() {
		ConditionalExpression exp = new ConditionalExpression( FieldValueGetters.JMBAG, "0000000001", ComparisonOperators.GREATER_OR_EQUALS);
		assertEquals( true, exp.apply( new StudentRecord( "0000000001", "pero", "peric", 2)), "apply ne radi");
	}

	@Test
	void neProlazi() {
		ConditionalExpression exp = new ConditionalExpression( FieldValueGetters.JMBAG, "0000000001", ComparisonOperators.NOT_EQUALS);
		assertEquals( false, exp.apply( new StudentRecord( "0000000001", "pero", "peric", 2)), "apply ne radi");
	}
	
	@Test
	void isDirectQueryVracaTrue() {
		ConditionalExpression exp = new ConditionalExpression( FieldValueGetters.JMBAG, "0000000001", ComparisonOperators.EQUALS);
		assertEquals( true, exp.isDirectExpression(), "isDirectExpression ne radi");
	}
	
	@Test
	void isDirectQueryVracaFalse() {
		ConditionalExpression exp = new ConditionalExpression( FieldValueGetters.JMBAG, "0000000001", ComparisonOperators.GREATER_OR_EQUALS);
		assertEquals( false, exp.isDirectExpression(), "isDirectExpression ne radi");
	}
}
