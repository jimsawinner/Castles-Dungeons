

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.Point;

/**
 * The test class GameTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GameTest
{
    /**
     * Default constructor for test class GameTest
     */
    public GameTest()
    {
        Game game = new Game();
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void playerLocationManipulationTest() {
        Game testGame = new Game(); // MyClass is tested

        // assert statements
        assertEquals(LocationType.OUTSIDE, testGame.player1.getCurrentPosition().getLocationType());
        
    }
    
    @Test
    public void playerItemManipulationTest() {
        Game testGame = new Game(); // MyClass is tested

        // assert statements
        assertEquals(LocationType.OUTSIDE, testGame.player1.getCurrentPosition().getLocationType());
        
    }
}
