package hr.fer.oprpp1.hw04.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Main class.
 * @author renat
 */
public class StudentDB {
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main( String[] args) {
		try(BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));){
			String queryInput = null;
			
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			StudentDatabase db = new StudentDatabase( lines);
			
			try {
				System.out.print("> ");
				queryInput = reader.readLine();
			}catch( IOException e) {
				System.out.println( "ne moze se ucitati query s konzole");
				return;
			}
			while( !queryInput.equals("exit")) {			
				try {
					queryInput = queryInput.trim();
					if( !queryInput.startsWith( "query")) throw new IllegalArgumentException( "podrzana je samo jedna komanda, a to je query");
					queryInput = queryInput.substring(5); // izbaci query na pocetku
					QueryParser parser = new QueryParser( queryInput);
					
					List<StudentRecord> records;
					if( parser.isDirectQuery()) {
						StudentRecord r = db.forJMBAG( parser.getQueriedJMBAG());
						records = new LinkedList<>();
						if( r != null) {
							records.add( r);							
						}
					}else{
						records = db.filter( new QueryFilter( parser.getQuery()));
					}
					
					String output = RecordFormatter.format( records, parser.getWithStatistics());
					System.out.println( output);
					
				}catch( Exception e) {
					System.out.println( "dogodila se greska: " +e.getClass() +" : " + e.getMessage());
				}
				try {
					System.out.print("> ");
					queryInput = reader.readLine();
				} catch (IOException e) {
					System.out.println( "ne moze se ucitati query s konzole");
					return;
				}
			}
			
		}catch( IOException e) {
			System.out.println( "ne moze se procitati baza");
			return;
		}catch( IllegalArgumentException e) {
			System.out.println( e.getClass() + " : " + e.getMessage());
			return;
		}finally {
			System.out.println( "Goodbye!");
		}
		
	}
}
