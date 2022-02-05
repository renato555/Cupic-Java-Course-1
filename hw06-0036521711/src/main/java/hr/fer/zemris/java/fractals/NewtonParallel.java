package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
 *  Parallel implementation.
 * @author renat
 */
public class NewtonParallel implements IFractalProducer {
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
	 * Map that contains starting arguments.
	 */
	private Map< NewtonParallelArgs, Integer> args;
	/**
	 * @param crp Underlying polynomial.
	 * @param args Map that contains starting arguments.
	 */
	public NewtonParallel(ComplexRootedPolynomial crp, Map< NewtonParallelArgs, Integer> args) {
		this.crp = crp;
		this.args = args;
	}

	/**
	 * Determines a color for each pixel depending on convergence
	 *  of a corresponding complex number for a given polynomial.
	 */
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {
		System.out.println("Zapocinjem izracun...");

		int brojRadnika;
		if( args.containsKey( NewtonParallelArgs.WORKERS)) {
			brojRadnika = args.get( NewtonParallelArgs.WORKERS);
		}else {
			brojRadnika = Runtime.getRuntime().availableProcessors();
		}
		int brojTraka;
		if( args.containsKey( NewtonParallelArgs.TRACKS)) {
			brojTraka = args.get( NewtonParallelArgs.TRACKS);
			brojTraka = brojTraka > height ? height : brojTraka;
		}else {
			brojTraka = Runtime.getRuntime().availableProcessors()*4;
		}
		System.out.println( "Broj radnika: " + brojRadnika);
		System.out.println( "Broj traka: " + brojTraka);
		
		int brojYPoTraci = height / brojTraka;
		short[] data = new short[width * height];
		final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();
		
		Thread[] radnici = new Thread[ brojRadnika];
		for (int i = 0; i < radnici.length; i++) {
			radnici[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						PosaoIzracuna p = null;
						try {
							p = queue.take();
							if (p == PosaoIzracuna.NO_JOB)
								break;
						} catch (InterruptedException e) {
							continue;
						}
						p.run();
					}
				}
			});
		}
		for (int i = 0; i < radnici.length; i++) {
			radnici[i].start();
		}

		for (int i = 0; i < brojTraka; i++) {
			int yMin = i * brojYPoTraci;
			int yMax = (i + 1) * brojYPoTraci - 1;
			if (i == brojTraka - 1) {
				yMax = height - 1;
			}
			PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel, crp);
			while (true) {
				try {
					queue.put(posao);
					break;
				} catch (InterruptedException e) {
				}
			}
		}
		for (int i = 0; i < radnici.length; i++) {
			while (true) {
				try {
					queue.put(PosaoIzracuna.NO_JOB);
					break;
				} catch (InterruptedException e) {
				}
			}
		}

		for (int i = 0; i < radnici.length; i++) {
			while (true) {
				try {
					radnici[i].join();
					break;
				} catch (InterruptedException e) {
				}
			}
		}

		System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
		observer.acceptResult( data, (short) (crp.toComplexPolynom().order()+1), requestNo);
	}
	
	/**
	 * Represents a thread job.
	 * Colors only a part of an image.
	 * @author renat
	 *
	 */
	public static class PosaoIzracuna implements Runnable {
		/**
		 * Min real number. 
		 */
		private double reMin;
		/**
		 * Max real number. 
		 */
		private double reMax;
		/**
		 * Min imaginary number. 
		 */
		private double imMin;
		/**
		 * Max imaginary number. 
		 */
		private double imMax;
		/**
		 * Window width.
		 */
		private int width;
		/**
		 * Window height.
		 */
		private int height;
		/**
		 * Starting row.
		 */
		private int yMin;
		/**
		 * Ending row.
		 */
		private int yMax;
		/**
		 * Pixel colors.
		 */
		private short[] data;
		/**
		 * If true then cancel drawing.
		 */
		private AtomicBoolean cancel;
		/**
		 * Underlying polynomial
		 */
		private ComplexRootedPolynomial crp;
		/**
		 * Represents an empty job.
		 */
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		/**
		 * Default constructor.
		 */
		private PosaoIzracuna() {}
		/**
		 * Initialises all attributes.
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax,
				short[] data, AtomicBoolean cancel, ComplexRootedPolynomial crp) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.cancel = cancel;
			this.crp = crp;
		}
		/**
		 * Colors certain part of an image.
		 */
		@Override
		public void run() {
			ComplexPolynomial cp = crp.toComplexPolynom();
			ComplexPolynomial derived = cp.derive();
			int offset = width*yMin;
			double module;
			
			for( int y = yMin; y <= yMax; ++y) {
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
		}
	}
	
	/**
	 * Main function.
	 * @param args command arguments
	 */
	public static void main(String[] args) {
		try( Scanner scanner = new Scanner( System.in)){
			Map< NewtonParallelArgs, Integer> arguments = Utils.argsToMap( args);
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
			FractalViewer.show(new NewtonParallel( crp, arguments));
		}catch( Exception e) {
			System.out.println( "uh oh, negdje je nešto pošlo po zlu ");
			System.out.println( e.getMessage());
		}
	}

}
