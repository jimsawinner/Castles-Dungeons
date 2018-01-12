import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.awt.Point;
import java.util.Iterator;
import java.util.Random;

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
    private Random generator = new Random();

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
        this.ap = 12;
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
        }
        
        this.hp = hp - 1;
        
        return true;
    }
    
    public boolean freeHostage(String hostageName) throws CharacterException
    {
        if(getCurrentPosition().hasNpc(hostageName)){
            if(hasItem("key")){
                if(getCurrentPosition().getNPC(hostageName).getCharType() == CharacterType.PRINCESS){
                    getCurrentPosition().removeNPC(hostageName);
                    removeItem("key");
                }else{
                    throw new CharacterException("Unable to free "+hostageName);
                }
            }else{
                throw new CharacterException("Find a key!");
            }
        }else{
            throw new CharacterException("NPC Doesnt Exist!");
        }
        return true;
    }
    
    public boolean useItem(String itemName) throws InventoryException
    {
        if(!this.hasItem(itemName)){
            throw new InventoryException("You do not have that item to use");
        }else{
            switch(itemName){
                case "ether":
                    this.hp = this.hp + getItem(itemName).getHealthPoints();
                    if(this.hp > maxHp){
                        this.hp = maxHp;
                    }
                    break;
                case "coin":
                    double chance = generator.nextDouble();
                    //System.out.println(chance);
                    
                    if(!this.getCurrentPosition().isLocked()){
                        throw new InventoryException("No guards to bribe");
                    }
                    
                    if(chance < 0.5){
                        throw new InventoryException("Guard would not be bribed");
                    }
                    
                    this.getCurrentPosition().unlockExits();
                    break;
                default:
                    throw new InventoryException("You cannot find a way to use this item here");
            }
        }
        
        this.removeItem(itemName);
        
        return true;
    }
    
    public boolean attack(String opponentName) throws CharacterException
    {
        if(this.hp < 1){
            throw new CharacterException("No health remaining to attack");
        }
        
        int damagePoints = this.getCurrentPosition().getNPC(opponentName).getDamagePoints();
        
        try{
            this.getCurrentPosition().getNPC(opponentName).defend(this);
        }catch(Exception e){
            this.getCurrentPosition().removeNPC(opponentName);
            throw new CharacterException(opponentName+" killed!");
            //System.out.println(e);
        }
        
        this.hp = this.hp - damagePoints; 
        return true;
    }
    
    public boolean isLockedIn()
    {
        return this.getCurrentPosition().isLocked();
    }
}
