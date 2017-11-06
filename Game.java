import java.util.HashMap;

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
    private HashMap<String, Location> map;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player1 = new Player();
        map = new HashMap<String, Location>();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {   
        Location outside, theatre, pub, lab, office;
      
        // create the rooms
        outside = new Location("outside the main entrance of the university");
        theatre = new Location("in a lecture theatre");
        pub = new Location("in the campus pub");
        lab = new Location("in a computing lab");
        office = new Location("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        map.put("outside", outside);
        map.put("theatre", theatre);
        map.put("pub", pub);
        map.put("lab", lab);
        map.put("office", office);

        player1.setCurrentPosition(map.get("outside"));  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player1.getCurrentPosition().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
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
            goRoom(command);
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
            try{
                System.out.println(player1.getCurrentPosition().getItemString());;
            }catch(Exception e){
                System.out.println("Error! Could not getItem().getName()");
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
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
        
        if(player1.takeItem(itemName)){
            System.out.println("Picked up "+itemName);
        }else{
            System.out.println("No " +itemName+ " is here.");
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
}
