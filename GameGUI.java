import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.File;

import java.util.HashMap;
import java.util.ArrayList;

import java.io.InputStream;

/**
 * Write a description of class GameGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameGUI extends JFrame
{
    // instance variables - replace the example below with your own
    private HashMap<String, JButton> buttons;
    
    // location image (instance variable to be able to change it)
    JLabel imageLabel = new JLabel();
    
    Game g1; 
    private Parser parser;
    private boolean finished;

    /**
     * Constructor for objects of class GameGUI
     */
    public GameGUI()
    {
        // initialise instance variables
        buttons = new HashMap<String, JButton>();
        
        this.setResizable(false);
        
        g1 = new Game();
        finished = false;
        parser = new Parser();
        
        setup();
        
        g1.play();
        
        g1.log("Game Ready");
        
        checkAndEnableButtons();
        
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
        // Fetch the command from field1 ...
        Command command = parser.getCommandFromText(commandText);
        
        finished = processCommand(command);
        if (finished == true)
        // Game is over
        {
            System.out.println("Thank you for playing.  Good bye.");
            System.exit(0);
        }
        
        LocationType type = g1.player1.getCurrentPosition().getLocationType();
        //System.out.println(type);
        
        switch(type){
            case OUTSIDE:
                changeImageLabel(imageLabel, "images/forest.png");
                break;
            case BRIDGE:
                changeImageLabel(imageLabel, "images/bridge-gates.png");
                break;

        }
        
        checkAndEnableButtons();
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
            System.out.println("I don't know what you mean...");
            return false;
        }
        
        if (commandWord == CommandWord.HELP) {
            //g1.printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            g1.goToLocation(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            //System.exit(0);
            wantToQuit = true;
        }
        else if (commandWord == CommandWord.PICK) {
            g1.takeItem(command);
        }
        else if (commandWord == CommandWord.DROP) {
            g1.dropItem(command);
        }
        else if (commandWord == CommandWord.LOOK) {
            if(!command.hasSecondWord()){
                try{
                    System.out.println(g1.player1.getCurrentPosition().getItemString());
                }catch(Exception e){
                    System.out.println("Error! Could not getItem().getName()");
                }
            }else{
                try{
                    System.out.println(g1.player1.getInventoryString());
                }catch(Exception e){
                    System.out.println("Error! Could not getItem().getName()");
                }
            }
        }
        // else command not recognised.
        return wantToQuit;
    }
    
    private void setup()
    {
        this.setTitle("Castles & Dungeons Zuul Project");
        this.getContentPane().setLayout(new GridLayout(10,10));
        this.setSize(800, 600);   
        
        Container pane = this.getContentPane();
        //JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JButton button;
        pane.setLayout(new GridBagLayout());
        //GridBagConstraints c = new GridBagConstraints();
        

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
        c.gridx = 9;
        c.gridy = 0;
        label.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        
        pane.add(label, c);
        
        // controls panel
        Image dimg = loadImage("images/forest.png").getScaledInstance(800, 200, Image.SCALE_SMOOTH);
        
        //JLabel imageLabel = new JLabel(new ImageIcon(dimg));
        
        imageLabel.setIcon(new ImageIcon(dimg));

        //c.ipady = 100;
        //c.ipadx = 100;
        c.weightx = 0.0;
        c.gridwidth = 10;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(imageLabel, c);
    
        
        label = new JLabel("Inventory");
        c.gridx = 0;
        c.gridy = 5;
        
        pane.add(label, c);  
        
        c.ipady = 0; // reset to default
        c.weighty = 1.0; // request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; // bottom of space
        c.insets = new Insets(0, 0, 10, 0); // top padding
        c.gridx = 0; // aligned with button 2
        c.gridwidth = 1; // 2 columns wide
        c.gridy = 6; // third row
        
        String[] selections = { "green", "red", "orange", "dark blue" };
        JList list = new JList(selections);
        
        pane.add(list, c);
        setupMenu();
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        showDialog();
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
        
        menuItem = new JMenuItem("Quit", KeyEvent.VK_T);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                //call the processGameAction hardcoding quit as command
                processGameAction("quit");
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
    }
}
