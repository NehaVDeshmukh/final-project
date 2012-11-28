package server;

import java.rmi.Remote;
import java.util.ArrayList;

import student.Program;

public interface RemoteSpecies extends Remote {
	
	/**
	 * @return The unique attribute array for this critters of this species
	 */
	public int[] getSpeciesAttributes();
	
	/**
	 * @return The set of Species that preceded to the current species
	 */
	public ArrayList<RemoteSpecies> getLineage();
	
	/**
	 * @return The program of rules that critters of this species execute
	 */
	public Program getSpeciesProgram();

}
