import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.File;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.HashMap;
import java.util.ArrayList;

import java.io.InputStream;

/**
 * GameGUI is responsible for managing the graphical user experience
 *
 * @author Jamie Dixon
 * @version v0.30
 */
public class GameGUI extends JFrame
{
    
    // instance variables - replace the example below with your own
    private HashMap<String, JButton> buttons;
    private HashMap<String, JComponent> components; //is this recommended?
    
    // location image (instance variable to be able to change it)
    JLabel imageLabel = new JLabel();
    
    // a game object
    Game g1; 
    
    // still need a parser object temporarily
    private Parser parser;

    /**
     * Constructor for objects of class GameGUI
     */
    public GameGUI()
    {
        // initialise instance variables
        buttons = new HashMap<String, JButton>();
        components = new HashMap<String, JComponent>();
        
        // we dont want the windows resizable from its 800x600
        this.setResizable(false);
        
        // new parser object
        parser = new Parser();
        
        // setup the GUI components (buttons, labels etc)
        setup();
        
        // if we got this far play the game!
        play();
    }
    
    /**
     * Player - calls a bunch of methods to set the state
     * of the components within the interface
     * 
     */
    private void play()
    {
        g1 = new Game();
        
        
        if(!g1.setupLogger()){
            showDialog();
            updateStatusLabel("Error setting up logger");
            
        }
        
        checkAndEnableButtons();
        checkAndPopulateInventoryList();
        checkAndPopulateCharacterList();
        
        updateAllLabels();
        updateLocationLabel();
        updateLocationInfoLabel("");
        updateHostagesLabel("0");
        updateHealthLabel();
        updateStatusLabel("Game Ready");
        
        g1.log("Game Ready", "info");
        
        changeImageLabel(imageLabel, "images/forest.png");
    }
    
    /**
     * updateAllLabels calls all the methods to update just 
     * the label components
     */
    private void updateAllLabels()
    {
        updateHealthLabel();
        updateItemDescriptionLabel("");
    }
    
    /**
     * updateStatusLabels updates the status label with a new message
     * 
     * @param String label - the label for the status bar
     */
    private void updateStatusLabel(String label)
    {
        JLabel statusLabel = (JLabel) components.get("statusLabel");
        statusLabel.setText("Status: "+label);
    }
    
    /**
     * updateHostagesLabel updates the hostages label with
     * the current amount of saved hostages (and total)
     * 
     * @param String amount - the amount of hostages saved so far.
     */
    private void updateHostagesLabel(String amount)
    {
        JLabel hostagesLabel = (JLabel) components.get("hostageLabel");
        hostagesLabel.setText("Hostages Saved: "+amount+"/4");
    }
    
    /**
     * updateHealthLabel updates the health label
     *
     */
    private void updateHealthLabel()
    {
        String health = Integer.toString(g1.player1.getHp());
        String maxHealth = Integer.toString(g1.player1.getMaxHp());
        JLabel healthLabel = (JLabel) components.get("healthLabel");
        healthLabel.setText("Health: "+health+"/"+maxHealth);
    }
    
    
    /**
     * checkAndPopulateInventoryList will get the current items in
     * both the players inventory and the locations inventory and
     * populate the respective lists with these items
     * 
     */
    private void checkAndPopulateInventoryList()
    {
        JList inventoryList = (JList) components.get("inventory");
        JList roomItemsList = (JList) components.get("locationItems");
        
        String[] nullInventory = {};
        
        ArrayList<String> inventory = g1.player1.getInventoryItemsArray();
        ArrayList<String> roomItems = g1.player1.getCurrentPosition().getItemsArray();
        
        if(inventory != null){
            inventoryList.setListData(inventory.toArray());
        }else{
            inventoryList.setListData(nullInventory);
        }
        
        if(roomItems != null){
            roomItemsList.setListData(roomItems.toArray());
        }else{
            roomItemsList.setListData(nullInventory);
        }
    }
    
