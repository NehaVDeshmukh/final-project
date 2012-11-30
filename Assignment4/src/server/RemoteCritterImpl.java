package server;

import gui.Critter;
import gui.InsufficientEnergyException;

public class RemoteCritterImpl implements RemoteCritter {
	Critter c;
	Species s;

	public RemoteCritterImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act(Action action) {
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
			c.act(a, 0);
		} catch (InsufficientEnergyException e) {
			// TODO Auto-generated catch block
		}
	}

	@Override
	public int getTag() {
		// TODO Auto-generated method stub
		return 0;
	}

}
