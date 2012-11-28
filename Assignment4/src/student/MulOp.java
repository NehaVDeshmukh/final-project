package student;

import java.util.Random;

/**
 *
 * @author lauraherrle
 *
 */
public class MulOp extends BinaryOp {

	/**
	 * Creates a new MulOp with the given operand
	 * @param left
	 * @param op the token representation of the operand
	 * @param right
	 * @param p
	 */
	public MulOp(Expression left, int op, Expression right, Program p, Node par) {
		super(left, op, right, p, par);
	}
	
	/**
	 * Creates a new MulOp with a random operand
	 * @param left
	 * @param right
	 * @param p
	 */
	public MulOp(Expression left, Expression right, Program p, Node par) {
		super(left, new Random().nextInt(3) + 60, right, p, par);
	}
}
