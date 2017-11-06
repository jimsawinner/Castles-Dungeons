import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Location 
{
    private String description;
    private HashMap<String, Location> exits;        // stores exits of this room.
    private HashMap<String, Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Location(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Location>();
        items = new HashMap<String, Item>();
        
        addItem("pistol", new Item("Vintage Pistol"));
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The location to which the exit leads.
     */
    public void setExit(String direction, Location neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this location
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the location's exits, for example
     * "Exits: north west".
     * @return Details of the location's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
     * Return a string describing the location's exits, for example
     * "Exits: north west".
     * @return Details of the location's exits.
     */
    public String getItemString()
    {
        String returnString = "Items:";
        Set<String> keys = items.keySet();
        for(String item : keys) {
            returnString += " " + item;
        }
        return returnString;
    }

    /**
     * Return the location that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Location getExit(String direction) 
    {
        return exits.get(direction);
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
    
    public String getItemName(String name)
    {
        return items.get(name).getName();
    }
}

