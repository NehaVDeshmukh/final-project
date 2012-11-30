package server;

public interface Inhabitant {
	/**
	 * Gets the location of the inhabitant
	 * 
	 * @return
	 */
	public Location getLocation();

	/**
	 * sets the location of the inhabitant
	 * 
	 * @param l
	 */
	public void setLocation(Location l);

	/**
	 * sets the world of the inhabitant/adds the inhabitant to the world
	 * 
	 * @param world
	 */
	public void addWorld(World world);

	/**
	 * Acts, if a critter; updates regardless
	 * 
	 * @param action
	 *            the id of the action (0-10)
	 * @param tag
	 *            the tag value the action is tag
	 * @throws InsufficientEnergyException
	 */
	public void act(int action, int tag) throws InsufficientEnergyException;

	public void act();
}
