package gui;

public class Direction {

	public static final Direction ZERO = new Direction(0);
	public static final Direction ONE = new Direction(1);
	public static final Direction TWO = new Direction(2);
	public static final Direction THREE = new Direction(3);
	public static final Direction FOUR = new Direction(4);
	public static final Direction FIVE = new Direction(5);

	private int dir;

	/**
	 * Creates a direction, 0-5.
	 * @param d
	 */
	public Direction(int d) {
		dir = d % 6;
	}

	public Direction getForward() {
		return new Direction(dir);
	}

	public Direction getBackward() {
		int d = (dir + 3) % 6;
		return new Direction(d);
	}

	/**
	 * computes the location of the next tile in the Direction dir from Location
	 * loc
	 * 
	 * @param loc
	 *            Location of the center
	 * @param dir
	 *            Direction to be considered
	 * */
	static public Location getFacingTile(Direction dir, Location loc) {
		if (dir.equals(ZERO))
			return new Location(loc.col, loc.row + 1);
		else if (dir.equals(ONE))
			return new Location(loc.col + 1, loc.row + 1);
		else if (dir.equals(TWO))
			return new Location(loc.col + 1, loc.row);
		else if (dir.equals(THREE))
			return new Location(loc.col, loc.row - 1);
		else if (dir.equals(FOUR))
			return new Location(loc.col - 1, loc.row - 1);
		else if (dir.equals(FIVE))
			return new Location(loc.col - 1, loc.row);
		return loc;
	}

	/**
	 * Gets the radian value (0=vertical) associated with the direction.
	 * @return
	 */
	public double getRadians() {
		return Math.toRadians(dir * 60);
	}

	public Direction left() {
		if (dir == 0)
			return FIVE;
		else
			return new Direction(dir - 1);
	}

	public Direction right() {
		if (dir == 5)
			return ZERO;
		else
			return new Direction(dir + 1);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Direction)
			if (((Direction) o).dir == this.dir)
				return true;
		return false;
	}
}
