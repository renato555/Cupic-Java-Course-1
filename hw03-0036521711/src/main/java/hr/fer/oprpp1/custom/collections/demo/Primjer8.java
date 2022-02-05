package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;

public class Primjer8 {
	public static void main( String[] args) {
		SimpleHashtable<String, Integer> d = new SimpleHashtable<>(4);
		d.put("Ivana", 2);
		d.put("Ante", 2);
		d.put("Jasna", 2);
		d.put("Kristina", 5);
		
		System.out.println( d.toString());
	}
}
