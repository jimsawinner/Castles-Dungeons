import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.File;

import java.io.InputStream;

/**
 * Write a description of class HelpGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class HelpGUI extends JFrame
{
    // instance variables - replace the example below with your own
    JLabel imageLabel = new JLabel();

    /**
     * Constructor for objects of class HelpGUI
     */
    public HelpGUI()
    {
        //2. Optional: What happens when the frame closes?
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //3. Create components and put them in the frame.
        //...create emptyLabel...
        //JLabel emptyLabel = new JLabel("Help");
        //this.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        
        //4. Size the frame.
        //this.pack();
        this.setResizable(false);
        setup();
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void setup()
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
        
        // controls panel
        Image dimg = loadImage("images/GameMap.png").getScaledInstance(750, 550, Image.SCALE_SMOOTH);
        
        //JLabel imageLabel = new JLabel(new ImageIcon(dimg));
        
        imageLabel.setIcon(new ImageIcon(dimg));

        //c.ipady = 100;
        //c.ipadx = 100;
        c.weightx = 0.0;
        c.gridwidth = 10;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(imageLabel, c);   
        
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
}
