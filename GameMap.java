import java.util.HashMap;
import java.awt.Point;

/**
 * Responsible for coordinating players/locations
 * around a 'map' object.
 *
 * @author Jamie Dixon
 * @version v0.03
 */
public class GameMap
{
    // instance variables - replace the example below with your own
    private int x;
    private HashMap<String, Location> map;
    private HashMap<Point, Location> coords;

    /**
     * Constructor for objects of class GameMap
     */
    public GameMap()
    {
        // initialise instance variables
        x = 0;
        map = new HashMap<String, Location>();
        coords = new HashMap<Point, Location>();
        setupLocations();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void setupLocations()
    {   
        Location outside, theatre, pub, lab, office, spaceship;
        Location sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto;
        
        // create the rooms
        outside = new Location("outside the main entrance of the university");
        theatre = new Location("in a lecture theatre");
        pub = new Location("in the campus pub");
        lab = new Location("in a computing lab");
        office = new Location("in the computing admin office");
        spaceship = new Location("a derelict spaceship");
        
        // create new locations for the space wars game
        sun = new Location("The sun");
        mercury = new Location("Mercury");
        venus = new Location("Venus");
        earth = new Location("The planet earth");
        mars = new Location("Mars");
        jupiter = new Location("Jupiter");
        saturn = new Location("Saturn");
        uranus = new Location("Uranus");
        neptune = new Location("Neptune");
        pluto = new Location("Pluto");
        
        sun.setExit("south", mercury);
        mercury.setExit("north", sun);
        
        mercury.setExit("south", venus);
        venus.setExit("north", mercury);
        
        venus.setExit("south", earth);
        earth.setExit("north", venus);
        
        earth.setExit("south", mars);
        mars.setExit("north", earth);
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", spaceship);
        
        spaceship.setExit("south", outside);
        spaceship.setExit("north", sun);
        
        coords.put(new Point(0,0), outside);
        coords.put(new Point(-1,0), theatre);
        coords.put(new Point(1,0), pub);
        coords.put(new Point(0,-1), lab);
        coords.put(new Point(-1,-1), office);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        //office.setExit("west", lab);
        
        map.put("outside", outside);
        map.put("theatre", theatre);
        map.put("pub", pub);
        map.put("lab", lab);
        map.put("office", office);
    }
    
    public Location getLocationByPoint(Point point)
    {
        return coords.get(point);
    }
    
    public Location getLocationByName(String name)
    {
        return map.get(name);
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
