import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

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

    private boolean locked;

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
        this.locked = false;
        exits = new HashMap<String, Location>();
        items = new HashMap<String, Item>();
        npcs = new HashMap<String, Character>();
        
        if(type == LocationType.GUARDED){
            this.locked = true;
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
        String locationMessage = "You are " + description + ".\n At Position: " + point.getX() + "," + point.getY() + "\n";
        locationMessage += " the guards may need bribing";
        return locationMessage;
        
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
    
    public ArrayList<String> getCharactersArray()
    {
        if(!hasNPCs()){
            return null;
        }
        ArrayList<String> charactersArray = new ArrayList<String>();
        Set<String> keys = npcs.keySet();
        for(String item : keys) {
            charactersArray.add(item);
        }
        return charactersArray;
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
    
    /**
     * Return a array describing the location's items, for example
     * "Exits: north west".
     * @return Details of the location's exits.
     */
    public ArrayList<String> getItemsArray()
    {
        if(!hasItems()){
            return null;
        }
        ArrayList<String> itemsArray = new ArrayList<String>();
        Set<String> keys = items.keySet();
        for(String item : keys) {
            itemsArray.add(item);
        }
        return itemsArray;
    }
    
    public ArrayList<String> getExitsFirstChar()
    {
        ArrayList<String> exitChars = new ArrayList<String>();
        
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        
        if(this.locked){
            return exitChars;
        }
        
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
    
    public Character getNPC(String name)
    {
        return npcs.get(name);
    }
    
    public void removeNPC(String name)
    {
        npcs.remove(name);
    }
    
    public boolean hasNpc(String k){
        return npcs.containsKey(k);
    }
    
    public boolean hasNPCs()
    {
        return !npcs.isEmpty();
    }
    
    public boolean unlockExits()
    {
        this.locked = false;
        return true;
    }
    
    public boolean lockExits()
    {
        this.locked = true;
        return true;
    }
    
    public boolean isLocked()
    {
        return this.locked;
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

