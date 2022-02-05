package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	void dobreDimenzije() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals( 152, dim.getWidth(), "nije dobra sirina");
		assertEquals( 158, dim.getHeight(), "nije dobra visina");
	}
	
	@Test
	void dobreDimenzije2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals( 152, dim.getWidth(), "nije dobra sirina");
		assertEquals( 158, dim.getHeight(), "nije dobra visina");
	}

	@Test
	void bacaIznimkuZaKriveKoordinate() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		
		assertThrows( CalcLayoutException.class, () -> { p.add(l1, new RCPosition(-1, 1));}, "negativan red ne baca iznimku");
		assertThrows( CalcLayoutException.class, () -> { p.add(l1, new RCPosition(6, 1));}, "prevelik red ne baca iznimku");
		assertThrows( CalcLayoutException.class, () -> { p.add(l1, new RCPosition(1, -1));}, "negativan stupac ne baca iznimku");
		assertThrows( CalcLayoutException.class, () -> { p.add(l1, new RCPosition(1, 8));}, "prevelik stupac ne baca iznimku");
	}
	
	@Test
	void bacaIznimkuZaKriveKoordinate2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		
		assertThrows( CalcLayoutException.class, () -> { p.add(l1, new RCPosition(1, 3));}, "ne baca iznimku kada se stavi na koordinate koje zauzima prva komponenta");
	}
	
	@Test
	void isteKoordinate() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		JLabel l2 = new JLabel("");
		
		assertThrows( CalcLayoutException.class, () -> { 
			p.add(l1, new RCPosition(2, 2));
			p.add(l2, new RCPosition(2, 2));},
				"dvije komponente na istom ogranicenju");
	}
}
