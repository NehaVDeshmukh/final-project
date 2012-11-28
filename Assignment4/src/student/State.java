package student;

import gui.Critter;
import gui.Food;
import gui.Location;
import gui.Rock;
import gui.World;

public class State {
	World w;
	Critter c;

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
}
