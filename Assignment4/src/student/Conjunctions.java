package student;

import java.util.Random;

/**
 * Represents Condtions, Conjunctions, and Relations (which one is kept track of
 * through an enum)
 * 
 * @author lauraherrle
 * 
 */

public class Conjunctions implements Condition {
	enum Type {
		CONDITION, CONJUNCTION, RELATION
	}

	private Condition[] c;
	private Type type;
	private Program parent;
	private Node pNode;

	/**
	 * Creates a new node of the given type
	 * 
	 * @param co
	 * @param t
	 * @param p
	 */
	public Conjunctions(Condition[] co, Type t, Program p, Node par) {
		c = co;
		type = t;
		parent = p;
		pNode = par;
	}

	@Override
	public int size() {
		int r = 1;
		for (int i = 0; i < c.length; i++)
			r += c[i].size();
		return r;
	}

	@Override
	public Node mutate() {
		Random r = new Random();
		switch (r.nextInt(5)) {
		case 0:
			return c[r.nextInt(c.length)];
		case 1:
			if (c.length > 1) {

				Condition[] cs = c.clone();
				int i = r.nextInt(cs.length);
				Condition co = cs[i];
				int j = r.nextInt(cs.length);
				while (j == i)
					j = r.nextInt(cs.length);
				cs[i] = cs[j];
				cs[j] = co;
				return new Conjunctions(cs, type, parent, pNode);
			}
		case 2:
			return parent.getConjunctions();
		case 3:
			if (type == Type.RELATION) {
				switch (r.nextInt(2)) {
				case 0:
					return new Conjunctions(c, Type.CONDITION, parent, pNode);
				default:
					return new Conjunctions(c, Type.CONJUNCTION, parent, pNode);
				}
			}
			if (type == Type.CONDITION)
				return new Conjunctions(c, Type.CONJUNCTION, parent, pNode);

			return new Conjunctions(c, Type.CONDITION, parent, pNode);

		case 4:
			Condition[] condition = { this };
			return new Conjunctions(condition, Type.values()[r.nextInt(3)],
					parent, pNode);
		default:
			return null;
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		if (type != Type.RELATION) {
			String s;
			if (type == Type.CONDITION)
				s = " or";
			else
				s = " and";
			c[0].prettyPrint(sb);
			for (int i = 1; i < c.length; i++) {
				sb.append(s + " ");
				c[i].prettyPrint(sb);
			}
		} else {
			if (c[0] instanceof BinaryRelation)
				c[0].prettyPrint(sb);
			else {
				sb.append(" {");
				c[0].prettyPrint(sb);
				sb.append("} ");
			}
		}
	}

	@Override
	public boolean eval(State s) {
		boolean b;
		if (type != Type.RELATION) {
			if (type == Type.CONDITION) {
				b = false;
				for (int i = 1; i < c.length; i++) {
					b = b || c[i].eval(s);
				}
			} else {
				b = true;
				for (int i = 1; i < c.length; i++) {
					b = b && c[i].eval(s);
				}
			}
		} else {
			b = c[0].eval(s);
		}
		
		return b;
	}

	@Override
	public Expression getExpression() {
		Random r = new Random();
		return c[r.nextInt(c.length)].getExpression();
	}

	public Conjunctions getConjunctions() {
		Random r = new Random();
		int i = r.nextInt(2);
		if (i == 0 || type == Type.RELATION)
			return this;

		return ((Conjunctions) c[r.nextInt(c.length)]).getConjunctions();
	}

	public BinaryRelation getBinaryRelation() {
		if (type == Type.RELATION && c[0] instanceof BinaryRelation)
			return (BinaryRelation) c[0];

		Random r = new Random();
		return ((Conjunctions) c[r.nextInt(c.length)]).getBinaryRelation();
	}

	@Override
	public void addParentProgram(Program p, Node n) {
		parent = p;
		pNode = n;
		for (int i = 0; i < c.length; i++)
			c[i].addParentProgram(p, this);
	}

	@Override
	public Node getParentNode() {
		return pNode;
	}

	@Override
	public void switchChild(Node n, Node r) {
		if (r instanceof Condition) {
			for (int i = 0; i < c.length; i++)
				if (n.equals(c[i]))
					c[i] = (Condition) r;
		}
	}

}
