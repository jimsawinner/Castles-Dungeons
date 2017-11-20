
/**
 * This class will be responsible for containing the methods
 * and parameters that apply to a non playable character in the
 * game.
 * 
 * @author Jamie Dixon
 * @version v0.05
 */
public class Character
{
    // instance variables
    private int hp;
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
                break;
            case MONSTER:
                this.hp = 150;
                break;
            case WITCH:
                this.hp = 50;
                break;
        }
    }
}
