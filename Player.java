
/**
 * Player class - will be responsible for handling
 * actions of that like a real world player.
 *
 * @author Jamie Dixon
 * @version v0.01
 */
public class Player
{
    // instance variables - replace the example below with your own
    private int hp;
    private Location currentPosition;
    private Item currentItem;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables
        hp = 100;
    }
    
    public boolean move(String direction)
    {
        // Try to leave current room.
        Location nextLocation = getCurrentPosition().getExit(direction);

        if (nextLocation == null) {
            return false;
        }
        else {
            setCurrentPosition(nextLocation);
        }
        
        return true;
    }
    
    public Location getCurrentPosition()
    {
        return currentPosition;
    }
    
    public void setCurrentPosition(Location location)
    {
        currentPosition = location;
    }
    
    public boolean getItem(String itemName)
    {
        currentItem = currentPosition.getItem(itemName);
        this.currentPosition.removeItem(itemName);
        return currentItem != null;
    }
}
