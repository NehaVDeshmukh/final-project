package student;

import java.util.Random;

/**
 * Represents a binary relation (using <, <=, =, >=, >)
 * 
 * @author lauraherrle
 * 
 */

public class BinaryRelation implements Condition {

	private int r;
	private Expression left, right;
	private Program parent;
	private Node pNode;

	/**
	 * Creates a new BinaryRElation
	 * 
	 * @param e
	 *            the left expression
	 * @param t
	 *            the token representing the operand
	 * @param e2
	 *            the right expression
	 * @param p
	 *            the parent program
	 */
	public BinaryRelation(Expression e, int t, Expression e2, Program p, Node par) {
		left = e;
		right = e2;
		r = t;
		parent = p;
		pNode = par;
	}

	@Override
	public int size() {
		return left.size() + 1 + right.size();
	}

	@Override
	public Node mutate() {
		Random r = new Random();
		switch (r.nextInt(5)) {
		case 0:
			return null;
		case 1:
			return new BinaryRelation(right, this.r, left, parent, pNode);
		case 2:
			return parent.getBinaryRelation();
		case 3:
			switch (r.nextInt(6)) {
			case 0:
				return new BinaryRelation(left, Token.LT, right, parent, pNode);
			case 1:
				return new BinaryRelation(left, Token.LE, right, parent, pNode);
			case 2:
				return new BinaryRelation(left, Token.EQ, right, parent, pNode);
			case 3:
				return new BinaryRelation(left, Token.GT, right, parent, pNode);
			case 4:
				return new BinaryRelation(left, Token.GE, right, parent, pNode);
			default:
				return new BinaryRelation(left, Token.NE, right, parent, pNode);
			}
		case 4:
			Condition[] c = { this };
			return new Conjunctions(c, Conjunctions.Type.RELATION, parent, pNode);
		default:
			return null;
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		left.prettyPrint(sb);
		if (r == Token.LT)
			sb.append(" < ");
		else if (r == Token.LE)
			sb.append(" <= ");
		else if (r == Token.EQ)
			sb.append(" = ");
		else if (r == Token.GE)
			sb.append(" >= ");
		else if (r == Token.GT)
			sb.append(" > ");
		else if (r == Token.NE)
			sb.append(" != ");
		right.prettyPrint(sb);
	}

	@Override
	public boolean eval(State s) {
		if (r == Token.LT)
			return (left.evaluate(s) < right.evaluate(s));
		if (r == Token.LE)
			return (left.evaluate(s) <= right.evaluate(s));
		if (r == Token.EQ)
			return (left.evaluate(s) == right.evaluate(s));
		if (r == Token.GE)
			return (left.evaluate(s) >= right.evaluate(s));
		if (r == Token.GT)
			return (left.evaluate(s) > right.evaluate(s));
		return (left.evaluate(s) != right.evaluate(s));
	};

	@Override
	public Expression getExpression() {
		Random r = new Random();
		int i = r.nextInt(2);
		if (i == 0)
			return left;

		return right;
	}

	@Override
	public void addParentProgram(Program p, Node n) {
		parent = p;
		pNode = n;
		left.addParentProgram(p, this);
		right.addParentProgram(p, this);
	}

	@Override
	public Node getParentNode() {
		return pNode;
	}
	
	@Override
	public void switchChild(Node n, Node r) {
		if (r instanceof Expression) {
			if (n.equals(right))
				right = (Expression) r;
			else if (n.equals(left))
				left = (Expression) r;
		}
	}
}
