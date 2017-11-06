
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

    /**
     * Constructor for objects of class Item
     */
    public Item(String name)
    {
        // initialise instance variables
        x = 0;
        this.name = name;
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
        return name;
    }
}
