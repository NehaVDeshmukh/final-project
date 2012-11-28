package gui;

import gui.World.Thing;

import java.util.Random;

/**
 * Represents the Rock
 * 
 * @author Scarlet
 * */
public class Rock implements Inhabitant {

	/**
	 * loc : Location of this Rock world : the World that the Rock is Located
	 * */
	Location loc;
	World world;

	/**
	 * moveP : the probability of this rock moving shatterP : the probability of
	 * this rock shattering and disappearing.
	 * */
	final double moveP = 0.000012;
	final double shatterP = 0.05;

	public Rock(Location l) {
		loc = l;
	}

	/**
	 * extension: sometimes when the wind blows, the rock moves. Because rock is
	 * very heavy, the chances of it being moved is very low.
	 * */
	public Location move() {
		Random rand = new Random();
		if (rand.nextDouble() < moveP) {
			world.delete(loc);
			int p = ((int) (moveP * 100)) % ((int) (moveP * 100 / 6));
			switch (p) {
			case 0:
				loc = Direction.getFacingTile(Direction.ZERO, loc);
				break;
			case 1:
				loc = Direction.getFacingTile(Direction.ONE, loc);
				break;
			case 2:
				loc = Direction.getFacingTile(Direction.TWO, loc);
				break;
			case 3:
				loc = Direction.getFacingTile(Direction.THREE, loc);
				break;
			case 4:
				loc = Direction.getFacingTile(Direction.FOUR, loc);
				break;
			case 5:
				loc = Direction.getFacingTile(Direction.FIVE, loc);
				break;
			}
		}
		return loc;
	}

	/**
	 * extension: sometimes when the wind blows very strongly, the rock shatters
	 * and disappears. Because a rock is very hard, its chances of shattering is
	 * very low.
	 * */
	public void shatter() {
		Random rand = new Random();
		if (rand.nextDouble() < shatterP) {
			world.delete(loc);
		}
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public void addWorld(World w) {
		world = w;
		try {
			world.newThing(Thing.ROCK, loc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		world.inhabitants.add(this);
	}

	@Override
	public void setLocation(Location l) {
		loc = l;
	}

	@Override
	public void act(int action, int tag) throws InsufficientEnergyException {
		act();
	}

	@Override
	public void act() {
		move();
		shatter();
	}
}
