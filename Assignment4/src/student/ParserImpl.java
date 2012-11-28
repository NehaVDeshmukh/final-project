package student;

import java.io.Reader;
import java.util.ArrayList;

import student.Conjunctions.Type;

public class ParserImpl implements Parser {
	/** The program from which AST starts */
	Program program;

	/** The Tokenizer from which input is read. */
    Tokenizer tokenizer;

    /** constructor
	 * @param tk Tokenizer from which input is read
    */
    public ParserImpl(Tokenizer tk) {
    	tokenizer = tk;
    }
    /**
     * build Abstract Syntax Tree of the input parsed from r
     * if The program contains a syntax error,
     * print out "The program was unable to be parsed due to a syntax error on line." 
     * @param r Reader initializes tokenizer
     * */
    public Program parse(Reader r) {
    	tokenizer = new Tokenizer(r);
    	try {
    		program = parseProgram();
    		program.addParentProgram(program, null);
			return program;
		} catch (SyntaxError e) {
			System.out.println("The program was unable to be parsed due to a syntax error on line.");
			return null;
		}
    }

    /** Parses a program from the stream of tokens provided by the Tokenizer,
     *  consuming tokens representing the program. All following methods with
     *  a name "parseX" have the same spec except that they parse syntactic form
     *  X.
     *  @return the created AST
     *  @throws SyntaxError if there the input tokens have invalid syntax
     */
    public Program parseProgram() throws SyntaxError {
    	Rule[] r = {};
    	ArrayList<Rule> rules = new ArrayList<Rule>();
    	while(tokenizer.hasNext())
    	{
    		rules.add(parseRule());
    		consume(Token.SEMICOLON);
    	}        
        return new Program(rules.toArray(r));
    }
    
    public Rule parseRule() throws SyntaxError {
        Condition c = parseCondition();
        consume(Token.ARR);
        Command co = parseCommand();
        
        return new Rule(c, co, program, program); // c --> co
    }
    
    public Command parseCommand() throws SyntaxError {
    	//update* update-or-action
    	ArrayList<Update> u = new ArrayList<Update>();
		Update[] updates = {};
    	Command c;
    	while(tokenizer.peek().getType()==Token.MEM)
    	{
    		consume(Token.MEM);
    		consume(Token.LBRACKET);
    		Expression expr = parseExpression();
    		consume(Token.RBRACKET);
    		consume(Token.ASSIGN);
    		Expression expr2 = parseExpression();
    		u.add(new Update(expr, expr2, program, null));
    	}
    	int t =tokenizer.peek().getType();
    	if(t == Token.WAIT || t == Token.FORWARD || t == Token.BACKWARD || t == Token.LEFT || t == Token.RIGHT ||
    			t==Token.EAT || t == Token.ATTACK || t == Token.GROW || t == Token.BUD || t == Token.MATE || t == Token.TAG)
    	{
    		switch(t)
    		{
    		case Token.TAG:
    			consume(Token.TAG);
    			consume(Token.LBRACKET);
    			Expression expr3 = parseExpression();
    			consume(Token.RBRACKET);
    			c = new Command(u.toArray(updates), Token.TAG, expr3, program, null); // tag [expr3]
    			break;
    		default:
    			consume(t);
    			c = new Command(u.toArray(updates), t, program, null);
    		}
    	}
    	else
    		c = new Command(u.toArray(updates), program, null);
    	
    	return c;
    }

	public Conjunctions parseCondition() throws SyntaxError {
    	Conjunctions c = parseConjunctions();
    	ArrayList<Condition> cc = new ArrayList<Condition>();
    	Condition[] conditions = {};
    	cc.add(c);
    	while(tokenizer.peek().getType() == Token.OR)
    	{
    		consume(Token.OR);
	    	cc.add(parseConjunctions());
    	}
    	return new Conjunctions(cc.toArray(conditions), Type.CONDITION, program, null); // c ( or cc )*
    }
    
