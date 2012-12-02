package server;

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
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import server.RemoteCritter.Action;
import student.Interpreter;

public class AdminServerImpl implements AdminServer {
	World w;
	boolean running = false;
	Timer t;
	int delay = 1000000;
	Cell[] c; // is this necessary?
	boolean uploads = true, downloads = true;
	Hashtable<String, Server> users;
	Hashtable<String, String[]> passwords;
	String username;
	ArrayList<String> aRequests, pRequests, admins, players;
	SecureRandom generator;
	ArrayList<String> critterFiles;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		World myWorld = new World(22, 21);

		// Bind an object of interface type Server to this computer's registry
		try {
			Runtime.getRuntime().exec("rmiregistry");
		} catch (IOException e) {
			System.err.println("RMIRegistry Error " + e.toString());
		}
		try {
			Server obj = new AdminServerImpl(myWorld, "me");
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

	public AdminServerImpl(World world, String name) {
		// Register the location of the codebase with your rmi registry
		System.setProperty("java.rmi.server.codebase", AdminServerImpl.class
				.getProtectionDomain().getCodeSource().getLocation().toString());
		w = world;
		t = new Timer(0, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				w.stepTime();
			}
		});
		t.setDelay(delay);
		users = new Hashtable<String, Server>();
		username = name;
		critterFiles = new ArrayList<String>();
	}

	@Override
	public String uploadCritter(String critterFileContent)
			throws RemoteException {
		Critter c = Interpreter.importCritter(critterFileContent);
		w.addInhabitant(c);
		critterFiles.add(critterFileContent);
		return c.toString();
	}

	@Override
	public int maxColumn() throws RemoteException {
		return w.MAX_COLUMN;
	}

	@Override
	public int maxRow() throws RemoteException {
		return w.MAX_ROW;
	}

	@Override
	public Cell[] getSubsection(int llCol, int llRow, int numCols, int numRows)
			throws RemoteException {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		boolean food, plant, rock, critter;
		Location l;
		for (int c = 0; c < numCols; c++) {
			for (int r = 0; r < numRows; c++) {
				l = new Location(c, r);
				food = (w.containsFood(l) != null);
				plant = (w.containsPlant(l) != null);
				rock = (w.containsRock(l) != null);
				critter = (w.containsCritter(l) != null);

				if (food || plant || rock || critter)
					try {
						RemoteCritterImpl crit = new RemoteCritterImpl(
								w.containsCritter(l));
						cells.add(new Cell(r, c, critter, plant, rock, food,
								crit, crit.s, crit.getVal(3),
								crit.getDir().dir, crit.appearance()));
					} catch (Exception e) {
						// already a legal critter
					}
			}
		}
		Cell[] cArray = new Cell[cells.size()];
		return cells.toArray(cArray);
	}

	@Override
	public boolean isRunning() throws RemoteException {
		return running;
	}

	@Override
	public long getSimRate() throws RemoteException {
		return 60 / (delay / 1000);
	}

	@Override
	public int stepsCount() throws RemoteException {
		return w.time();
	}

	@Override
	public int numCritters() throws RemoteException {
		return w.critters();
	}

	@Override
	public int numPlants() throws RemoteException {
		return w.plants();
	}

	@Override
	public String getCritterProgram(int id) throws RemoteException,
			IncorrectIDException {
		Critter c = getCritter(id);
		StringBuffer sb = new StringBuffer();
		c.getProgram().prettyPrint(sb);
		return sb.toString();
	}

	@Override
	public int[] getCritterMemory(int id) throws RemoteException,
			IncorrectIDException {
		Critter c = getCritter(id);
		return c.getMem();
	}

	@Override
	public String getCritterCurrentRule(int id) throws RemoteException,
			IncorrectIDException {
		Critter c = getCritter(id);
		StringBuffer sb = new StringBuffer();
		c.getCurrentRule().prettyPrint(sb);
		return sb.toString();
	}

	@Override
	public Action getCritterAction(int id) throws RemoteException,
			IncorrectIDException {
		Critter c = getCritter(id);
		if (!c.equals(w.selectedInhabitant()))
			return null;

		int i = w.getAction();

		switch (i) {
		case 0:
			return Action.WAIT;
		case 1:
			return Action.FORWARD;
		case 2:
			return Action.BACKWARD;
		case 3:
			return Action.LEFT;
		case 4:
			return Action.RIGHT;
		case 5:
			return Action.EAT;
		case 6:
			return Action.ATTACK;
		case 7:
			return Action.TAG;
		case 8:
			return Action.GROW;
		case 9:
			return Action.BUD;
		case 10:
			return Action.MATE;
		default:
			return null;
		}
	}

	@Override
	public String requestUserAcc(String user, String pw) throws RemoteException {
		boolean goodName = !passwords.containsKey(user);
		if (!goodName)
			return "FAILURE: DUPLICATE USERNAMD";
		pRequests.add(user);
		byte[] salt = new byte[10];
		generator.nextBytes(salt);
		String[] stuff = { salt.toString(), hash(salt.toString() + pw) };
		passwords.put(user, stuff);
		return "SUCCESS";
	}

	@Override
	public String requestAdminAcc(String user, String pw)
			throws RemoteException {
		boolean goodName = !passwords.containsKey(user);
		if (!goodName)
			return "FAILURE: DUPLICATE USERNAMD";
		aRequests.add(user);
		byte[] salt = new byte[10];
		generator.nextBytes(salt);
		String[] stuff = { salt.toString(), hash(salt.toString() + pw) };
		passwords.put(user, stuff);
		return "SUCCESS";
	}

	@Override
	public PlayerServer getPlayerServer(String user, String pw)
			throws RemoteException {
		if(players.contains(user))
			if(testValidLogin(user, pw))
				return (PlayerServer) users.get(user);
		return null;
	}

	@Override
	public AdminServer getAdminServer(String user, String pw)
			throws RemoteException {
		if(admins.contains(user))
			if(testValidLogin(user, pw))
				return (AdminServer) users.get(user);
		return null;
	}

	private boolean testValidLogin(String user, String pw) {
		String[] stuff = passwords.get(user);
		if (stuff == null)
			return false;

		String pass = hash(stuff[0] + pw);
		
		return(pass.equals(stuff[1]));
	}

	@Override
	public int[] getSpeciesAttributes(int species_id) throws RemoteException {
		return w.getSpecies(species_id).getSpeciesAttributes();
	}

	@Override
	public String getSpeciesProgram(int species_id) throws RemoteException {
		StringBuffer sb = new StringBuffer();

		w.getSpecies(species_id).getSpeciesProgram().prettyPrint(sb);

		return sb.toString();
	}

	@Override
	public int[] getLineage(int species_id) throws RemoteException {
		ArrayList<RemoteSpecies> lin = w.getSpecies(species_id).getLineage();
		int[] l = new int[lin.size()];

		for (int i = 0; i < l.length; i++) {
			l[l.length - i - 1] = w.species.indexOf(lin.get(i));
		}

		return l;
	}

	@Override
	public void loadWorld(String worldFileContent) throws RemoteException {
		w = Interpreter.importWorld(worldFileContent);
	}

	@Override
	public void simStep() throws RemoteException {
		w.stepTime();
	}

	@Override
	public void startSim() throws RemoteException {
		t.start();
	}

	@Override
	public void pauseSim() throws RemoteException {
		t.stop();
	}

	@Override
	public void resetSim() throws RemoteException {
		ArrayList<Critter> c = new ArrayList<Critter>();
		for(Inhabitant i : w.getInhabitants()) {
			if (i instanceof Critter)
				c.add((Critter) i);
		}
		
		w.getInhabitants().removeAll(c);
	}

	@Override
	public void setSimRate(long rate) throws RemoteException {
		int i = (int) ((60 / rate) * 1000);
		delay = i;
	}

	@Override
	public void killAll() throws RemoteException {
		for (Inhabitant i : w.getInhabitants()) {
			if (i instanceof Critter)
				((Critter) i).die();
		}
	}

	@Override
	public void kill(int id) throws RemoteException, IncorrectIDException {
		Critter c = getCritter(id);
		c.die();
	}

	@Override
	public void control(RemoteCritter critter, Action a) throws RemoteException {
		if(critter instanceof Critter)
			w.setSelectedInhabitant((Critter) critter);	//will always be the case; impossible to have a non-Critter RemoteCritter
		
		w.setAction(actionToInt(a));
	}

	@Override
	public boolean uploadsOn() throws RemoteException {
		return uploads;
	}

	@Override
	public void setCritterUploads(boolean on) throws RemoteException {
		uploads = on;
	}

	@Override
	public boolean downloadsOn() throws RemoteException {
		return downloads;
	}

	@Override
	public void setCritterDownloads(boolean on) throws RemoteException {
		downloads = on;
	}

	@Override
	public String[] listCritterFiles() throws RemoteException {
		return critterFiles.toArray(new String[0]);
	}

	@Override
	public String[] getPlayerList() throws RemoteException {
		return players.toArray(new String[0]);
	}

	@Override
	public String[] getPlayerRequests() throws RemoteException {
		return pRequests.toArray(new String[0]);
	}

	@Override
	public void addPlayer(String name) throws RemoteException {
		if (pRequests.remove(name)) {
			players.add(name);
			PlayerServer p = new AdminServerImpl(w, name);
			users.put(name, p);
		}
	}

	@Override
	public void rejectPlayer(String name) throws RemoteException {
		pRequests.remove(name);
		passwords.remove(name);
	}

	@Override
	public void removePlayer(String name) throws RemoteException {
		players.remove(name);
		passwords.remove(name);
		users.remove(name);
	}

	@Override
	public String[] getAdminList() throws RemoteException {
		return admins.toArray(new String[0]);
	}

	@Override
	public String[] getAdminRequests() throws RemoteException {
		return aRequests.toArray(new String[0]);
	}

	@Override
	public void addAdmin(String name) throws RemoteException {
		if (aRequests.remove(name)) {
			admins.add(name);
			AdminServer a = new AdminServerImpl(w, name);
			users.put(name, a);
		}
	}

	@Override
	public void rejectAdmin(String name) throws RemoteException {
		aRequests.remove(name);
		passwords.remove(name);
	}

	@Override
	public void removeAdmin(String name) throws RemoteException {
		admins.remove(name);
		passwords.remove(name);
		users.remove(name);
	}

	private Critter getCritter(int id) throws IncorrectIDException {
		Inhabitant p = w.getInhabitants().get(id);
		if (!(p instanceof Critter))
			throw new IncorrectIDException();

		return (Critter) p;
	}

	@Override
	public String getUsername() {
		return username;
	}

	private String hash(String s) {
		int i = s.hashCode();
		return Integer.toHexString(i);
	}
	
	private int actionToInt(Action action) {
		int a;

		switch (action) {
		case FORWARD:
			a = 1;
			break;
		case BACKWARD:
			a = 2;
			break;
		case LEFT:
			a = 3;
			break;
		case RIGHT:
			a = 4;
			break;
		case EAT:
			a = 5;
			break;
		case ATTACK:
			a = 6;
			break;
		case GROW:
			a = 8;
			break;
		case BUD:
			a = 9;
			break;
		case MATE:
			a = 10;
			break;
		case TAG:
			a = 7;
			break;
		default:
			a = 0;
			break;
		}
		
		return a;
	}
}
