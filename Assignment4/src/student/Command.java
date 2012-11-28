package student;

import java.util.Random;

/**
 * 
 * @author lauraherrle
 * 
 */

public class Command implements Node{

	Update[] updates;
	int a;
	Expression tag;
	Program parent;
	private Node pNode;

	/**
	 * Creates a new command with no action
	 * 
	 * @param u
	 * @param p
	 */
	public Command(Update[] u, Program p, Node par) {
		updates = u;
		a = 0;
		tag = new Num(0, p, par);
		parent = p;
		pNode = par;
	}

	/**
	 * Creates a new command with an action
	 * 
	 * @param u
	 * @param act
	 *            the tooken representing the action
	 * @param p
	 */
	public Command(Update[] u, int act, Program p, Node par) {
		updates = u;
		a = act;
		tag = new Num(0, p, par);
		parent = p;
		pNode = par;
	}

	/**
	 * Creates a new command with the action and expression
	 * 
	 * @param u
	 *            the array of updates
	 * @param e
	 * @param p
	 */
	public Command(Update[] u, int a, Expression e, Program p, Node par) {
		updates = u;
		this.a = a;
		tag = e;
		parent = p;
		pNode = par;
	}

	@Override
	public int size() {
		int r = 1;
		for (int i = 0; i < updates.length; i++) {
			r += updates[i].size();
		}
		return r;
	}

	/**
	 * gets one of the updates performed for this command
	 * 
	 * @return
	 * @throws SyntaxError
	 */
	protected Update getUpdate() throws SyntaxError {
		Random r = new Random();
		if (updates.length > 0)
			return updates[r.nextInt(updates.length)];
		throw new SyntaxError();
	}

	@Override
	public Node mutate() {
		Random r = new Random();
		switch (r.nextInt(3)) {
		case 0:
			if (updates.length > 2) {
				Update[] us = updates.clone();
				int i = r.nextInt(us.length);
				Update u = us[i];
				int j = r.nextInt(us.length);
				while (j == i)
					j = r.nextInt(us.length);
				us[i] = us[j];
				us[j] = u;
				return new Command(us, a, tag, parent, pNode);
			}
		case 1:
			return parent.getCommand();
		case 2:
			return new Command(updates, r.nextInt(10) + 10, parent, pNode);
		default:
			return null;
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		for (int i = 0; i < updates.length; i++)
			updates[i].prettyPrint(sb);

		switch (a) {
		case Token.WAIT:
			sb.append("wait ");
			break;
		case Token.FORWARD:
			sb.append("forward ");
			break;
		case Token.BACKWARD:
			sb.append("backward ");
			break;
		case Token.LEFT:
			sb.append("left ");
			break;
		case Token.RIGHT:
			sb.append("right ");
			break;
		case Token.EAT:
			sb.append("eat ");
			break;
		case Token.ATTACK:
			sb.append("attack ");
			break;
		case Token.GROW:
			sb.append("grow ");
			break;
		case Token.BUD:
			sb.append("bud ");
			break;
		case Token.MATE:
			sb.append("mate ");
			break;
		case Token.TAG:
			sb.append("tag [");
			tag.prettyPrint(sb);
			sb.append("] ");
			break;
		default:
			break;
		}
	}

	@Override
	public void addParentProgram(Program p, Node n) {
		parent = p;
		pNode = n;
		for (int i = 0; i < updates.length; i++)
			updates[i].addParentProgram(p, this);
	}

	@Override
	public Node getParentNode() {
		return pNode;
	}

	@Override
	public void switchChild(Node n, Node r) {
		if (r instanceof Update) {
			for (int i = 0; i < updates.length; i++)
				if (updates[i].equals(n))
					updates[i] = (Update) r;
		}
	}

}
