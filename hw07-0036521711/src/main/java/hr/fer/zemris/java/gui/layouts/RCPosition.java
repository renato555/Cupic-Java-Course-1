package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;


/**
 * Represents coordinates in a grid layout.
 * @author renat
 *
 */
public class RCPosition {
	/**
	 * Component row.
	 */
	private int row;
	/**
	 * Component column.
	 */
	private int column;

	/**
	 * Constructor.
	 * Initialises attributes.
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Parses strings of type a,b to this object with row = a and column = b
	 * @param text string that we want to parse
	 * @return corresponding RCPosition
	 */
	public static RCPosition parse(String text) {
		String[] elements = text.split(",");
		if (elements.length != 2) {
			throw new IllegalArgumentException("text '" + text + "' se ne moze parsirati u RCPosition");
		}

		int row = Integer.parseInt(elements[0].trim());
		int column = Integer.parseInt(elements[1].trim());
		return new RCPosition(row, column);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}

}
