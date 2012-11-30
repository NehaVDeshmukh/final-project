package server;


public class RemoteCritterImpl extends Critter implements RemoteCritter {
	Species s;	//what to do about this?
	
	/**
	 * @param memory
	 * @param l
	 * @throws Exception
	 */
	public RemoteCritterImpl(int[] memory, Location l) throws Exception {
		super(memory, l);
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
			super.act(a, 0);
		} catch (InsufficientEnergyException e) {
			// TODO Auto-generated catch block
		}
	}

	@Override
	public int getTag() {
		return super.getVal(7);
	}

}
