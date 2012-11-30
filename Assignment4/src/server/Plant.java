package server;


import java.util.Random;

import server.World.Thing;

/**
 * Represents the plant.
 * 
 * @author Scarlet
 * */
public class Plant implements Inhabitant {

	/**
	 * grow : possibility of this plant to generate a new plant in its adjacent
	 * tile. 0 <= grow <= 1 world : the world that this Plant lives in loc : the
	 * Location of this Plant age : the time steps that this Plant has lived
	 * */
	final double grow = 0.15;
	World world;
	Location loc;
	private int age;

	/**
	 * Creates a new plant on location l
	 * 
	 * @param l
	 */
	public Plant(Location l) {
		loc = l;
		age = 0;
	}

	/**
	 * Spawns an adjacent plant
	 * 
	 * @param loc
	 * @throws Exception
	 */
	public void generate(Location loc) throws Exception {
		Random rand = new Random();
		if (rand.nextDouble() < 0.15)
			world.addInhabitant(new Plant(Direction.getFacingTile(
					Direction.ZERO, loc)));
		if (rand.nextDouble() < 0.15)
			world.addInhabitant(new Plant(Direction.getFacingTile(
					Direction.ONE, loc)));
		if (rand.nextDouble() < 0.15)
			world.addInhabitant(new Plant(Direction.getFacingTile(
					Direction.TWO, loc)));
		if (rand.nextDouble() < 0.15)
			world.addInhabitant(new Plant(Direction.getFacingTile(
					Direction.THREE, loc)));
		if (rand.nextDouble() < 0.15)
			world.addInhabitant(new Plant(Direction.getFacingTile(
					Direction.FOUR, loc)));
		if (rand.nextDouble() < 0.15)
			world.addInhabitant(new Plant(Direction.getFacingTile(
					Direction.FIVE, loc)));
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public void addWorld(World w) {
		world = w;
	}

	@Override
	public void setLocation(Location l) {
		loc = l;
	}

	@Override
	public void act(int action, int tag) throws InsufficientEnergyException {
		age++;
	}

	@Override
	public void act() {
		age++;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
}
