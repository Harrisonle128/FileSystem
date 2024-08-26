/**
 * This file contains j-unit test cases for the File System class
 */
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;



public class FileSystemTest {
	
	@Test
	public void addTest() {
		System.out.println(System.getProperty("user.dir"));
		FileSystem file = new FileSystem("src/input.txt");
		file.add("mySample.txt", "/root", "02/01/2021");
		file.findFile("mySample.txt", "/root").toString();
		assertEquals("mySample.txt", file.findFile("mySample.txt", "/root").name);
	}
	
	@Test 
	public void FindAllFileNamesTest() {
		FileSystem file = new FileSystem("src/input.txt");
		file.add("mySample.txt", "/root", "02/01/2021");
		file.add("Test.txt", "/home", "05/17/2023");
		
		ArrayList<String> Array = new ArrayList<>();
		Array.add("mySample.txt");
		Array.add("Test.txt");
		
		assertEquals(Array, file.findAllFilesName());
	}
	
	
}
