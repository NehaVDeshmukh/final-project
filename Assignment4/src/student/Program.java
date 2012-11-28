package student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A representation of a critter program.
 * 
 * @author lauraherrle
 */
public class Program implements Node {
	protected Rule[] rules;

	/**
	 * Creates a new program with the given set of rules
	 * 
	 * @param r
	 */
	public Program(Rule[] r) {
		rules = r;
	}

	public int getRuleLength()
	{
		return rules.length;
	}
	@Override
	public int size() {
		int r = 1;
		for (int i = 0; i < rules.length; i++)
			r += rules[i].size();
		return r;
	}

	@Override
	public Node mutate() {
		Random r = new Random();
		switch (r.nextInt(8)) {
		case 0:
			mutateNode(getRule());
			break;
		case 1:
			mutateNode(getCondition());
			break;
		case 2:
			mutateNode(getCommand());
			break;
		case 3:
			try {
				mutateNode(getUpdate());
				break;
			} catch (SyntaxError e) {
				//does not break, goes to next
			}
		case 4:
			mutateNode(getExpression());
			break;
		case 5:
			mutateNode(getBinaryRelation());
			break;
		case 6:
			mutateNode(getConjunctions());
			break;
		case 7:
			mutateNode(getBinaryOp());
			break;
		default:
			this.mutate();
		}
		return this;
	}
	
	private void mutateNode(Node n) {
		Node p = n.getParentNode();
		p.switchChild(n, n.mutate());
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		for (Rule r : rules)
			r.prettyPrint(sb);
	}

	protected Rule getRule() {
		Random r = new Random();
		return rules[r.nextInt(rules.length)];
	}

	protected Condition getCondition() {
		return getRule().getCondition();
	}

	protected Command getCommand() {
		return getRule().getCommand();
	}

	protected Update getUpdate() throws SyntaxError {
		return getRule().getCommand().getUpdate();
	}

	protected Expression getExpression() {
		return getRule().getCondition().getExpression();
	}

	protected BinaryRelation getBinaryRelation() {
		try {
			return getRule().getConjunctions().getBinaryRelation();
		} catch (SyntaxError e) {
			return getBinaryRelation();
		}
	}

	protected Conjunctions getConjunctions() {
		try {
			return getRule().getConjunctions();
		} catch (SyntaxError e) {
			return getConjunctions();
		}
	}

	protected BinaryOp getBinaryOp() {
		Expression e = getExpression();
		if (e instanceof BinaryOp)
			return (BinaryOp) e;
		return getBinaryOp();
	}

	protected boolean removeRule(Rule r) {
		ArrayList<Rule> rs = new ArrayList<Rule>();
		for(int i=0; i<rules.length; i++)
			rs.add(rules[i]);
		Boolean b = rs.remove(r);
		Rule[] rus = new Rule[0];
		rs.toArray(rus);
		rules = rus;

		return b;
	}

	@Override
	public void addParentProgram(Program p, Node n) {
		for(int i=0; i<rules.length; i++)
			rules[i].addParentProgram(p, this);
	}
	
	@Override
	public Node getParentNode() {
		return null;
	}

	@Override
	public void switchChild(Node n, Node r) {
		if (r instanceof Rule) {
			for (int i = 0; i < rules.length; i++)
				if (rules[i].equals(n))
					rules[i] = (Rule) r;
		}
	}
	
	public Rule[] getRules() {
		return rules;
	}
}
