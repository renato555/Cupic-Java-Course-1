package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

class QueryFilterTest {

	@Test
	void testProlazi() {
		List<ConditionalExpression> list = new LinkedList<>();
		list.add( new ConditionalExpression( FieldValueGetters.JMBAG, "0000000001", ComparisonOperators.GREATER_OR_EQUALS));
		list.add( new ConditionalExpression( FieldValueGetters.FIRST_NAME, "*", ComparisonOperators.LIKE));
		QueryFilter filter = new QueryFilter(list);
		
		assertEquals( true, filter.accepts( new StudentRecord( "0000000001", "pero", "peric", 2)), "ne moze se prihvatiti");
	}

	@Test
	void testNeProlazi() {
		List<ConditionalExpression> list = new LinkedList<>();
		list.add( new ConditionalExpression( FieldValueGetters.JMBAG, "0000000001", ComparisonOperators.EQUALS));
		list.add( new ConditionalExpression( FieldValueGetters.FIRST_NAME, "*", ComparisonOperators.LIKE));
		QueryFilter filter = new QueryFilter(list);
		
		assertEquals( false, filter.accepts( new StudentRecord( "0000000003", "pero", "peric", 2)), "ne odbacuje se");
	}
}
