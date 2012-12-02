package server;

import java.util.ArrayList;

import student.Program;

public class Species implements RemoteSpecies {
	int[] attributes;
	ArrayList<RemoteSpecies> lineage;
	Program program;

	public Species(ArrayList<RemoteSpecies> l, int[] a, Program p) {
		attributes = a;
		lineage = l;
		program = p;
	}

	@Override
	public int[] getSpeciesAttributes() {
		return attributes;
	}

	@Override
	public ArrayList<RemoteSpecies> getLineage() {
		return lineage;
	}

	@Override
	public Program getSpeciesProgram() {
		return program;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Species) {
			if(((Species) o).attributes.equals(attributes) && ((Species) o).lineage.equals(lineage) && ((Species) o).program.equals(program))
				return true;
		}
		return false;
	}
}
