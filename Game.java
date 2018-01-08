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
    private Player player1;
    //private HashMap<String, Location> map;
    private GameMap map;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player1 = new Player();
        parser = new Parser();
        map = new GameMap();
        
        player1.setCurrentPosition(map.getLocationByPoint(new Point(2,-4)));  // start game outside
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
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goToLocation(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
     else if (commandWord == CommandWord.PICK) {
            takeItem(command);
        }
        else if (commandWord == CommandWord.DROP) {
            dropItem(command);
        }
        else if (commandWord == CommandWord.LOOK) {
            if(!command.hasSecondWord()){
                try{
                    System.out.println(player1.getCurrentPosition().getItemString());
                }catch(Exception e){
                    System.out.println("Error! Could not getItem().getName()");
                }
            }else{
                try{
                    System.out.println(player1.getInventoryString());
                }catch(Exception e){
                    System.out.println("Error! Could not getItem().getName()");
                }
            }
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the castle.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goToLocation(Command command) 
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
    
    private void takeItem(Command command)
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
        }
        catch (Exception e){
            System.out.println("Error Picking up item");
            System.out.println(e);
        }
    }
    
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Drop what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        if(player1.dropItem(itemName)){
            System.out.println("Dropped "+itemName);
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
    public void log()
    {
        Logger logger = Logger.getLogger("MyLog");  
        FileHandler fh;  
    
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
    }
    
    /**
     * Development test methods - prevents making other methods public
     * delete these methods from production game.
     */
    public void testProcessCommand(Command command)
    {
        processCommand(command);
    }
    
}
