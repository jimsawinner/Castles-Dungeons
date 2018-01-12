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
    private Parser parser;
    //private Room currentRoom;
    public Player player1;
    //private HashMap<String, Location> map;
    private GameMap map;
    
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
        parser = new Parser();
        map = new GameMap();
        
        player1.setCurrentPosition(map.getLocationByPoint(new Point(2,-4)));  // start game outside
        
        addCharacterToGame(); // add a princess to the game
    }
    
    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    public void goToLocation(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        
        if(player1.move(direction)){
            System.out.println("Moved");
            System.out.println(player1.getCurrentPosition().getLongDescription());
        }else{
            System.out.println("There is no door!");
        }
    }

    public void takeItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        try{
            player1.takeItem(itemName);
            System.out.println("Picked up "+itemName);
            log("Player obtained item: "+itemName, "info");
        }
        catch (Exception e){
            System.out.println("Error Picking up item");
            System.out.println(e);
        }
    }
    
    public void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Drop what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        if(player1.dropItem(itemName)){
            System.out.println("Dropped "+itemName);
            log("Player dropped item: "+itemName, "info");
        }else{
            System.out.println("No " +itemName+ " in inventory.");
        }
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
    
    private void addCharacterToGame()
    {
        map.getLocationByPoint(new Point(4,7)).addNPC("Princess", new Character(CharacterType.PRINCESS));  // start game outside
    }
}
