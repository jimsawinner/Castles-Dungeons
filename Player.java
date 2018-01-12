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
    //private HashMap<String, Item> inventory;    // collection of items

    /**
     * Constructor for objects of class Player
     * 
     * Sets the players hitpoints 
     * and prepares an inventory array
     */
    public Player()
    {
        this.hp = 150; // players health points
        this.maxHp = 150; // players health points
        //inventory = new HashMap<String, Item>(); // new empty hashmap for inventory
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
        if(hp < 1){
            return false;
        }
        
        // Try to leave current room.
        Location nextLocation = getCurrentPosition().getExit(direction);

        if (nextLocation == null) {
            return false;
        }else if (currentPosition.getLocationType() == LocationType.TRAP){
            return false;
        } else {
            setCurrentPosition(nextLocation);
            //System.out.println(nextLocation.getItemString());
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
                        //System.out.println("The Dragon breathes fire in your direction");
                        break;
                    case "Witch":
                        //System.out.println("The witch is attempting to cast a spell on you");
                        break;
                    case "Princess":
                        System.out.println("A princess is trapped. Find the keys to free her");
                        break;
                }
            }
        }
        
        this.hp = hp - 1;
        
        return true;
    }
    
    public boolean freeHostage(String hostageName)
    {
        if(getCurrentPosition().hasNpc(hostageName)){
            if(hasItem("key")){
                getCurrentPosition().removeNPC(hostageName);
                removeItem("key");
            }else{
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
    
    public boolean useItem(String itemName)
    {
        if(!this.hasItem(itemName)){
            return false;
        }else{
            this.hp = getItem(itemName).getHealthPoints();
        }
        
        return true;
    }
}
