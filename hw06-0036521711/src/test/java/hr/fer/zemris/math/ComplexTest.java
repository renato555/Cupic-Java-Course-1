package hr.fer.zemris.math;

import java.util.List;

import org.junit.jupiter.api.Test;

class ComplexTest {

	@Test
	void power() {
		Complex res = new Complex( 1,-1).power( 5);
		System.out.println( res);
	}

	@Test
	void sqrt() {
		List<Complex> res = new Complex( 1, -1).root( 5);
	}
}
