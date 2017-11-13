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
    // instance variables
    private HashMap<Point, Location> coords;

    /**
     * Constructor for objects of class GameMap
     */
    public GameMap()
    {
        // initialise instance variables
        coords = new HashMap<Point, Location>();
        setupLocations();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void setupLocations()
    {   
        // create new locations for the dungeons and beasts game
        
        // east hall
        createPointOnMap(1,-2,"in the great hall");        
        createPointOnMap(1,-1,"in the great hall");
        createPointOnMap(1,0,"in the great hall");
        createPointOnMap(1,1,"in the great hall");
        createPointOnMap(1,2,"in the great hall");
        createPointOnMap(1,3,"in the great hall");
        createPointOnMap(1,4,"in the great hall");
        createPointOnMap(1,5,"in the great hall");
        
        // west hall
        createPointOnMap(-1,5,"in the great hall");
        createPointOnMap(-1,4,"in the great hall");
        createPointOnMap(-1,3,"in the great hall");
        createPointOnMap(-1,2,"in the great hall");
        createPointOnMap(-1,1,"in the great hall");
        createPointOnMap(-1,0,"in the great hall");
        createPointOnMap(-1,-1,"in the great hall");
        createPointOnMap(-1,-2,"in the great hall");
        
        createPointOnMap(0,0,"in a store room");
        createPointOnMap(0,-2,"standing in the grand entrance");
        createPointOnMap(0,-3,"standing on the entrance bridge");
        createPointOnMap(0,-4,"standing at bridge gates");

        createPointOnMap(1,-4,"rummaging through forest");
        createPointOnMap(2,-4,"rummaging through forest");
        createPointOnMap(-1,-4,"rummaging through forest");
        createPointOnMap(-2,-4,"rummaging through forest");
        
        // west tunnel & guard room
        createPointOnMap(-2,-1,"standing in a guard room");
        createPointOnMap(-3,-1,"creeping up a dark and smelly tunnel");
        createPointOnMap(-4,-1,"creeping up a dark and smelly tunnel");
        createPointOnMap(-5,-1,"creeping up a dark and smelly tunnel");
        createPointOnMap(-5,0,"creeping up a dark and smelly tunnel");
        createPointOnMap(-5,1,"creeping up a dark and smelly tunnel");
        createPointOnMap(-5,2,"creeping up a dark and smelly tunnel");
        createPointOnMap(-5,3,"creeping up a dark and smelly tunnel");
        createPointOnMap(-5,4,"creeping up a dark and smelly tunnel");
        createPointOnMap(-5,5,"creeping up a dark and smelly tunnel");
        
        // east tunnel & guard room
        createPointOnMap(2,-1,"standing in a guard room");
        createPointOnMap(3,-1,"creeping up a dark and smelly tunnel");
        createPointOnMap(4,-1,"creeping up a dark and smelly tunnel");
        createPointOnMap(5,-1,"creeping up a dark and smelly tunnel");
        createPointOnMap(5,0,"creeping up a dark and smelly tunnel");
        createPointOnMap(5,1,"creeping up a dark and smelly tunnel");
        createPointOnMap(5,2,"creeping up a dark and smelly tunnel");
        createPointOnMap(5,3,"creeping up a dark and smelly tunnel");
        createPointOnMap(5,4,"creeping up a dark and smelly tunnel");
        createPointOnMap(5,5,"creeping up a dark and smelly tunnel");
        
        
        // Iterate through the coordinates HashMap to check/add exits
        for (Point key : coords.keySet()) {
            
            // if the HashMap contains a key with a point with an x value of this points x value + 1
            if(coords.containsKey(new Point((int)key.getX()+1,(int)key.getY()))){
                // there is a location to the east (set an exit?)
                Point neighbourPoint = new Point((int)key.getX()+1,(int)key.getY());
                
                // set exits to the east
                getLocationByPoint(key).setExit("east", getLocationByPoint(neighbourPoint));
            }
            
            if(coords.containsKey(new Point((int)key.getX()-1,(int)key.getY()))){
                Point neighbourPoint = new Point((int)key.getX()-1,(int)key.getY());
                
                // there is a location to the west (set an exit?)                
                getLocationByPoint(key).setExit("west", getLocationByPoint(neighbourPoint));
            }
            
            if(coords.containsKey(new Point((int)key.getX(),(int)key.getY()+1))){
                Point neighbourPoint = new Point((int)key.getX(),(int)key.getY()+1);
                
                // there is a location to the north (set an exit?)                
                getLocationByPoint(key).setExit("north", getLocationByPoint(neighbourPoint));
            }
            
            if(coords.containsKey(new Point((int)key.getX(),(int)key.getY()-1))){
                Point neighbourPoint = new Point((int)key.getX(),(int)key.getY()-1);
                
                // there is a location to the west (set an exit?)                
                getLocationByPoint(key).setExit("south", getLocationByPoint(neighbourPoint));
            }
        }
    }
    
    private boolean createPointOnMap(int xPos, int yPos, String description)
    {
        Point thisPoint = new Point(xPos, yPos);
        
        if(coords.containsKey(thisPoint)){
            System.out.println("Position: " +xPos+ "," +yPos+ "taken. Cannot create new: " +description); 
            return false;
        }else{
            coords.put(new Point(xPos,yPos), new Location(description));
            return true;
        }
    }
    
    public Location getLocationByPoint(Point point)
    {
        return coords.get(point);
    }
}
