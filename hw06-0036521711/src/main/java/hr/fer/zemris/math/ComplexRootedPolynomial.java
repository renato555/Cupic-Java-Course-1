package hr.fer.zemris.math;

/**
 * Represents a polynomial in a factored form.
 * @author renat
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Leading coefficient.
	 */
	private Complex constant;
	
	/**
	 * Roots of this polynomial
	 */
	private Complex[] roots;
	
	/**
	 * Initialises attributes.
	 * @param constant leading coefficient.
	 * @param roots roots of this polynomial
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		if( roots != null) {
			this.roots = roots;			
		}else {
			this.roots = new Complex[0];
		}
	}
	
	/**
	 * Computes polynomial value at z.
	 * @param z argument of a polynomial
	 * @return result value
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for( Complex c : roots) {
			result = result.multiply( z.sub( c));
		}
		return result;
	}
	
	/**
	 * Computes corresponding polynomial in a standard form.
	 * @return resulting polynomial in a standard form
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] coefficients = new Complex[ roots.length+1];
		toComplexPolynom( 0, coefficients, null, 0);
		return new ComplexPolynomial( coefficients);
	}
	
	/**
	 * Recursive helper function.
	 * Calculates coefficients of a polynomial.
	 * @param i current recursion depth
	 * @param coefficients resulting coefficients
	 * @param coef current coefficient value 
	 * @param n indicates how many times coefficient got multiplied by z
	 */
	private void toComplexPolynom( int i, Complex[] coefficients, Complex coef, int n) {
		if( i == coefficients.length - 1) { 
			Complex result = coef == null ? constant : coef.multiply( constant);
			if( coefficients[n] != null) {
				coefficients[n] = coefficients[n].add( result);
			}else {
				coefficients[n] = result;
			}
			return;
		}
		
		toComplexPolynom( i+1, coefficients, coef == null ? roots[i].negate(): coef.multiply( roots[i].negate()), n);
		toComplexPolynom( i+1, coefficients, coef, n+1);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( '(');
		builder.append( constant);
		builder.append( ')');
		for( Complex c : roots) {
			builder.append( "*(z-(");
			builder.append( c);
			builder.append( "))");
		}
		return builder.toString();
	}
	
	/**
	 * Finds an index of a closest root for a given complex number z that is within threshold
	 * @param z complex number
	 * @param treshold minimum distance to the root
	 * @return resulting index, or -1 if neither root satisfies threshold condition
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double dist = 0;
		
		for( int i = 0; i < roots.length; ++i) {
			Complex c = roots[i];
			double dist_temp = c.sub( z).module();
			if( dist_temp < treshold ) {
				if( index == -1 || dist_temp < dist) {
					index = i;
					dist = dist_temp;
					
				}
			}
		}
		
		return index;
	}	
}
