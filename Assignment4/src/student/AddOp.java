package student;

import java.util.Random;

/**
 * 
 * @author lauraherrle
 * 
 */
public class AddOp extends BinaryOp {

	/**
	 * Creates a new AddOp
	 * 
	 * @param left
	 * @param op
	 *            the operand
	 * @param right
	 * @param p
	 *            the parent program
	 */
	public AddOp(Expression left, int op, Expression right, Program p, Node par) {
		super(left, op, right, p, par);
	}

	/**
	 * Creates a new AddOp with a random operand
	 * 
	 * @param left
	 * @param right
	 * @param p
	 *            the parent program
	 */
	public AddOp(Expression left, Expression right, Program p, Node par) {
		super(left, new Random().nextInt(2) + 50, right, p, par);
	}
}
