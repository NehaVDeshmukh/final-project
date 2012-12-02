package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import student.Program;
import student.Rule;

public class RemoteCritterImpl extends Critter implements RemoteCritter {
	Species s; // what to do about this?

	/**
	 * @param memory
	 * @param l
	 * @throws Exception
	 */
	public RemoteCritterImpl(int[] memory, Location l) throws Exception {
		super(memory, l);

	}

	public RemoteCritterImpl(Critter c) throws Exception {
		super(c.mem, c.loc);
	}

	@Override
	public void act(RemoteCritter.Action action) {
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

		try {
			super.act(a, 0);
		} catch (InsufficientEnergyException e) {
			// TODO Auto-generated catch block
		}
	}

	@Override
	public int getTag() {
		return super.getVal(7);
	}

	public int getSpecies() {
		return world.species.indexOf(s);
	}

	public void setSpecies(Species species) {
		s = species;
		if (!world.species.contains(species))
			world.species.add(species);
	}

	@Override
	public void actionBud() throws InsufficientEnergyException {
		if (mem[4] <= energyBud()) {
			die();
			throw new InsufficientEnergyException(
					"there is not enough energy to bud");
		}
		mem[4] -= energyBud();
		try {
			RemoteCritterImpl cr = new RemoteCritterImpl(mem,
					Direction.getFacingTile(Direction.THREE, loc));
			cr.addProgram((Program) prog.mutate());
			ArrayList<RemoteSpecies> l = new ArrayList<RemoteSpecies>();
			l.addAll(s.getLineage());
			l.add(s);
			cr.setSpecies(new Species(l, mem, cr.prog));
			world.addInhabitant(cr);
		} catch (Exception e) {
			// bud failed
		}
	}

	@Override
	public void actionMate() throws InsufficientEnergyException {
		if (mem[4] <= energyMate()) {
			throw new InsufficientEnergyException(
					"there is not enough energy to mate");
		}
		Critter c = world.containsCritter(Direction
				.getFacingTile(getDir(), loc));
		if (c != null && c.getDir().equals(getDir().getBackward())) {
			mem[4] -= energyMate();
			try {
				RemoteCritterImpl cr = mixSpecies(c);
				world.addInhabitant(cr);
			} catch (Exception e) {
				mem[4] += energyMate(); // mate failed
			}
		}
	}

	private RemoteCritterImpl mixSpecies(Critter c) throws Exception {
		Random r = new Random();
		ArrayList<Rule> rules = new ArrayList<Rule>();
		int ruleCount;
		if (r.nextBoolean()) {
			ruleCount = this.prog.getRuleLength();
		} else
			ruleCount = c.prog.getRuleLength();

		for (int i = 0; i < ruleCount; i++) {
			if (r.nextBoolean() && i < this.prog.getRuleLength())
				rules.add(this.prog.getRules()[i]);
			else if (i < c.prog.getRuleLength())
				rules.add(c.prog.getRules()[i]);
			else
				rules.add(this.prog.getRules()[i]);
		}

		Program p = new Program(rules.toArray(new Rule[0]));

		int[] m;

		if (r.nextBoolean())
			m = new int[mem[0]];
		else
			m = new int[c.mem[0]];

		m[0] = m.length;

		if (r.nextBoolean())
			m[1] = mem[1];
		else
			m[1] = c.mem[1];

		if (r.nextBoolean())
			m[2] = mem[2];
		else
			m[2] = c.mem[2];
		
		for(int i=3; i<Math.min(m.length, mem.length); i++)
			m[i] = mem[i];

		RemoteCritterImpl cr = new RemoteCritterImpl(this.mem,
				Direction.getFacingTile(getDir().left(), loc));
		cr.addProgram(p);
		
		ArrayList<RemoteSpecies> l = new ArrayList<RemoteSpecies>();
		l.addAll(s.getLineage());
		l.addAll(world.species.get(c.getSpecies()).getLineage());
		l.add(s);
		l.add(world.species.get(c.getSpecies()));
		
		cr.setSpecies(new Species(l, new int[] {m[0], m[1], m[2]}, p));
		
		return cr;
	}
}
