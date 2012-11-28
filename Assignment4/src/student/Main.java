package student;

import java.util.*;
import java.io.*;

public class Main {

	/**
	 * @param args --mutate <mutnum> filename
	 */
	public static void main(String[] args) throws Exception {
		if(args.length!=1 && args.length!=3)
			throw new IOException("requires correct number of arguments");
		int mutnum = 0;
		FileReader fr;
		FileWriter fw = new FileWriter("output.txt");
		ParserImpl pi;
		Tokenizer tz;
		StringBuffer sbf;
		
		if(args[0].equals("--mutate"))
		{
			try
			{
				mutnum = Integer.parseInt(args[1]);
			}
			catch(Exception e)
			{
				throw new Exception("your input was not a valid value of n");
			}
			finally
			{
				fr = new FileReader(args[2]);
			}
		}
		else
		{
			fr = new FileReader(args[0]);
		}
		
		sbf = new StringBuffer();
		tz = new Tokenizer(fr);
		pi = new ParserImpl(tz);
		Program p = pi.parse(fr);
		for(int i=0;i<mutnum;i++)
		{
			p.mutate();
		}
		p.prettyPrint(sbf); 
		System.out.print(sbf.toString());
		fw.write(sbf.toString());		
		fw.write("\n");
		fw.close();
	}

}
