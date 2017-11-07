import java.util.HashMap;
import java.util.Set;
import java.awt.Point;

/**
 * Player class - will be responsible for handling
 * actions of that like a real world player.
 *
 * @author Jamie Dixon
 * @version v0.01
 */
public class Player
{
    private Location currentPosition;   // the players current position
    private HashMap<String, Item> inventory;    // collection of items
    private int hp; // the players health points
    private int maxStorage;

    /**
     * Constructor for objects of class Player
     * 
     * Sets the players hitpoints 
     * and prepares an inventory array
     */
    public Player()
    {
        hp = 100; // players hitpoints
        inventory = new HashMap<String, Item>(); // new empty hashmap for inventory
        maxStorage = 1;
    }
    
    /**
     * Move - player moves to a new location
     * 
     * @params  String  direction
     * @return  true if the move was successful otherwise false
     */
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
    
    /**
     * getCurrentPosition returns the players current position as a location
     * 
     * @return  Location    the players current location object
     */
    public Location getCurrentPosition()
    {
        return currentPosition;
    }
    
    /**
     * setCurrentPosition method
     * 
     * Sets the players current location (brute force compared to move)
     * 
     * @param   Location    a location object to set as the players current position
     */
    public void setCurrentPosition(Location location)
    {
        currentPosition = location;
    }
    
    /**
     * Return a string describing the players inventory, for example
     * "Inventory: health".
     * 
     * @return Details of the players inventory
     */
    public String getInventoryString()
    {
        if(!hasItems()){
            return "Inventory Empty";
        }
        
        String returnString = "Inventory:";
        Set<String> keys = inventory.keySet();
        for(String item : keys) {
            returnString += " " + item;
        }
        return returnString;
    }
    
    public boolean hasItem(String k){
        return inventory.containsKey(k);
    }
    
    public boolean hasItems(){
        return !inventory.isEmpty();
    }
    
    public void addItem(String name, Item item)
    {
        inventory.put(name,item);
    }
    
    public Item getItem(String name)
    {
        return inventory.get(name);
    }
    
    public void removeItem(String name)
    {
        inventory.remove(name);
    }
    
    public boolean takeItem(String itemName)
    {
        if(inventory.size() >= this.maxStorage){
            return false;
        }
        
        if(this.currentPosition.hasItem(itemName)){
            Item thisItem = this.currentPosition.getItem(itemName);
            inventory.put(itemName, thisItem);
            this.currentPosition.removeItem(itemName);
            return true;
        }else{
            return false;
        }
    }
    
    public boolean dropItem(String itemName)
    {
        if(hasItem(itemName)){
            Item thisItem = inventory.get(itemName);
            this.currentPosition.addItem(itemName, thisItem);
            inventory.remove(itemName);
            return true;
        }else{
            return false;
        }        
    }
}
