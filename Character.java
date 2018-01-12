
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
                this.hp = 50;
                this.dp = 10;
                break;
            case MONSTER:
                this.hp = 25;
                this.dp = 7;
                break;
            case WITCH:
                this.hp = 20;
                this.dp = 5;
                break;
            default:
                //System.out.println("Error");
        }
    }
    
    public CharacterType getCharType()
    {
        return this.charType;
    }
    
    public boolean defend(Char attacker) throws CharacterException
    {
        // this character/player defending and attack
        this.hp = this.hp - attacker.getAp();
        
        if(this.hp < 0){
            throw new CharacterException("CharacterDead");
        }
        return true;
    }
}
