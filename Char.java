
/**
 * Write a description of class Char here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Char
{
    // instance variables - replace the example below with your own
    private int x;
    protected int hp; // health points
    protected int ap; // attack points
    protected int dp; // defence points

    /**
     * Constructor for objects of class Char
     */
    public Char()
    {
        // initialise instance variables
        x = 0;
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
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
