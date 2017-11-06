import java.util.HashMap;

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
    //private Item currentItem;
    private HashMap<String, Item> items;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables
        hp = 100;
        items = new HashMap<String, Item>();
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
    
    public boolean hasItem(String k){
        return items.containsKey(k);
    }
    
    public boolean hasItems(){
        return !items.isEmpty();
    }
    
    public void addItem(String name, Item item)
    {
        items.put(name,item);
    }
    
    public Item getItem(String name)
    {
        return items.get(name);
    }
    
    public void removeItem(String name)
    {
        items.remove(name);
    }
    
    public boolean takeItem(String itemName)
    {
        if(this.currentPosition.hasItem(itemName)){
            Item thisItem = this.currentPosition.getItem(itemName);
            items.put(itemName, thisItem);
            this.currentPosition.removeItem(itemName);
            return true;
        }else{
            return false;
        }
    }
    
    public boolean dropItem(String itemName)
    {
        if(hasItem(itemName)){
            Item thisItem = items.get(itemName);
            this.currentPosition.addItem(itemName, thisItem);
            items.remove(itemName);
            return true;
        }else{
            return false;
        }        
    }
}
