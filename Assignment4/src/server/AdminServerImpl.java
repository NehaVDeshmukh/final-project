package server;

import gui.Critter;
import gui.World;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JFrame;

import student.Interpreter;

public class AdminServerImpl implements AdminServer {
	World w;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Bind an object of interface type Server to this computer's registry
		try {
			Runtime.getRuntime().exec("rmiregistry");
		} catch (IOException e) {
			System.err.println("RMIRegistry Error " + e.toString());
		}
		try {	
			Server obj = new AdminServerImpl();
		    Server stub = (Server) UnicastRemoteObject.exportObject(obj, 0);

		    // Bind the remote object's stub in the registry
		    Registry registry = LocateRegistry.getRegistry();
		    registry.bind("Server", stub);
		    System.err.println("Server ready");
		} catch (RemoteException re) {
			System.err.println("Remote Server Exception: " + re.toString());
		} catch (AlreadyBoundException abe) {
			System.err.println("Server Binding Exception: " + abe.toString());
		}
		
		// A button to terminate the server once it is no longer needed
		JFrame frame = new JFrame();
		JButton unbind = new JButton("Terminate Server");
		unbind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Registry registry = null;
				try {
					registry = LocateRegistry.getRegistry();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				try {
					if (registry != null) {
						registry.unbind("Server");
					} else {
						throw new RemoteException();
					}
				} catch (AccessException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
			
		});
		frame.add(unbind);
		frame.setSize(new Dimension(200, 200));
		frame.setVisible(true);
	}
	
	public AdminServerImpl() {
		// Register the location of the codebase with your rmi registry
		System.setProperty("java.rmi.server.codebase", 
				AdminServerImpl.class.getProtectionDomain().getCodeSource().getLocation().toString());
	}
	
	@Override
	public String uploadCritter(String critterFileContent)
			throws RemoteException {
		Critter c = Interpreter.importCritter(critterFileContent);
		w.addInhabitant(c);
		return c.toString();
	}

	@Override
	public int maxColumn() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int maxRow() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cell[] getSubsection(int llCol, int llRow, int numCols, int numRows)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRunning() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getSimRate() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int stepsCount() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int numCritters() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int numPlants() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCritterProgram(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getCritterMemory(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCritterCurrentRule(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCritterAction(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String requestUserAcc(String user, String pw) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String requestAdminAcc(String user, String pw)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerServer getPlayerServer(String user, String pw)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdminServer getAdminServer(String user, String pw)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getSpeciesAttributes(int species_id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSpeciesProgram(int species_id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLineage(int species_id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadWorld(String worldFileContent) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void simStep() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void startSim() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void pauseSim() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetSim() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSimRate(long rate) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void killAll() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void kill(int id) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void control(RemoteCritter critter, Action a) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean uploadsOn() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCritterUploads(boolean on) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean downloadsOn() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCritterDownloads(boolean on) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] listCritterFiles() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getPlayerList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getPlayerRequests() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPlayer(String name) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rejectPlayer(String name) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePlayer(String name) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getAdminList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAdminRequests() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAdmin(String name) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rejectAdmin(String name) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAdmin(String name) throws RemoteException {
		// TODO Auto-generated method stub

	}

}
