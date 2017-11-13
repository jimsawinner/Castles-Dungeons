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
        hp = 100; // players health points
        inventory = new HashMap<String, Item>(); // new empty hashmap for inventory
        maxStorage = 1;
    }
    
    /**
     * Move - player moves to a new location
     * 
     * @since v0.02
     * @params  String  direction
     * @return  true if the move was successful otherwise false
     */
    public boolean move(String direction)
    {
        // Try to leave current room.
        Location nextLocation = getCurrentPosition().getExit(direction);
        
        switch(direction){
            case "north" :
                System.out.println("go north");
                break;
            case "east" :
                System.out.println("go east");
                break;
            case "west" :
                System.out.println("go west");
                break;
            case "south" :
                System.out.println("go south");
                break;
        }

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
    
    /**
     * hasItem checks if the player has an item with
     * particular key in inventory
     * 
     * @param   String  k the items key
     * @return  true if the player has the item otherwise false
     */
    public boolean hasItem(String k){
        return inventory.containsKey(k);
    }
    
    /**
     * hasItems checks if the player has ANY 
     * items in the inventory
     * 
     * @return  true if the player has >0 items otherwise false
     */
    public boolean hasItems(){
        return !inventory.isEmpty();
    }
    
    /**
     * addItem adds a new item object to the players inventory
     * 
     * @params  String name - item key, Item item - item object
     */
    public void addItem(String name, Item item)
    {
        inventory.put(name,item);
    }
    
    /**
     * getItem retrieves an item from the players inventory
     * 
     * @params  String name - the name (key) of an item
     * @return  Item the item object if found
     */
    public Item getItem(String name)
    {
        return inventory.get(name);
    }
    
    /**
     * removeItem removes an item from the players inventory
     * 
     * @params  String name - the name (key) of an item
     */    
    public void removeItem(String name)
    {
        inventory.remove(name);
    }
    
    /**
     * takeItem checks if inventory is not already full and
     * proceeds to add the item to the players inventory and
     * remove it from the players currentPosition (location)
     * 
     * @params  String name - the name (key) of an item
     * @return  boolean True if success
     */
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
    
    /**
     * takeItem removes the item from the players inventory
     * and then adds it to the players currentPosition
     * 
     * @params  String name - the name (key) of an item
     * @return  boolean True if success false otherwise
     */
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
