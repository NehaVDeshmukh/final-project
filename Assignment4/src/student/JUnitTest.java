package student;

import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import org.junit.Test;

public class JUnitTest {

	@Test
	public void test() throws FileNotFoundException {
		FileInputStream fis = new FileInputStream("input.txt");
		Scanner scan = new Scanner(fis);
		StringBuilder sb = new StringBuilder();
		String s="";
		while(scan.hasNext())
		{
			sb.append(scan.nextLine());
		}
		s = sb.toString();
		String[] str = s.split(";");
	}

}
