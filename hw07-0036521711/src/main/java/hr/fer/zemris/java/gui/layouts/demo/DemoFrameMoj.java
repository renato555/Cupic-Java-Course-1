package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Color;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class DemoFrameMoj extends JFrame {
	public DemoFrameMoj() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	private void initGUI() {
		Container cp = getContentPane();
		CalcLayout cl = new CalcLayout(3);
		cp.setLayout( cl);
		((JComponent) cp).setBorder( BorderFactory.createLineBorder(Color.BLUE, 5));
		for( int i = 1; i <= 5; ++i) {
			for( int j = 1; j <= 7; ++j) {
				if( i == 1 && j < 6 && j >1) continue;
				
				JLabel l = l( i +"," + j);
				cp.add( l, new RCPosition(i, j));
			}
		}
		
//		Container cp = getContentPane();
//		cp.setLayout( new BorderLayout());
//		
//		JLabel l = l( "text");
//		JLabel l2 = l( "drugi");
//		cp.add(l, BorderLayout.PAGE_END);
//		cp.add(l, BorderLayout.CENTER);
//		cp.add(l2, BorderLayout.PAGE_END);
	}

	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground( Color.YELLOW);
		l.setBorder( BorderFactory.createLineBorder(Color.RED, 2));
		l.setPreferredSize( new Dimension( 50, 50));
		l.setOpaque(true);
		return l;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrameMoj().setVisible(true);
		});
	}
}
