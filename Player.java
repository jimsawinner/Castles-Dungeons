import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.awt.Point;
import java.util.Iterator;

/**
 * Player class - will be responsible for handling
 * actions of that like a real world player.
 *
 * @author Jamie Dixon
 * @version v0.01
 */
public class Player extends Char
{
    private Location currentPosition;   // the players current position
    private HashMap<String, Item> inventory;    // collection of items
    private int maxStorage;

    /**
     * Constructor for objects of class Player
     * 
     * Sets the players hitpoints 
     * and prepares an inventory array
     */
    public Player()
    {
        this.hp = 100; // players health points
        inventory = new HashMap<String, Item>(); // new empty hashmap for inventory
        maxStorage = 10;
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
        
        /*switch(direction){
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
        }*/

        if (nextLocation == null) {
            return false;
        }else if (currentPosition.getLocationType() == LocationType.TRAP){
            return false;
        } else {
            setCurrentPosition(nextLocation);
            System.out.println(nextLocation.getItemString());
            HashMap<String, Character> npcs = nextLocation.getNPCs();
            
            if (npcs.size() > 0) {
                System.out.println("Characters Here: ");
            }
            
            //Iterate over the npcs HashMap and printout the contents
            Iterator entries = npcs.entrySet().iterator();
            while (entries.hasNext()) {
                HashMap.Entry entry = (HashMap.Entry) entries.next();
                String key = (String)entry.getKey();
                Character value = (Character)entry.getValue();
                System.out.println(key + "Health: " + value.getHealthPoints());
                
                switch(key) {
                    case "Dragon":
                        System.out.println("The Dragon breathes fire in your direction");
                        break;
                    case "Witch":
                        System.out.println("The witch is attempting to cast a spell on you");
                        break;
                    case "Princess":
                        System.out.println("A princess is trapped. Find the keys to free her");
                        break;
                }
            }
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
     * Return a string describing the players inventory, for example
     * "Inventory: health".
     * 
     * @return Details of the players inventory
     */
    public ArrayList<String> getInventoryItems()
    {
        if(!hasItems()){
            return null;
        }
        ArrayList<String> items = new ArrayList<String>();
        Set<String> keys = inventory.keySet();
        for(String item : keys) {
            items.add(item);
        }
        return items;
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
     * @return  boolean True if success throws a custom InventoryException otherwise
     */
    public boolean takeItem(String itemName) throws InventoryException
    {
        if(inventory.size() >= this.maxStorage){
            throw new InventoryException("Inventory Full");
        }
        
        if(!this.currentPosition.hasItem(itemName)){
            throw new InventoryException("No Item Located");
        }else{
            Item thisItem = this.currentPosition.getItem(itemName);
            inventory.put(itemName, thisItem);
            this.currentPosition.removeItem(itemName);
            return true;
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
