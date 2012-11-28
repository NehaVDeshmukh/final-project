package server;

import java.rmi.Remote;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface RemoteCritter extends Remote{
	
	/**
	 * Instructs this critter to perform the specified action
	 */
	public void act(Action action);
	
	/**
	 * An enumeration of all possible actions.
	 */
	public static enum Action {
		WAIT,
		FORWARD,
		BACKWARD,
		LEFT,
		RIGHT,
		EAT,
		ATTACK,
		GROW,
		BUD,
		MATE;

		/**
		 * The list of actions.
		 */
		public static final List<Action> VALUES =
				Collections.unmodifiableList(Arrays.asList(values()));
		/**
		 * The number of actions.
		 */
		public static final int NUM_ACTIONS = VALUES.size();

		public static final Error NOT_ACTION = new Error("Undefined action");

		@Override
		public String toString() {
			switch (this) {
			case WAIT: return "wait";
			case FORWARD: return "forward";
			case BACKWARD: return "backward";
			case LEFT: return "left";
			case RIGHT: return "right";
			case EAT: return "eat";
			case ATTACK: return "attack";
			case GROW: return "grow";
			case BUD: return "bud";
			case MATE: return "mate";
			default: throw NOT_ACTION;
			}
		}
	}

}
