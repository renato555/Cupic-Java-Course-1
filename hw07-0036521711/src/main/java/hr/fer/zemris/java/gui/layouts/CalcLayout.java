package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Layout manager for Calculator app.
 * @author renat
 *
 */

public class CalcLayout implements LayoutManager2 {
	/**
	 * Stores all components and their constraints.
	 */
	private List<Cell> cells = new ArrayList<>(31);
	/**
	 * Margin between rows and columns.
	 */
	private int margin;

	/**
	 * Constructor.
	 * @param margin desired margin
	 */
	public CalcLayout(int margin) {
		this.margin = margin;
	}

	/**
	 * Constructor.
	 * Sets margin to 0.
	 */
	public CalcLayout() {
		margin = 0;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("operacija nije podrzana");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		Iterator<Cell> it = cells.iterator();
		while (it.hasNext()) {
			Cell c = it.next();
			if (c.comp.equals( comp)) {
				it.remove();
				break;
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Function<Component, Dimension> strategijaDohavti = Component::getPreferredSize;
		BiFunction<Double, Double, Double> strategijaUsporedi = Math::max;
		
		return getLayoutSize( parent, strategijaDohavti, strategijaUsporedi);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Function<Component, Dimension> strategijaDohavti = Component::getMinimumSize;
		BiFunction<Double, Double, Double> strategijaUsporedi = Math::max;
		
		return getLayoutSize( parent, strategijaDohavti, strategijaUsporedi);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Function<Component, Dimension> strategijaDohavti = Component::getMaximumSize;
		BiFunction<Double, Double, Double> strategijaUsporedi = Math::min;
		
		return getLayoutSize( target, strategijaDohavti, strategijaUsporedi);
	}

	/**
	 * Helper function. 
	 * Calculates size dimensions for the specified container,given the components it contains depending on passed in strategies.
	 * @param parent the parent container
	 * @param dohvatiDimenziju strategy for extracting dimensions from components
	 * @param usporedi strategy for comparing component sizes
	 * @return calculated size of a container
	 */
	private Dimension getLayoutSize(Container parent, Function<Component, Dimension> dohvatiDimenziju,
			BiFunction<Double, Double, Double> usporedi) {
		double cellHeight = -1;
		double cellWidth = -1;

		boolean prvi = true;
		Iterator<Cell> it = cells.iterator();
		while (it.hasNext()) {
			Cell c = it.next();
			Dimension d = dohvatiDimenziju.apply(c.comp);
			if (d == null) continue;
			
			if (prvi) {
				cellHeight = d.getHeight();
				if( (c.constraint.getRow() == 1 && c.constraint.getColumn() == 1)) {
					cellWidth = (d.getWidth() - 4 * margin) / 5;
				}else {
					cellWidth = d.getWidth();					
				}
				prvi = false;
			}

			cellHeight = usporedi.apply(d.getHeight(), cellHeight);

			if (!(c.constraint.getRow() == 1 && c.constraint.getColumn() == 1)) {
				cellWidth = usporedi.apply(d.getWidth(), cellWidth);
			} else {
				cellWidth = usporedi.apply((d.getWidth() - 4 * margin) / 5, cellWidth);
			}

		}

		return addInsetsSize(parent, cellWidth, cellHeight);
	}

	/**
	 * Adds insets to the calculated layout size. 
	 * @param parent the parent container
	 * @param cellWidth calculated cell width
	 * @param cellHeight calculated cell height
	 * @return calculated size of a container
	 */
	private Dimension addInsetsSize(Container parent, double cellWidth, double cellHeight) {
		Insets ins = parent.getInsets();

		int totalWidth = (int) (cellWidth == -1 ? 500 : Math.ceil(cellWidth * 7 + margin * 6 + ins.left + ins.right));
		int totalHeight = (int) (cellHeight == -1 ? 500
				: Math.ceil(cellHeight * 5 + margin * 4 + ins.top + ins.bottom));

		return new Dimension(totalWidth, totalHeight);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets ins = parent.getInsets();
		Dimension size = parent.getSize();
		Dimension contentSize = new Dimension((int) (size.getWidth() - ins.left - ins.right),
				(int) (size.getHeight() - ins.top - ins.bottom));
		int[] width = new int[7];
		int[] x = new int[7];
		int[] height = new int[5];
		int[] y = new int[5];
		findCoordinates(contentSize, width, x, height, y, margin);

		Iterator<Cell> it = cells.iterator();
		while (it.hasNext()) {
			Cell c = it.next();
			int row = c.constraint.getRow();
			int column = c.constraint.getColumn();
			if (row == 1 && column == 1) {
				c.comp.setBounds(ins.left, ins.top,
						r(width[0] + width[1] + width[2] + width[3] + width[4] + 4 * margin), r(height[0]));
			} else {
				c.comp.setBounds(r(x[column - 1] + ins.left), r(y[row - 1] + ins.top), r(width[column - 1]),
						r(height[row - 1]));
			}
		}
	}

	/**
	 * Fills x and y arrays with corresponding coordinates of components.
	 * Fills width and height arrays with corresponding sizes of components.  
	 * @param size total size that components have to fill in
	 * @param width array which we want to fill
	 * @param x array which we want to fill
	 * @param height array which we want to fill
	 * @param y array which we want to fill
	 * @param margin margin between columns and rows
	 */
	private static void findCoordinates(Dimension size, int[] width, int[] x, int[] height, int[] y, int margin) {
		double cellWidth = (size.getWidth() - 6 * margin) / 7;
		int cellWidthInt = (int) Math.floor( cellWidth);

		int preostaloWidth = (int) (size.getWidth() - (cellWidthInt * 7 + 6 * margin));
		if (preostaloWidth == 1) {
			width[3] = 1;
		} else if (preostaloWidth == 2) {
			width[2] = width[4] = 1;
		} else if (preostaloWidth == 3) {
			width[1] = width[3] = width[5] = 1;
		} else if (preostaloWidth == 4) {
			width[0] = width[1] = width[3] = width[5] = 1;
		} else if (preostaloWidth == 5) {
			width[1] = width[2] = width[3] = width[4] = width[5] = 1;
		} else if (preostaloWidth == 6) {
			width[0] = width[1] = width[2] = width[4] = width[5] = width[6] = 1;
		}
		for (int i = 0; i < 7; ++i) {
			if (i > 0) {
				x[i] = x[i - 1] + width[i - 1] + margin;
			} else {
				x[i] = 0;
			}
			width[i] += cellWidthInt;
		}

		double cellHeight = (size.getHeight() - 4 * margin) / 5;
		int cellHeightInt = (int) Math.floor( cellHeight);

		int preostaloHeight = (int) (size.getHeight() - (cellHeightInt * 5 + 4*margin));
		if (preostaloHeight == 1) {
			height[2] = 1;
		} else if (preostaloHeight == 2) {
			height[1] = height[3] = 1;
		} else if (preostaloHeight == 3) {
			height[1] = height[2] = height[3] = 1;
		} else if (preostaloHeight == 4) {
			height[0] = height[1] = height[3] = height[4] = 1;
		}
		for (int i = 0; i < 5; ++i) {
			if (i > 0) {
				y[i] = y[i - 1] + height[i - 1] + margin;
			} else {
				y[i] = 0;
			}
			height[i] += cellHeightInt;
		}
	}

	/**
	 * 
	 * @throws NullPointerException comp or constraints equals null
	 * @throws IllegalArgumentException when constraints is not an instance of String or RCPosition
	 * @throws CalcLayoutException for undefined positions or if the position is already filled
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null)
			throw new NullPointerException("comp i constraints ne mogu biti null");

		RCPosition c = null;
		if (constraints instanceof String) {
			c = RCPosition.parse((String) constraints);
		} else if (constraints instanceof RCPosition) {
			c = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("constrains mora biti string ili RCPosition");
		}

		int row = c.getRow();
		int column = c.getColumn();

		if (row < 1 || row > 5 || column < 1 || column > 7) {
			throw new CalcLayoutException("nedozvoljene koordinate");
		}
		if (row == 1 && column > 1 && column < 6) {
			throw new CalcLayoutException("nedozvoljene koordinate");
		}

		Iterator<Cell> it = cells.iterator();
		while (it.hasNext()) {
			Cell cell = it.next();
			if (cell.comp.equals(comp)) {
				throw new CalcLayoutException("dvaput dodana ista komponenta");
			}
			if (cell.constraint.equals(c)) {
				throw new CalcLayoutException("pozicija je vec popunjena");
			}
		}

		cells.add(new Cell(comp, c));
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * Rounds double to int.
	 * @param d double
	 * @return corresponding int
	 */
	private static int r(double d) {
		return (int) d;
	}

	/**
	 * Helper class.
	 * A structure that keeps a component together with its constraint.
	 * @author renat
	 *
	 */
	private static class Cell {
		/**
		 * Component.
		 */
		private Component comp;
		/**
		 * This component's constraint.
		 */
		private RCPosition constraint;

		/**
		 * Constructor.
		 * Initialises all attributes.
		 * @param comp component
		 * @param constraint this component's constraint
		 */
		public Cell(Component comp, RCPosition constraint) {
			this.comp = comp;
			this.constraint = constraint;
		}

		@Override
		public int hashCode() {
			return Objects.hash(comp);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cell other = (Cell) obj;
			return Objects.equals(comp, other.comp);
		}
	}

	@Override
	public void invalidateLayout(Container target) {}

}
