package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;

public class PrimjerIspit {
	public static void main( String[] args) {
		Collection<String> c1 = new ArrayIndexedCollection<>();
		c1.add( "Kristina");
		c1.add( "Jasna");
		
		Collection<String> c2 = new ArrayIndexedCollection<>();
		c2.addModified(c1, String::toUpperCase);
		
		Collection<Integer> c3 = new ArrayIndexedCollection<>();
		c3.addModified(c1, String::length);
		
		c2.forEach((elem)-> System.out.println( elem));
		c3.forEach((elem)-> System.out.println( elem));
	}
}
