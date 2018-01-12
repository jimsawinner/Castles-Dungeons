import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

/**
 * Write a description of class Char here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Char
{
    // instance variables - replace the example below with your own
    protected Location currentPosition;   // the players current position
    protected int hp; // health points
    protected int ap; // attack points
    protected int dp; // defence points
    protected int maxStorage;
    protected HashMap<String, Item> inventory;    // collection of items

    /**
     * Constructor for objects of class Char
     */
    public Char()
    {
        // initialise instance variables
        inventory = new HashMap<String, Item>(); // new empty hashmap for inventory
        maxStorage = 10;
    }
    
    public boolean attack(Char target)
    {
        // this character/player attacking an opponent
        return true;
    }
    
    public boolean defend(Char attacker)
    {
        // this character/player defending and attack
        this.hp = this.hp - attacker.getAp();
        return true;
    }
    
    public int getHp()
    {
        return this.hp;
    }
    
    public int getAp()
    {
        return this.ap;
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
    public ArrayList<String> getInventoryItemsArray()
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
            Item playerItemKey = inventory.get(itemName);
            if(playerItemKey != null) {
                throw new InventoryException("Already have this item");
            }else{
                inventory.put(itemName, thisItem);
                this.currentPosition.removeItem(itemName);
                return true;
            }
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
}
