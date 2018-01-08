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
        // setup the logger
        try {  
    
            // This block configure the logger with handler and formatter  
            Date date = new Date();
            fh = new FileHandler("logs/logfile-"+ date.getTime() +".log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
    
            // the following statement is used to log any messages  
            logger.info("My first log");  
    
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    
        logger.info("Hi How r u?");
        
        player1 = new Player();
        parser = new Parser();
        map = new GameMap();
        
        player1.setCurrentPosition(map.getLocationByPoint(new Point(2,-4)));  // start game outside
        
        addCharacterToGame(); // add a princess to the game
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        //boolean finished = false;
        //while (! finished) {
        //    Command command = parser.getCommand();
        //    finished = processCommand(command);
        //}
        //System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to my version of Zuul!");
        System.out.println("You are in the ground's of a castle. You must collect");
        System.out.println("all of the items on the map without becoming trapped!");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player1.getCurrentPosition().getLongDescription());
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
            log("Player obtained item: "+itemName);
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
            log("Player dropped item: "+itemName);
        }else{
            System.out.println("No " +itemName+ " in inventory.");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Logging Method
     */
    public void log(String logMessage)
    {
        logger.info(logMessage);
    }
    
    private void addCharacterToGame()
    {
        map.getLocationByPoint(new Point(4,7)).addNPC("Princess", new Character(CharacterType.PRINCESS));  // start game outside
    }
    
    
}
