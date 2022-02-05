package hr.fer.zemris.java.gui.charts;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Frame that uses a BarChartComponent.
 * @author renat
 *
 */
public class BarChartDemo extends JFrame{
	/**
	 * Bar chart model.
	 */
	private BarChart barChart;
	/**
	 * Name of a path from which we read data.
	 */
	private String pathName;
	
	/**
	 * Constructor.
	 * @param barChart bar chart model
	 * @param pathName name of a path from which we read data
	 */
	public BarChartDemo( BarChart barChart, String pathName) {
		setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		this.barChart = barChart;
		this.pathName = pathName;
		initGUI();
	}
	
	/**
	 * Initialises GUI components.
	 */
	public void initGUI() {
		Container cp = getContentPane();
		cp.setLayout( new BorderLayout());
		JLabel dirPath = new JLabel( pathName, SwingConstants.CENTER);
		cp.add( dirPath, BorderLayout.PAGE_START);
		
		BarChartComponent barCharComponent = new BarChartComponent( barChart);
		cp.add( barCharComponent, BorderLayout.CENTER);
	}
	/**
	 * App demonstrates use of a BarChartComponent.
	 * @param args path from which we read data
	 */
	public static void main( String[] args) {
		if( args.length != 1) throw new IllegalArgumentException( "nedozvoljen broj argumenata");
		
		Path p = Path.of( args[0]);
		if( !Files.isReadable( p)) throw new IllegalArgumentException( "file se ne moze citat");
		
		try ( BufferedReader reader = Files.newBufferedReader( p)){
			String xOpis = reader.readLine();
			String yOpis = reader.readLine();
			
			List<XYValue> values = new ArrayList<>();
			for( String pair : reader.readLine().split( "\\s+")) {
				String[] xy = pair.split(",");
				int x = Integer.parseInt( xy[0]);
				int y = Integer.parseInt( xy[1]);
				values.add( new XYValue( x, y));
			}
			
			int minY = Integer.parseInt( reader.readLine());
			int maxY = Integer.parseInt( reader.readLine());
			int razmak = Integer.parseInt( reader.readLine());
			
			BarChart model = new BarChart( values, xOpis, yOpis, minY, maxY, razmak);

			SwingUtilities.invokeLater(() -> {
				new BarChartDemo( model, p.toString()).setVisible(true);
			});
		}catch( IOException e) {
			System.out.println( "nije moguce otvoriti datoteku");
		}
	}
}
