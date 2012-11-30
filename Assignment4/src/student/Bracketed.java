package student;


import java.util.Random;

import server.Direction;
import server.Location;

/**
 * 
 * @author lauraherrle
 * 
 */

public class Bracketed extends Expression {

	private Expression expr;
	private int type;
	private Program parent;
	private Node pNode;

	/**
	 * Creates a representation of an expression of the form type[expression]
	 * 
	 * @param e
	 *            the expression
	 * @param t
	 *            the token representing the type
	 * @param p
	 *            the parent program
	 */
	public Bracketed(Expression e, int t, Program p, Node par) {
		expr = e;
		type = t;
		parent = p;
		pNode = par;
	}

	@Override
	public int size() {
		int r = expr.size();
		if (type != Token.MEM)
			r++;
		return r;
	}

	@Override
	public Node mutate() {
		Random r = new Random();
		switch (r.nextInt(4)) {
		case 0:
			return expr;
		case 1:
			return parent.getExpression();
		case 2:
			switch (r.nextInt(4)) {
			case 0:
				return new Bracketed(expr, Token.MEM, parent, pNode);
			case 1:
				return new Bracketed(expr, Token.NEARBY, parent, pNode);
			case 2:
				return new Bracketed(expr, Token.AHEAD, parent, pNode);
			case 3:
				return new Bracketed(expr, Token.RANDOM, parent, pNode);
			default:
				return null;
			}
		case 3:
			return new MulOp(expr, parent.getExpression(), parent, pNode);
		default:
			return null;
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		sb.append(" " + Token.toString(type) + "[");
		expr.prettyPrint(sb);
		sb.append("] ");
	}

	@Override
	public void addParentProgram(Program p, Node n) {
		parent = p;
		pNode = n;
		expr.addParentProgram(p, this);
	}

	@Override
	public Node getParentNode() {
		return pNode;
	}

	@Override
	public void switchChild(Node n, Node r) {
		if (r instanceof Expression)
			expr = (Expression) r;
	}

	@Override
	public int evaluate(State s) {
		Location l;
		if (type == Token.NEARBY) {
			l = Direction.getFacingTile(new Direction(expr.evaluate(s)),
					s.c.getLocation());
			return s.getVal(l, false);
		} else if (type == Token.AHEAD) {
			l = s.c.getLocation();
			boolean ignoreCritter = false;
			int d = expr.evaluate(s);
			if (d <= 0) {
				d = -d;
				ignoreCritter = true;
			}
			for (int i = 0; i < d; i++) {
				l = Direction.getFacingTile(Direction.ZERO, l);
			}
			return s.getVal(l, ignoreCritter);
		} else if (type == Token.RANDOM) {
			int n = expr.evaluate(s);
			Random r = new Random();
			return (r.nextInt(n));
		} else if (type == Token.MEM) {
			return s.c.getVal(Math.abs(expr.evaluate(s)));
		}
		return 0;
	}
}
