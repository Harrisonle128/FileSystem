/**
 * This file contains j-unit tests for the File Data class
 */
import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.*;

public class FileDataTest {
	
	@Test
	public void FileDataConstructerTest() {
		FileData fileData = new FileData("mySample.txt", "/root", "02/01/2021");
		String expected = "{Name: " + "mySample.txt" + ", Directory: " + "/root" + ", Modified Date: " + "02/01/2021" + "}";
		assertEquals(expected, fileData.toString());
		
	}
	
	@Test
	public void ConstructerTest2() {
		FileData filedata = new FileData("mySample.txt", "/root", "02/01/2021");
		FileData filedata2 = new FileData("Test.txt", "/home", "5/17/2023");
		FileData filedata3 = new FileData("mySample.txt", "/home", "02/01/2021");
		
		ArrayList<FileData> File = new ArrayList<>();
		
		File.add(filedata);
		File.add(filedata2);
		File.add(filedata3);
		
		String expected = "[{Name: mySample.txt, Directory: /root, Modified Date: 02/01/2021}, "
						+ "{Name: Test.txt, Directory: /home, Modified Date: 5/17/2023}, "
						+ "{Name: mySample.txt, Directory: /home, Modified Date: 02/01/2021}]";
		assertEquals(expected, File.toString());
		
		
	}
}
