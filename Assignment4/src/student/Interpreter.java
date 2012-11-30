package student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import server.Critter;
import server.InsufficientEnergyException;
import server.Location;
import server.Plant;
import server.Rock;
import server.World;


public class Interpreter {
	public static boolean execute(Rule r, State s) {
		if (r.getCondition().eval(s)) {
			for (Update u : r.getCommand().updates) {
				s.c.update(u.one.evaluate(s), u.two.evaluate(s));
			}
			try {
				s.c.act(r.getCommand().a, r.getCommand().tag.evaluate(s));
			} catch (InsufficientEnergyException e) {
				// TODO Auto-generated catch block
			}
			return true;
		}
		return false;
	}

	public static boolean possible(Rule r, State s) {
		return (r.getCondition().eval(s));
	}

	public static World importWorld(File f) throws FileNotFoundException {
		Scanner r = new Scanner(f);
		String[] l;

		World w = new World(22, 21);

		while (r.hasNext()) {
			l = r.nextLine().split(" ");
			if (l[1].equals("plant")) {
				w.addInhabitant(new Plant(new Location(Integer.parseInt(l[3]),
						Integer.parseInt(l[2]))));
			} else if (l[1].equals("rock")) {
				w.addInhabitant(new Rock(new Location(Integer.parseInt(l[3]),
						Integer.parseInt(l[2]))));
			} else if (l[1].equals("critter")) {
				Critter c = importCritter(new File(l[2]));
				c.setLocation(Integer.parseInt(l[3]), Integer.parseInt(l[4]));
				c.setDirection(Integer.parseInt(l[5]));
				w.addInhabitant(c);
			}
		}

		return w;
	}

	public static Critter importCritter(File f) throws FileNotFoundException {
		Critter c;
		int[] m;
		String[] l;
		int i;

		BufferedReader r = new BufferedReader(new FileReader(f));
		try {
			l = r.readLine().split(" ");

			i = Integer.parseInt(l[l.length - 1]);
			m = new int[i];
			m[0] = i;

			for (int j = 0; j < 5; j++) {
				String s = r.readLine();
				l = s.split(" ");
				if (j != 5)
					m[j] = Integer.parseInt(l[l.length - 1]);
				else
					m[8] = Integer.parseInt(l[l.length - 1]);
			}
			try {
				c = new Critter(m, null);
				Program p = ParserFactory.getParser().parse(r);
				c.addProgram(p);
			} catch (Exception e) {
				c = null;
			}
		} catch (IOException e1) {
			c = null;
		}
		return c;
	}
	
	public static Critter importCritter(String s){
		Critter c;
		int[] m;
		String[] l;
		int i;

		BufferedReader r = new BufferedReader(new StringReader(s));
		try {
			l = r.readLine().split(" ");

			i = Integer.parseInt(l[l.length - 1]);
			m = new int[i];
			m[0] = i;

			for (int j = 0; j < 5; j++) {
				String str = r.readLine();
				l = str.split(" ");
				if (j != 5)
					m[j] = Integer.parseInt(l[l.length - 1]);
				else
					m[8] = Integer.parseInt(l[l.length - 1]);
			}
			try {
				c = new Critter(m, null);
				Program p = ParserFactory.getParser().parse(r);
				c.addProgram(p);
			} catch (Exception e) {
				c = null;
			}
		} catch (IOException e1) {
			c = null;
		}
		return c;
	}
}
