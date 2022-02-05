package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	@Test
	void forJMBAGworks() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase( lines);
		
		StudentRecord prvi = db.forJMBAG( "0000000001"); // 0000000001	Akšamović	Marin	2 
		assertEquals( "0000000001", prvi.getJmbag(), "jmbag nije isti");
		assertEquals( "Marin", prvi.getFirstName(), "ime nije isto");
		assertEquals( "Akšamović", prvi.getLastName(), "prezime nije isto");
		assertEquals( 2, prvi.getFinalGrade(), "ocjena nije ista");
	}
	
	@Test
	void filterKojiJeUvijekTrue() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase( lines);
		
		assertEquals( 63, db.filter( (s) -> true).size());
	}
	
	@Test
	void filterKojiJeUvijekFalse() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase( lines);
		
		assertEquals( 0, db.filter( (s) -> false).size());
	}
}
