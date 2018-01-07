
/**
 * This class will be responsible for containing the methods
 * and parameters that apply to a non playable character in the
 * game.
 * 
 * @author Jamie Dixon
 * @version v0.05
 */
public class Character extends Char
{
    // instance variables
    private int dp; // damage points
    
    private CharacterType charType;

    /**
     * Constructor for objects of class Character
     */
    public Character(CharacterType characterType)
    {
        // initialise instance variables
        this.charType = characterType;
        switch(characterType){
            case DRAGON:
                this.hp = 250;
                this.dp = 10;
                break;
            case MONSTER:
                this.hp = 150;
                this.dp = 7;
                break;
            case WITCH:
                this.hp = 50;
                this.dp = 5;
                break;
        }
    }
    
    public int getHealthPoints() {
        return this.hp;
    }
}
