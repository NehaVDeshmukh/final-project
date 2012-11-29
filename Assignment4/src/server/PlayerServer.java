package server;

import java.rmi.RemoteException;

/**
 * A PlayerServer exposes upload critter permissions to the user.
 */
public interface PlayerServer extends Server {

	/**
	 * Upload a critter to the server.
	 * @param critterFileContent The string content of the critter definition file.
	 * @returns "SUCCESS:..." or "FAILURE:..." to indicate whether the 
     * critter upload was successful. ... is an optional customized message
     * to be displayed to the user
	 * @throws RemoteException
	 */
	public String uploadCritter(String critterFileContent) throws RemoteException;
	
	/*
	 * so, plan... that I came up with after almost fully implementing this...
	 * instantiate all PlayerServer objects as AdminServerImpl objects.
	 * but only the PlayerServer operations will be visible, obviously.
	 * yay me.
	 * But, yeah, Scarlet, there isn't a class missing. IT'S THE PLAN.
	 */
}
