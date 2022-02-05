package hr.fer.zemris.java.gui.charts;

import java.util.Collections;
import java.util.List;

/**
 * Model for bar char component.
 * @author renat
 */
public class BarChart {
	/**
	 * List of (x, y) values.
	 */
	private List<XYValue> values;
	/**
	 * x axis description.
	 */
	private String xOpis;
	/**
	 * y axis description.
	 */
	private String yOpis;
	/**
	 * Minimal y value on y axis.
	 */
	private int minY;
	/**
	 * Maximal y value on y axis.
	 */
	private int maxY;
	/**
	 * Unit metric on y axis.
	 */
	private int razmak;

	/**
	 * Constructor.
	 * Initialises all values.
	 * @param values list of (x, y) values.
	 * @param xOpis x axis description
	 * @param yOpis y axis description
	 * @param minY minimal y value on y axis
	 * @param maxY maximal y value on y axis
	 * @param razmak unit metric on y axis
	 * 
	 * @throws IllegalArgumentException if min < 0 or if maxY <= minY or if values contains y that is < minY
	 */
	public BarChart(List<XYValue> values, String xOpis, String yOpis, int minY, int maxY, int razmak) {
		if (minY < 0)
			throw new IllegalArgumentException("minY ne moze biti negativan");
		if (maxY <= minY)
			throw new IllegalArgumentException("maxY mora biti veci od minY");

		values.forEach(v -> {
			if (v.getY() < minY)
				throw new IllegalArgumentException("lista sadrzi vrijednost koja je manja od y min");
		});
		
		int ostatak = (maxY - minY) % razmak; 
		if( ostatak != 0) {
			maxY += razmak - ( maxY - minY) % razmak;			
		}
		
		Collections.sort( values, (v1, v2) -> Integer.compare(v1.getX(), v2.getX()));
		this.values = values;
		this.xOpis = xOpis;
		this.yOpis = yOpis;
		this.minY = minY;
		this.maxY = maxY;
		this.razmak = razmak;
	}

	public List<XYValue> getValues() {
		return values;
	}

	public String getxOpis() {
		return xOpis;
	}

	public String getyOpis() {
		return yOpis;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getRazmak() {
		return razmak;
	}

}
