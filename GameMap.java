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
        createPointOnMap(1,-2,"in the great hall", LocationType.HALL);        
        createPointOnMap(1,-1,"in the great hall", LocationType.HALL);
        createPointOnMap(1,0,"in the great hall", LocationType.HALL);
        createPointOnMap(1,1,"in the great hall", LocationType.HALL);
        createPointOnMap(1,2,"in the great hall", LocationType.HALL);
        createPointOnMap(1,3,"in the great hall", LocationType.HALL);
        createPointOnMap(1,4,"in the great hall", LocationType.HALL);
        createPointOnMap(1,5,"in the great hall", LocationType.HALL);
        
        // west hall
        createPointOnMap(-1,5,"in the great hall", LocationType.HALL);
        createPointOnMap(-1,4,"in the great hall", LocationType.HALL);
        createPointOnMap(-1,3,"in the great hall", LocationType.HALL);
        createPointOnMap(-1,2,"in the great hall", LocationType.HALL);
        createPointOnMap(-1,1,"in the great hall", LocationType.HALL);
        createPointOnMap(-1,0,"in the great hall", LocationType.HALL);
        createPointOnMap(-1,-1,"in the great hall", LocationType.HALL);
        createPointOnMap(-1,-2,"in the great hall", LocationType.HALL);
        
        createPointOnMap(0,2, "rumaging through bed chambers", LocationType.ROOM);
        createPointOnMap(2,1, "rumaging through bed chambers", LocationType.ROOM);
        createPointOnMap(0,5, "rumaging through bed chambers", LocationType.ROOM);
        createPointOnMap(-2,5, "rumaging through bed chambers", LocationType.ROOM);
        createPointOnMap(-3,5, "walking into a bathroom", LocationType.ROOM);
        createPointOnMap(-2,2, "walking into a bathroom", LocationType.ROOM);
        createPointOnMap(2,3, "standing in the kitchen", LocationType.ROOM);
        createPointOnMap(3,3, "browsing the pantry", LocationType.ROOM);
        
        
        createPointOnMap(0,0,"in a store room", LocationType.STORE);
        createPointOnMap(0,-2,"standing in the grand entrance", LocationType.ENTRANCE);
        createPointOnMap(0,-3,"standing on the entrance bridge", LocationType.BRIDGE);
        createPointOnMap(0,-4,"standing at bridge gates", LocationType.BRIDGE);

        createPointOnMap(1,-4,"rummaging through forest", LocationType.OUTSIDE);
        createPointOnMap(2,-4,"rummaging through forest", LocationType.OUTSIDE);
        createPointOnMap(-1,-4,"rummaging through forest", LocationType.OUTSIDE);
        createPointOnMap(-2,-4,"rummaging through forest", LocationType.OUTSIDE);
        
        // west tunnel & guard room
        createPointOnMap(-2,-1,"standing in a guard room", LocationType.GUARDED);
        createPointOnMap(-3,-1,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-4,-1,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-5,-1,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-5,0,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-5,1,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-5,2,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-5,3,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-5,4,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-5,5,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-5,6,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-4,6,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-6,6,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(-4,7,"in a dungeon - there is no way out", LocationType.DUNGEON);
        createPointOnMap(-6,7,"in a dungeon - there is no way out", LocationType.DUNGEON);
        
        // east tunnel & guard room
        createPointOnMap(2,-1,"standing in a guard room", LocationType.GUARDED);
        createPointOnMap(3,-1,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(4,-1,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(5,-1,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(5,0,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(5,1,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(5,2,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(5,3,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(5,4,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(5,5,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(5,6,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(6,6,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(4,6,"creeping up a dark and smelly tunnel", LocationType.TUNNEL);
        createPointOnMap(4,7,"in a dungeon - there is no way out", LocationType.DUNGEON);
        createPointOnMap(6,7,"in a dungeon - there is no way out", LocationType.DUNGEON);
        
        createPointOnMap(4,-2, "stuck in a trap", LocationType.TRAP);
        createPointOnMap(-2,6, "stuck in a trap", LocationType.TRAP);
        createPointOnMap(-6,-1, "stuck in a trap", LocationType.TRAP);
        
        autoSetExits();
    }
    
    /**
     * Automatically set room exits - this method will iteraste through the entire
     * hashmap to check the position of a location on the map, retrieve its neighbouring
     * room if exists and setting it as an exit in the correct direction.
     * 
     * @return boolean - true if the method completes successfully.
     */
    private boolean autoSetExits()
    {
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
        
        return true;
    }
    
    /**
     * createPointOnMap is responsible for creating map tiles first
     * it will check if a item exists in the chosen position and if
     * it does not exist in the HashMap it will create a new Point object
     * and a new Location object storing them in the map coordinates HashMap
     * respectively.
     * 
     * @params  int xPos, int yPos, String description
     * @returns boolean true if the new location was added to map, false otherwise.
     */
    private boolean createPointOnMap(int xPos, int yPos, String description, LocationType type)
    {
        Point thisPoint = new Point(xPos, yPos);
        
        if(coords.containsKey(thisPoint)){
            return false;
        }else{
            coords.put(new Point(xPos,yPos), new Location(description, type, thisPoint));
            return true;
        }
    }
    
    /**
     * getLocationByPoint - this method will return a reference to an instance
     * of the Location object if it exists in the HashMap with the key point.
     * 
     * @params Point point - a point object with x,y coords relating the map position
     * @return Location - a location object if found with key point. Null otherwise.
     */
    public Location getLocationByPoint(Point point)
    {
        return coords.get(point);
    }
}
