package student;

import java.util.Random;

// Represents +, -, *, /, mod
/**
 * 
 * @author lauraherrle
 * 
 */
public class BinaryOp extends Expression {
	protected Expression left;
	protected Expression right;
	protected Program parent;
	private int op;
	private String o;
	private Node pNode;

	/**
	 * Creates a new BinaryOp
	 * 
	 * @param l
	 *            the left expression
	 * @param operator
	 * @param r
	 *            the right expression
	 * @param p
	 *            the parent program
	 */
	public BinaryOp(Expression l, int operator, Expression r, Program p,
			Node par) {
		op = operator;
		left = l;
		right = r;
		parent = p;
		pNode = par;
		switch (op) {
		case Token.PLUS:
			o = "+";
			break;
		case Token.DIV:
			o = "/";
			break;
		case Token.MINUS:
			o = "-";
			break;
		case Token.MOD:
			o = "mod";
			break;
		case Token.MUL:
			o = "*";
			break;
		default:
			o = " ";
			break;
		}
	}

	@Override
	public int evaluate(State s) {
		if (op == Token.PLUS)
			return (left.evaluate(s) + right.evaluate(s));
		if (op == Token.MINUS)
			return (left.evaluate(s) - right.evaluate(s));
		if (op == Token.MUL)
			return (left.evaluate(s) * right.evaluate(s));
		if (op == Token.DIV)
			return (left.evaluate(s) / right.evaluate(s));

		return (left.evaluate(s) % right.evaluate(s));
	}

	@Override
	public int size() {
		return (left.size() + right.size() + 1);
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
			return new BinaryOp(right, op, left, parent, pNode);
		case 2:
			return parent.getExpression();
		case 3:
			switch (r.nextInt(5)) {
			case 0:
				return new BinaryOp(left, Token.PLUS, right, parent, pNode);
			case 1:
				return new BinaryOp(left, Token.MINUS, right, parent, pNode);
			case 2:
				return new BinaryOp(left, Token.DIV, right, parent, pNode);
			case 3:
				return new BinaryOp(left, Token.MUL, right, parent, pNode);
			default:
				return new BinaryOp(left, Token.MOD, right, parent, pNode);
			}
		case 4:
			switch (r.nextInt(5)) {
			case 0:
				return new BinaryOp(this, Token.PLUS, parent.getExpression(),
						parent, pNode);
			case 1:
				return new BinaryOp(this, Token.MINUS, parent.getExpression(),
						parent, pNode);
			case 2:
				return new BinaryOp(this, Token.DIV, parent.getExpression(),
						parent, pNode);
			case 3:
				return new BinaryOp(this, Token.MUL, parent.getExpression(),
						parent, pNode);
			default:
				return new BinaryOp(this, Token.MOD, parent.getExpression(),
						parent, pNode);
			}
		default:
			return null;
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		left.prettyPrint(sb);
		sb.append(" " + o + " ");
		right.prettyPrint(sb);
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
