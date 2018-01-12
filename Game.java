import java.util.HashMap;
import java.util.logging.*;
import java.io.*;
import java.util.Date;
import java.awt.Point;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Game 
{
    public Player player1;
    //private HashMap<String, Location> map;
    private GameMap map;
    private int hostages;
    
    Logger logger = Logger.getLogger("GameLog");  
    FileHandler fh;  
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        // setup the game logger
        try {  
            // This block configure the logger with handler and formatter
            // Get the current date
            Date date = new Date();
            
            // create a new file handler
            fh = new FileHandler("logs/logfile-"+ date.getTime() +".log");  
            
            // add the filehandler to the logger
            logger.addHandler(fh);
            
            // create a new simple formatter
            SimpleFormatter formatter = new SimpleFormatter();  
            
            // add the simple formatter to the file handler
            fh.setFormatter(formatter);  

        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        player1 = new Player();
        map = new GameMap();
        
        player1.setCurrentPosition(map.getLocationByPoint(new Point(2,-4)));  // start game outside
        
        addCharacterToGame(new Point(-6, 7), new Character(CharacterType.PRINCESS)); // add a princess to the game
        addCharacterToGame(new Point(-4, 7), new Character(CharacterType.PRINCESS)); // add a princess to the game
        addCharacterToGame(new Point(4, 7), new Character(CharacterType.PRINCESS)); // add a princess to the game
        addCharacterToGame(new Point(6, 7), new Character(CharacterType.PRINCESS)); // add a princess to the game
        hostages = 4;
    }
    

    /**
     * Logging Method
     * 
     * @params String logMessage - the message to be stored into the log, String logType the type of log
     */
    public void log(String logMessage, String logType)
    {
        switch(logType) {
            case "info":
                logger.info(logMessage);
                break;
            case "error":
                logger.warning(logMessage);
                break;
            default:
                logger.info(logMessage);
                break;
            }
    }
    
    private void addCharacterToGame(Point location, Character character)
    {
        map.getLocationByPoint(location).addNPC("princess", character);
    }
    
    public int decrementHostages()
    {
        hostages = hostages-1;
        
        return hostages;
    }
}
