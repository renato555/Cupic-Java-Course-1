package hr.fer.zemris.java.gui.charts;

import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.util.List;

/**
 * Uses graphics to draw a bar char.
 * @author renat
 */
public class BarChartComponent extends JComponent {
	/**
	 * Bar char model.
	 */
	private BarChart barChart;

	/**
	 * Default offset.
	 */
	private static final int DEFAULT_OFFSET = 10;

	/**
	 * Constructor.
	 * @param barChart bar chart model
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}
	
	/**
	 * Paints a bar chart on this component using Graphics object.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Insets ins = getInsets();
		Dimension dim = getSize();
		Rectangle r = new Rectangle(ins.left, ins.top, dim.width - ins.left - ins.right,
				dim.height - ins.top - ins.bottom);

		List<XYValue> values = barChart.getValues();
		FontMetrics fm = g2d.getFontMetrics();
		int fontHeight = fm.getHeight();
		int fontDescent = fm.getDescent();
		int fontAscent = fm.getAscent();
		int nColumns = values.size();
		int yMin = barChart.getMinY();
		int yMax = barChart.getMaxY();
		int razmak = barChart.getRazmak();
		int nRows = (yMax - yMin) / razmak;
		int najsiriY = 0;
		for (int i = yMin; i <= yMax; i += razmak) {
			String tempString = Integer.toString(i);
			int tempLen = fm.stringWidth(tempString);
			if (najsiriY == 0 || tempLen > najsiriY) {
				najsiriY = tempLen;
			}
		}
		int x, y;
		int xOffset = fontHeight + DEFAULT_OFFSET * 2 + najsiriY;
		int yOffset = 2 * DEFAULT_OFFSET + 2 * fontHeight;
		int rowHeight = (r.height - DEFAULT_OFFSET - yOffset) / (nRows);
		int columnWidth = (r.width - DEFAULT_OFFSET - xOffset) / nColumns;
		if( rowHeight < 1|| columnWidth < 1) return;
		
		// mesh behind bars
		Color cDefault = g2d.getColor();
		Stroke oldStroke = g2d.getStroke();
		
		g2d.setColor( Color.YELLOW);
		g2d.setStroke(new BasicStroke(2));
		x = r.x + xOffset;
		y = r.y + r.height - yOffset;
		for ( int i = 0; i < nRows + 1; y -= rowHeight, ++i) {
			g2d.drawLine(x, y, r.x + r.width - DEFAULT_OFFSET, y);
		}
		
		// bars
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.CYAN);
		x = r.x + xOffset;
		y = r.y + r.height - fm.getHeight() * 2 - DEFAULT_OFFSET * 2;
		for (XYValue v : values) {
			int barHeight = (int) (rowHeight * ( ((double) (v.getY() - yMin)) / razmak));
			g2d.setColor(Color.CYAN);
			g2d.fillRect(x, y - barHeight, columnWidth, barHeight);
			g2d.setColor(Color.BLUE);
			g2d.drawRect(x, y - barHeight, columnWidth, barHeight);
			x += columnWidth;
		}

		g2d.setStroke(oldStroke);
		g2d.setColor(cDefault);

		// nacrtaj y os
		// opis
		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		String yOpis = barChart.getyOpis();
		x = -fontHeight - r.y - (r.height - yOffset) / 2 - fm.stringWidth(yOpis) / 2;
		y = r.x + fontAscent;
		g2d.drawString(yOpis, x, y);
		//g2d.drawLine( -10, y, -10, y+1);
		g2d.setTransform(defaultAt);

		// brojevi
		x = r.x + fontHeight + DEFAULT_OFFSET + najsiriY;
		int yNum;
		for (y = r.y + r.height - yOffset, yNum = yMin; yNum <= yMax; yNum += razmak, y -= rowHeight) {
			String tempString = Integer.toString(yNum);
			int width = fm.stringWidth(tempString);
			g2d.drawString(tempString, x - width, y + fontAscent / 2);
		}

		// horzizntalne linije po y osi
		x = r.x + xOffset;
		y = r.y + r.height - yOffset;
		for (int i = 0; i < nRows + 1; y -= rowHeight, ++i) {
			g2d.drawLine(x, y, x - DEFAULT_OFFSET / 2, y);
		}

		// y os
		y = r.y + DEFAULT_OFFSET / 2;
		g2d.drawLine(x, r.y + r.height - yOffset, x, y);
		// strelica y os
		g2d.fillPolygon(new int[] { x - DEFAULT_OFFSET / 2, x + DEFAULT_OFFSET / 2, x }, new int[] { y, y, r.y }, 3);

		// nacrtaj x os
		// opis
		String xOpis = barChart.getxOpis();
		g2d.drawString(xOpis, r.x + xOffset + (r.width - xOffset) / 2 - fm.stringWidth(xOpis) / 2,
				r.y + r.height - fm.getDescent());

		// brojevi
		y = r.y + r.height - fontHeight - DEFAULT_OFFSET - fontDescent;
		for (int i = 0; i < nColumns; ++i) {
			XYValue val = values.get(i);
			String xVal = Integer.toString(val.getX());
			int stringWidth = fm.stringWidth(xVal);
			x = r.x + (columnWidth / 2) + columnWidth * i - stringWidth / 2 + xOffset;
			g2d.drawString(xVal, x, y);
		}
		// vertilakne linije na x osi
		y = r.y + r.height - fm.getHeight() * 2 - DEFAULT_OFFSET * 2;
		x = r.x + xOffset;
		for ( int i = 0; i < nColumns + 1; x += columnWidth, ++i) {
			g2d.drawLine(x, y, x, y + DEFAULT_OFFSET / 2);
		}

		// x os
		x = r.x + r.width - DEFAULT_OFFSET / 2;
		g2d.drawLine(r.x + xOffset, y, x, y);
		// strelica x os
		g2d.fillPolygon(new int[] { x, x, r.x + r.width },
				new int[] { y + DEFAULT_OFFSET / 2, y - DEFAULT_OFFSET / 2, y }, 3);
	}
}
