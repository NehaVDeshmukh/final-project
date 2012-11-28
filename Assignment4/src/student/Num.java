package student;

import java.util.Random;

/**
 * 
 * @author lauraherrle
 * 
 */
public class Num extends Expression implements Node {
	int value;
	Program parent;
	private Node pNode;

	/**
	 * Creates a new Num node
	 * 
	 * @param v
	 * @param p
	 */
	public Num(int v, Program p, Node par) {
		value = v;
		parent = p;
		pNode = par;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public Node mutate() {
		Random r = new Random();
		switch (r.nextInt(3)) {
		case 0:
			return parent.getExpression();
		case 1:
			int i = java.lang.Integer.MAX_VALUE / r.nextInt();
			if (r.nextInt(2) == 0)
				i = value + i;
			else
				i = value - i;

			return new Num(Math.abs(i), parent, pNode);
		case 2:
			if (r.nextInt(2) == 0)
				return new MulOp(this, parent.getExpression(), parent , pNode);
			return new AddOp(this, parent.getExpression(), parent , pNode);
		default:
			return null;
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		sb.append(value);
	}

	@Override
	public int evaluate(State s) {
		return value;
	}

	@Override
	public void addParentProgram(Program p, Node n) {
		parent = p;
		pNode = n;
	}
	
	@Override
	public Node getParentNode() {
		return pNode;
	}

	@Override
	public void switchChild(Node n, Node r) {
		//does nothing because Num is infertile :(
	}
}
