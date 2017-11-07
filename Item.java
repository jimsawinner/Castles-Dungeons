
/**
 * Item class will be responsible for actions pertaining
 * to and Item in-game object.
 *
 * @author Jamie Dixon
 * @version v0.02
 */
public class Item
{
    // instance variables - replace the example below with your own
    private int x;
    private String name;
    private int itemType;
    private int hp;
    private int damagePoints;

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, int hp, int damagePoints)
    {
        // initialise instance variables
        x = 0;
        this.name = name;
        this.hp = hp;
        this.damagePoints = damagePoints;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String getName()
    {
        // put your code here
        return this.name;
    }
    
    /**
     * getHitPoints returns the hitpoints that this item is worth
     * 
     * @return  the items hit points value
     */
    public int getHitPoints()
    {
        return this.hp;
    }
    
    /**
     * getDamagePoints returns the damage points that this item can deal
     * 
     * @return and integer value for the amount of damage this item can do
     */
    public int getDamagePoints()
    {
        return this.damagePoints;
    }
}
