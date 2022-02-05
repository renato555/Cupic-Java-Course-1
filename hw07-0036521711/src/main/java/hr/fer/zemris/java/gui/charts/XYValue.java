package hr.fer.zemris.java.gui.charts;

/**
 * Class that represents a (x, y) value.
 * @author renat
 */
public class XYValue {
	/**
	 * x value.
	 */
	private int x;
	/**
	 * y value.
	 */
	private int y;

	/**
	 * Constructor.
	 * Initialises attributes.
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
