import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.File;

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
    private int x;
    
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    
    Game g1; 
    private Parser parser;
    private boolean finished;

    /**
     * Constructor for objects of class GameGUI
     */
    public GameGUI()
    {
        // initialise instance variables
        x = 0;
        
        this.setResizable(false);
        
        g1 = new Game();
        finished = false;
        parser = new Parser();
        
        setup();
        
        g1.play();
        
        g1.log("Game Ready");
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
    
    private void processGameAction(String commandText)
    {
        // Fetch the command from field1 ...
        Command command = parser.getCommandFromText(commandText);
        finished = g1.processCommand(command);
        if (finished == true)
        // Game is over
        {
            System.out.println("Thank you for playing.  Good bye.");
            System.exit(0);
        }
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
        
        if (shouldFill) {
          // natural height, maximum width
          c.fill = GridBagConstraints.HORIZONTAL;
        }
    
        button = new JButton("NW");
        if (shouldWeightX) {
          c.weightx = 0.5;
        }
        c.gridx = 0;
        c.gridy = 0;
        //pane.add(button, c);
    
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
    
        button = new JButton("NE");
        c.gridx = 2;
        c.gridy = 0;
        //pane.add(button, c);
        
        button = new JButton("W");
        if (shouldWeightX) {
          c.weightx = 0.5;
        }
        c.gridx = 0;
        c.gridy = 1;
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                processGameAction("go west");
            }
        });
        pane.add(button, c);
    
        button = new JButton("HOME");
        c.gridx = 1;
        c.gridy = 1;
        //pane.add(button, c);
    
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
        
        button = new JButton("SW");
        if (shouldWeightX) {
          c.weightx = 0.5;
        }
        c.gridx = 0;
        c.gridy = 2;
        //pane.add(button, c);
    
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
    
        button = new JButton("SE");
        c.gridx = 2;
        c.gridy = 2;
        //pane.add(button, c);
    
        button = new JButton("Long-Named Button 4");
        c.ipady = 40; // make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 3;
        //pane.add(button, c);
        
        // controls panel
        Image dimg = loadImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        
        JLabel imageLabel = new JLabel(new ImageIcon(dimg));
        //JLabel imageLabel = new JLabel("Image");
        c.ipady = 100;
        c.ipadx = 100;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(imageLabel, c);
    
        
        JLabel label = new JLabel("Inventory");
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
                // Fetch the command from field1 ...
                String commandText = "quit";
                Command command = parser.getCommandFromText(commandText);
                finished = g1.processCommand(command);
                if (finished == true)
                // Game is over
                {
                    System.out.println("Thank you for playing.  Good bye.");
                    System.exit(0);
                }

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
    private BufferedImage loadImage()
    {
        try {
            InputStream imageStream = this.getClass().getResourceAsStream("resources/n.png");
            BufferedImage image = ImageIO.read(imageStream);
            return image;
        }catch(Exception ex) {
            System.out.println(ex);
        }
        
        BufferedImage bufImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        
        return bufImage;
    }
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
