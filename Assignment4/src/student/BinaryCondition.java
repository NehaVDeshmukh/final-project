package student;

import java.util.Random;

/**
 * A representation of a binary Boolean condition: 'and' or 'or'
 * 
 * @author lauraherrle
 */

public class BinaryCondition implements Condition {
	private Condition left, right;
	private BinaryConditionOperator o;
	private Program parent;
	private Node pNode;

	/**
	 * Create an AST representation of l op r.
	 * 
	 * @param l
	 * @param op
	 * @param r
	 */
	public BinaryCondition(Condition l, BinaryConditionOperator op,
			Condition r, Program p, Node par) {
		left = l;
		right = r;
		o = op;
		parent = p;
		pNode = par;
	}

	@Override
	public boolean eval(State s) {
		if (o == BinaryConditionOperator.AND)
			return (left.eval(s) && right.eval(s));

		return (left.eval(s) || right.eval(s));
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
			if (r.nextInt(2) == 0)
				return left;
			return right;
		case 1:
			return new BinaryCondition(right, o, left, parent, pNode);
		case 2:
			return parent.getConjunctions();
		case 3:
			return new BinaryCondition(left,
					BinaryConditionOperator.values()[r.nextInt(2)], right,
					parent, pNode);
		case 4:
			Condition[] c = { this };
			return new Conjunctions(c,
					Conjunctions.Type.values()[r.nextInt(2)], parent, pNode);
		default:
			return null;
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		left.prettyPrint(sb);
		if (o == BinaryConditionOperator.AND)
			sb.append(" AND ");

		sb.append(" OR ");
	}

	@Override
	public Expression getExpression() {
		Random r = new Random();
		int i = r.nextInt(2);
		if (i == 0)
			return left.getExpression();

		return right.getExpression();
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
		if (r instanceof Condition) {
			if (n.equals(right))
				right = (Condition) r;
			else if (n.equals(left))
				left = (Condition) r;
		}
	}
}
