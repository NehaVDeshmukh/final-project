package server;

import java.rmi.RemoteException;

import server.RemoteCritter.Action;


/**
 * An AdminServer exposes the administrative interface to
 * the critter system. 
 */
public interface AdminServer extends PlayerServer {

	/**
	 * Restart the simulation with the specified world definition.
	 * @param worldFileContent The file content of the world definition file
	 * @throws RemoteException
	 */
	public void loadWorld(String worldFileContent) throws RemoteException;

	/**
	 * Advance the simulation by one step.
	 * @throws RemoteException
	 */
	public void simStep() throws RemoteException;

	/**
	 * Continuously advance the simulation.
	 * @throws RemoteException
	 */
	public void startSim() throws RemoteException;

	/**
	 * Pause the simulation.
	 * @throws RemoteException
	 */
	public void pauseSim() throws RemoteException;

	/**
	 * Reset the world to its original state, free of critters.
	 * @throws RemoteException
	 */
	public void resetSim() throws RemoteException;

	//methods for managing the simulation
	/**
	 * Set the simulation rate.
	 * @param rate Maximum number of steps per minute.
	 * @throws RemoteException
	 */
	public void setSimRate(long rate) throws RemoteException;


	/**
	 * Remove all critters from the world.
	 * @throws RemoteException
	 */
	public void killAll() throws RemoteException;

	/**
	 * Remove the specified critter from the world.
	 * @param id Critter ID
	 * @throws RemoteException
	 * @throws IncorrectIDException 
	 */
	public void kill(int id) throws RemoteException, IncorrectIDException;

	/**
	 * Set an action a critter should take, overriding regular behavior.
	 * @param critter A reference to a critter object
	 * @param a The overriding action, or {@code null} if the critter should
	 * resume its regular behavior.
	 * @throws RemoteException
	 */
	public void control(RemoteCritter critter, Action a) throws RemoteException;

	//Uploads
	public boolean uploadsOn() throws RemoteException;

	public void setCritterUploads(boolean on) throws RemoteException;

	public boolean downloadsOn() throws RemoteException;

	public void setCritterDownloads(boolean on) throws RemoteException;

	public String[] listCritterFiles() throws RemoteException;
	
	//Methods for managing user credentials
	/**
	 * Return the list of players.
	 * @return An array containing all usernames of players.
	 * @throws RemoteException
	 */
	public String[] getPlayerList() throws RemoteException;

	/**
	 * Return the list of pending player requests.
	 * @return An array containing all usernames of pending players
	 * @throws RemoteException
	 */
	public String[] getPlayerRequests() throws RemoteException;

	/**
	 * Approve the player request and add the player to the player list.
	 * @param name The username of approved pending player
	 * @throws RemoteException
	 */
	public void addPlayer(String name) throws RemoteException;

	/**
	 * Reject the player request.
	 * @param name The username of rejected pending player
	 * @throws RemoteException
	 */
	public void rejectPlayer(String name) throws RemoteException;

	/**
	 * Remove the player from the player list.
	 * @param name The username of player to be removed
	 * @throws RemoteException
	 */
	public void removePlayer(String name) throws RemoteException;

	/**
	 * Return the list of admins.
	 * @return An array containing all usernames of admins.
	 * @throws RemoteException
	 */
	public String[] getAdminList() throws RemoteException;

	/**
	 * Return the list of pending admin requests.
	 * @return An array containing all usernames of pending admins
	 * @throws RemoteException
	 */
	public String[] getAdminRequests() throws RemoteException;

	/**
	 * Approve the admin request and add the admin to the player list.
	 * @param name The username of approved pending admin
	 * @throws RemoteException
	 */
	public void addAdmin(String name) throws RemoteException;

	/**
	 * Reject the admin request.
	 * @param name The username of rejected pending admin
	 * @throws RemoteException
	 */
	public void rejectAdmin(String name) throws RemoteException;

	/**
	 * Remove the admin from the player list.
	 * @param name The username of admin to be removed
	 * @throws RemoteException
	 */
	public void removeAdmin(String name) throws RemoteException;
}
