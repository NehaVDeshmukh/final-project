package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.AdminServer;
import server.PlayerServer;
import server.Server;

public abstract class Client {
	
	String HOST = null;	
	Registry registry;
	protected Server stub;

	/**
	 * A constructor that will connect to the given host via RMI
	 */
	public Client(String host) {
		HOST = host;
		connectToServer();
	}

	/**
	 * Establishes an RMI connection to the server specified by host and retrieves a 
	 * stub of interface type Server. 
	 */
	private void connectToServer() {
		try {
			Registry registry = LocateRegistry.getRegistry(HOST);
			stub = (Server) registry.lookup("Server");
		} catch (RemoteException re) {
			System.err.println("Remote Server Exception: " + re.toString());
			re.printStackTrace();
		} catch (NotBoundException abe) {
			System.err.println("Server Binding Exception: " + abe.toString());
			abe.printStackTrace();
		}
	}
	
	/**
	 * Retrieves an object of interface type AdminServer using the Server stub.
	 * userName and password should pass through authentication before the 
	 * AdminServer is provided
	 * @return an object of type AdminServer
	 */
	protected AdminServer getAdminServer(String userName, String password) {
		try {
			return stub.getAdminServer(userName, password);
		} catch (RemoteException e) {
			System.err.println("Unable to retrieve Admin Server");
			return null;
		}
	}
	
	/**
	 * Retrieves an object of interface type PlayerServer using the Server stub.
	 * userName and password should pass through authentication before the 
	 * PlayerServer is provided
	 * @return an object of type PlayerServer
	 */
	protected PlayerServer getPlayerServer(String userName, String password) {
		try {
			return stub.getPlayerServer(userName, password);
		} catch (RemoteException e) {
			System.err.println("Unable to retrieve Admin Server");
			return null;
		}
	}

}
