package student;

import java.util.Random;

/**
 * 
 * @author lauraherrle
 *
 */

public class Update implements Node{

	Expression one, two;
	Program parent;
	private Node pNode;
	
	/**
	 * Creates a new update of the form mem[expr1] := expr2
	 * @param exp1
	 * @param exp2
	 * @param p
	 */
	public Update(Expression exp1, Expression exp2, Program p, Node par) {
		one = exp1;
		two = exp2;
		parent = p;
		pNode = par;
	}
	
	@Override
	public int size() {
		return (one.size() + two.size() + 1);
	}

	@Override
	public Node mutate() {
		Random r = new Random();
		switch (r.nextInt(2)) {
		case 0:
			try {
				return parent.getUpdate();
			} catch (SyntaxError e) {
				//does not return; goes to next
			}
		case 1:
			return new Update(two, one, parent, pNode);
		default:
			return null;
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		sb.append("mem[");
		one.prettyPrint(sb);
		sb.append("] := ");
		two.prettyPrint(sb);
		sb.append(" ");
	}

	@Override
	public void addParentProgram(Program p, Node n) {
		parent = p;
		pNode = n;
		one.addParentProgram(p, this);
		two.addParentProgram(p, this);
	}
	
	@Override
	public Node getParentNode() {
		return pNode;
	}
	
	@Override
	public void switchChild(Node n, Node r) {
		if (r instanceof Expression) {
			if (n.equals(one))
				one = (Expression) r;
			else if (n.equals(two))
				two = (Expression) r;
		}
	}
}
