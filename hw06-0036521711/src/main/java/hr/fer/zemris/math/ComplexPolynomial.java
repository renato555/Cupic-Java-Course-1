package hr.fer.zemris.math;

/**
 * Represents a polynomial in a standard form.
 * @author renat
 *
 */
public class ComplexPolynomial {
	
	/**
	 * Coefficients of a polynomial.
	 */
	private Complex[] coefficients;
	
	/**
	 * Constructs a polynomial.
	 * @param Coefficients of a polynomial.
	 */
	public ComplexPolynomial(Complex ...factors) {
		if( factors != null) {
			coefficients = factors;
		}else {
			coefficients = new Complex[0];
		}
	}
	
	/**
	 * @return order of this polynomial
	 */
	public short order() {
		return (short) Math.max( coefficients.length - 1, 0);
	}

	/**
	 * Computes this*p.
	 * @param p other polynomial
	 * @return resulting polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int n1 = order();
		int n2 = p.order();
		int n = n1+n2;
		Complex[] coeff = new Complex[ n];
		
		for( int i = n; i >= 0; --i) {
			Complex temp = new Complex( 0, 0);
			int j, k;
			for( j = n1 < i ? n1 : i, k = i-j; j >= 0 && k <= n2; --j, ++k) {
				temp.add( coefficients[j].multiply( p.coefficients[k]));
			}
			coeff[i] = temp;
		}
		
		return new ComplexPolynomial( coeff);
	}
	
	/**
	 * Computes a derivative of this polynomial.
	 * @return resulting polynomial
	 */
	public ComplexPolynomial derive() {
		if( order() <= 0) return new ComplexPolynomial( new Complex[] { new Complex(0,0)}); 
		
		Complex[] temp = new Complex[ order()];
		
		for( int i = 1; i < coefficients.length; ++i) {
			temp[i-1] = coefficients[ i].multiply( new Complex( i, 0));
		}
		
		return new ComplexPolynomial( temp);
	}
	
	/**
	 * Computes polynomial value at z.
	 * @param z argument of a polynomial
	 * @return result value
	 */
	public Complex apply(Complex z) {
		if( coefficients.length <= 0) return new Complex( 0, 0);
		
		Complex result = coefficients[0];
		Complex power = z;
		for( int i = 1; i < coefficients.length; ++i) {
			if( i != 1) power = power.multiply( z);
			
			result = result.add( power.multiply( coefficients[i])); 
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for( int i = coefficients.length-1; i >= 0; --i) {
			builder.append( '(');
			builder.append( coefficients[i]);
			builder.append( ')');
			if( i != 0) {
				builder.append( '*');
				builder.append( "z^");
				builder.append( i);
				builder.append( " + ");
			}
		}
		return builder.toString();
	}
}
