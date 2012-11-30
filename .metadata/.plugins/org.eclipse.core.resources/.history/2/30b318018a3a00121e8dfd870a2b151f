package gui;

public class Food implements Inhabitant {
	int eng;
	Location loc;
	World w;

	/**
	 * Creates a new Food
	 * @param l the location
	 * @param energy the amount of energy in the food
	 */
	public Food(Location l, int energy) {
		eng = energy;
		loc = l;
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public void setLocation(Location l) {
		loc = l;
	}

	@Override
	public void addWorld(World world) {
		w = world;
	}

	/**
	 * gets the energy contained in the food
	 * @return
	 */
	public int getEnergy() {
		return eng;
	}

	@Override
	public void act(int action, int tag) throws InsufficientEnergyException {
		//does nothing, food just sits there (unless we decide to make it grow/decay in energy)
	}

	@Override
	public void act() {
		//does nothing, food just sits there (unless we decide to make it grow/decay in energy)
	}
}
