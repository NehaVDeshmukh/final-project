package student;

// A critter program expression that has an integer value.
public abstract class Expression implements Node {
	
	public abstract int evaluate(State s);
}