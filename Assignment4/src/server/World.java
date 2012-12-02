package server;

import java.util.ArrayList;
import java.util.Random;

public class World {
	static int time = 0;
	double radius = 0;
	public final int MAX_ROW, MAX_COLUMN;

	enum Thing {
		FOOD, CRITTER, ROCK, PLANT, EMPTY
	};

	private ArrayList<Inhabitant> inhabitants;
	ArrayList<Species> species;
	private Location selected;
	private Critter in;
	private boolean wait = true;
	private int inAct;
	int numCritters = 0;
	int numPlants = 0;

	/**
	 * creates a new row with r rows and c columns
	 * 
	 * @param r
	 * @param c
	 */
	public World(int r, int c) {
		inhabitants = new ArrayList<Inhabitant>();
		species = new ArrayList<Species>();
		MAX_ROW = r - 1;
		MAX_COLUMN = c - 1;
	}

	/**
	 * checks if the given location is an empty tile
	 * */
	public boolean isEmpty(Location loc) {
		return true;
	}

	/**
	 * @returns Thing that is located in the given location loc
	 * @throws Exception
	 *             if the tile is empty
	 * */
	public Thing sensor(Location loc) throws Exception {
		if (isEmpty(loc))
			throw new Exception("there is nothing on this tile");
		// TODO
		Thing thing = Thing.EMPTY;
		return thing;
	}

	/**
	 * Deletes the Thing at the Location loc
	 * 
	 * @return Thing the type of Thing that was at loc, returns EMPTY Thing if
	 *         the tile was empty
	 * */
	public Thing delete(Location loc) {
		Inhabitant del = null;
		for (Inhabitant i : inhabitants) {
			if (i.getLocation().equals(loc))
				del = i;
		}
		if (del != null)
			inhabitants.remove(del);
		return Thing.EMPTY;
	}

	/**
	 * Checks to see if the given location is on the grid
	 * 
	 * @param l
	 * @return
	 */
	public boolean isValidLocation(Location l) {
		// TODO
		return true;
	}

	public void stepTime() {
		Random r = new Random();
		for (int i = 0; i < inhabitants.size(); i++) {
			if (inhabitants.get(i) == in) {
				try {
					inhabitants.get(i).act(inAct, r.nextInt(1000));
				} catch (InsufficientEnergyException e) {
					// TODO scarlet, what do you want to do at this level?
				}
			} else {
				inhabitants.get(i).act();
			}
		}
		time++;
	}

	public void setSelectedLocation(Location l) {
		selected = l;
		selectInhabitant(l);
	}

	private void selectInhabitant(Location l) {
		Critter c = this.containsCritter(l);
		if (c != null)
			in = c;
	}

	/**
	 * returns the selected inhabitant
	 * 
	 * @return
	 */
	public Critter selectedInhabitant() {
		return in;
	}

	/**
	 * Sets the default action of the critters
	 * 
	 * @param b
	 *            true if wait, false if random
	 */
	public void setWait(boolean b) {
		wait = b;
	}

	/**
	 * Sets the default for the selected critter to do the given action
	 * 
	 * @param i
	 */
	public void setAction(int i) {
		inAct = i;
	}

	public void addInhabitant(Inhabitant i) {
		i.addWorld(this);
		inhabitants.add(i);
		i.getLocation().setRadius(radius);
		if (i instanceof Critter)
			numCritters++;
		if (i instanceof Plant)
			numPlants++;
	}

	/**
	 * Returns the critter at Location l, null if none
	 * 
	 * @param l
	 * @return
	 */
	public Critter containsCritter(Location l) {
		for (Inhabitant i : inhabitants) {
			if (i.getLocation().equals(selected)) {
				if (i instanceof Critter)
					return (Critter) i;
			}
		}

		return null;
	}

	/**
	 * Returns the food at Location l, null if none
	 * 
	 * @param l
	 * @return
	 */
	public Food containsFood(Location l) {
		for (Inhabitant i : inhabitants) {
			if (i.getLocation().equals(selected)) {
				if (i instanceof Food)
					return (Food) i;
			}
		}

		return null;
	}

	/**
	 * Returns the rock at Location l, null if none
	 * 
	 * @param l
	 * @return
	 */
	public Rock containsRock(Location l) {
		for (Inhabitant i : inhabitants) {
			if (i.getLocation().equals(selected)) {
				if (i instanceof Rock)
					return (Rock) i;
			}
		}

		return null;
	}

	/**
	 * Returns the plant at Location l, null if none
	 * 
	 * @param l
	 * @return
	 */
	public Plant containsPlant(Location l) {
		for (Inhabitant i : inhabitants) {
			if (i.getLocation().equals(selected)) {
				if (i instanceof Plant)
					return (Plant) i;
			}
		}

		return null;
	}

	/**
	 * deletes the given inhabitant
	 * 
	 * @param i
	 */
	public void delete(Inhabitant i) {
		inhabitants.remove(i);
	}

	public void setRadius(double r) {
		radius = r;
		for (Inhabitant i : inhabitants) {
			i.getLocation().setRadius(r);
		}
	}

	public int getAction() {
		return inAct;
	}

	public int time() {
		return time;
	}

	public int critters() {
		return numCritters;
	}

	public int plants() {
		return numPlants;
	}

	/**
	 * @return the selected
	 */
	public Location getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(Location selected) {
		this.selected = selected;
	}

	/**
	 * @return the inhabitants
	 */
	public ArrayList<Inhabitant> getInhabitants() {
		return inhabitants;
	}

	/**
	 * @param inhabitants
	 *            the inhabitants to set
	 */
	public void setInhabitants(ArrayList<Inhabitant> inhabitants) {
		this.inhabitants = inhabitants;
	}

	public Species getSpecies(int id) {
		try {
			return species.get(id);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public void setSelectedInhabitant(Critter c) {
		in = c;
	}
}
