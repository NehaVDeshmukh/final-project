package testing;

import static org.junit.Assert.*;
import org.junit.*;

public class JUnitStarter {
    
    // this is a basic test
    @Test
    public void basicTest() {
        int x = 1 + 2;
        
        // assertTrue(msg, bool) fails the test with message msg
        // if bool is not true
        assertTrue("x is not greater than 1", x > 1);
        assertTrue("x is not equal to 3", x == 3);
    }
    
    // this is test that expects an exception
    @Test(expected = NumberFormatException.class)
    public void exceptionTest() {
        int x = Integer.parseInt("this is not an integer");
        
        // fail(msg) fails the test with message msg
        fail("Expected a NumberFormatException; x is " + x);
    }

    
    // the following is how to write a test fixture
    // which is a suite of tests all sharing a common setup
    
    // these are the common setup variables
    private int y;
    private int z;
    
    // this method sets up the variables
    // it is called before each test
    @Before
    public void setup() {
        y = 10;
        z = 7;
    }
    
    // this method cleans up after each test
    // in this case we don't need it, but if we e.g. wrote a file for each test
    // we could delete that file here
//    @After
//    public void cleanup() {}
    
    // now all of our tests can use y and z
    @Test
    public void fixtureTest1() {
        assertTrue("y is not equal to 10", y == 10);
        assertTrue("z is not equal to 7", z == 7);
    }
    @Test(expected = ArithmeticException.class)
    public void fixtureTest2() {
        int x = y / (z - 7);
        
        fail("Expected an ArithmeticException; x is " + x);
    }
}
