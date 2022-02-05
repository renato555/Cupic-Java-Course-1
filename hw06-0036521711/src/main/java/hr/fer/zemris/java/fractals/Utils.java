package hr.fer.zemris.java.fractals;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.math.Complex;

/**
 * Utility class.
 * @author renat
 */
public class Utils {

	/**
	 * Parses string to complex number.
	 * @param complex string which we want to parse
	 * @return resulting complex number
	 */
	public static Complex getComplex(String complex) {
		double re, im;
		boolean negative = false;

		complex = complex.trim();
		char[] arr = complex.toCharArray();
		int i = 0;
		if (arr[i] == '+') {
			++i;
		}else if ( arr[i] == '-') {
			negative = true;
			++i;
		}
		i = removeWhiteSpaces(arr, i);
		if (arr[i] == 'i') {
			// nema realnog dijela
			re = 0;
			++i;
			int lastIndex = getNumber(arr, i);
			if (lastIndex == i) {
				im = 1;
			} else {
				im = getDouble(new String(arr, i, lastIndex - i));
			}
			if (negative) {
				im *= -1;
				negative = false;
			}

			i = removeWhiteSpaces(arr, lastIndex);
			if (i < arr.length) {
				throw new IllegalArgumentException("krivo unesen kompleksni broj");
			}
		} else {
			// realni dio
			int lastIndex = getNumber(arr, i);
			re = getDouble(new String(arr, i, lastIndex - i));
			if (negative) {
				re *= -1;
				negative = false;
			}
			i = removeWhiteSpaces(arr, lastIndex);

			// sad imaginarni dio
			if (i < arr.length) {
				if (arr[i] == '+') {
					++i;
				}else if ( arr[i] == '-') {
					negative = true;
					++i;
				}
			}

			i = removeWhiteSpaces(arr, i);
			if (i < arr.length && arr[i] == 'i') {
				++i;
				lastIndex = getNumber(arr, i);
				if (lastIndex == i) {
					im = 1;
				} else {
					im = getDouble(new String(arr, i, lastIndex - i));
				}
				if (negative) {
					im *= -1;
				}

				i = removeWhiteSpaces(arr, lastIndex);
				if (i < arr.length) {
					throw new IllegalArgumentException("krivo unesen kompleksni broj");
				}

			} else if (!negative) {
				im = 0;
			} else {
				throw new IllegalArgumentException("krivo unesen kompleksni broj");
			}

		}

		return new Complex(re, im);
	}

	/**
	 * Parses string to double.
	 * @param re string which we wish to parse
	 * @return corresponding double
	 */
	private static double getDouble(String re) {
		re = re.trim();
		return Double.parseDouble(re);
	}

	/**
	 * Shifts index over digits.
	 * @param arr string characters
	 * @param i current index
	 * @return index of last digit + 1
	 */
	private static int getNumber(char[] arr, int i) {
		while (i < arr.length && (arr[i] == '.' || Character.isDigit(arr[i]))) {
			++i;
		}
		return i;
	}

	/**
	 * Moves index past white spaces.
	 * 
	 * @param data         char array
	 * @param currentIndex current index in data
	 * @return new index
	 */
	private static int removeWhiteSpaces(char[] data, int currentIndex) {
		while (currentIndex < data.length && jelPraznina(data[currentIndex])) {
			++currentIndex;
		}
		return currentIndex;
	}

	/**
	 * @param c char for we wish to know if it is a whitespace
	 * @return true if c is a whitespace, false otherwise
	 */
	private static boolean jelPraznina(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}

	/**
	 * Maps a pixel to a complex number in a imaginary plane.
	 * @return corresponding complex number 
	 */
	public static Complex mapToComplexPlain(int x, int y, int width, int height, double reMin, double reMax,
			double imMin, double imMax) {
		double re = x / (width - 1.0) * (reMax - reMin) + reMin;
		double im = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;

		return new Complex(re, im);
	}
	
	/**
	 * Builds a map of arguments for NewtonParallel class.
	 * @param args command arguments
	 * @return map that contains arguments
	 */
	public static Map<NewtonParallelArgs, Integer> argsToMap( String[] args){
		Map<NewtonParallelArgs, Integer> result = new HashMap<>();
		for( int i = 0; i < args.length; ++i) {
			if( args[i].startsWith( "--workers=")) {
				int workers = Integer.parseInt( args[i].substring( "--workers=".length()));
				if( workers < 0) throw new IllegalArgumentException( "workers ne moze biti < 0");
				if( result.containsKey( NewtonParallelArgs.WORKERS)) throw new IllegalArgumentException( "parametar se moze navesti najvise jednom");
				result.put( NewtonParallelArgs.WORKERS, workers);
			}else if( args[i].startsWith( "-w")) {
				int workers = Integer.parseInt( args[i+1]);
				++i;
				if( workers < 0) throw new IllegalArgumentException( "workers ne moze biti < 0");
				if( result.containsKey( NewtonParallelArgs.WORKERS)) throw new IllegalArgumentException( "parametar se moze navesti najvise jednom");
				result.put( NewtonParallelArgs.WORKERS, workers);
			}else if( args[i].startsWith( "--tracks=")) {
				int tracks = Integer.parseInt( args[i].substring( "--tracks=".length()));
				if( tracks < 0) throw new IllegalArgumentException( "tracks ne moze biti < 0");
				if( result.containsKey( NewtonParallelArgs.TRACKS)) throw new IllegalArgumentException( "parametar se moze navesti najvise jednom");
				result.put( NewtonParallelArgs.TRACKS, tracks);
			}else if( args[i].startsWith( "-t")) {
				int tracks = Integer.parseInt( args[i+1]);
				++i;
				if( tracks < 0) throw new IllegalArgumentException( "tracks ne moze biti < 0");
				if( result.containsKey( NewtonParallelArgs.TRACKS)) throw new IllegalArgumentException( "parametar se moze navesti najvise jednom");
				result.put( NewtonParallelArgs.TRACKS, tracks);
			}else {
				throw new IllegalArgumentException( "nepoznat parametar");
			}
		}
		return result;
	}
}
