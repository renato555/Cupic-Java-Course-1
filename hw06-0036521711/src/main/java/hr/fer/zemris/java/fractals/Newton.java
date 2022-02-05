package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Determines a color for each pixel depending on convergence
 *  of a corresponding complex number for a given polynomial.
 *  Sequential implementation.
 * @author renat
 */
public class Newton implements IFractalProducer{
	/**
	 * Minimum distance between two consecutive complex numbers.
	 */
	private static final double CONVERGENCE_THRESHOLD = 0.001;
	/**
	 * Minimum distance to polynomial root.
	 */
	private static final double ROOT_THRESHOLD = 0.002;
	/**
	 * Maximum number of iterations.
	 */
	private static final int MAX_ITER = 1000;
	/**
	 * Underlying polynomial.
	 */
	private ComplexRootedPolynomial crp;
	/**
	 * @param crp Underlying polynomial.
	 */
	public Newton(ComplexRootedPolynomial crp) {
		this.crp = crp;
	}

	/**
	 * Determines a color for each pixel depending on convergence
	 *  of a corresponding complex number for a given polynomial.
	 */
	@Override
	public void produce( double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {
		
		ComplexPolynomial cp = crp.toComplexPolynom();
		ComplexPolynomial derived = cp.derive();
		
		short data[] = new short[width*height];
		int offset = 0;
		double module;
		
		for( int y = 0; y < height; ++y) {
			if( cancel.get()) break;
			for( int x = 0; x < width; ++x) {
				Complex zn = Utils.mapToComplexPlain( x, y, width, height, reMin, reMax, imMin, imMax); 
				int iter = 0;
				do {
					Complex znOld = zn;
					zn = zn.sub( cp.apply(zn).divide( derived.apply( zn)));
					++iter;
					module = znOld.sub( zn).module();
				}while( module > CONVERGENCE_THRESHOLD && iter < MAX_ITER);
				int index = crp.indexOfClosestRootFor( zn, ROOT_THRESHOLD);
				data[ offset++] = (short) (index+1);
			}
		}
		
		observer.acceptResult( data, (short) (cp.order()+1), requestNo);
	}
	
	/**
	 * Main function.
	 * @param args command arguments
	 */
	public static void main( String[] args) {
		
		try( Scanner scanner = new Scanner( System.in)){
			System.out.println( "Welcome to Newton-Raphson iteration-based fractal viewer.");
			System.out.println( "Please enter at least two roots, one root per line. Enter 'done' when done.");
			System.out.print( "Root 1 > ");
			String line = scanner.nextLine();
			List<Complex> roots = new LinkedList<>();
			int i = 0;
			while( line != null & !line.equals( "done")) {
				++i;
				try {
					roots.add( Utils.getComplex( line));					
				}catch( Exception e) {
					System.out.println( "kompleksni broj je krivo unesen");
					return;
				}
				System.out.print( "Root " + (i+1) + " > ");
				line = scanner.nextLine();
			}
			if( i < 2) {
				System.out.println( "nije uneseno dovljno argumenata");
				return;
			}
			
			System.out.println( "Image of a fractal will appear shortly. Thank you.");
			Complex[] rootsArr = new Complex[ roots.size()];
			i = 0;
			for( Complex c : roots) {
				rootsArr[i] = c;
				++i;
			}
			ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, rootsArr);
			FractalViewer.show(new Newton( crp));
		}
	}
}