    private void checkAndPopulateCharacterList()
    {
        JList characterList = (JList) components.get("characterList");
        
        String[] nullCharacters = {};
        
        ArrayList<String> characterArray = g1.player1.getCurrentPosition().getCharactersArray();
        
        if(characterArray != null){
            characterList.setListData(characterArray.toArray());
            for(int i = 0; i < characterArray.toArray().length; i++){
                switch((String) characterList.getModel().getElementAt(i)){
                    case "Princess":
                        buttons.get("freeHostage").setEnabled(true);
                        break;
                    default:
                        buttons.get("attack").setEnabled(true);
                }
            }
        }else{
            characterList.setListData(nullCharacters);
        }
    }
    
    private void updateLocationLabel()
    {
        JLabel locationLabel = (JLabel) components.get("locationLabel");
        String locationText = g1.player1.getCurrentPosition().getShortDescription();
        
        locationLabel.setText("Location: "+locationText);
        
    }
    
    private void updateItemDescriptionLabel(String itemDescription)
    {   
        JLabel itemInfoLabel = (JLabel) components.get("itemInfoLabel");
        itemInfoLabel.setText(itemDescription);
        
    }
    
    private void updateLocationInfoLabel(String locationText)
    {
        JLabel locationInfoLabel = (JLabel) components.get("locationInfoLabel");
        locationInfoLabel.setText(locationText);
    }
    
    public void keyPressed(KeyEvent e) {
    
        int key = e.getKeyCode();
    
        if (key == KeyEvent.VK_LEFT) {
            processGameAction("go west");
        }
    
        if (key == KeyEvent.VK_RIGHT) {
            processGameAction("go east");
        }
    
        if (key == KeyEvent.VK_UP) {
            processGameAction("go north");
        }
    
        if (key == KeyEvent.VK_DOWN) {
            processGameAction("go down");
        }
    }
    
    private void checkAndEnableButtons()
    {
        // disable n,e,s,w buttons and re-enable if exit exists
        buttons.get("N").setEnabled(false);
        buttons.get("E").setEnabled(false);
        buttons.get("S").setEnabled(false);
        buttons.get("W").setEnabled(false);
        buttons.get("freeHostage").setEnabled(false);
        buttons.get("attack").setEnabled(false);
        
        ArrayList<String> exits = g1.player1.getCurrentPosition().getExitsFirstChar();
        
        for (int i = 0; i < exits.size(); i++) {
            switch(exits.get(i)) {
                case "n":
                    buttons.get("N").setEnabled(true);
                    break;
                case "e":
                    buttons.get("E").setEnabled(true);
                    break;
                case "s":
                    buttons.get("S").setEnabled(true);
                    break;
                case "w":
                    buttons.get("W").setEnabled(true);
                    break;
            }
        }
    }
    
    private void processGameAction(String commandText)
    {
        // parse the command by text
        Command command = parser.getCommandFromText(commandText);
        
        // process the command
        processCommand(command);
        
        // determine the location type
        LocationType type = g1.player1.getCurrentPosition().getLocationType();
        
        switch(type){
            case HALL:
                changeImageLabel(imageLabel, "images/hall.png");
                break;
            case OUTSIDE:
                changeImageLabel(imageLabel, "images/forest.png");
                break;
            case BRIDGEGATE:
                changeImageLabel(imageLabel, "images/bridge-gates.png");
                break;
            case TUNNEL:
                changeImageLabel(imageLabel, "images/tunnel.png");
                break;
            case DUNGEON:
                changeImageLabel(imageLabel, "images/dungeon.png");
                break;
            case BEDROOM:
                changeImageLabel(imageLabel, "images/bedroom.png");
                break;
            case GUARDED:
                changeImageLabel(imageLabel, "images/guard.png");
                if(g1.player1.getCurrentPosition().isLocked()){
                    String locationInfoText = "The guard may need bribing try using a coin!";
                    updateLocationInfoLabel(locationInfoText);
                }else{
                    updateLocationInfoLabel("");
                }
                break;
            case ENTRANCE:
                changeImageLabel(imageLabel, "images/entrance.png");
                break;
            case TRAP:
                changeImageLabel(imageLabel, "images/trapped.png");
                updateLocationInfoLabel("Trapped! Game Over!");
                break;
            case BRIDGE:
                changeImageLabel(imageLabel, "images/bridge.png");
                break;
        }
        
        checkAndEnableButtons();
        checkAndPopulateInventoryList();
        checkAndPopulateCharacterList();
        updateLocationLabel();
        updateAllLabels();
    }
    
