import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.Point;

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
    private Point point;
    private LocationType type;
    private String description;
    private HashMap<String, Location> exits;        // stores exits of this room.
    private HashMap<String, Item> items;
    private HashMap<String, Character> npcs;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Location(String description, LocationType type, Point point) 
    {
        this.type = type;
        this.description = description;
        this.point = point;
        exits = new HashMap<String, Location>();
        items = new HashMap<String, Item>();
        npcs = new HashMap<String, Character>();
        
        Random rand = new Random();
        
        // Dont add characters or items in certain locations
        if(type != LocationType.OUTSIDE && type != LocationType.TRAP && type != LocationType.DUNGEON){
            // Give a 50% chance of this location gaining an item
            float chance = rand.nextFloat();
            if (chance <= 0.25f) {
                addItem("ether", new Item("Health  boost.", 0, 10));
            } else if (chance <= 0.50f){
                addItem("gold coin", new Item("A gold coin.", 0, 50));
            }
            
            chance = rand.nextFloat();
            if(chance <= 0.20f) {
                addNPC("Dragon", new Character(CharacterType.DRAGON));
            }
            
            if(chance <= 0.40f) {
                addNPC("Witch", new Character(CharacterType.WITCH));
            }
        }
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
        String locationMessage = "You are " + description + ".\n At Position: " + point.getX() + "," + point.getY() + "\n" + getExitString();
        return locationMessage;
        
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
    public String getCharactersString()
    {
        if(!hasItems()){
            return "No Items Here";
        }
        
        String returnString = "Items:";
        Set<String> keys = items.keySet();
        for(String item : keys) {
            returnString += " " + item;
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
        if(!hasItems()){
            return "No Items Here";
        }
        
        String returnString = "Items:";
        Set<String> keys = items.keySet();
        for(String item : keys) {
            returnString += " " + item;
        }
        return returnString;
    }
    
    public ArrayList<String> getExitsFirstChar()
    {
        ArrayList<String> exitChars = new ArrayList<String>();
        
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        
        for(String exit : keys) {
            switch(exit) {
                case "north":
                    exitChars.add("n");
                    break;
                case "east":
                    exitChars.add("e");
                    break;
                case "south":
                    exitChars.add("s");
                    break;
                case "west":
                    exitChars.add("w");
                    break;
            }
        }
        return exitChars;
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
    
    /**
     * Checks if an item with specific name exists in location
     * 
     * @params String k - the key (name) of the item
     */
    public boolean hasItem(String k){
        return items.containsKey(k);
    }
    
    /**
     * Checks if the location holds any items
     */
    public boolean hasItems(){
        return !items.isEmpty();
    }    
    
    /**
     * Adds an item to the location
     * 
     * @params String name, Item item
     */
    public void addItem(String name, Item item)
    {
        items.put(name,item);
    }
    
    /**
     * Gets an item object if it exists in the location
     * 
     * @params String name - the name of the item
     */
    public Item getItem(String name)
    {
        return items.get(name);
    }
    
    /**
     * Remove an item object from the items HashMap if exists
     * 
     * @params String name - the name of the item
     */
    public void removeItem(String name)
    {
        items.remove(name);
    }
    
    /**
     * Returns an items name by its key.
     * 
     * @params String key
     * @return String - the items name
     */
    public String getItemName(String key)
    {
        return items.get(key).getName();
    }
    
    /**
     * Add a non playable character to the location
     * 
     * @params String name, Character object
     */
    public void addNPC(String name, Character character)
    {
        npcs.put(name, character);
    }
    
    /**
     * Returns the HashMap of NPC's at this location
     * 
     * @return HashMap<String, Character>
     */
    public HashMap<String, Character> getNPCs(){
        return this.npcs;
    }
    
    /**
     * Get the location type
     * 
     */
    
    public LocationType getLocationType()
    {
        return this.type;
    }
}

