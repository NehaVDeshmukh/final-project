package student;

import java.util.Random;

/**
 * A representation of a critter rule.
 * 
 * @author lauraherrle
 */
public class Rule implements Node {

	private Condition condition;
	// how to represent the command?
	private Command command; // aren't the commands all expressions?
	// I believe so; updates are overtly expressions and actions are represented
	// by numbers which are expressions (discuss)
	// Can I represent all: expression, term, factor, atom as binaryOps? I think
	// so...
	private Program parent;
	private Node pNode;

	/**
	 * Creates a new rule
	 * 
	 * @param c
	 * @param co
	 * @param p
	 */
	public Rule(Condition c, Command co, Program p, Node par) {
		condition = c;
		command = co;
		parent = p;
		pNode = par;
	}

	@Override
	public int size() {
		return (condition.size() + 1 + command.size());
	}

	@Override
	public Node mutate() {
		Random r = new Random();
		switch (r.nextInt(2)) {
		case 0:
			parent.removeRule(this);
			return this;
		case 1:
			return parent.getRule();
		default:
			return null;
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		condition.prettyPrint(sb);
		sb.append(" --> ");
		command.prettyPrint(sb);
		sb.append(" ;\n");
	}

	public Condition getCondition() {
		return condition;
	}

	public Command getCommand() {
		return command;
	}

	public Conjunctions getConjunctions() throws SyntaxError {
		if (condition instanceof Conjunctions)
			return ((Conjunctions) condition).getConjunctions();
		else
			throw new SyntaxError();
	}

	@Override
	public void addParentProgram(Program p, Node n) {
		parent = p;
		pNode = n;
		condition.addParentProgram(p, this);
		command.addParentProgram(p, this);
	}

	@Override
	public Node getParentNode() {
		return pNode;
	}

	@Override
	public void switchChild(Node n, Node r) {
		if (r instanceof Condition) {
			if (n.equals(condition))
				condition = (Condition) r;
		} else if (r instanceof Command) {
			if (n.equals(command))
				command = (Command) r;
		}
	}
}
