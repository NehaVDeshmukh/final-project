package student;

import java.util.ArrayList;

import server.Critter;
import server.Food;
import server.Location;
import server.Rock;
import server.World;


public class State {
	World w;
	Critter c;
	ArrayList<Location> arr = new ArrayList<Location>();
	boolean visited[][] = new boolean[20][20];
			
	public State(World world, Critter critter) {
		w = world;
		c = critter;
	}

	public int getVal(Location l, boolean ignoreCritter) {
		Critter c = w.containsCritter(l);
		Food f = w.containsFood(l);
		Rock r = w.containsRock(l);
		if (c != null && !ignoreCritter) {
			return c.appearance();
		} else if (f != null) {
			return -f.getEnergy();
		} else if (r != null) {
			return -1;
		}
		return 0;
	}
	public int getFood()
	{
		Location loc = c.getLocation();
		arr.add(loc);
		visited[c.getLocation().col - loc.col+10][c.getLocation().row - loc.row+10]=true;
		if(w.containsFood(loc)!=null)
			return 0;
		//if current location has food
		int dist=0;
		while(!arr.isEmpty() && dist<=10)
		{
			Location cur = arr.remove(0);
			for(int j=0;j<6;j++)
			{
				Location loc2 = cur.getLocationAt(j);
				if(w.containsRock(loc2)!=null || w.containsCritter(loc2)!=null)
					continue;
				if(w.containsFood(loc2)!=null)
					return dist;
				if(!visited[loc.col - loc2.col+10][loc.row - loc2.row+10])
				{
					arr.add(loc2);
					visited[loc.col - loc2.col+10][loc.row - loc2.row+10]=true;
				}				
			}
			dist++;
		}
		return 1000000; //the value of food;
	}
}