    public Conjunctions parseConjunctions() throws SyntaxError {
    	Conjunctions relation = parseRelation();
    	ArrayList<Conjunctions> rr = new ArrayList<Conjunctions>();
    	Condition[] c = {};
    	rr.add(relation);
    	while(tokenizer.peek().getType() == Token.AND)
    	{
    		consume(Token.AND);
	    	rr.add(parseConjunctions());
    	}
    	return new Conjunctions(rr.toArray(c), Type.CONJUNCTION, program, null); // r ( and rr )*
    }
    
    public Conjunctions parseRelation() throws SyntaxError {
    	if(tokenizer.peek().getType()!=Token.LBRACE)
    	{
	    	BinaryRelation rel = parseRel();
	    	BinaryRelation[] r = {rel};
	    	return new Conjunctions(r, Type.RELATION, program, null);
    	}
    	else
    	{
    		consume(Token.LBRACE);
    		Condition c = parseCondition();
    		consume(Token.RBRACE);
    		Condition[] co = {c};
	    	return new Conjunctions(co, Type.RELATION, program, null);
    	}
    }
    
    public BinaryRelation parseRel() throws SyntaxError {
    	Expression e = parseExpression();
    	int t = tokenizer.next().getType();
       	Expression e2 = parseExpression();
       	BinaryRelation r = new BinaryRelation(e, t, e2, program, null);
    	return r;
    }
    
    public Expression parseExpression() throws SyntaxError {
        return parseTerm();
    }
    
    public Expression parseTerm() throws SyntaxError {
    	Expression f = parseFactor();
    	Expression a;
    	int t = tokenizer.peek().getType();
    	while(t == Token.PLUS || t == Token.MINUS)
    	{
    		a = parseAddOp(f);
	    	f = a;
	    	t = tokenizer.peek().getType();
    	}    	
    	return f; // f ( addop ff )*
    }
    
    public AddOp parseAddOp(Expression left) throws SyntaxError {
    	int t = tokenizer.next().getType();
    	AddOp a = new AddOp(left, t, parseFactor(), program, null);
    	
    	return a;    	
    }
    
    public Expression parseFactor() throws SyntaxError {
    	Expression a = parseAtom();
    	Expression factor;
    	int t = tokenizer.peek().getType();
    	while(t == Token.MUL || t == Token.DIV || t == Token.MOD)
    	{
    		factor = parseMulOp(a);
	    	a = factor;
	    	t = tokenizer.peek().getType();
    	}    	
    	return a;
    }
    
    public MulOp parseMulOp(Expression left) throws SyntaxError {
    	int t = tokenizer.next().getType();
    	MulOp m = new MulOp(left, t, parseAtom(), program, null);
    	return m;   	
    }
    
    public Expression parseAtom() throws SyntaxError {
    	int t = tokenizer.peek().getType();
    	if(t == Token.LPAREN) // ( expr )
    	{
    		consume(Token.LPAREN);
    		Expression expr = parseExpression();
    		consume(Token.RPAREN);
    		return expr;
    	}
    	else if(t == Token.MEM) // mem [ expr ]
    	{
    		consume(Token.MEM);
    		consume(Token.LBRACKET);
    		Expression expr = parseExpression();
    		consume(Token.RBRACKET);
    		return new Bracketed(expr, t, program, null);
    	}
    	else if(t == Token.NEARBY || t == Token.AHEAD || t == Token.RANDOM)
    	{
    		Bracketed s = parseSensor();
    		return s;
    	}
    	else // <number>
    	{
    		Num n = new Num(tokenizer.next().toNumToken().getValue(), program, null);
    		return n;
    	}
    }
    
    public Bracketed parseSensor() throws SyntaxError {
    	Token t = tokenizer.next(); // nearby | ahead | random
    	consume(Token.LBRACKET);
    	Expression expr = parseExpression();
    	consume(Token.RBRACKET);
    	return new Bracketed(expr, t.getType(), program, null);
    }

    /** Consumes a token of the expected type. Throws a SyntaxError if the wrong kind of token is encountered. */
    public void consume(int tokenType) throws SyntaxError {
    	if(tokenizer.peek().getType() == tokenType)
    	{
    		tokenizer.next();
    	}
    	else throw new SyntaxError();
    }
}
