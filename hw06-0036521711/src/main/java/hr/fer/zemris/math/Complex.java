package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a complex number.
 * @author renat
 *
 */
public class Complex {
	/**
	 * Real part.
	 */
	private double re;
	
	/**
	 * Imaginary part.
	 */
	private double im;

	/**
	 * Represents 0 + i0
	 */
	public static final Complex ZERO = new Complex(0, 0);

	/**
	 * Represents 1 + i0
	 */
	public static final Complex ONE = new Complex(1, 0);

	/**
	 * Represents -1 + i0
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/**
	 * Represents 0 + i
	 */
	public static final Complex IM = new Complex(0, 1);
	
	/**
	 * Represents 0 - i
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default constructor.
	 */
	public Complex() {
	}

	/**
	 * Initializes real and imaginary part.
	 * @param re real part
	 * @param im imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Returns a module of a complex number.
	 * @return module
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * Multiplies this number with c.
	 * @param c number with which we wish to multiply
	 * @return resulting complex number
	 */
	public Complex multiply(Complex c) {
		double reTemp = re * c.re - im * c.im;
		double imTemp = re * c.im + im * c.re;
		return new Complex(reTemp, imTemp);
	}

	/**
	 * Divides this number with c.
	 * @param c number with which we wish to divide
	 * @return resulting complex number
	 * @throws IllegalArgumentException if re == 0 && im == 0
	 */
	public Complex divide(Complex c) {
		if (c.re == 0 && c.im == 0)
			throw new IllegalArgumentException("dijeljenje s nulom nije dozvoljeno");

		double reTemp = (re * c.re + im * c.im) / (c.re * c.re + c.im * c.im);
		double imTemp = (im * c.re - re * c.im) / (c.re * c.re + c.im * c.im);
		return new Complex(reTemp, imTemp);
	}

	/**
	 * Adds c to this number.
	 * @param c number which we wish to add
	 * @return resulting complex number
	 */
	public Complex add(Complex c) {
		double reTemp = re + c.re;
		double imTemp = im + c.im;
		return new Complex(reTemp, imTemp);
	}

	/**
	 * Subtracts c for this number.
	 * @param c number which we wish to subtract
	 * @return resulting complex number
	 */
	public Complex sub(Complex c) {
		double reTemp = re - c.re;
		double imTemp = im - c.im;
		return new Complex(reTemp, imTemp);
	}

	/**
	 * Negates this number
	 * @return -this
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Pow function.
	 * @param n exponent. Must be non-negative
	 * @return this^2
	 * @throws IllegalArgumentException if n < 0
	 */
	public Complex power(int n) {
		if( n < 0) throw new IllegalArgumentException( "n ne moze biti < 0");
		
		double rN = Math.pow(re * re + im * im, ((double) n) / 2);
		double phi = re == 0 ? Math.PI /2 : Math.atan(im / re);
		if( re < 0 && im >= 0) {
			phi = Math.PI + phi;
		}else if( re < 0 && im < 0) {
			phi = -Math.PI + phi;
		}
		
		double reTemp = rN * Math.cos(n * phi);
		double imTemp = rN * Math.sin(n * phi);
		return new Complex(reTemp, imTemp);
	}

	/**
	 * Calculates n-th root.
	 * @param n root. Must be non-negative
	 * @return n-th root of this
	 * @throws IllegalArgumentException if n <= 0
	 */
	public List<Complex> root(int n) {
		if( n <= 0) throw new IllegalArgumentException( "n ne moze biti <= 0");
		
		List<Complex> result = new LinkedList<>();
		double rN = Math.pow(re * re + im * im, 1.0 / (2 * n));
		double phi = re == 0 ? Math.PI /2 : Math.atan(im / re);
		if( re < 0 && im >= 0) {
			phi = Math.PI + phi;
		}else if( re < 0 && im < 0) {
			phi = -Math.PI + phi;
		}
		for (int k = 0; k < n; ++k) {
			double angle = (phi + 2 * Math.PI * k) / n;
			double reTemp = rN * Math.cos(angle);
			double imTemp = rN * Math.sin(angle);
			result.add(new Complex(reTemp, imTemp));
		}

		return result;
	}

	@Override
	public String toString() {
		String sign = im >= 0 ? "+" : "-";
		return re + sign + Math.abs( im) + "i";
	}
}