    /**
     * Moved from Game Class since v0.26
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            updateStatusLabel("Unknown Command");
            return false;
        }
        
        if (commandWord == CommandWord.GO) {
            String direction = command.getSecondWord();
            
            if(g1.player1.move(direction)){
                updateStatusLabel(g1.player1.getCurrentPosition().getLongDescription());
            }else{
                updateStatusLabel("You have no remaining health to move!");
            }
        }
        else if (commandWord == CommandWord.QUIT) {
            //System.exit(0);
            wantToQuit = true;
        }
        else if (commandWord == CommandWord.USE) {
            try{
                g1.player1.useItem(command.getSecondWord());
                updateStatusLabel("Guard successfully bribed");
                updateAllLabels();
            }catch(Exception e){
                updateStatusLabel(e.toString());
            }
        }
        else if (commandWord == CommandWord.FREE) {
            try{
                if(g1.player1.freeHostage(command.getSecondWord())){
                    int freed = (int) g1.decrementHostages();
                    freed = 4 - freed;
                    
                    updateHostagesLabel(""+freed);
                    updateStatusLabel(command.getSecondWord()+" Freed");
                }else{
                    updateStatusLabel("Find the key!");
                }
            }catch(Exception e){
                updateStatusLabel(e.toString());
            }
        }
        else if (commandWord == CommandWord.ATTACK) {
            try{
                g1.player1.attack(command.getSecondWord());
                updateStatusLabel("Attacked: "+command.getSecondWord());
            }catch(Exception e){
                updateStatusLabel(e.toString());
            }
        }
        
        // else command not recognised.
        return wantToQuit;
    }
    
    private void setup()
    {
        this.setTitle("Castles & Dungeons Zuul Project");
        this.getContentPane().setLayout(new GridLayout(20,20));
        this.setSize(800, 600);   
        
        Container pane = this.getContentPane();
        GridBagConstraints c = new GridBagConstraints();
        
        JButton button;
        pane.setLayout(new GridBagLayout());

        // natural height, maximum width
        c.fill = GridBagConstraints.HORIZONTAL;
        
    
        button = new JButton("N");
        c.gridx = 1;
        c.gridy = 0;
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                processGameAction("go north");
            }
        });
        pane.add(button, c);
        buttons.put("N", button);
        
        button = new JButton("S");
        c.gridx = 1;
        c.gridy = 2;
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                processGameAction("go south");
            }
        });
        pane.add(button, c);
        buttons.put("S", button);
        
        button = new JButton("W");
        c.gridx = 0;
        c.gridy = 1;
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                processGameAction("go west");
            }
        });
        pane.add(button, c);
        buttons.put("W", button);
    
        button = new JButton("E");
        c.gridx = 2;
        c.gridy = 1;
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                processGameAction("go east");
            }
        });
        pane.add(button, c);
        buttons.put("E", button);
        
        JLabel label = new JLabel("Player Stats");
        c.gridx = 3;
        c.gridy = 0;
        label.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        
        pane.add(label, c);
        
        JLabel locationLabel = new JLabel("Location: ");
        c.gridx = 3;
        c.gridy = 1;
        //label.setFont(new Font("Sans-Serif"));
        
        pane.add(locationLabel, c);
        components.put("locationLabel", locationLabel);
        
        JLabel locationInfoLabel = new JLabel("");
        c.gridx = 3;
        c.gridy = 2;
        //label.setFont(new Font("Sans-Serif"));
        
        pane.add(locationInfoLabel, c);
        components.put("locationInfoLabel", locationInfoLabel);
        
        JLabel hostageLabel = new JLabel("Hostages Saved: ");
        c.gridx = 3;
        c.gridy = 3;
        //label.setFont(new Font("Sans-Serif"));
        
        pane.add(hostageLabel, c);
        components.put("hostageLabel", hostageLabel);
        
        JLabel healthLabel = new JLabel("Health: ");
        c.gridx = 3;
        c.gridy = 4;
        //label.setFont(new Font("Sans-Serif"));
        
        pane.add(healthLabel, c);
        components.put("healthLabel", healthLabel);
        
        // controls panel
        Image dimg = loadImage("images/forest.png").getScaledInstance(800, 200, Image.SCALE_SMOOTH);
        
        imageLabel.setIcon(new ImageIcon(dimg));

        //c.ipady = 100;
        //c.ipadx = 100;
        c.weightx = 0.0;
        c.gridwidth = 0;
        c.gridx = 0;
        c.gridy = 5;
        pane.add(imageLabel, c);
    
        
        label = new JLabel("Inventory");
        c.gridx = 0; // first column
        c.gridy = 6; // fifth row
        c.insets = new Insets(0, 10, 0, 0); // left padding
        c.gridwidth = 3; // 3 columns wide
        
        pane.add(label, c); 
        
        String[] inventory = { };
        
        c.gridx = 0; // first column
        c.gridy = 7; // sixth row
        JList list = new JList(inventory);
        list.setToolTipText("Single click for description, Double click to drop");
        
        pane.add(list, c);
        components.put("inventory", list);
        
        label = new JLabel("Click an item for description");
        c.gridx = 0; // first column
        c.gridy = 8; // fifth row
        c.insets = new Insets(0, 10, 0, 0); // left padding
        c.gridwidth = 3; // 3 columns wide
        
        pane.add(label, c); 
        components.put("itemInfoLabel", label);
        
        button = new JButton("Use");
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1; // 3 columns wide
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                JList selected = (JList) components.get("inventory");
                processGameAction("use "+selected.getSelectedValue());
            }
        });
        pane.add(button, c);
        buttons.put("useItem", button);
        
        label = new JLabel("Room Items");
        c.gridx = 3; // fourth column
        c.gridy = 6; // fifth row
        c.gridwidth = 3; // 3 columns wide
        
        pane.add(label, c); 
        
        JList list2 = new JList(inventory);
        
        // setup location on pane
        c.gridx = 3; // 4th column
        c.gridy = 7; // sixth row
        
        list2.setToolTipText("Double click to pickup");
        
        pane.add(list2, c);
        components.put("locationItems", list2);
        
        label = new JLabel("Players Here");
        c.gridx = 6; // seventh column
        c.gridy = 6; // fifth row
        c.gridwidth = 3; // 3 columns wide
        
        pane.add(label, c); 
        
        String[] characters = { "1", "2" };
        JList list3 = new JList(characters);
        
        // setup location on pane
        c.gridx = 6; // 4th column
        c.gridy = 7; // sixth row
        c.gridwidth = 3; // 3 columns wide
        
        list3.setToolTipText("Single Click for description.");
        
        pane.add(list3, c);
        components.put("characterList", list3);
        
        button = new JButton("Free Hostage");
        c.gridx = 6;
        c.gridy = 8;
        c.gridwidth = 3; // 3 columns wide
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                JList selected = (JList) components.get("characterList");
                processGameAction("free "+selected.getSelectedValue());
            }
        });
        pane.add(button, c);
        buttons.put("freeHostage", button);
        
        button = new JButton("Attack");
        c.gridx = 6;
        c.gridy = 9;
        c.gridwidth = 3; // 3 columns wide
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                JList selected = (JList) components.get("characterList");
                processGameAction("attack "+selected.getSelectedValue());
            }
        });
        pane.add(button, c);
        buttons.put("attack", button);
        
        
        // add a status label (to fill the space)
        label = new JLabel("Status: ");
        c.weighty = 1.0; // request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; // bottom of space
        c.gridx = 0; // 0 being the first in a 10x10
        c.gridy = 10; // 9 being the last in a 10x10
        c.gridwidth = 11; // take up the whole bottom row
        c.insets = new Insets(0, 0, 0, 0); // reset left padding
        
        pane.add(label, c); 
        components.put("statusLabel", label);
        
        setupMenu();
        
        // setup mouse event listeners
        // http://www.java2s.com/Tutorial/Java/0240__Swing/Selectioneventfordoubleclickinganiteminthelist.htm
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        String itemName = o.toString();
                        try{
                            g1.player1.dropItem(itemName);
                            updateStatusLabel("Dropped "+itemName);
                            g1.log("Player dropped item: "+itemName, "info");
                        }catch(Exception e){
                            updateStatusLabel(e.toString());
                        }
                        checkAndPopulateInventoryList();
                    }
                } else if (mouseEvent.getClickCount() == 1) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        String itemName = o.toString();
                        try{
                            String description = g1.player1.getItem(itemName).getDescription();
                            updateItemDescriptionLabel(description);
                        }catch(Exception e){
                            updateStatusLabel(e.toString());
                        }
                        //checkAndPopulateInventoryList();
                    }
                }
            }
        };
        
        MouseListener secondMouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        String itemName = o.toString();
                        try{
                            if(g1.player1.takeItem(itemName)){
                                updateStatusLabel("Picked up "+itemName);
                                g1.log("Player picked up item: "+itemName, "info");
                            }else{
                                System.out.println("No " +itemName+ " in room.");
                            }
                        }catch(Exception e){
                            updateStatusLabel(e.toString());
                        }
                        checkAndPopulateInventoryList();
                    }
                }
            }
        };
        
        components.get("inventory").addMouseListener(mouseListener);
        components.get("locationItems").addMouseListener(secondMouseListener);
    
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        //showDialog();
    }
    
    private void changeImageLabel(JLabel label, String imageFileName)
    {
        Image dimg = loadImage(imageFileName).getScaledInstance(800, 200, Image.SCALE_SMOOTH);
        
        //JLabel imageLabel = new JLabel(new ImageIcon(dimg));
        
        imageLabel.setIcon(new ImageIcon(dimg));
    }
    
    private void setupMenu()
    {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        
        // create a menu bar
        menuBar = new JMenuBar();
        
        // create a file menu
        menu = new JMenu("File");
        
        // add to the menu bar
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Restart Game", KeyEvent.VK_T);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                //call the processGameAction hardcoding quit as command
                play();
            }
        });
        
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Quit", KeyEvent.VK_T);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                //call the processGameAction hardcoding quit as command
                System.exit(0);
            }
        });
        
        menu.add(menuItem);
        
        // create a tools menu
        menu = new JMenu("Tools");
        
        // add to the menu bar
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Delete Logs", KeyEvent.VK_T);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                deleteLogs();
            }
        });
        
        menu.add(menuItem);
        
        // create a help menu
        menu = new JMenu("Help");
        
        // add to the menu bar
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Show Help", KeyEvent.VK_T);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                HelpGUI help = new HelpGUI();
            }
        });
        
        // add show help to menu option
        menu.add(menuItem);
        
        this.setJMenuBar(menuBar);
    }
    
    private void deleteLogs()
    {
        File file = new File("logs");      
        String[] myFiles;    
        
        if(file.isDirectory()){
            myFiles = file.list();
            for (int i=0; i<myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]); 
                myFile.delete();
            }
        }
    }
    
    
    /**
     * A method to load an image from file
     * 
     * 
     */
    private BufferedImage loadImage(String filename)
    {
        try {
            InputStream imageStream = this.getClass().getResourceAsStream("resources/"+filename);
            BufferedImage image = ImageIO.read(imageStream);
            return image;
        }catch(Exception ex) {
            System.out.println(ex);
        }
        
        BufferedImage bufImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        
        return bufImage;
    }
    
    private void showDialog()
    {
        String backupDir = "logs";
        
        // create a jframe
        JFrame frame = new JFrame("JOptionPane showMessageDialog example");
        
        // show a joptionpane dialog using showMessageDialog
        JOptionPane.showMessageDialog(frame,
            "Log Directory doesnt exist - creating one now: '" + backupDir + "'.");
        //System.exit(0);
        
        // create log directory
        File file = new File("logs");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }
}
